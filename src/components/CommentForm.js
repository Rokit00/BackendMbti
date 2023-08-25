// CommentForm.js
import React, { useState } from "react";
import styles from "./CommentForm.module.css";

const CommentForm = ({ onSubmit }) => {
  const [newComment, setNewComment] = useState("");
  const [selectedOpinion, setSelectedOpinion] = useState(null);

  const handleSubmit = () => {
    if (newComment && selectedOpinion) {
      const newCommentData = {
        userId: "tempUser",
        userImage: "https://yourDefaultUserImageUrl.com",
        opinion: selectedOpinion,
        comment: newComment,
        likes: 0,
        date: new Date().toISOString(),
      };
      onSubmit(newCommentData);
      setNewComment("");
    }
  };

  return (
    <div className={styles.inputContainer}>
      <button
        className={`${styles.opinionButton} ${
          selectedOpinion === "A" ? styles.selectedA : ""
        }`}
        onClick={() => setSelectedOpinion("A")}
      >
        A
      </button>
      <button
        className={`${styles.opinionButton} ${
          selectedOpinion === "B" ? styles.selectedB : ""
        }`}
        onClick={() => setSelectedOpinion("B")}
      >
        B
      </button>
      <input
        className={styles.commentInput}
        value={newComment}
        onChange={(e) => setNewComment(e.target.value)}
        placeholder="Write your comment..."
      />
      <button className={styles.submitButton} onClick={handleSubmit}>
        Submit
      </button>
    </div>
  );
};

export default CommentForm;
