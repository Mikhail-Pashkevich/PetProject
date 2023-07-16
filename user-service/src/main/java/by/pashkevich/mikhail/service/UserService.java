package by.pashkevich.mikhail.service;

import by.pashkevich.mikhail.model.entity.User;

public interface UserService {
    User getByJwt(String username);

    void create(User user);
}
