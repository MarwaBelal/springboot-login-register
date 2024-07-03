package com.fawry.authentication.services;

import com.fawry.authentication.model.RequestLoginModel;
import com.fawry.authentication.model.User;

public interface AuthenticationService {

    void registerUser(User user) throws Exception;

    User loginUser(RequestLoginModel user) throws Exception;

}
