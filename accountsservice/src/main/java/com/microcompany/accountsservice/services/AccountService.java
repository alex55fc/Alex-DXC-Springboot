package com.microcompany.accountsservice.services;

import com.microcompany.accountsservice.exception.AccountNotfoundException;
import com.microcompany.accountsservice.exception.CustomerNotAllowedException;
import com.microcompany.accountsservice.exception.CustomerNotOwnerException;
import com.microcompany.accountsservice.exception.GlobalException;
import com.microcompany.accountsservice.model.Account;
import com.microcompany.accountsservice.model.Customer;
import com.microcompany.accountsservice.persistence.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AccountService implements IAccountService {
    private Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerService customerService;

    @Override
    public Account getAccount(Long id, Long ownerId) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new AccountNotfoundException(id));
        if(account.getOwner().getId() != ownerId) throw new CustomerNotOwnerException();
        return account;
    }

    @Override
    public List<Account> getAccounts(Long id) {
        List<Account> listaUsuario = accountRepository.findByOwnerId(id);
        if(listaUsuario == null || listaUsuario.isEmpty()) throw new NullPointerException();
        return listaUsuario;
    }
    @Override
    public Account updateAccount(Long id, Account account, Long ownerId) {
        Account newAccount = accountRepository.findById(id).orElseThrow(() -> new AccountNotfoundException(id));
        if(ownerId  != newAccount.getOwner().getId()) throw new CustomerNotAllowedException();
        newAccount.setType(account.getType());
        return accountRepository.save(newAccount);
    }

    @Override
    public Account create(Account account, Long ownerId) {
        if(ownerId  != account.getOwner().getId()) throw new CustomerNotAllowedException();
        Date current_Date = new Date();
        account.setOpeningDate(current_Date);
        return accountRepository.save(account);
    }

    @Override
    public void deleteUserAccount(Long id, Long ownerId) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new AccountNotfoundException(id));
        if(ownerId != account.getOwner().getId()) throw new CustomerNotAllowedException();
        this.accountRepository.delete(account);
    }

    @Override
    public void deleteAccountsUsingOwnerId(Long ownerId) {
        List<Account> accounts = accountRepository.findByOwnerId(ownerId);
        for (Account account : accounts) {
            this.accountRepository.delete(account);
        }
    }

    @Override
    public Account requestLoan(Long id, int amount) {
        Customer customer = customerService.getCustomerById(id);
        int monto = 0;
        for (Account account : customer.getAccount()) {
            monto += account.getBalance();
        }
        if(monto * 0.8 < amount) throw new GlobalException();
        return null;
    }



    @Override
    public List<Account> getAccountByOwnerId(Long ownerId) {
        return accountRepository.findByOwnerId(ownerId);
    }



    @Override
    public Account addBalance(Long id, int amount, Long ownerId) {
        Account newAccount = accountRepository.findById(id).orElseThrow(() -> new AccountNotfoundException(id));
        if(ownerId != newAccount.getOwner().getId()) throw new CustomerNotAllowedException();
        int newBalance = newAccount.getBalance() + amount;
        newAccount.setBalance(newBalance);
        return accountRepository.save(newAccount);
    }

    @Override
    public Account withdrawBalance(Long id, int amount, Long ownerId) {
        Account newAccount = accountRepository.findById(id).orElseThrow(() -> new AccountNotfoundException(id));
        if(ownerId != newAccount.getOwner().getId()) throw new CustomerNotAllowedException();
        int newBalance = newAccount.getBalance() - amount;
        newAccount.setBalance(newBalance);
        return accountRepository.save(newAccount);
    }








}
