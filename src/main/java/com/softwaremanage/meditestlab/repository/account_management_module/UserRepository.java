package com.softwaremanage.meditestlab.repository.account_management_module;

import com.softwaremanage.meditestlab.pojo.account_management_module.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByuName(String uName);
    Optional<User> findByuId(Integer uId);

}
