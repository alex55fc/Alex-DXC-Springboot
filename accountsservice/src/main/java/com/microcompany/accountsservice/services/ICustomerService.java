package com.microcompany.accountsservice.services;

import com.microcompany.accountsservice.model.Customer;

public interface ICustomerService {

    public Customer getCustomerById(Long cid);
}
