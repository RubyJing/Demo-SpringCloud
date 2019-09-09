package com.lii.demo.example.rest;

import com.lii.demo.example.entity.po.User;
import com.lii.demo.example.service.UserService;
import com.springboot.cloud.common.core.entity.vo.Result;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author RubyJing
 * @Date 2019/9/4 9:22
 * @Version 1.0
 */
@RequestMapping("/user")
@RestController
@Api("user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "新增用户", notes = "新增一个用户")
    @ApiImplicitParam(name = "user", value = "新增用户的用户信息", required = true, dataType = "User")
    @RequestMapping(method = RequestMethod.POST)
    public Result saveUser(@RequestBody User user) {
        log.info("name:{}");
        return Result.success(userService.add(user));
    }

    @ApiOperation(value = "删除用户", notes = "删除了一个用户")
    @ApiImplicitParam(paramType = "path", name = "id", value = "用户ID", required = true, dataType = "long")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result deleteUser(@PathVariable long id) {
        log.info("id:{}");
        return Result.success(userService.delete(id));
    }

    @ApiOperation(value = "查询用户", notes = "根据条件查询用户信息，简单查询")
    @ApiImplicitParam(paramType = "query", name = "name", value = "用户名称", required = true, dataType = "string")
    @ApiResponses(
            @ApiResponse(code = 200, message = "处理成功", response = Result.class)
    )
    @GetMapping
    public Result query(@RequestParam String name) {
        log.info("query with name:{}", name);
        User user = new User();
        user.setUserName(name);
        List<User> userList = userService.query(user);
        return Result.success(userList);
    }

    @ApiOperation(value = "获取用户 ", notes = "获取指定用户信息")
    @ApiImplicitParam(paramType = "path", name = "user", value = "User", required = true, dataType = "long")
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public Result searchUser(@PathVariable long id) {
        log.info("get with id:{}",id);
        User user = userService.search(id);
        return Result.success(user);
    }
}
