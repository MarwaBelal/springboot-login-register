package com.fawry.authentication.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
    private Long id;
    private String username;
    private String email;

    @Setter
    private String password;
}
