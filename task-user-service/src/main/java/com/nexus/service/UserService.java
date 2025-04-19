package com.nexus.service;

import com.nexus.model.User;

import java.util.List;

public interface UserService {
    public User getUserProfile(String jwt);

    public List<User> getAllUsers();
}
