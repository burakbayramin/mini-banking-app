import { useContext } from 'react';
import { AppBar, Toolbar, Typography, Button } from '@mui/material';
import { AppContext } from '../context/AppContext';
import { useNavigate } from 'react-router-dom';

const NavBar = () => {
  const { authTokens, logout } = useContext(AppContext);
  const navigate = useNavigate();

  return (
    <AppBar position="static">
      <Toolbar>
        <Typography variant="h6" sx={{ flexGrow: 1 }}>
          Mini Bank
        </Typography>
        {authTokens ? (
          <>
            <Button color="inherit" onClick={() => navigate('/')}>
              Accounts
            </Button>
            <Button color="inherit" onClick={() => navigate('/transfer')}>
              Transfer
            </Button>
            <Button color="inherit" onClick={logout}>
              Logout
            </Button>
          </>
        ) : (
          <>
            <Button color="inherit" onClick={() => navigate('/login')}>
              Login
            </Button>
            <Button color="inherit" onClick={() => navigate('/register')}>
              Register
            </Button>
          </>
        )}
      </Toolbar>
    </AppBar>
  );
};

export default NavBar;
