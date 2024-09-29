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
      setError('Tüm alanlar doldurulmalıdır.');
      return;
    }
    if (transferData.amount <= 0) {
      setError('Tutar pozitif bir sayı olmalıdır.');
      return;
    }
    setError('');

    axios
      .post('/transactions/transfer', transferData)
      .then((response) => {
        setSuccess('Transfer başarılı!');
        setTransactions((prevTransactions) => [response.data, ...prevTransactions]);
      })
      .catch((error) => {
        setError('Transfer başarısız: ' + error.response.data.message);
      });
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>Para Transferi</h2>
      {error && <p style={{ color: 'red' }}>{error}</p>}
      {success && <p style={{ color: 'green' }}>{success}</p>}
      <input
        type="text"
        placeholder="Gönderen Hesap ID"
        value={transferData.fromAccountId}
        onChange={(e) => setTransferData({ ...transferData, fromAccountId: e.target.value })}
        required
      />
      <input
        type="text"
        placeholder="Alıcı Hesap ID"
        value={transferData.toAccountId}
        onChange={(e) => setTransferData({ ...transferData, toAccountId: e.target.value })}
        required
      />
      <input
        type="number"
        placeholder="Tutar"
        value={transferData.amount}
        onChange={(e) => setTransferData({ ...transferData, amount: e.target.value })}
        required
      />
      <button type="submit">Gönder</button>
    </form>
  );
};

export default Transfer;
