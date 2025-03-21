package com.microcompany.accountsservice.controller;

import com.microcompany.accountsservice.exception.AccountNotfoundException;
import com.microcompany.accountsservice.exception.CustomerNotAllowedException;
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
        Account newAccount = as.create(account , ownerId);
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(newAccount);
    }

    @Override
    public ResponseEntity updateAccount(Long id, Account account, Long ownerID) {
        try{
            Account accountUpdated = as.updateAccount(id, account, ownerID);
            return ResponseEntity.status(HttpStatus.OK.value()).body(accountUpdated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al actualizar la cuenta.");
        }
    }

    @Override
    public ResponseEntity deleteAccount(Long id, Long cid) {
        try{
            as.deleteUserAccount(id, cid);
            return ResponseEntity.status(HttpStatus.OK.value()).body("Eliminada la cuenta del usuario");
        }catch (AccountNotfoundException acountNF){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo eliminar la cuenta" + acountNF.getMessage());
        }catch (CustomerNotAllowedException customerNA){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Customer denegado" + customerNA.getMessage());
        }
    }

    @Override
    public ResponseEntity updateDepositAccount(Long id, int amount, Long ownerId) {
        Account account = as.addBalance(id, amount, ownerId);
        return ResponseEntity.status(HttpStatus.OK.value()).body(account);
    }

    @Override
    public ResponseEntity updateWithdrawAccount(Long id, int amount, Long ownerId) {
        Account account = as.withdrawBalance(id, amount, ownerId);
        return ResponseEntity.status(HttpStatus.OK.value()).body(account);
    }
     @Override
    public ResponseEntity deleteAccountsByOwnerId(Long ownerId) {
        if (ownerId != null && ownerId > 0) {
            as.deleteAccountsUsingOwnerId(ownerId);
            return ResponseEntity.status(HttpStatus.OK.value()).body("Cuentas eliminadas correctamente");
        } else
            return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body("No se ha podido realizar");
    }

    @Override
    public ResponseEntity requestLoan(Long id, int amount) {
        try{
            as.requestLoan(id, amount);
            return ResponseEntity.status(HttpStatus.OK.value()).body("Prestamo aceptado.");
        } catch(GlobalException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Préstamo denegado:" + e.getMessage());
        }
    }
}

