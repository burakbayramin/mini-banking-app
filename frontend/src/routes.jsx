import { useContext } from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import Login from './components/Login';
import Register from './components/Register';
import Dashboard from './components/Dashboard';
import AccountDetails from './components/AccountDetails';
import CreateAccount from './components/CreateAccount';
import Transfer from './components/Transfer';
import TransactionHistory from './components/TransactionHistory';
import { AppContext } from './context/AppContext';

const AppRoutes = () => {
  const { authTokens } = useContext(AppContext);

  return (
    <Routes>
      <Route path="/login" element={!authTokens ? <Login /> : <Navigate to="/" />} />
      <Route path="/register" element={!authTokens ? <Register /> : <Navigate to="/" />} />
      <Route path="/" element={authTokens ? <Dashboard /> : <Navigate to="/login" />} />
      <Route path="/accounts/:id" element={authTokens ? <AccountDetails /> : <Navigate to="/login" />} />
      <Route path="/create-account" element={authTokens ? <CreateAccount /> : <Navigate to="/login" />} />
      <Route path="/transfer" element={authTokens ? <Transfer /> : <Navigate to="/login" />} />
      <Route path="/transactions/:accountId" element={authTokens ? <TransactionHistory /> : <Navigate to="/login" />} />
    </Routes>
  );
};

export default AppRoutes;
