import { useEffect, useState } from 'react';
import { api } from '../Api';

function Clientes() {
  const [clientes, setClientes] = useState([]);
  const [form, setForm] = useState({ firstName: '', lastName: '', accountNumber: '', balance: '' });

  const cargar = () => {
    api.get('/api/customers').then(res => setClientes(res.data));
  };

  useEffect(() => { cargar(); }, []);

  const crear = (e) => {
    e.preventDefault();
    api.post('/api/customers', { ...form, balance: parseFloat(form.balance) })
      .then(() => { cargar(); setForm({ firstName: '', lastName: '', accountNumber: '', balance: '' }); })
      .catch(err => alert('Error: ' + (err.response?.data || err.message)));
  };

  return (
    <div>
      <h2>Clientes</h2>
      <form onSubmit={crear} style={{ marginBottom: '20px' }}>
        <input placeholder="Nombre" value={form.firstName} onChange={e => setForm({...form, firstName: e.target.value})} required style={{marginRight: '5px'}} />
        <input placeholder="Apellido" value={form.lastName} onChange={e => setForm({...form, lastName: e.target.value})} required style={{marginRight: '5px'}} />
        <input placeholder="Cuenta" value={form.accountNumber} onChange={e => setForm({...form, accountNumber: e.target.value})} required style={{marginRight: '5px'}} />
        <input placeholder="Saldo" type="number" value={form.balance} onChange={e => setForm({...form, balance: e.target.value})} required style={{marginRight: '5px'}} />
        <button type="submit">Crear</button>
      </form>
      <table border="1" cellPadding="8">
        <thead>
          <tr><th>ID</th><th>Nombre</th><th>Apellido</th><th>Cuenta</th><th>Saldo</th></tr>
        </thead>
        <tbody>
          {clientes.map(c => (
            <tr key={c.id}>
              <td>{c.id}</td><td>{c.firstName}</td><td>{c.lastName}</td>
              <td>{c.accountNumber}</td><td>{c.balance}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default Clientes;