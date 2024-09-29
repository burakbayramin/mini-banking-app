import { useState, useContext } from 'react';
import axios from '../api';
import { AppContext } from '../context/AppContext';
import { useParams, useNavigate } from 'react-router-dom';

const AccountDetails = () => {
  const { accounts, setAccounts } = useContext(AppContext);
  const { id } = useParams();
  const navigate = useNavigate();
  const account = accounts.find((acc) => acc.id === id);

  const [isEditing, setIsEditing] = useState(false);
  const [accountData, setAccountData] = useState({ name: account.name, number: account.number });

  if (!account) return <div>Hesap bulunamadı.</div>;

  const handleUpdate = () => {
    axios
      .put(`/accounts/${id}`, accountData)
      .then((response) => {
        setAccounts((prevAccounts) =>
          prevAccounts.map((acc) => (acc.id === id ? response.data : acc))
        );
        setIsEditing(false);
      })
      .catch((error) => {
        console.error('Hesap güncellenemedi:', error);
      });
  };

  const handleDelete = () => {
    axios
      .delete(`/accounts/${id}`)
      .then(() => {
        setAccounts((prevAccounts) => prevAccounts.filter((acc) => acc.id !== id));
        navigate('/');
      })
      .catch((error) => {
        console.error('Hesap silinemedi:', error);
      });
  };

  return (
    <div>
      <h2>{account.name}</h2>
      <p>Numara: {account.number}</p>
      <p>Bakiye: {account.balance}</p>
      <button onClick={() => navigate(`/transactions/${account.id}`)}>İşlem Geçmişini Gör</button>
      {isEditing ? (
        <>
          <input
            type="text"
            value={accountData.name}
            onChange={(e) => setAccountData({ ...accountData, name: e.target.value })}
          />
          <input
            type="text"
            value={accountData.number}
            onChange={(e) => setAccountData({ ...accountData, number: e.target.value })}
          />
          <button onClick={handleUpdate}>Güncelle</button>
          <button onClick={() => setIsEditing(false)}>İptal</button>
        </>
      ) : (
        <>
          <button onClick={() => setIsEditing(true)}>Düzenle</button>
          <button onClick={handleDelete}>Sil</button>
        </>
      )}
    </div>
  );
};

export default AccountDetails;
