package com.yupi.demo.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.yupi.demo.common.ApiResult;
import com.yupi.demo.model.DTO.UserLoginDTO;
import com.yupi.demo.model.DTO.UserRegisterDTO;
import com.yupi.demo.service.UserService;
import com.yupi.demo.service.impl.UserServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {
    // 把
//    private final UserServiceImpl userServiceImpl;controller不要注入实体类，注入接口
    // 改成
    private final UserService userService;

    public UserController(UserServiceImpl userServiceImpl) {
        this.userService = userServiceImpl;
    }

    //测试注册  POST http://localhost:8081/api/user/doRegister
    @PostMapping("doRegister")
    public ApiResult<String> DoRegister(@RequestBody UserRegisterDTO dto){
        return userService.register(dto);
    }


    // 测试登录，浏览器访问： POST http://localhost:8081/api/user/doLogin
    @PostMapping("doLogin")
    public ApiResult<String> doLogin(@RequestBody UserLoginDTO dto) {
       return userService.login(dto);
    }

    // 查询登录状态，浏览器访问： GET http://localhost:8081/api/user/isLogin
    @GetMapping("isLogin")
    public ApiResult<Boolean> isLogin() {
        Boolean loginflag =StpUtil.isLogin();
        return  ApiResult.success(loginflag);
    }
    //注销登录  http://localhost:8081/api/user/exit
    @PostMapping("exit")
    public ApiResult<String> exit() {
        StpUtil.logout();
        return ApiResult.success("退出成功");
    }
}
