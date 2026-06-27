package com.yupi.demo.service.impl;

import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yupi.demo.common.ApiResult;
import com.yupi.demo.mapper.UserMapper;
import com.yupi.demo.model.DTO.UserLoginDTO;
import com.yupi.demo.model.DTO.UserRegisterDTO;
import com.yupi.demo.model.entity.User;
import com.yupi.demo.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public ApiResult<String> register(UserRegisterDTO dto) {
        // 1. 检查用户名是否已存在
        String username=dto.getUsername();//去拿用户名
        LambdaQueryWrapper<User>wrapper=new LambdaQueryWrapper<>();//创建sql含义的纸条
        wrapper.eq(User::getUsername,username);//往纸条上写内容，wrapper作为内容
        User existUser=userMapper.selectOne(wrapper);//拿着内容去数据库比对，成立则注册失败，为空则继续注册。
        if (existUser!=null){
           return  ApiResult. badRequest("用户名已存在");
        }
        // 2. 密码用 BCrypt 加密
        User user=new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        //获取前端DTO传过来的明文密码
        String plainPassword = dto.getPassword();//BCrypt加密，自动生成盐值
        String encryptPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt());//设置加密后的密码到User实体
        user.setPassword(encryptPassword);
        // 3. 插入数据库
        userMapper.insert(user);
        // 4. 返回注册成功
        return ApiResult.success("注册成功");
    }

    @Override
    public ApiResult<String> login(UserLoginDTO dto) {
        // 1. 查用户
        String username=dto.getUsername();//去拿用户名
        LambdaQueryWrapper<User>wrapper=new LambdaQueryWrapper<>();//创建sql含义的纸条
        wrapper.eq(User::getUsername,username);//往纸条上写内容，wrapper作为内容
        User existUser = userMapper.selectOne(wrapper);//用找到的用户里的值直接赋值给创建的，临时用户对象
        // 2. 校验密码（BCrypt）
        String inputPwd= dto.getPassword();
        if (existUser==null){
            return ApiResult.badRequest("用户名不存在");
        }
        String dbPwd=existUser.getPassword();
        boolean isRight=BCrypt.checkpw(inputPwd,dbPwd);
        if(isRight!=true){
            return ApiResult.badRequest("登陆失败，密码错误");
        }
        // 3. 生成 Token（先简单返回一个固定串，后面再加 Sa-Token）
        StpUtil.login(existUser.getId());
        String token = StpUtil.getTokenValue();
        // 4. 返回 Token
        return ApiResult.success(token);
    }
}