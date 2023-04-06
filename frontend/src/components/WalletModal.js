import React, { useState } from "react";
import { Button, Modal, TextField, Paper } from "@mui/material";

function WalletModal({ onAddWallet }) {
  const [open, setOpen] = useState(false);
  const [name, setName] = useState("");

  const handleOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleSubmit = () => {
    onAddWallet(name);
    setName("");
    handleClose();
  };

  const modalStyle = {
    display: "flex",
    alignItems: "center",
    justifyContent: "center",
  };

  const paperStyle = {
    padding: "16px",
    display: "flex",
    flexDirection: "column",
    gap: "8px",
  };

  return (
      <div>
        <Button variant="contained" color="primary" onClick={handleOpen}>
          Add Wallet
        </Button>
        <Modal open={open} onClose={handleClose} style={modalStyle}>
          <Paper style={paperStyle}>
            <h2>Add Wallet</h2>
            <TextField
                label="Wallet Name"
                value={name}
                onChange={(e) => setName(e.target.value)}
            />
            <Button variant="contained" color="primary" onClick={handleSubmit}>
              Add
            </Button>
            <Button variant="outlined" color="secondary" onClick={handleClose}>
              Cancel
            </Button>
          </Paper>
        </Modal>
      </div>
  );
}

export default WalletModal;
