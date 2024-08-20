package com.jonssonyan.controller;

import com.jonssonyan.entity.User;
import com.jonssonyan.entity.dto.UserDto;
import com.jonssonyan.entity.vo.Result;
import com.jonssonyan.security.constant.SystemConstant;
import com.jonssonyan.security.util.JwtUtil;
import com.jonssonyan.security.util.SecurityUtil;
import com.jonssonyan.service.UserService;
import com.jonssonyan.util.ValidatorUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@Slf4j
@Api(tags = "登录和注册")
public class AuthController {
    @Autowired
    private UserService userService;



    @ApiOperation(value = "登录")
    @PostMapping("/login")
    public Result login(@RequestBody UserDto userDto) {
        ValidatorUtil.validateEntity(userDto);
        if (!SecurityUtils.getSubject().isAuthenticated()) {
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(userDto.getUsername(), userDto.getPassword(), true);
            try {
                // shiro验证用户名密码
                SecurityUtils.getSubject().login(usernamePasswordToken);
                // 生成token
                String token = JwtUtil.createToken(userDto.getUsername(), false);
                // 将用户户名和token返回
                HashMap<String, String> map = new HashMap<>();
                map.put("username", userDto.getUsername());
                map.put("Authorization", token);
                map.put("role_id", SecurityUtil.getCurrentUser().getRoleId().toString());
                return Result.success(map);
            } catch (UnknownAccountException e) {
                return Result.fail("帐号不存在");
            } catch (IncorrectCredentialsException e) {
                return Result.fail("登录密码错误");
            } catch (DisabledAccountException e) {
                return Result.fail("用户已被禁用");
            } catch (UnauthorizedException e) {
                return Result.fail("没有权限");
            } catch (Exception e) {
                log.error("登录失败 msg:{},err:{}", e.getMessage(), e);
                return Result.fail("登录失败");
            }
        }
        return Result.fail("你已经登录了");
    }

    @ApiOperation(value = "注册时校验用户名是否存在")
    @GetMapping("/countUsername")
    public Result countUsername(String username) {
        return Result.success(userService.countByUsername(username));
    }
}
