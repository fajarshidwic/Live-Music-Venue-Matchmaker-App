package com.example.livemusicvenuematchmakerapp.controller;

import com.example.livemusicvenuematchmakerapp.dao.UserDAO;
import com.example.livemusicvenuematchmakerapp.model.User;

public class LoginController {
    public User login(String username, String password) {
        return UserDAO.getUserByUsernameAndPassword(username, password);
    }
}
