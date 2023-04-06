package com.kn.ewallet.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kn.ewallet.model.Wallet;
import com.kn.ewallet.service.WalletService;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(WalletController.class)
@ExtendWith(MockitoExtension.class)
@WithMockUser(username = "user", password = "password")
public class WalletControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WalletService walletService;

    @Test
    void testCreateWallet() throws Exception {
        Wallet wallet = new Wallet(1L, "Test Wallet", BigDecimal.ZERO);
        when(walletService.createWallet(any())).thenReturn(wallet);

        mockMvc.perform(post("/wallets?name=Test Wallet"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.name").value("Test Wallet"))
            .andExpect(jsonPath("$.balance").value(0));
    }

    @Test
    void testGetWallets() throws Exception {
        Wallet wallet1 = new Wallet(1L, "Wallet 1", BigDecimal.ZERO);
        Wallet wallet2 = new Wallet(2L, "Wallet 2", BigDecimal.valueOf(100));
        List<Wallet> wallets = Arrays.asList(wallet1, wallet2);
        when(walletService.getWallets()).thenReturn(wallets);

        mockMvc.perform(get("/wallets"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1L))
            .andExpect(jsonPath("$[0].name").value("Wallet 1"))
            .andExpect(jsonPath("$[0].balance").value(0))
            .andExpect(jsonPath("$[1].id").value(2L))
            .andExpect(jsonPath("$[1].name").value("Wallet 2"))
            .andExpect(jsonPath("$[1].balance").value(100));
    }

    @Test
    void testGetWalletById() throws Exception {
        Wallet wallet = new Wallet(1L, "Test Wallet", BigDecimal.ZERO);
        when(walletService.getWalletById(1L)).thenReturn(wallet);

        mockMvc.perform(get("/wallets/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.name").value("Test Wallet"))
            .andExpect(jsonPath("$.balance").value(0));
    }

    @Test
    void testAddFunds() throws Exception {
        Wallet wallet = new Wallet(1L, "Test Wallet", BigDecimal.valueOf(100));
        when(walletService.updateBalance(1L, BigDecimal.valueOf(100))).thenReturn(wallet);

        mockMvc.perform(put("/wallets/1/fund?amount=100"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.name").value("Test Wallet"))
            .andExpect(jsonPath("$.balance").value(100));
    }

    @Test
    void testTransfer() throws Exception {
        Wallet wallet1 = new Wallet(1L, "Wallet 1", BigDecimal.ZERO);
        Wallet wallet2 = new Wallet(2L, "Wallet 2", BigDecimal.valueOf(200));
        List<Wallet> wallets = Arrays.asList(wallet1, wallet2);

        when(walletService.transfer(1L, 2L, BigDecimal.valueOf(100))).thenReturn(wallets);

        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setReceiverId(2L);
        transferRequest.setAmount(BigDecimal.valueOf(100));

        mockMvc.perform(put("/wallets/1/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(transferRequest)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1L))
            .andExpect(jsonPath("$[0].name").value("Wallet 1"))
            .andExpect(jsonPath("$[0].balance").value(0))
            .andExpect(jsonPath("$[1].id").value(2L))
            .andExpect(jsonPath("$[1].name").value("Wallet 2"))
            .andExpect(jsonPath("$[1].balance").value(200));
    }
}
