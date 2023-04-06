import React, {useState} from "react";
import {IconButton, TableCell, TableRow, TextField} from "@mui/material";
import {
  Add as AddIcon,
  Remove as WithdrawIcon,
  Sync as TransferIcon
} from "@mui/icons-material";
import TransferDialog from "./TransferDialog";
import api from '../api';

function Wallet({wallet, rowIndex, allWallets, onTransferFund}) {
  const [openTransferDialog, setOpenTransferDialog] = useState(false);
  const [amount, setAmount] = useState("");

  const handleAddWithdrawFund = (isAdd) => {
    if (!amount || amount <= 0) {
      return;
    }

    if (isAdd) {
      api
      .put(`/wallets/${wallet.id}/fund?amount=${amount}`)
      .then((response) => {
        wallet.balance = response.data.balance;
        setAmount("");
      })
      .catch((error) => {
        console.log(error);
      });
    } else {
      if (amount > wallet.balance) {
        return;
      }

      api
      .put(`/wallets/${wallet.id}/fund?amount=${amount * -1}`)
      .then((response) => {
        wallet.balance = response.data.balance;
        setAmount("");
      })
      .catch((error) => {
        console.log(error);
      });
    }
  };

  const handleTransfer = ({receiverId, amount}) => {
    onTransferFund({senderId: wallet.id, receiverId, amount});
    setOpenTransferDialog(false);
  };

  return (
      <TableRow>
        <TableCell>{rowIndex}</TableCell>
        <TableCell>{wallet.name}</TableCell>
        <TableCell>
          {wallet.balance.toFixed(2)} €
        </TableCell>
        <TableCell>
          <TextField
              label="Amount"
              type="number"
              variant="outlined"
              size="small"
              value={amount}
              onChange={(e) => setAmount(e.target.value)}
              InputProps={{inputProps: {min: 0, max: 99999.99, step: 0.01}}}
          />

          <IconButton onClick={() => handleAddWithdrawFund(true)}
                      disabled={amount === ''}>
            <AddIcon/>
          </IconButton>
          <IconButton onClick={() => handleAddWithdrawFund(false)}
                      disabled={amount === '' || wallet.balance === 0 || amount > wallet.balance}>
            <WithdrawIcon/>
          </IconButton>
        </TableCell>
        <TableCell>
          <IconButton onClick={() => setOpenTransferDialog(true)} disabled={allWallets.size > 1}>
            <TransferIcon/>
          </IconButton>
          <TransferDialog
              open={openTransferDialog}
              onClose={() => setOpenTransferDialog(false)}
              wallets={allWallets.filter((w) => w.id !== wallet.id)}
              onTransfer={handleTransfer}
              balance={wallet.balance}
          />
        </TableCell>
      </TableRow>
  );
}

export default Wallet;
