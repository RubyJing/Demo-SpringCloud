package com.springboot.cloud.gateway.admin.entity.po;

import com.springboot.cloud.common.core.entity.po.BasePo;
import lombok.*;

/**
 * 网关路由表(对应数据库)
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GatewayRoute extends BasePo {
    /** uri路径 **/
    private String uri;
    /** 路由id **/
    private String routeId;
    /** 判定器 **/
    private String predicates;
    /** 过滤器 **/
    private String filters;
    /** 形容 **/
    private String description;
    /** 排序 **/
    private Integer orders = 0;
    /** 状态：Y-有效，N-无效 **/
    private String status = "Y";
}
