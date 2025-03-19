package com.microcompany.accountsservice.controller;

import com.microcompany.accountsservice.services.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/accounts")
public class AccountController {

    private IAccountService as;

    public AccountController(IAccountService as) {
        this.as = as;
    }

}

