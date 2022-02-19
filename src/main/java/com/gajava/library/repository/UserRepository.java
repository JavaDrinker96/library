package com.gajava.library.repository;

import com.gajava.library.model.User;

public interface UserRepository extends BaseRepository<User>{

    User findByLogin(String login);

}
