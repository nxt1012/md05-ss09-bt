package com.ra.md05ss09bt.service;

import com.ra.md05ss09bt.payload.request.LoginRequest;
import com.ra.md05ss09bt.payload.request.SignupRequest;
import com.ra.md05ss09bt.payload.response.MessageResponse;
import com.ra.md05ss09bt.payload.response.UserInfoResponse;

public interface UserService {
    MessageResponse login(LoginRequest loginRequest);

    UserInfoResponse register(SignupRequest signupRequest);
}
