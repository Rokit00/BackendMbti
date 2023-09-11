import React, { useState, useEffect } from "react";
import styles from "./Mypage.module.css";
import { Mypage } from "./Mypage";
import { useAuth } from "../../components/AuthContext"; // AuthContext의 경로를 넣어주세요.

const Myinfor = () => {
  const { userInfo } = useAuth();
  const [userDetails, setUserDetails] = useState({
    id: "",
    password: "",
    MBTI: "",
    nickname: "",
    email: "",
    birthdate: "",
  });

  useEffect(() => {
    if (userInfo) {
      setUserDetails({
        ...userDetails,
        id: userInfo.id,
        MBTI: userInfo.MBTI,
      });
    }
  }, [userInfo]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setUserDetails({
      ...userDetails,
      [name]: value,
    });
  };

  const handleSubmit = () => {
    // 여기에서 백엔드 API를 호출하여 정보를 업데이트하세요.
  };

  return (
    <section className={styles.Myinfo}>
      <Mypage />
      <div className={styles.Box2}>
        <div className={styles.infor}>
          <div className={styles.inputbox}>
            <label htmlFor="id">아이디</label>
            <input
              id="id"
              name="id"
              type="text"
              value={userDetails.id}
              onChange={handleChange}
            />
          </div>
          <div className={styles.inputbox}>
            <label htmlFor="MBTI">MBTI</label>
            <input
              id="MBTI"
              name="MBTI"
              type="text"
              value={userDetails.MBTI}
              onChange={handleChange}
            />
          </div>
          <button onClick={handleSubmit}>정보 수정</button>
        </div>
      </div>
    </section>
  );
};

export default Myinfor;
