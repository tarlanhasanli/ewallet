import React from "react";
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper } from "@mui/material";
import Wallet from "./Wallet";

function WalletList({ wallets, onTransferFund }) {
  return (
      <div>
        <h2>Wallets</h2>
        <TableContainer component={Paper}>
          <Table>
            <TableHead>
              <TableRow>
                <TableCell>#</TableCell>
                <TableCell>Name</TableCell>
                <TableCell>Balance</TableCell>
                <TableCell>Actions</TableCell>
                <TableCell>Transfer</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {wallets?.map((wallet, index) => (
                  <Wallet
                      key={wallet.id}
                      wallet={wallet}
                      rowIndex={index + 1}
                      allWallets={wallets}
                      onTransferFund={onTransferFund}
                  />
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      </div>
  );
}

export default WalletList;
