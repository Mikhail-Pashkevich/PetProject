package by.pashkevich.mikhail.service.impl;

import by.pashkevich.mikhail.exception.DuplicateException;
import by.pashkevich.mikhail.exception.NotFoundException;
import by.pashkevich.mikhail.model.entity.Role;
import by.pashkevich.mikhail.model.entity.User;
import by.pashkevich.mikhail.model.entity.enums.Rolename;
import by.pashkevich.mikhail.repository.RoleRepository;
import by.pashkevich.mikhail.repository.UserRepository;
import by.pashkevich.mikhail.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public User getByUsername(String username) {
        return userRepository.findByLoginWithRoles(username).orElseThrow(() ->
                new NotFoundException("Can't find user by current login: " + username)
        );
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByLogin(username);
    }

    @Override
    public void create(User user) {
        if (existsByUsername(user.getLogin())) {
            throw new DuplicateException("User with login = " + user.getLogin() + " already exist");
        }
        Role role = roleRepository.findByName(Rolename.USER).orElseThrow(() ->
                new NotFoundException("Can't find role with name = " + Rolename.USER)
        );
        Set<Role> roles = user.getRoles();
        roles = Objects.requireNonNullElseGet(roles, HashSet::new);
        roles.add(role);
        user.setRoles(roles);
        if (user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userRepository.save(user);
    }
}
