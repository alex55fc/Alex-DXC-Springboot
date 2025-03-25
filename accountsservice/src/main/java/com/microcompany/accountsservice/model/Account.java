package com.microcompany.accountsservice.model;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Table(name = "account")
public class Account {

    //@NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Type´s obligatory")
    private String type;

    @DateTimeFormat
    @NotEmpty
    Date openingDate;

    @NotEmpty
    private int balance;

    //@Column(name = "owner_id_value")
    //private Long ownerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    @NotEmpty
    Customer owner;

    public Account() {
    }

    public Account(Long id, String type, Date openingDate, int balance, Customer owner) {
        this.id = id;
        this.type = type;
        this.openingDate = openingDate;
        this.balance = balance;
        this.owner = owner;
    }

    public Account(Long id, String type, Date openingDate, int balance, long owner) {
        this.id = id;
        this.type = type;
        this.openingDate = openingDate;
        this.balance = balance;
        this.owner = new Customer(owner);
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

    public Customer getOwner() {
        return owner;
    }

    public void setOwner(Customer owner) {
        this.owner = owner;
    }

    public Date getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(Date openingDate) {
        this.openingDate = openingDate;
    }
}
