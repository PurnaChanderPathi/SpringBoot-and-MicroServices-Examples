import logo from './logo.svg';
import './App.css';
import Header from './components/header/Header.js'
import Amlak from './components/body/Amlak.js';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import PPCDetails from './components/body/PPCDetails.js';
import CaseInformation from './components/body/CaseInformation.js';

function App() {
  return (
    <div className="App">
      {/* <Header/> */}
          <Router>
            <Routes>
              <Route path="/" element={<Amlak/>} />
              <Route path="/PPCDetails" element={<PPCDetails/>}/>
              <Route path="/CaseInformation/:reviewId" element={<CaseInformation/>} />
            </Routes>
          </Router>
    </div>
  );
}

export default App;
