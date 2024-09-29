import { createContext, useState, useEffect } from 'react';
import axios from '../api';
import PropTypes from 'prop-types';

export const AppContext = createContext();

export const AppProvider = ({ children }) => {
  const [authTokens, setAuthTokens] = useState(() => {
    const tokens = localStorage.getItem('tokens');
    return tokens ? JSON.parse(tokens) : null;
  });
  const [accounts, setAccounts] = useState([]);
  const [transactions, setTransactions] = useState([]);

  const login = (tokens) => {
    setAuthTokens(tokens);
    localStorage.setItem('tokens', JSON.stringify(tokens));
  };

  const logout = () => {
    setAuthTokens(null);
    localStorage.removeItem('tokens');
    setAccounts([]);
    setTransactions([]);
  };

  useEffect(() => {
    if (authTokens) {
      axios
        .get('/accounts')
        .then((response) => {
          setAccounts(response.data);
        })
        .catch((error) => {
          console.error('Hesaplar yüklenemedi:', error);
        });
    }
  }, [authTokens]);

  return (
    <AppContext.Provider
      value={{
        authTokens,
        login,
        logout,
        accounts,
        setAccounts,
        transactions,
        setTransactions,
      }}
    >
      {children}
    </AppContext.Provider>
  );
};

AppProvider.propTypes = {
  children: PropTypes.node.isRequired,
};
