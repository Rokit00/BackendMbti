import React, { useState, useEffect } from "react";
import axios from "axios";
import styles from "./Mypage.module.css";
import { Mypage } from "./Mypage";
const Mychemilist = () => {
  const [myChemis, setMyChemis] = useState([]); // 내가 만든 케미 목록을 저장할 상태

  useEffect(() => {
    const fetchData = async () => {
      try {
        const token = sessionStorage.getItem("token");
        const headers = {
          Authorization: `Bearer ${token}`,
        };
        const response = await axios.get("/mypage/mbti", { headers });
        setMyChemis(response.data);
      } catch (error) {
        console.error("내 케미를 불러오는 중 에러가 발생했습니다", error);
      }
    };

    fetchData();
  }, []);

  return (
    <section className={styles.Myinfo}>
      <Mypage />
      <div className={styles.Box2}>
        <div className={styles.Mycontentlist}>
          <h1 className={styles.h1}>내가 만든 케미</h1>
          <p className={styles.p}>너를 위해 구웠지🍪</p>
          <ul>
            {myChemis.map((chemi, index) => (
              <li key={index}>
                <strong>{chemi.groupName}</strong>
                <ul>
                  {chemi.mbtiAndMembers.map((member, memberIndex) => (
                    <li key={memberIndex}>
                      {member.name} - {member.mbtiType}
                    </li>
                  ))}
                </ul>
              </li>
            ))}
          </ul>
        </div>
      </div>
    </section>
  );
};

export default Mychemilist;
