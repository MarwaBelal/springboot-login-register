package com.fawry.authentication.services;

import com.fawry.authentication.model.User;

public interface FileWriterService {
    void writeUserToFile(User user);
}
