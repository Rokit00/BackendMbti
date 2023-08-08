import React from "react";
import {Routes, Route} from 'react-router-dom';
import "./App.css";
import Signup from "./pages/Signup";
import LoginModal from "./component/LoginModal";
import Home from "./pages/Home";

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