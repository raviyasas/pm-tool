package com.app.repository;

import com.app.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Integer> {
    AppUser findByUsername(String username);

    @Query(value="select username from app_user", nativeQuery=true)
    List<String> findAllUsernames();
}
