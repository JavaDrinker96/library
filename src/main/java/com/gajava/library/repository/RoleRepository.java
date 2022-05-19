package com.gajava.library.repository;

import com.gajava.library.model.Role;

public interface RoleRepository extends BaseRepository<Role> {

    Role findByName(String name);

}
