package com.kn.ewallet;

import com.kn.ewallet.db.WalletRepository;
import com.kn.ewallet.exceptions.WalletNotFoundException;
import com.kn.ewallet.model.Wallet;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    public Wallet updateBalance(Long id, BigDecimal amount) {
        Wallet wallet = getWalletById(id);
        wallet.updateBalance(amount);
        return walletRepository.save(wallet);
    }

    public List<Wallet> getWallets() {
        return walletRepository.findAll();
    }

    public Wallet getWalletById(Long id) {
        return walletRepository.findById(id).orElseThrow(() -> new WalletNotFoundException(id));
    }

    public Wallet createWallet(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }

        Wallet newWallet = new Wallet(name);
        return walletRepository.save(newWallet);
    }

    public List<Wallet> transfer(Long id, Long receiverId, BigDecimal amount) {
        Wallet sender = getWalletById(id);
        Wallet receiver = getWalletById(receiverId);

        sender.updateBalance(amount.negate());
        receiver.updateBalance(amount);

        return walletRepository.saveAll(Arrays.asList(sender, receiver));
    }
}
