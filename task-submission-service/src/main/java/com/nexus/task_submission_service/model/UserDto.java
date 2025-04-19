package com.nexus.task_submission_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;

    private String password;

    private String email;

    private String role;

    private String fullName;

}
