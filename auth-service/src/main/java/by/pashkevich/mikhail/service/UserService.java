package by.pashkevich.mikhail.service;

import by.pashkevich.mikhail.model.entity.User;

public interface UserService {

    User getByLogin(String login);
}
