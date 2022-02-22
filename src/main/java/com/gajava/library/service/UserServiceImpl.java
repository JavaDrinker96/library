package com.gajava.library.service;

import com.gajava.library.exception.AuthenticationLoginException;
import com.gajava.library.exception.AuthenticationPasswordException;
import com.gajava.library.exception.SaveEntityException;
import com.gajava.library.model.Role;
import com.gajava.library.model.User;
import com.gajava.library.repository.RoleRepository;
import com.gajava.library.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        final Role userRole = roleRepository.findByName("ROLE_USER");
        user.setRole(userRole);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return Optional.ofNullable(userRepository.save(user))
                .orElseThrow(() -> new SaveEntityException(User.class.getTypeName()));
    }

    @Override
    public User findByLogin(final String login) {
        return Optional.ofNullable(userRepository.findByLogin(login))
                .orElseThrow(AuthenticationLoginException::new);
    }

    @Override
    public User findByLoginAndPassword(final String login, final String password) {
        final User user = findByLogin(login);
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new AuthenticationPasswordException();
        }
        return user;
    }

}
