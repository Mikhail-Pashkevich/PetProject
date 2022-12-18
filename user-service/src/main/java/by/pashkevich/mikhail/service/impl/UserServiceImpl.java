package by.pashkevich.mikhail.service.impl;

import by.pashkevich.mikhail.exception.DuplicateException;
import by.pashkevich.mikhail.exception.NotFoundException;
import by.pashkevich.mikhail.model.Role;
import by.pashkevich.mikhail.model.User;
import by.pashkevich.mikhail.model.enums.Rolename;
import by.pashkevich.mikhail.repository.RoleRepository;
import by.pashkevich.mikhail.repository.UserRepository;
import by.pashkevich.mikhail.service.UserSecurityService;
import by.pashkevich.mikhail.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final UserSecurityService userSecurityService;


    @Override
    public void create(User user) {
        if (userRepository.existsByLogin(user.getLogin())) {
            throw new DuplicateException("User with login = %s already exist", user.getLogin());
        }

        Role role = roleRepository.findByName(Rolename.USER).orElseThrow(() ->
                new NotFoundException("Can't find role with name = ", Rolename.USER.name())
        );

        if (user.getRoles() == null) {
            user.setRoles(new HashSet<>());
        }
        user.getRoles().add(role);

        user = userSecurityService.encodePassword(user);

        userRepository.save(user);
    }
}
