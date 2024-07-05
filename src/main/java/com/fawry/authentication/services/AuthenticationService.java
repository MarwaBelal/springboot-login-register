package com.fawry.authentication.services;

import com.fawry.authentication.common.model.RequestLoginModel;
import com.fawry.authentication.common.model.ResponseAuthenticationModel;
import com.fawry.authentication.common.model.UserModel;

import java.util.List;

public interface AuthenticationService {

    void registerUser(UserModel userModel) throws Exception;


    ResponseAuthenticationModel loginUser(RequestLoginModel user) throws Exception;

    List<UserModel> listUsers();
}
