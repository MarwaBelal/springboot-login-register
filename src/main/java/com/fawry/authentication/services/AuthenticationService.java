package com.fawry.authentication.services;

import com.fawry.authentication.common.model.RequestLoginModel;
import com.fawry.authentication.common.model.RequestRegisterModel;
import com.fawry.authentication.common.model.ResponseAuthenticationModel;

import java.util.List;

public interface AuthenticationService {

    ResponseAuthenticationModel registerUser(RequestRegisterModel requestRegisterModel);


    ResponseAuthenticationModel loginUser(RequestLoginModel requestLoginModel);

    List<ResponseAuthenticationModel> listUsers();
}
