import { useState, useContext } from 'react';
import axios from '../api';
import { AppContext } from '../context/AppContext';
import { useNavigate } from 'react-router-dom';

const CreateAccount = () => {
  const [accountData, setAccountData] = useState({ name: '', number: '' });
  const { setAccounts } = useContext(AppContext);
  const navigate = useNavigate();

  const handleSubmit = (e) => {
    e.preventDefault();
    axios
      .post('/accounts', accountData)
      .then((response) => {
        setAccounts((prevAccounts) => [...prevAccounts, response.data]);
        navigate('/');
      })
      .catch((error) => {
        console.error('Account creation failed:', error);
      });
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>Create Account</h2>
      <input
        type="text"
        placeholder="Account Name"
        value={accountData.name}
        onChange={(e) => setAccountData({ ...accountData, name: e.target.value })}
        required
      />
      <input
        type="text"
        placeholder="Account Number"
        value={accountData.number}
        onChange={(e) => setAccountData({ ...accountData, number: e.target.value })}
        required
      />
      <button type="submit">Create</button>
    </form>
  );
};

export default CreateAccount;
