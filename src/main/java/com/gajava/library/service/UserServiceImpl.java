package com.gajava.library.service;

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
    private final PasswordEncoder  passwordEncoder = new BCryptPasswordEncoder();

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
        return userRepository.save(user);
    }

    @Override
    public User findByLogin(final String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public User findByLoginAndPassword(final String login, final String password) {
        Optional<User> optionalUser = Optional.of(findByLogin(login));
        if (optionalUser.isPresent()) {
            if (passwordEncoder.matches(password, optionalUser.get().getPassword())) {
                return optionalUser.get();
            }
        }
        return null;
    }
}
