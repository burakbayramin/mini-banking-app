import { useContext } from 'react';
import { AppContext } from '../context/AppContext';
import { useNavigate } from 'react-router-dom';
import AccountList from './AccountList';

const Dashboard = () => {
  const { logout } = useContext(AppContext);
  const navigate = useNavigate();

  return (
    <div>
      <h1>Dashboard</h1>
      <button onClick={logout}>Logout</button>
      <button onClick={() => navigate('/create-account')}>Create Account</button>
      <button onClick={() => navigate('/transfer')}>Transfer</button>
      <AccountList />
    </div>
  );
};

export default Dashboard;
