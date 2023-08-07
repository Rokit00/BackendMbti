import React, { useEffect, useState } from "react";
import styles from "../pages/signup.module.css";

function Signup() {
  const [userId, setUserId] = useState("");
  const [password, setPassword] = useState("");
  const [nick, setNick] = useState("");
  const [email, setEmail] = useState("");
  const [birthdate, setBirthdate] = useState("");
  const [term, setTerm] = useState(false);

  const [idValid, setIdValid] = useState(false);
  const [pwValid, setPwValid] = useState(false);
  const [emailValid, setEmailValid] = useState(false);
  const [birthdateValid, setBirthdateValid] = useState(false);
  const [notAllow, setNotAllow] = useState(true);

  const handleId = (e) => {
    setUserId(e.target.value);
    const regex = /^[A-Za-z0-9]{5,20}$/;
    if (regex.test(userId)) {
      setIdValid(true);
    } else {
      setIdValid(false);
    }
  };

  const handlePassword = (e) => {
    setPassword(e.target.value);
    const regex = /^[A-Za-z0-9]{8,20}$/;
    if (regex.test(password)) {
      setPwValid(true);
    } else {
      setPwValid(false);
    }
  };

  const handleEmail = (e) => {
    setEmail(e.target.value);
    const regEmail =
      /^[A-Za-z0-9]([-_.]?[A-Za-z0-9])*@[A-Za-z0-9]([-_.]?[A-Za-z0-9])*\.[A-Za-z]{2,3}$/;
    if (regEmail.test(email)) {
      setEmailValid(true);
    } else {
      setEmailValid(false);
    }
  };

  const handleBirthdate = (e) => {
    const value = e.target.value;
    setBirthdate(value);
    const today = new Date();
    const selecttedDate = new Date(value);
    setBirthdateValid(selecttedDate <= today);
  };

  useEffect(() => {
    if (idValid && pwValid && emailValid && birthdateValid && term) {
      setNotAllow(false);
      return;
    }
    setNotAllow(true);
  }, [idValid, pwValid, emailValid, birthdateValid, term]);

  return (
    <div className={styles.container}>
      <form className={styles.form}>
        <h1 className={styles.signup_title}>회원가입</h1>
        <div className={styles.form_element}>
          <label htmlFor="id">아이디</label>
          <div className={styles.idWrap}>
            <input
              id="id"
              value={userId}
              className={`${styles.input} ${styles.idInput}`}
              onChange={handleId}
              required
            />
            <button className={styles.doubleCheck}>중복체크</button>
          </div>

          <div className={styles.errorMessage}>
            {!idValid && userId.length > 0 && (
              <div>아이디는 6글자 이상의 영문과 숫자 조합으로 가능합니다.</div>
            )}
          </div>
        </div>

        <div className={styles.form_element}>
          <label htmlFor="password">비밀번호</label>
          <input
            type="password"
            id="password"
            value={password}
            className={styles.input}
            onChange={handlePassword}
            required
          />
          <div className={styles.errorMessage}>
            {!pwValid && password.length > 0 && (
              <div>
                비밀번호는 8글자 이상의 영문과 숫자 조합으로 가능합니다.
              </div>
            )}
          </div>
        </div>

        <div className={styles.form_element}>
          <label htmlFor="nickname">닉네임</label>
          <input
            id="nickname"
            value={nick}
            className={styles.input}
            onChange={(e) => setNick(e.target.value)}
            required
          />
          <div className={styles.errorMessage}>
            {nick.length === 0 ? <div>필수 입력 사항입니다.</div> : null}
          </div>
        </div>

        <div className={styles.form_element}>
          <label htmlFor="email">이메일</label>
          <input
            type="email"
            id="email"
            value={email}
            className={styles.input}
            onChange={handleEmail}
            required
          />
          <div className={styles.errorMessage}>
            {!emailValid && email.length > 0 && (
              <div>올바른 이메일 형식이 아닙니다.</div>
            )}
          </div>
        </div>

        <div className={styles.form_element}>
          <label htmlFor="birthdate">생년월일</label>
          <input
            type="date"
            id="birthdate"
            value={birthdate}
            className={styles.input}
            onChange={handleBirthdate}
            required
          />
          <div className={styles.errorMessage}>
            {!birthdateValid && birthdate.length > 0 && (
              <div>미래 날짜는 선택할 수 없습니다.</div>
            )}
          </div>
        </div>

        <div className={styles.checkbox}>
          <label>
            <input
              type="checkbox"
              name="term"
              value={term}
              onChange={(e) => setTerm(e.target.checked)}
              required
            />
            <span className={styles.black}>만 14세 이상입니다.</span>{" "}
            <span className={styles.red}>(필수)</span>
          </label>
        </div>
        <button disabled={notAllow} className={styles.signup_button}>
          가입하기
        </button>
      </form>

      <div className={styles.right}>약관 자리</div>
    </div>
  );
}

export default Signup;
