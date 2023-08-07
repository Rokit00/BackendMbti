import React from "react";
import {Routes, Route} from 'react-router-dom';
import "./App.css";
import Signup from "./pages/Signup";
import LoginModal from "./component/loginModal";
import Home from "./pages/home";

function App(){
  return (
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<LoginModal />} />
        <Route path="/signup" element={<Signup />} /> 
      </Routes>
  );

}

export default App;