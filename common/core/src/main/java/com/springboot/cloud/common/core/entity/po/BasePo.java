package com.springboot.cloud.common.core.entity.po;

import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

@Data
public class BasePo implements Serializable {
    public final static String DEFAULT_USERNAME = "system";
    private String id = UUID.randomUUID().toString().replaceAll("-","");
    private String createdBy = DEFAULT_USERNAME;
    private String updatedBy = DEFAULT_USERNAME;
    private Date createdTime = Date.from(ZonedDateTime.now().toInstant());
    private Date updatedTime = Date.from(ZonedDateTime.now().toInstant());
}
