package by.pashkevich.mikhail.service;

import by.pashkevich.mikhail.model.entity.User;

public interface UserService {
    String getJwt(User user);

    User getByJwt(String username);

    void create(User user);
}
