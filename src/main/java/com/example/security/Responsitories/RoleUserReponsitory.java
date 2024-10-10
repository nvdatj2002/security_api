package com.example.security.Responsitories;


import com.example.security.Entity.RoleUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleUserReponsitory extends JpaRepository<RoleUser, Integer> {
}
