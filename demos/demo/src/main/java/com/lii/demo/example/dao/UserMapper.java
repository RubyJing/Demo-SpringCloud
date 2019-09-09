package com.lii.demo.example.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lii.demo.example.entity.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author RubyJing
 * @Date 2019-09-04
 */
@Mapper
@Component
public interface UserMapper extends BaseMapper<User> {
    /**
     * 通过用户条件查找用户信息
     *
     * @param user
     * @return List<User>
     */
    List<User> selectUser(User user);
}
