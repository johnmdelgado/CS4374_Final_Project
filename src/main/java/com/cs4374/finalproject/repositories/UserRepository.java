package com.cs4374.finalproject.repositories;

import com.cs4374.finalproject.siteElements.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
