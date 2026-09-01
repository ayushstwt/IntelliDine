package com.hunger.saviour.portal.repositories;

import com.hunger.saviour.portal.entities.RoleEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<RoleEntity, Integer> {
    Optional<RoleEntity> findByRoleName(String roleName);
}

