package com.wapi.project.service.impl;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wapi.model.entity.UserInterfaceInfo;
import com.wapi.project.common.ErrorCode;
import com.wapi.project.exception.BusinessException;
import com.wapi.project.mapper.UserInterfaceInfoMapper;
import com.wapi.project.service.UserInterfaceInfoService;
import org.springframework.stereotype.Service;

/**
* @author mac
* @description 针对表【user_interface_info(用户调用接口关系表)】的数据库操作Service实现
* @createDate 2023-03-11 15:00:26
*/
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
    implements UserInterfaceInfoService {

    @Override
    public void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add) {
        if (userInterfaceInfo == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (add){
            if (userInterfaceInfo.getUserId() <= 0 || userInterfaceInfo.getInterfaceInfoId() <= 0) {
                throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "接口或者用户不存在");
            }
            if (userInterfaceInfo.getLeftNum() <= 0){
                throw new BusinessException(ErrorCode.OPERATION_ERROR, "接口调用次数用尽");
            }
        }
    }

    @Override
    // todo 添加锁，事务
    public boolean invokeCount(long interfaceInfoId, long userId) {
        if (interfaceInfoId <= 0 || userId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口或用户不存在");
        }
        UpdateWrapper<UserInterfaceInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("interfaceInfoId", interfaceInfoId);
        updateWrapper.eq("userId", userId);
        updateWrapper.setSql("leftNum = leftNum - 1,totalNum = totalNum + 1");
        return this.update(updateWrapper);
    }
}




