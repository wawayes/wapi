package com.wapi.project.model.dto.interfaceinfo;

import com.wapi.project.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 查询请求
 *
 * @author walker
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InterfaceInfoQueryRequest extends PageRequest implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 接口名字
     */
    private String name;

    /**
     * 地址
     */
    private String url;

    /**
     * 描述
     */
    private String description;

    /**
     * 状态 0 - 关闭 1 - 开启
     */
    private Integer status;

    /**
     * 请求参数
     */
    private String requestParams;

    /**
     * 请求头
     */
    private String requestHeader;

    /**
     * 响应头
     */
    private String responseHeader;

    /**
     * 接口类型
     */
    private String method;

    /**
     * 创建人
     */
    private Long userId;

}