// Signup.js
import React, { useEffect, useState } from "react";
import styles from "../pages/Signup.module.css";
import useInput from "../hook/useInput"; // Import the custom hook

function Signup() {
  const idValidator = (id) => /^[A-Za-z0-9]{6,20}$/.test(id);
  const pwValidator = (pw) => /^[A-Za-z0-9]{8,20}$/.test(pw);
  const emailValidator = (email) =>
    /^[A-Za-z0-9]([-_.]?[A-Za-z0-9])*@[A-Za-z0-9]([-_.]?[A-Za-z0-9])*\.[A-Za-z]{2,3}$/.test(
      email
    );
  const birthdateValidator = (date) => {
    const today = new Date();
    const selectedDate = new Date(date);
    return selectedDate <= today;
  };

  const userId = useInput("", idValidator);
  const password = useInput("", pwValidator);
  const email = useInput("", emailValidator);
  const birthdate = useInput("", birthdateValidator);
  const [nick, setNick] = useState("");
  const [term, setTerm] = useState(false);
  const [notAllow, setNotAllow] = useState(true);

  useEffect(() => {
    setNotAllow(
      !(
        userId.isValid &&
        password.isValid &&
        email.isValid &&
        birthdate.isValid &&
        term
      )
    );
  }, [
    userId.isValid,
    password.isValid,
    email.isValid,
    birthdate.isValid,
    term,
  ]);

  return (
    <div className={styles.container}>
      <form className={styles.form}>
        <h1 className={styles.signup_title}>회원가입</h1>
        <div className={styles.form_element}>
          <label htmlFor="id">아이디</label>
          <div className={styles.idWrap}>
            <input
              id="id"
              value={userId.value}
              className={`${styles.input} ${styles.idInput}`}
              onChange={userId.onChange}
              required
            />
            <button className={styles.doubleCheck}>중복체크</button>
          </div>

          <div className={styles.errorMessage}>
            {!userId.isValid && userId.value.length > 0 && (
              <div>아이디는 6글자 이상의 영문과 숫자 조합으로 가능합니다.</div>
            )}
          </div>
        </div>

        <div className={styles.form_element}>
          <label htmlFor="password">비밀번호</label>
          <input
            type="password"
            id="password"
            value={password.value}
            className={styles.input}
            onChange={password.onChange}
            required
          />
          <div className={styles.errorMessage}>
            {!password.isValid &&
              password.value.length > 0 && ( // Updated here
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
            value={email.value}
            className={styles.input}
            onChange={email.onChange}
            required
          />
          <div className={styles.errorMessage}>
            {!email.isValid && email.value.length > 0 && (
              <div>올바른 이메일 형식이 아닙니다.</div>
            )}
          </div>
        </div>

        <div className={styles.form_element}>
          <label htmlFor="birthdate">생년월일</label>
          <input
            type="date"
            id="birthdate"
            value={birthdate.value}
            className={styles.input}
            onChange={birthdate.onChange}
            required
          />
          <div className={styles.errorMessage}>
            {!birthdate.isValid && birthdate.length > 0 && (
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
