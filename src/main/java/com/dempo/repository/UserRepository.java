package com.dempo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dempo.model.OrderEntity;
import com.dempo.model.UserEntity;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
