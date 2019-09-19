package com.springboot.cloud.gateway.admin.entity.po;

import lombok.*;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *  Predicate判定器定义信息
 *  例子：
 *   "predicates": [{
 *     	"name": "Path",
 *         "args": {
 *             "pattern": "/xx"
 *         }
 *     }]
 */
@EqualsAndHashCode
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PredicateDefinition {
    private String name;
    private Map<String, String> args = new LinkedHashMap<>();
}
