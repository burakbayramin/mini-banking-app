import { BrowserRouter as Router } from 'react-router-dom';
import Routes from './routes';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import NavBar from './components/NavBar';

// Tema oluşturma
const theme = createTheme({
  palette: {
    primary: {
      main: '#004481', // Banka mavisi
    },
    secondary: {
      main: '#ff6f00', // İkinci renk
    },
  },
});

const App = () => {
  return (
    <ThemeProvider theme={theme}>
      <Router>
        <NavBar />
        <Routes />
      </Router>
    </ThemeProvider>
  );
};

export default App;
