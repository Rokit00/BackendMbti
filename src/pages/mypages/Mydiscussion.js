import React, { useState, useEffect } from "react";
import axios from "axios";
import styles from "./Mypage.module.css";
import { Mypage } from "./Mypage";
import { useAuth } from "../../components/AuthContext"; // 실제 useAuth의 경로로 변경

const Mydiscussion = () => {
  const [posts, setPosts] = useState([]);
  const { userInfo } = useAuth();

  console.log(userInfo);
  useEffect(() => {
    if (userInfo) {
      const userId = userInfo.id; // 실제 유저 정보에 맞게 변경이 필요합니다.
      axios
        .get(`/mypage/${userId}/posts`)
        .then((response) => {
          setPosts(response.data);
        })
        .catch((error) => {
          console.error("Error fetching user posts", error);
        });
    }
  }, [userInfo]);

  return (
    <section className={styles.Myinfo}>
      <Mypage />
      <div className={styles.Box2}>
        <div className={styles.Mycontentlist}>
          <h1 className={styles.h1}>내가 만든 토론</h1>
          <p className={styles.p}>너를 위해 구웠지🍪</p>
          {posts.map((post) => (
            <div key={post.id}>
              {post.title} {/* 포스트의 제목, 다른 필드도 추가 가능 */}
            </div>
          ))}
        </div>
      </div>
    </section>
  );
};

export default Mydiscussion;
