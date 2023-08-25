import React from "react";
import UserInfo from "./UserInfo";
import CommentText from "./CommentText";
import styles from "./TopComments.module.css";

const TopComments = ({ topCommentsA, topCommentsB }) => {
  return (
    <div className={styles.commentsContainer}>
      <h3>Top Comments</h3>
      <div className={styles.topCommentsWrapper}>
        {topCommentsA && (
          <div className={`${styles.comment} ${styles.opinionA}`}>
            <UserInfo
              userImage={topCommentsA.userImage}
              userId={topCommentsA.userId}
              opinion="A"
            />
            <CommentText
              comment={topCommentsA.comment}
              opinion="A"
              likes={topCommentsA.likes}
            />
          </div>
        )}
        {topCommentsB && (
          <div className={`${styles.comment} ${styles.opinionB}`}>
            <UserInfo
              userImage={topCommentsB.userImage}
              userId={topCommentsB.userId}
              opinion="B"
            />
            <CommentText
              comment={topCommentsB.comment}
              opinion="B"
              likes={topCommentsB.likes}
            />
          </div>
        )}
      </div>
    </div>
  );
};

export default TopComments;
