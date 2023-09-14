import React from "react";
import styles from "./UserInfo.module.css";
const UserInfo = ({ userImage, userId, opinion, mbti }) => {
  const imagePath = `http://mbtichemi.com/images/${userImage}`;
  return (
    <div
      className={`${styles.userInfo} ${
        opinion === "A" ? styles.opinionA : styles.opinionB
      }`}
    >
      <img className={styles.profile} src={imagePath} alt="user profile" />
      <span>
        {userId} ({mbti})
      </span>
    </div>
  );
};

export default UserInfo;
