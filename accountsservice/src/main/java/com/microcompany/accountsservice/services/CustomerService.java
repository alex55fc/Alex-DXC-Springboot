package com.microcompany.accountsservice.services;

import com.microcompany.accountsservice.exception.GlobalException;
import com.microcompany.accountsservice.model.Customer;
import com.microcompany.accountsservice.persistence.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService implements ICustomerService{

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer getCustomerById(Long cid) {
        return customerRepository.findById(cid).orElseThrow(() -> new GlobalException());
    }
}
