const express = require('express');
const app = express();
const PORT = 3001;

const HOST = '0.0.0.0';

app.use(express.json());

app.post('/register', (req, res) => {
  res.json({
    email: req.body.email,
    password: req.body.password,
    name: req.body.name
  })
})

app.post('/login', (req, res) => {
  res.json({
    email: req.body.email,
    password: req.body.password,
    name: req.body.name
  })
})

app.listen(PORT, HOST, () => {
  console.log(`Servidor rodando em http://${HOST}:${PORT}`);
});
