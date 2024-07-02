package com.fawry.authentication.services;

import com.fawry.authentication.model.User;

public interface AuthenticationService {

    void registerUser(User user) throws Exception;

    String loginUser(User user);

}
