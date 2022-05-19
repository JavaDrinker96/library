package com.gajava.library.service;

import com.gajava.library.exception.AuthenticationLoginException;
import com.gajava.library.exception.AuthenticationPasswordException;
import com.gajava.library.exception.NoEntityException;
import com.gajava.library.exception.SaveEntityException;
import com.gajava.library.model.Role;
import com.gajava.library.model.User;
import com.gajava.library.repository.RoleRepository;
import com.gajava.library.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private PasswordEncoder passwordEncoder;
    private User user;
    private Role role;
    private String login;

    @Before
    public  void setUp() {
        passwordEncoder = new BCryptPasswordEncoder();
        login = "login";
        role = Role.builder()
                .id(1L)
                .name("ROLE_USER")
                .build();

        user = User.builder()
                .id(1L)
                .login(login)
                .password("password")
                .role(role)
                .build();
    }

    @Test(expected = NoEntityException.class)
    public  void saveUser_getNullRole_throwException() {
        when(roleRepository.findByName("ROLE_USER")).thenReturn(null);
        userService.saveUser(user);
    }

    @Test(expected = SaveEntityException.class)
    public  void saveUser_getNullUser_throwException() {
        when(roleRepository.findByName("ROLE_USER")).thenReturn(role);
        when(userRepository.save(user)).thenReturn(null);
        userService.saveUser(user);
    }

    @Test(expected = AuthenticationLoginException.class)
    public  void findByLogin_getNullUser_throwException() {
        when(userRepository.findByLogin(login)).thenReturn(null);
        userService.findByLogin(login);
    }

    @Test(expected = AuthenticationPasswordException.class)
    public void findByLoginAndPassword_notMatchPasswords_throwException() {
        when(userRepository.findByLogin(login)).thenReturn(user);
        userService.findByLoginAndPassword(login,"incorrect password");
    }

}