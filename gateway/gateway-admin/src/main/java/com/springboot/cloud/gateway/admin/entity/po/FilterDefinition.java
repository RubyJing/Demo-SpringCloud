package com.springboot.cloud.gateway.admin.entity.po;

import lombok.*;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 *Filter过滤器定义信息
 *
 * 格式例子（限路由）:
 * "filters":[{
 *         "name": "RequestRateLimiter",
 *         "args": {
 *             "redis-rate-limiter.replenishRate": "10",
 *             "redis-rate-limiter.burstCapacity": "20"
 *         }
 *     }]
 */
@EqualsAndHashCode
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilterDefinition {
    private String name;
    private Map<String, String> args = new LinkedHashMap<>();
}
