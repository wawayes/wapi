package com.wapi.project.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wapi.model.entity.UserInterfaceInfo;


/**
* @author mac
* @description 针对表【user_interface_info(用户调用接口关系表)】的数据库操作Service
* @createDate 2023-03-11 15:00:26
*/
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {

    void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add);

    boolean invokeCount(long interfaceInfoId, long userId);
}
