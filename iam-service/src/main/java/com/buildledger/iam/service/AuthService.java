package com.buildledger.iam.service;

import com.buildledger.iam.dto.request.LoginRequestDTO;
import com.buildledger.iam.dto.response.LoginResponseDTO;

public interface AuthService {
    LoginResponseDTO login(LoginRequestDTO request);
}

