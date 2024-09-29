import { useEffect, useContext } from 'react';
import axios from '../api';
import { AppContext } from '../context/AppContext';
import { useParams } from 'react-router-dom';

const TransactionHistory = () => {
  const { transactions, setTransactions } = useContext(AppContext);
  const { accountId } = useParams();

  useEffect(() => {
    axios
      .get(`/transactions/account/${accountId}`)
      .then((response) => {
        setTransactions(response.data);
      })
      .catch((error) => {
        console.error('İşlem geçmişi yüklenemedi:', error);
      });
  }, [accountId]);

  const accountTransactions = transactions.filter(
    (transaction) =>
      transaction.fromAccountId === accountId || transaction.toAccountId === accountId
  );

  return (
    <div>
      <h2>İşlem Geçmişi</h2>
      <ul>
        {accountTransactions.map((transaction) => (
          <li key={transaction.id}>
            {transaction.transactionDate} - {transaction.amount} - {transaction.status}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default TransactionHistory;
