package by.pashkevich.mikhail.service;

import by.pashkevich.mikhail.model.entity.User;

public interface UserService {

    User getByUsername(String username);

    boolean existsByUsername(String username);

    void create(User user);
}
