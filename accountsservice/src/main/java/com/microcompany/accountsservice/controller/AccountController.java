package com.microcompany.accountsservice.controller;

import com.microcompany.accountsservice.model.Account;
import com.microcompany.accountsservice.services.IAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class AccountController implements IAccountController{
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    private final IAccountService as;
    public AccountController(IAccountService as) {
        this.as = as;
    }

    @Override
    public ResponseEntity getAnAccount(Long pid, Long cid) {

        return null;
    }

    @Override
    public ResponseEntity getAccountsByOwnerId(Long pid) {
        return null;
    }

    @Override
    public ResponseEntity createAccount(Account account) {
        return null;
    }

    @Override
    public ResponseEntity updateAccount(Long id, Account account) {
        return null;
    }

    @Override
    public ResponseEntity deleteAccount(Long id, Long cid) {
        return null;
    }

    @Override
    public ResponseEntity updateDepositAccount(Long id, Account account) {
        return null;
    }

    @Override
    public ResponseEntity updateWithdrawAccount(Long id, Account account) {
        return null;
    }

    @Override
    public ResponseEntity deleteAccountsByOwnerId(Long pid) {
        return null;
    }

    @Override
    public ResponseEntity updateWithdrawAccount(Long id, Float amount) {
        return null;
    }
}

