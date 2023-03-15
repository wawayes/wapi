package com.wapi.model.service;

import com.wapi.model.entity.User;

public interface InnerUserService {

    /**
     * 获取调用者信息
     * @param accessKey 公钥
     * @return 返回User
     */
    User getInvokeUser(String accessKey);
}
