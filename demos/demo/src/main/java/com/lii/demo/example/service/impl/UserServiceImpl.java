package com.lii.demo.example.service.impl;

import com.lii.demo.example.dao.UserMapper;
import com.lii.demo.example.entity.po.User;
import com.lii.demo.example.service.UserService;
import com.springboot.cloud.common.core.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author RubyJing
 * @Date 2019-09-04
 */
@RefreshScope//刷新配置
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int add(User user) {
        return userMapper.insert(user);
    }

    @Override
    @CacheEvict(value = "user", key = "#root.targetClass+'-'+#id")//清理缓存
    public int delete(long id) {
        return userMapper.deleteById(id);
    }

    @Override
    @CacheEvict(value = "user", key = "#root.targetClass+'-'+#user.id")
    public int update(User user) {
        return userMapper.updateById(user);
    }

    @Override
    @Cacheable(value = "user", key = "#root.targetClass+'-'+Users")
    public List<User> query(User user) {
        return userMapper.selectUser(user);
    }

    @Override
    @Cacheable(value = "user", key = "#root.targetClass+'-'+#id")//缓存
    public User search(long id) {
        return userMapper.selectById(id);
    }

}
