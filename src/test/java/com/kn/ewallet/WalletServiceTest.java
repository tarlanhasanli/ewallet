package com.kn.ewallet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.kn.ewallet.db.WalletRepository;
import com.kn.ewallet.service.WalletService;
import com.kn.ewallet.exceptions.InsufficientFundsException;
import com.kn.ewallet.model.Wallet;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class WalletServiceTest {

    @Mock
    private WalletRepository walletRepository;

    @InjectMocks
    private WalletService walletService;

    private Wallet wallet1;
    private Wallet wallet2;

    @BeforeEach
    public void setup() {
        wallet1 = new Wallet("wallet1");
        wallet1.setId(1L);
        wallet1.setBalance(new BigDecimal(100));

        wallet2 = new Wallet("wallet2");
        wallet2.setId(2L);
        wallet2.setBalance(new BigDecimal(200));
    }

    @Test
    @DisplayName("Test updateBalance")
    public void testUpdateBalance() {
        BigDecimal amount = new BigDecimal(50);
        when(walletRepository.findById(1L)).thenReturn(Optional.of(wallet1));
        when(walletRepository.save(any())).thenReturn(wallet1);
        Wallet result = walletService.updateBalance(1L, amount);
        Assertions.assertEquals(result.getBalance(), new BigDecimal(150));
    }

    @Test
    @DisplayName("Test getWallets")
    public void testGetWallets() {
        List<Wallet> wallets = Arrays.asList(wallet1, wallet2);
        when(walletRepository.findAll()).thenReturn(wallets);
        List<Wallet> result = walletService.getWallets();
        Assertions.assertEquals(result, wallets);
    }

    @Test
    @DisplayName("Test getWalletById")
    public void testGetWalletById() {
        when(walletRepository.findById(1L)).thenReturn(Optional.of(wallet1));
        Wallet result = walletService.getWalletById(1L);
        Assertions.assertEquals(result, wallet1);
    }

    @Test
    @DisplayName("Test createWallet")
    public void testCreateWallet() {
        when(walletRepository.save(any())).thenReturn(wallet1);
        Wallet result = walletService.createWallet("wallet1");
        Assertions.assertEquals(result.getName(), "wallet1");
    }

    @Test
    @DisplayName("Test createWallet with blank name")
    public void testCreateWalletWithBlankName() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            walletService.createWallet("  ");
        });
    }

    @Test
    @DisplayName("Test transfer with sufficient funds")
    public void testTransferSufficientFunds() {
        BigDecimal amount = new BigDecimal(50);
        when(walletRepository.findById(1L)).thenReturn(Optional.of(wallet1));
        when(walletRepository.findById(2L)).thenReturn(Optional.of(wallet2));
        when(walletRepository.saveAll(any())).thenReturn(Arrays.asList(wallet1, wallet2));
        List<Wallet> result = walletService.transfer(1L, 2L, amount);
        Assertions.assertEquals(result.get(0).getBalance(), new BigDecimal(50));
        Assertions.assertEquals(result.get(1).getBalance(), new BigDecimal(250));
    }

    @Test
    @DisplayName("Test withdraw with insufficient funds")
    public void testWithdrawFundsInsufficientFunds() {
        BigDecimal amount = new BigDecimal(-200);
        when(walletRepository.findById(1L)).thenReturn(Optional.of(wallet1));
        Assertions.assertThrows(InsufficientFundsException.class, () -> {
            walletService.updateBalance(1L, amount);
        });
    }
}

