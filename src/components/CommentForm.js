// CommentForm.js
import React, { useState, useContext } from "react";
import styles from "./CommentForm.module.css";
import { useAuth } from "./AuthContext";
import axios from "axios";

const CommentForm = ({ onSubmit, writeNum }) => {
  const [newComment, setNewComment] = useState("");
  const [selectedOpinion, setSelectedOpinion] = useState(null);
  const { isLoggedIn } = useAuth();
  const authContext = useContext(useAuth);
  const handleSubmit = () => {
    if (newComment && selectedOpinion) {
      const newCommentData = {
        userId: authContext.userId,
        userImage:
          authContext.userImage || "https://yourDefaultUserImageUrl.com",
        opinion: selectedOpinion,
        comment: newComment,
        likes: 0,
        date: new Date().toISOString(),
        write_num: writeNum,
      };
      onSubmit(newCommentData);
      setNewComment("");
      setSelectedOpinion(null);
    }
  };

  return (
    <div className={styles.inputContainer}>
      <button
        className={`${styles.opinionButton} ${
          selectedOpinion === "A" ? styles.selectedA : ""
        }`}
        onClick={() => setSelectedOpinion("A")}
        disabled={!isLoggedIn}
      >
        A
      </button>
      <button
        className={`${styles.opinionButton} ${
          selectedOpinion === "B" ? styles.selectedB : ""
        }`}
        onClick={() => setSelectedOpinion("B")}
        disabled={!isLoggedIn}
      >
        B
      </button>
      <input
        className={styles.commentInput}
        value={newComment}
        onChange={(e) => setNewComment(e.target.value)}
        placeholder={
          isLoggedIn
            ? "Write your comment..."
            : "로그인 후에 이용 가능한 기능입니다."
        }
        disabled={!isLoggedIn}
      />
      <button
        className={styles.submitButton}
        onClick={handleSubmit}
        disabled={!isLoggedIn}
      >
        Submit
      </button>
    </div>
  );
};

export default CommentForm;
