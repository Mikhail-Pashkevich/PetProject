package by.pashkevich.mikhail.service;

import by.pashkevich.mikhail.model.User;

public interface UserSecurityService {
    User encodePassword(User user);

    User getAuthenticatedUser();
}
