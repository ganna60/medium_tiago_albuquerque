package com.tiagoamp.userinfo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//@RepositoryRestResource(collectionResourceRel = "user", path = "user")
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findUserById(Long id);
}