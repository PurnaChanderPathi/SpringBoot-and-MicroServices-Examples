import logo from './logo.svg';
import './App.css';
import Header from './components/header/Header.js'
import Amlak from './components/body/Amlak.js';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import PPCDetails from './components/body/PPCDetails.js';
import CaseInformation from './components/body/CaseInformation.js';
import LoginScreen from './components/loginScreen/LoginScreen.js';
import PrivateRoute from './components/Authenticate/PrivateRoute.js';
import { Dashboard } from '@mui/icons-material';

function App() {
  return (
    <div className="App">
      {/* <Header/> */}
          <Router>
            <Routes>
              <Route path="/home" element={<Amlak/>} />
              <Route path="/PPCDetails" element={<PPCDetails/>}/>
              <Route path="/CaseInformation/:reviewId" element={<CaseInformation/>} />
              <Route path='/' element={<LoginScreen />} />
              <Route path='/dashboard' element={<PrivateRoute element={<Dashboard/>} />} />
            </Routes>
          </Router>
    </div>
  );
}

export default App;
