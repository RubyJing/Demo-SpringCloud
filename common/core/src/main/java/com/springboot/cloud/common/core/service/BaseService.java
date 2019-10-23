package com.springboot.cloud.common.core.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;

import java.util.List;

/**
 * @Author RubyJing
 * @Date 2019/9/4 11:01
 * @Version 1.0
 */
public interface BaseService<T> {
    int add(T entity);

    int update(T entity);

    List<T> query(T entity);

    T search(long id);
}
