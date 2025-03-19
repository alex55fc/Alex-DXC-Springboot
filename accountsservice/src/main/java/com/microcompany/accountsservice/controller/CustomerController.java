package com.microcompany.accountsservice.controller;

import com.microcompany.accountsservice.services.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {

    private ICustomerService cs;

    public CustomerController(ICustomerService cs) {
        this.cs = cs;
    }
}
