package com.kn.ewallet.service;

import com.kn.ewallet.model.Wallet;
import java.math.BigDecimal;
import java.util.List;

public interface WalletService {

    Wallet updateBalance(Long id, BigDecimal amount);

    List<Wallet> getWallets();

    Wallet getWalletById(Long id);

    Wallet createWallet(String name);

    List<Wallet> transfer(Long id, Long receiverId, BigDecimal amount);
}
