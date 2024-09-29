import { useState, useContext } from 'react';
import axios from '../api';
import { AppContext } from '../context/AppContext';
import { useNavigate } from 'react-router-dom';
import { TextField, Button, List, ListItem, ListItemText, Typography, Container, Box } from '@mui/material';

const AccountList = () => {
  const { accounts, setAccounts } = useContext(AppContext);
  const [searchTerm, setSearchTerm] = useState('');
  const navigate = useNavigate();

  const handleSearch = () => {
    axios
      .get('/accounts/search', {
        params: { query: searchTerm },
      })
      .then((response) => {
        setAccounts(response.data);
      })
      .catch((error) => {
        console.error('Arama başarısız:', error);
      });
  };

  const viewAccount = (id) => {
    navigate(`/accounts/${id}`);
  };

  return (
    <Container maxWidth="md">
      <Box sx={{ mt: 4 }}>
        <Typography variant="h5" gutterBottom>
          Hesaplarınız
        </Typography>
        <Box sx={{ display: 'flex', mb: 2 }}>
          <TextField
            label="Hesap numarası veya adı"
            variant="outlined"
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            fullWidth
          />
          <Button variant="contained" color="primary" onClick={handleSearch} sx={{ ml: 2 }}>
            Ara
          </Button>
        </Box>
        <List>
          {accounts.map((account) => (
            <ListItem button key={account.id} onClick={() => viewAccount(account.id)}>
              <ListItemText primary={`${account.name} - ${account.number}`} />
            </ListItem>
          ))}
        </List>
      </Box>
    </Container>
  );
};

export default AccountList;
