package com.softwaremanage.meditestlab.repository;

import com.softwaremanage.meditestlab.pojo.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByuName(String uName);
}
