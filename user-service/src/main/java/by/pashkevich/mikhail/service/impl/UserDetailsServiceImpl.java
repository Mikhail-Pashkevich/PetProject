package by.pashkevich.mikhail.service.impl;

import by.pashkevich.mikhail.exception.NotFoundException;
import by.pashkevich.mikhail.model.User;
import by.pashkevich.mikhail.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws NotFoundException {
        User user = userRepository.findByLoginWithRoles(username).orElseThrow(() ->
                new NotFoundException("Can't find user by current login: " + username)
        );

        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getLogin())
                .password(user.getPassword())
                .roles(user.getRoles()
                        .stream()
                        .map(role -> role.getName().name())
                        .toArray(String[]::new)
                )
                .build();
    }
}