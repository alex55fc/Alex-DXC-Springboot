package com.microcompany.accountsservice.dto;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Date;


public class AccountDTO {

    private Long id;

    private String type;


    Date openingDate;

    private int balance;

    //@Column(name = "owner_id_value")
     private Long ownerId;


    public AccountDTO() {
    }

    public AccountDTO(Long id,String type, int balance, Date openingDate, Long ownerId) {
        this.id = id;
        this.balance = balance;
        this.openingDate = openingDate;
        this.type = type;
        this.ownerId = ownerId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Date getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(Date openingDate) {
        this.openingDate = openingDate;
    }
}
