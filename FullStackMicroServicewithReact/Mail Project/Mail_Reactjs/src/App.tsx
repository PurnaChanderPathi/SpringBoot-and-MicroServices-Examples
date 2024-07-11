import Login from './MailProject/Login';
import MailPage from './MailProject/MailPage';
import { BrowserRouter as Router,Route,Routes} from 'react-router-dom';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import MassMailPage from './MailProject/MassMailPage';
import MailFolder from './MailProject/MailFolder';


const App = () => {
  return (
    <div>
      <ToastContainer/>
      <Router>
        <Routes>
          <Route path='/' element={<Login/>} />
          <Route path='/sendMail' element={<MailPage/>} />
          <Route path='/massMail' element={<MassMailPage/>} />
          <Route path='/folder' element={<MailFolder/>} />
        </Routes>
      </Router>
    </div>
  )
}

export default App
