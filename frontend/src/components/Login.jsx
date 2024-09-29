import { useState, useContext } from 'react';
import axios from '../api';
import { AppContext } from '../context/AppContext';
import { useNavigate } from 'react-router-dom';

// Material-UI bileşenlerini içe aktarın
import { TextField, Button, Typography, Container, Box, Alert } from '@mui/material';

const Login = () => {
  const [credentials, setCredentials] = useState({ username: '', password: '' });
  const { login } = useContext(AppContext);
  const navigate = useNavigate();
  const [error, setError] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    axios
      .post('/users/login', credentials)
      .then((response) => {
        login(response.data.token);
        navigate('/');
      })
      .catch((error) => {
        setError('Giriş başarısız: ' + error.response.data.message);
      });
  };

  return (
    <Container maxWidth="xs">
      <Box sx={{ mt: 8 }}>
        <Typography variant="h4" component="h1" gutterBottom>
          Giriş Yap
        </Typography>
        {error && <Alert severity="error">{error}</Alert>}
        <Box component="form" onSubmit={handleSubmit} sx={{ mt: 2 }}>
          <TextField
            label="Kullanıcı Adı"
            fullWidth
            margin="normal"
            value={credentials.username}
            onChange={(e) => setCredentials({ ...credentials, username: e.target.value })}
            required
          />
          <TextField
            label="Şifre"
            type="password"
            fullWidth
            margin="normal"
            value={credentials.password}
            onChange={(e) => setCredentials({ ...credentials, password: e.target.value })}
            required
          />
          <Button type="submit" variant="contained" color="primary" fullWidth sx={{ mt: 2 }}>
            Giriş Yap
          </Button>
        </Box>
      </Box>
    </Container>
  );
};

export default Login;
