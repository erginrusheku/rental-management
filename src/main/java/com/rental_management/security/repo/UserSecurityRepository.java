package com.rental_management.security.repo;

import com.rental_management.security.entity.UserSecurity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSecurityRepository extends JpaRepository<UserSecurity, Long> {

    UserSecurity findByUsername(String username);
}
