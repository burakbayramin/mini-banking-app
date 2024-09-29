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
          Mini Banka
        </Typography>
        {authTokens ? (
          <>
            <Button color="inherit" onClick={() => navigate('/')}>
              Hesaplarım
            </Button>
            <Button color="inherit" onClick={() => navigate('/transfer')}>
              Para Transferi
            </Button>
            <Button color="inherit" onClick={logout}>
              Çıkış Yap
            </Button>
          </>
        ) : (
          <>
            <Button color="inherit" onClick={() => navigate('/login')}>
              Giriş Yap
            </Button>
            <Button color="inherit" onClick={() => navigate('/register')}>
              Kayıt Ol
            </Button>
          </>
        )}
      </Toolbar>
    </AppBar>
  );
};

export default NavBar;
