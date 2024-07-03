package com.fawry.authentication.services;

import com.fawry.authentication.model.User;

import java.io.IOException;
import java.util.Optional;

public interface FileReaderService {

  Optional<User> getUserFromFile(String email) throws IOException;
}
