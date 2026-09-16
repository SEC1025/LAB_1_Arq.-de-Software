import { useState } from 'react';
import { api } from '../Api';

function Historico() {
  const [cuenta, setCuenta] = useState('');
  const [transacciones, setTransacciones] = useState([]);

  const buscar = (e) => {
    e.preventDefault();
    api.get(`/api/transactions/${cuenta}`)
      .then(res => setTransacciones(res.data))
      .catch(err => alert('Error: ' + (err.response?.data || err.message)));
  };

  return (
    <div>
      <h2>Histórico por Cliente</h2>
      <form onSubmit={buscar}>
        <input placeholder="Número de cuenta" value={cuenta} onChange={e => setCuenta(e.target.value)} required />
        <button type="submit">Buscar</button>
      </form>
      <table border="1" cellPadding="8" style={{ marginTop: '15px' }}>
        <thead>
          <tr><th>ID</th><th>Origen</th><th>Destino</th><th>Monto</th><th>Fecha</th></tr>
        </thead>
        <tbody>
          {transacciones.map(t => (
            <tr key={t.id}>
              <td>{t.id}</td><td>{t.senderAccountNumber}</td><td>{t.receiverAccountNumber}</td>
              <td>{t.amount}</td><td>{t.timestamp}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default Historico;
