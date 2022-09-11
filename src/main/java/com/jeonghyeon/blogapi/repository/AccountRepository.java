package com.jeonghyeon.blogapi.repository;

import com.jeonghyeon.blogapi.model.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("SELECT a FROM Account a WHERE a.userId = :userId")
    Optional<Account> findByUserId(String userId);
}
