package com.microcompany.accountsservice.services;

import com.microcompany.accountsservice.model.Account;

import java.util.List;

public interface IAccountService {
    Account create(Account account);

    Account getAccount(Long id, Long ownerId);

    List<Account> getAccounts(Long id);

    List<Account> getAccountByOwnerId(Long ownerId);

    Account updateAccount(Long id, Account account, Long ownerId);

    Account addBalance(Long id, int amount, Long ownerId);

    Account withdrawBalance(Long id, int amount);

    void deleteUserAccount(Long id, Long ownerId);

    void deleteAccountsUsingOwnerId(Long ownerId);

}
