package by.pashkevich.mikhail.service.impl;

import by.pashkevich.mikhail.model.User;
import by.pashkevich.mikhail.service.UserSecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSecurityServiceImpl implements UserSecurityService {
    private final PasswordEncoder passwordEncoder;


    @Override
    public User encodePassword(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());

        user.setPassword(encodedPassword);

        return user;
    }
}
