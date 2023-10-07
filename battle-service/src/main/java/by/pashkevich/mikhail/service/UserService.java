package by.pashkevich.mikhail.service;

import by.pashkevich.mikhail.entity.User;

public interface UserService {

    User getByUsername(String username);
}
