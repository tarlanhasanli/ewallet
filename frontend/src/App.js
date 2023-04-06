import React, {useEffect, useState} from "react";
import WalletList from "./components/WalletList";
import WalletModal from "./components/WalletModal";
import api from './api';
import {Container} from "@mui/material";

function App() {
  const [wallets, setWallets] = useState([]);

  useEffect(() => {
    fetchWallets();
  }, []);

  const fetchWallets = async () => {
    const response = await api.get("/wallets");
    setWallets(response.data);
  };

  const handleAddWallet = async (name) => {
    try {
      const response = await api.post(`/wallets?name=${name}`);
      setWallets([...wallets, response.data]);
    } catch (error) {
      console.error("Error adding wallet: ", error);
    }
  };

  const handleTransfer = ({senderId, receiverId, amount}) => {
    api
    .put(`/wallets/${senderId}/transfer`, {
      receiverId: receiverId,
      amount: amount,
    })
    .then(async () => {
      const response = await api.get("/wallets");
      setWallets(response.data);
    })
    .catch((error) => {
      console.log(error);
    });
  };

  return (
      <div className="App">
        <Container maxWidth="lg">
          <WalletModal onAddWallet={handleAddWallet}/>
          <WalletList wallets={wallets} onTransferFund={handleTransfer} />
        </Container>
      </div>
  );
}

export default App;
