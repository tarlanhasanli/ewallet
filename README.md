#E-Wallet

E-Wallet is a web application built with Spring Boot (Java) backend and React frontend. It provides a simple way to create, view, and manage electronic wallets.

###Features
- Create a new wallet
- View wallets
- Add funds to a wallet
- Withdraw funds from a wallet
- Transfer funds between wallets

###Technologies Used
- Spring Boot (Java)
- H2 Database
- React
- Axios
- Material-UI

###How to Run
1. Clone the repository
2. Open a terminal and navigate to the ewallet/frontend directory
3. Run npm install to install the necessary dependencies
4. Run npm start to start the React development server
5. Open another terminal and navigate to the ewallet directory
6. Run mvn spring-boot:run to start the Spring Boot server
7. Open a web browser and navigate to http://localhost:3000 to use the application

###Endpoints
The backend provides the following endpoints:

- POST /wallets: Create a new wallet
- GET /wallets: Get all wallets
- GET /wallets/{id}: Get a single wallet by ID
- PUT /wallets/{id}/fund: Add/Withdraw funds from a wallet
- PUT /wallets/{id}/transfer: Transfer funds from one wallet to another

####Note
Basic Authentication and Transaction History functionality are not implemented in this version of the application.
