package by.pashkevich.mikhail.service;

import by.pashkevich.mikhail.model.User;

public interface UserService {
    void create(User user);

    User getAuthenticatedUser();
}
