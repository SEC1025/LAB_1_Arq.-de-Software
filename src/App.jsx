import { Routes, Route, Link } from 'react-router-dom';
import Clientes from './components/Clientes';
import Transferencia from './components/Transferencia';
import Historico from './components/Historico';

function App() {
  return (
    <div style={{ padding: '20px', fontFamily: 'Arial' }}>
      <h1>🏦 Banco Lab 1</h1>
      <nav style={{ marginBottom: '20px' }}>
        <Link to="/clientes" style={{ marginRight: '15px' }}>Clientes</Link>
        <Link to="/transferencia" style={{ marginRight: '15px' }}>Transferencia</Link>
        <Link to="/historico">Histórico</Link>
      </nav>
      <Routes>
        <Route path="/clientes" element={<Clientes />} />
        <Route path="/transferencia" element={<Transferencia />} />
        <Route path="/historico" element={<Historico />} />
        <Route path="/" element={<Clientes />} />
      </Routes>
    </div>
  );
}

export default App;