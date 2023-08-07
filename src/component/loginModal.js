import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import styles from "./login.module.css";
import logo from "../images/Rectangle 94.png";
import kakao from "../images/kakao_login.png";
import HorizonLine from "../utils/horizonLine";

function LoginModal() {
  const navigate = useNavigate();
  const handleSignUp = () => {
    navigate("/signup");
  };

  const [userId, setUserId] = useState("");
  const [password, setPassword] = useState("");

  const handleLogin = (e) => {
    e.preventDefault();

    console.log("User ID:", userId);
    console.log("Password:", password);
  };

  const handleID = (e) => setUserId(e.target.value);
  const handlePw = (e) => setPassword(e.target.value);

  return (
    <div className={styles.card}>
      <form id="form" className={styles.form} onSubmit={handleLogin}>
        <div>
          <img src={logo} alt="We-ing" className={styles.image} />
          <img src={kakao} className={styles.kakao_image} alt="kakao" />

          <HorizonLine text="   or  " />
        </div>
        <div className={styles.form_down}>
          <div className={styles.form_control}>
            <input
              type="text"
              id="username"
              placeholder="아이디"
              value={userId}
              onChange={handleID}
              required
            />
          </div>
          <div className={styles.form_control}>
            <input
              type="password"
              id="password"
              placeholder="비밀번호"
              value={password}
              onChange={handlePw}
              required
            />
          </div>
          <button className={styles.login_button}>로그인</button>
          <div className={styles.login_signup}>
            <div className={styles.login_signup_desc}>계정이 없으신가요?</div>
            <button className={styles.login_signup_link} onClick={handleSignUp}>
              가입하기
            </button>
          </div>
        </div>
      </form>
    </div>
  );
}
export default LoginModal;
