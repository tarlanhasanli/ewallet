package com.kn.ewallet.controller;

import com.kn.ewallet.service.WalletService;
import com.kn.ewallet.model.Wallet;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wallets")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @PostMapping
    public ResponseEntity<Wallet> createWallet(@RequestParam("name") String name) {
        Wallet wallet = walletService.createWallet(name);
        return ResponseEntity.ok(wallet);
    }

    @GetMapping
    public ResponseEntity<List<Wallet>> getWallets() {
        List<Wallet> wallets = walletService.getWallets();
        return ResponseEntity.ok(wallets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Wallet> getWalletById(@PathVariable Long id) {
        Wallet wallet = walletService.getWalletById(id);
        return ResponseEntity.ok(wallet);
    }

    @PutMapping("/{id}/fund")
    public ResponseEntity<Wallet> addFunds(@PathVariable Long id, @RequestParam("amount") BigDecimal amount) {
        Wallet wallet = walletService.updateBalance(id, amount);
        return ResponseEntity.ok(wallet);
    }

    @PutMapping("/{id}/transfer")
    public ResponseEntity<List<Wallet>> transfer(@PathVariable Long id, @RequestBody TransferRequest transferRequest) {
        List<Wallet> wallets = walletService.transfer(id, transferRequest.getReceiverId(), transferRequest.getAmount());
        return ResponseEntity.ok(wallets);
    }

}
