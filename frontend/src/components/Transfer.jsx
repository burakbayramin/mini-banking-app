import { useState, useContext } from 'react';
import axios from '../api';
import { AppContext } from '../context/AppContext';

const Transfer = () => {
  const [transferData, setTransferData] = useState({ fromAccountId: '', toAccountId: '', amount: '' });
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const { setTransactions } = useContext(AppContext);

  const handleSubmit = (e) => {
    e.preventDefault();
    // Form Doğrulama
    if (!transferData.fromAccountId || !transferData.toAccountId || !transferData.amount) {
      setError('All fields must be filled.');
      return;
    }
    if (transferData.amount <= 0) {
      setError('The amount must be a positive number.');
      return;
    }
    setError('');

    axios
      .post('/transactions/transfer', transferData)
      .then((response) => {
        setSuccess('Transfer successful!');
        setTransactions((prevTransactions) => [response.data, ...prevTransactions]);
      })
      .catch((error) => {
        setError('Transfer failed!' + error.response.data.message);
      });
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>Money Transfer</h2>
      {error && <p style={{ color: 'red' }}>{error}</p>}
      {success && <p style={{ color: 'green' }}>{success}</p>}
      <input
        type="text"
        placeholder="Sender Account ID"
        value={transferData.fromAccountId}
        onChange={(e) => setTransferData({ ...transferData, fromAccountId: e.target.value })}
        required
      />
      <input
        type="text"
        placeholder="Recipient Account ID"
        value={transferData.toAccountId}
        onChange={(e) => setTransferData({ ...transferData, toAccountId: e.target.value })}
        required
      />
      <input
        type="number"
        placeholder="Amount"
        value={transferData.amount}
        onChange={(e) => setTransferData({ ...transferData, amount: e.target.value })}
        required
      />
      <button type="submit">Transfer</button>
    </form>
  );
};

export default Transfer;
