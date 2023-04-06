import React, { useState } from "react";
import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  TextField,
  Button,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
} from "@mui/material";


function TransferDialog(props) {
  const [receiverId, setReceiverId] = useState("");
  const [amount, setAmount] = useState("");

  const handleReceiverChange = (event) => {
    setReceiverId(event.target.value);
  };

  const handleAmountChange = (event) => {
    setAmount(event.target.value);
  };

  const handleTransfer = () => {
    props.onTransfer({
      receiverId: receiverId,
      amount: amount,
    });
    setReceiverId("");
    setAmount("");
  };

  return (
      <Dialog open={props.open} onClose={props.onClose}>
        <DialogTitle>Transfer Funds</DialogTitle>
        <DialogContent>
          <FormControl>
            <InputLabel>Receiver</InputLabel>
            <Select value={receiverId} onChange={handleReceiverChange}>
              {props.wallets?.map((wallet) => (
                  <MenuItem key={wallet.id} value={wallet.id}>
                    {wallet.name}
                  </MenuItem>
              ))}
            </Select>
          </FormControl>
          <TextField
              autoFocus
              margin="dense"
              label="Amount"
              type="number"
              value={amount}
              onChange={handleAmountChange}
              fullWidth
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={props.onClose}>Cancel</Button>
          <Button onClick={handleTransfer} color="primary">
            Transfer
          </Button>
        </DialogActions>
      </Dialog>
  );
}

export default TransferDialog;
