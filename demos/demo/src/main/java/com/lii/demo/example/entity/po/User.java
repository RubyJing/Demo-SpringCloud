package com.lii.demo.example.entity.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author RubyJing
 * @Date 2019-09-04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("test_user")
public class User {
    private long id;
    @NotBlank(message = "用户名不能为空")
    private String userName;
    private String passWord;
    private String phone;
    private String status;
    private String uuid;
}
