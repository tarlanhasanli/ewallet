import axios from 'axios';

const username = 'user';
const password = 'password';
const encodedCredentials = btoa(`${username}:${password}`);
const authHeader = `Basic ${encodedCredentials}`;

const api = axios.create({
  baseURL: 'http://localhost:8080',
  headers: {
    'Content-Type': 'application/json',
    'Authorization': authHeader,
  },
});

export default api;
