package com.fawry.authentication.repository;

import com.fawry.authentication.repository.entity.User;
import org.springframework.data.repository.ListCrudRepository;

public interface UserRepository extends ListCrudRepository<User, String> {

}