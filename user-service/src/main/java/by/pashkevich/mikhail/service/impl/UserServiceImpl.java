package by.pashkevich.mikhail.service.impl;

import by.pashkevich.mikhail.exception.DuplicateException;
import by.pashkevich.mikhail.exception.NotFoundException;
import by.pashkevich.mikhail.model.entity.Role;
import by.pashkevich.mikhail.model.entity.User;
import by.pashkevich.mikhail.model.entity.enums.Rolename;
import by.pashkevich.mikhail.repository.RoleRepository;
import by.pashkevich.mikhail.repository.UserRepository;
import by.pashkevich.mikhail.service.JwtService;
import by.pashkevich.mikhail.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public String getJwt(User user) {
        User dbUser = getByLogin(user.getLogin());
        if (passwordEncoder.matches(user.getPassword(), dbUser.getPassword())) {
            return jwtService.createJwt(user.getLogin());
        }
        return null;
    }

    @Override
    public User getByJwt(String jwt) {
        String username = jwtService.getUsername(jwt);
        return getByLogin(username);
    }

    @Override
    public void create(User user) {
        if (userRepository.existsByLogin(user.getLogin())) {
            throw new DuplicateException("User with login = " + user.getLogin() + " already exist");
        }
        Role role = roleRepository.findByName(Rolename.USER).orElseThrow(() ->
                new NotFoundException("Can't find role with name = " + Rolename.USER)
        );
        user.getRoles().add(role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    private User getByLogin(String login) {
        return userRepository.findByLoginWithRoles(login).orElseThrow(() ->
                new NotFoundException("Can't find user by current email: " + login)
        );
    }
}
