import React from "react";
import { Link } from "react-router-dom";

function Home() {

  return(
    <div>
      <Link to='/login' style={{color: "white", fontSize: "30px"}}>로그인</Link>
      <Link to='/signup' style={{color: "white", margin: "10px", fontSize: "30px"}}>회원가입</Link>
    </div>
    
    
  );
}

export default Home;