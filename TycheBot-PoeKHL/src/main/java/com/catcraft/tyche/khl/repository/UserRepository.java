package com.catcraft.tyche.khl.repository;

import com.catcraft.tyche.khl.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;

public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User>, Serializable {
    User queryByPoeId(String id);

    User queryById(String id);
}
