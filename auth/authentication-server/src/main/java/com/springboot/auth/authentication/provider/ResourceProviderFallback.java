package com.springboot.auth.authentication.provider;

import com.springboot.cloud.sysadmin.organization.entity.po.Resource;
import com.springboot.cloud.common.core.entity.vo.Result;

import java.util.HashSet;
import java.util.Set;

public class ResourceProviderFallback implements ResourceProvider {
    @Override
    public Result<Set<Resource>> resources() {
        return Result.success(new HashSet<Resource>());
    }

    @Override
    public Result<Set<Resource>> resources(String username) {
        return Result.success(new HashSet<Resource>());
    }
}
