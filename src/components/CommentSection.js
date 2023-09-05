// CommentSection.js
import React, { useRef, useState, useEffect } from "react";
import CommentForm from "./CommentForm";
import UserInfo from "./UserInfo";
import CommentText from "./CommentText";
import styles from "./CommentSection.module.css";
import { useAuth } from "./AuthContext";
import axios from "axios";

const CommentSection = ({ comments }) => {
  const [allComments, setAllComments] = useState(comments || []);
  const handleCommentSubmit = async (newCommentData) => {
    try {
      const response = await axios.post("/sec3", newCommentData);

      setAllComments([...comments, response.data]);
    } catch (error) {
      console.error("Error posting the comment", error);
      alert("댓글 작성에 실패했습니다.");
    }
  };
  const endOfCommentsRef = useRef(null);
  const { isLoggedIn, userInfo } = useAuth();
  useEffect(() => {
    if (endOfCommentsRef.current) {
      endOfCommentsRef.current.scrollIntoView({ behavior: "smooth" });
    }
  }, [comments]);

  console.log(comments);
  return (
    <div>
      <div className={styles.commentsContainer}>
        <h3>All Comments</h3>
        {comments.map((comment) => (
          <div
            key={comment.id}
            className={`${styles.comment} ${
              comment.optionSelected === "A" ? styles.opinionA : styles.opinionB
            }`}
          >
            <UserInfo
              userImage={comment.userImage}
              userId={comment.nickname}
              opinion={comment.optionSelected}
            />
            <CommentText
              comment={comment.comment}
              likes={comment.likeCount}
              date={comment.createdAt}
              opinion={comment.optionSelected}
            />
          </div>
        ))}
      </div>
      <CommentForm onSubmit={handleCommentSubmit} />
      <div ref={endOfCommentsRef}></div>
    </div>
  );
};

export default CommentSection;
