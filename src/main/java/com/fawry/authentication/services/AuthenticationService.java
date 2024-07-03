package com.fawry.authentication.services;

import com.fawry.authentication.model.RequestLoginModel;
import com.fawry.authentication.model.ResponseAuthenticationModel;
import com.fawry.authentication.model.User;

public interface AuthenticationService {

    ResponseAuthenticationModel registerUser(User user) throws Exception;


    ResponseAuthenticationModel loginUser(RequestLoginModel user) throws Exception;

}
