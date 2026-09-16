import { useState } from 'react';
import { api } from '../Api';

function Transferencia() {
  const [form, setForm] = useState({ senderAccountNumber: '', receiverAccountNumber: '', amount: '' });
  const [msg, setMsg] = useState('');

  const enviar = (e) => {
    e.preventDefault();
    api.post('/api/transactions', { ...form, amount: parseFloat(form.amount) })
      .then(res => {
        setMsg(`✅ Transferencia exitosa. ID: ${res.data.id}`);
        setForm({ senderAccountNumber: '', receiverAccountNumber: '', amount: '' });
      })
      .catch(err => setMsg(`❌ Error: ${err.response?.data || err.message}`));
  };

  return (
    <div>
      <h2>Realizar Transferencia</h2>
      <form onSubmit={enviar}>
        <input placeholder="Cuenta origen" value={form.senderAccountNumber} onChange={e => setForm({...form, senderAccountNumber: e.target.value})} required /><br/><br/>
        <input placeholder="Cuenta destino" value={form.receiverAccountNumber} onChange={e => setForm({...form, receiverAccountNumber: e.target.value})} required /><br/><br/>
        <input placeholder="Monto" type="number" value={form.amount} onChange={e => setForm({...form, amount: e.target.value})} required /><br/><br/>
        <button type="submit">Transferir</button>
      </form>
      {msg && <p>{msg}</p>}
    </div>
  );
}

export default Transferencia;
