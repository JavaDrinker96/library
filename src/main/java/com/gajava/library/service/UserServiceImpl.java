package com.gajava.library.service;

import com.gajava.library.exception.*;
import com.gajava.library.model.Role;
import com.gajava.library.model.User;
import com.gajava.library.repository.RoleRepository;
import com.gajava.library.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserServiceImpl(final UserRepository userRepository,
                           final RoleRepository roleRepository) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User saveUser(final User user) {
        if (Objects.isNull(user)) {
            throw new NullParameterException();
        }
        final Role userRole = Optional.ofNullable(roleRepository.findByName("ROLE_USER"))
                .orElseThrow(() -> new NoEntityException(Role.class.getTypeName()));

        user.setRole(userRole);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return Optional.ofNullable(userRepository.save(user))
                .orElseThrow(() -> new SaveEntityException(User.class.getTypeName()));
    }

    @Override
    public User findByLogin(final String login) {
        if (Objects.isNull(login)) {
            throw new NullParameterException();
        }
        return Optional.ofNullable(userRepository.findByLogin(login))
                .orElseThrow(AuthenticationLoginException::new);
    }

    @Override
    public User findByLoginAndPassword(final String login, final String password) {
        if (Objects.isNull(login) || Objects.isNull(password)) {
            throw new NullParameterException();
        }
        final User user = findByLogin(login);
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new AuthenticationPasswordException();
        }
        return user;
    }

}
