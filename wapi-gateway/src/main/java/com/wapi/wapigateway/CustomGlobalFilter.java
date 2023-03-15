package com.wapi.wapigateway;

import com.wapi.model.entity.InterfaceInfo;
import com.wapi.model.entity.User;
import com.wapi.model.service.InnerInterfaceInfoService;
import com.wapi.model.service.InnerUserInterfaceInfoService;
import com.wapi.model.service.InnerUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.hibernate.validator.internal.util.StringHelper;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.wapi.wapiclientsdk.util.CommonUtil.checkTimeStamp;
import static com.wapi.wapiclientsdk.util.CommonUtil.getSign;

@Component
@Slf4j
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    @DubboReference
    private InnerInterfaceInfoService innerInterfaceInfoService;

    @DubboReference
    private InnerUserService innerUserService;

    @DubboReference
    private InnerUserInterfaceInfoService innerUserInterfaceInfoService;

    public static final List<String> IP_WHITE_LIST = Arrays.asList("127.0.0.1", "localhost");

    public static final String PREFIX_HOST = "http://localhost:8123";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = PREFIX_HOST + request.getPath().value();
        String method = request.getMethod().name();
        log.info("请求id: " + request.getId());
        log.info("请求路径" + path);
        String sourceAddress = request.getRemoteAddress().getHostString();
        log.info("请求来源地址" + sourceAddress);
        log.info("请求参数" + request.getQueryParams());
        log.info("请求方法" + request.getMethod());
        log.info("custom global filter");
        // 黑白名单
        ServerHttpResponse response = exchange.getResponse();
//        if (!IP_WHITE_LIST.contains(sourceAddress)) {
//            response.setStatusCode(HttpStatus.FORBIDDEN);
//            //完成的意思
//            return response.setComplete();
//        }
        // 鉴权
        HttpHeaders headers = request.getHeaders();
        String accessKey = headers.getFirst("accessKey");
        String body = headers.getFirst("body");
        String timestamp = headers.getFirst("timestamp");
        String sign = headers.getFirst("sign");
        String nonce = headers.getFirst("nonce");
        // todo 实际情况应该是去数据库中查是否已分配给用户
        User invokeUser = null;
        try{
            invokeUser = innerUserService.getInvokeUser(accessKey);
        } catch (Exception e){
            log.error("getInvokeUser error", e);
        }
        if (invokeUser == null) {
            return handleNoAuth(response);
        }

        //判断accessKey正确性，通常会调数据库，这里为了方便
        String userAccessKey = invokeUser.getAccessKey();
        if (!accessKey.equals(userAccessKey)){
            return handleNoAuth(response);
        }
        // 检验nonce是否有误
        if (nonce.length() != 4){
            return handleNoAuth(response);
        }
        // 检验时间戳与当前时间相比如果超过五分钟则抛出异常
        if (!checkTimeStamp(timestamp)) {
            return handleNoAuth(response);
        }
        String secretKey = invokeUser.getSecretKey();
        // 检验sign
        String serverSign = getSign(body, secretKey);
        if (!sign.equals(serverSign)){
            return handleNoAuth(response);
        }
        // 请求的接口是否存在
        // todo 如果该操作较为复杂并且gateway项目并未引入Mybatis等操作数据库的类库，可以远程调用
        InterfaceInfo interfaceInfo = null;
        try {
            interfaceInfo = innerInterfaceInfoService.getInterfaceInfo(path, method);
        } catch (Exception e){
            log.error("getInterfaceInfo error", e);
        }
        if (interfaceInfo == null){
            return handleNoAuth(response);
        }
        // 判断是否还有调用次数
        long interfaceInfoId = interfaceInfo.getId();
        long userId = invokeUser.getId();
        long leftNum = innerUserInterfaceInfoService.getLeftNum(interfaceInfoId, userId);
        if (leftNum <= 0){
            log.error("leftNum is not enough.");
            return handleInvokeError(response);
        }
//        // 请求转发，调用模拟接口
//        Mono<Void> filter = chain.filter(exchange);
//
//        // 响应日志
//        log.info("响应:" + response.getStatusCode());
//        // todo 调用成功 接口调用次数 + 1 invokeCount
//        if (response.getStatusCode() == HttpStatus.OK){
//
//        }else {
//            // 调用失败：返回一个规范的错误码
//            return handleNoAuth(response);
//        }

//        return filter;
        return handleResponse(exchange, chain, interfaceInfo.getId(), invokeUser.getId());
    }

    @Override
    public int getOrder() {
        return -1;
    }

    public static Mono<Void> handleNoAuth(ServerHttpResponse response){
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }

    /**
     * 处理响应
     *
     * @param exchange
     * @param chain
     * @return
     */
    public Mono<Void> handleResponse(ServerWebExchange exchange, GatewayFilterChain chain, long interfaceInfoId, long userId) {
        try {
            ServerHttpResponse originalResponse = exchange.getResponse();
            // 缓存数据的工厂
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();
            // 拿到响应码
            HttpStatus statusCode = originalResponse.getStatusCode();
            if (statusCode == HttpStatus.OK) {
                // 装饰，增强能力
                ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
                    // 等调用完转发的接口后才会执行
                    @Override
                    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                        log.info("body instanceof Flux: {}", (body instanceof Flux));
                        if (body instanceof Flux) {
                            Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                            // 往返回值里写数据
                            // 拼接字符串
                            return super.writeWith(
                                    fluxBody.map(dataBuffer -> {
                                        // 7. 调用成功，接口调用次数 + 1 invokeCount
                                        try {
                                            innerUserInterfaceInfoService.invokeCount(interfaceInfoId, userId);
                                        } catch (Exception e) {
                                            log.error("invokeCount error", e);
                                        }
                                        byte[] content = new byte[dataBuffer.readableByteCount()];
                                        dataBuffer.read(content);
                                        DataBufferUtils.release(dataBuffer);//释放掉内存
                                        // 构建日志
                                        StringBuilder sb2 = new StringBuilder(200);
                                        List<Object> rspArgs = new ArrayList<>();
                                        rspArgs.add(originalResponse.getStatusCode());
                                        String data = new String(content, StandardCharsets.UTF_8); //data
                                        sb2.append(data);
                                        // 打印日志
                                        log.info("响应结果：" + data);
                                        return bufferFactory.wrap(content);
                                    }));
                        } else {
                            // 8. 调用失败，返回一个规范的错误码
                            log.error("<--- {} 响应code异常", getStatusCode());
                        }
                        return super.writeWith(body);
                    }
                };
                // 设置 response 对象为装饰过的
                return chain.filter(exchange.mutate().response(decoratedResponse).build());
            }
            return chain.filter(exchange); // 降级处理返回数据
        } catch (Exception e) {
            log.error("网关处理响应异常" + e);
            return chain.filter(exchange);
        }
    }


    public Mono<Void> handleInvokeError(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return response.setComplete();
    }

}

