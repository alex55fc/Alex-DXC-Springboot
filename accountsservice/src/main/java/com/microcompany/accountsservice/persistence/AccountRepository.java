package com.microcompany.accountsservice.persistence;

import com.microcompany.accountsservice.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("SELECT a from Account a WHERE a.owner = ?1")
    List<Account> findByOwnerId(Long OwnerId);

    @Query("SELECT a from Account a WHERE a.type = ?1")
    List<Account> findByType(String Type);
}

