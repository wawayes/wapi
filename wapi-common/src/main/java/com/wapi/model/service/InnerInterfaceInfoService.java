package com.wapi.model.service;

import com.wapi.model.entity.InterfaceInfo;


public interface InnerInterfaceInfoService {

    /**
     * 从数据库中查询模拟接口是否存在（请求路径、请求方法、请求参数）
     * @param path 路径
     * @param method 方法
     * @return 返回接口信息
     */
    InterfaceInfo getInterfaceInfo(String path, String method);
}
