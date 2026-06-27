package com.yupi.demo.service;

import com.yupi.demo.common.ApiResult;
import com.yupi.demo.model.DTO.UserLoginDTO;
import com.yupi.demo.model.DTO.UserRegisterDTO;

public interface UserService {
    ApiResult<String> register(UserRegisterDTO dto);
    ApiResult<String> login(UserLoginDTO dto);
}