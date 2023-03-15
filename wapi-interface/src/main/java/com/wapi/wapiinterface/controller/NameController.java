package com.wapi.wapiinterface.controller;



import com.wapi.wapiclientsdk.model.User;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.wapi.wapiclientsdk.util.CommonUtil.checkTimeStamp;
import static com.wapi.wapiclientsdk.util.CommonUtil.getSign;



@RestController
@RequestMapping("/name")
public class NameController {

    @GetMapping("/get")
    public String getNameByGet(String username) {
        System.out.println(username);
        return "Get 你的名字是"+username;
    }

    @PostMapping("/post")
    public String getNameByPost(@RequestParam String username) {
        return "Post 你的名字是"+username;
    }



    @PostMapping("/user")
    public String getUsernameByPost(@RequestBody User user, HttpServletRequest request) {
//        String accessKey = request.getHeader("accessKey");
//        String body = request.getHeader("body");
//        String timestamp = request.getHeader("timestamp");
//        String sign = request.getHeader("sign");
//        //判断accessKey正确性，通常会调数据库，这里为了方便
//        if (!accessKey.equals("f8817805c21c895690457c6978748e47")){
//            throw new RuntimeException("无权限");
//        }
//        // 检验nonce是否有误
//
//        // 检验时间戳与当前时间相比如果超过五分钟则抛出异常
//        if (!checkTimeStamp(timestamp)) {
//            throw new RuntimeException("The time difference is more than 5 minutes.");
//        }
//
//        // 检验sign
//        String serverSign = getSign(body, "f64e19276261f043ff0fac7357285ed1");
//        if (!sign.equals(serverSign)){
//            throw new RuntimeException("无权限");
//        }
        return "Post 用户名字是"+user.getUsername();
    }
}
