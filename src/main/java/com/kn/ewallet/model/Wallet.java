package com.kn.ewallet.model;

import com.kn.ewallet.exceptions.InsufficientFundsException;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    private BigDecimal balance;

    public Wallet() {
    }

    public Wallet(String name) {
        this(name, BigDecimal.ZERO);
    }

    public Wallet(String name, BigDecimal balance) {
        this.name = name;
        this.balance = balance;
    }

    public void updateBalance(BigDecimal amount) {
        if (balance.add(amount).compareTo(BigDecimal.ZERO) >= 0) {
            balance = balance.add(amount);
        } else {
            throw new InsufficientFundsException();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
