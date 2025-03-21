package com.microcompany.accountsservice.controller;

import com.microcompany.accountsservice.exception.GlobalException;
import com.microcompany.accountsservice.model.Account;
import com.microcompany.accountsservice.services.IAccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class AccountController implements IAccountController{
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    private final IAccountService as;
    public AccountController(IAccountService as) {
        this.as = as;
    }

    @Override
    public ResponseEntity getAccountsByOwnerId(Long pid) {
        List<Account> cuentas = as.getAccountByOwnerId(pid);
        return ResponseEntity.status(HttpStatus.OK.value()).body(cuentas);
    }

    @Override
    public ResponseEntity getAnAccount(Long pid, Long cid) {
        Account userAccount = as.getAccount(pid, cid);
        return ResponseEntity.status(HttpStatus.OK.value()).body(userAccount);
    }

    @Override
    public ResponseEntity createAccount(Account account, Long ownerId) {
        return null;
    }

    @Override
    public ResponseEntity updateAccount(Long id, Account account, Long ownerID) {
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
    public ResponseEntity updateWithdrawAccount(Long id, int amount) {
        try{
            as.withdrawBalance(id, amount);
            return ResponseEntity.status(HttpStatus.OK.value()).body("Prestamo aceptado.");
        } catch(GlobalException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Préstamo denegado:" + e.getMessage());
        }
    }
}

