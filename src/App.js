//App.js
import React, {useState} from "react";
import "./App.css";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Home from "./pages/home/Home";
import DebateList from "./pages/debate_list/DebateList";
import SectionTwo from "./pages/home/sections/SectionTwo"; // SectionTwo 컴포넌트 임포트
import DebateDetail from "./components/DebateDetail";
import LoginModal from "./components/LoginModal";
import Signup from "./pages/Signup";
import NewDebate from "./components/NewDebate";
import debateData from "./utils/debateData";

const App = () => {
  const [debates, setDebates] = useState(debateData);
  const handleCreate = (newDebate) => {
    setDebates((prevDebates) => [...prevDebates, newDebate]);
  };
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/lists" element={<DebateList debates={debates} />} />
        <Route path="/debate/:id" element={<DebateDetail />} />
        {/* <Route path="/section2" element={<SectionTwo />} /> */}
        <Route path="/signup" element={<Signup />} />
        <Route path="/login" element={<LoginModal />} />
        <Route
          path="/newDebate"
          element={<NewDebate onCreate={handleCreate} />}
        />
      </Routes>
    </Router>
  );
};

export default App;
