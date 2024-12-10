package com.softwaremanage.meditestlab.repository.account_management_module;

import com.softwaremanage.meditestlab.pojo.account_management_module.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByuName(String uName);
    User findRealnameByuId(Integer uId);
}
