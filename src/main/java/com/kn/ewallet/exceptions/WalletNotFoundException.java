package com.kn.ewallet.exceptions;

public class WalletNotFoundException extends RuntimeException {

    public WalletNotFoundException(Long walletId) {
        super("Wallet with ID " + walletId + " not found");
    }

}
