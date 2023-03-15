package com.wapi.project.model.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 帖子性别枚举
 *
 * @author walker
 */

public enum InterfaceInfoStatusEnum {

    OFFLINE("关闭", 0),
    ONLINE("开启", 1);

    private final String text;

    private final int value;

    InterfaceInfoStatusEnum(String text, int value){
        this.text = text;
        this.value = value;
    }

    public String getText(){
        return text;
    }

    public int getValue(){
        return value;
    }
}
