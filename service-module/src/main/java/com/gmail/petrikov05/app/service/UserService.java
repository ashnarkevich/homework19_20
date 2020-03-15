package com.gmail.petrikov05.app.service;

import com.gmail.petrikov05.app.repository.model.User;

public interface UserService {

    User findByUserName(String username);

}
