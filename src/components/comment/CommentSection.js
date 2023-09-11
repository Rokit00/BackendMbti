// CommentSection.js
import React, { useRef, useState, useEffect, useCallback } from "react";
import CommentForm from "./CommentForm";
import UserInfo from "../UserInfo";
import CommentText from "./CommentText";
import styles from "./CommentSection.module.css";
import axios from "axios";

const CommentSection = ({ comments, postId }) => {
  console.log("CommentSection 렌더링됨");
  const [allComments, setAllComments] = useState(comments || []);

  const handleCommentSubmit = async (newCommentData) => {
    try {
      const response = await axios.post(`/comment/${postId}`, newCommentData);
      setAllComments((prevComments) => [...prevComments, response.data]);
    } catch (error) {
      console.error("Error posting the comment", error);
      alert("댓글 작성에 실패했습니다.");
    }
  };
  const endOfCommentsRef = useRef(null);
  useEffect(() => {
    if (endOfCommentsRef.current) {
      endOfCommentsRef.current.scrollIntoView({ behavior: "smooth" });
    }
  }, [allComments]);

  console.log(comments);
  return (
    <div>
      <div className={styles.commentsContainer}>
        <h3>All Comments</h3>
        {comments.map((comment) => (
          <div
            key={comment.id}
            className={`${styles.comment} ${
              comment.selectOption === "A" ? styles.opinionA : styles.opinionB
            }`}
          >
            <UserInfo
              userImage={comment.userImage}
              userId={comment.userId}
              opinion={comment.optionSelected}
            />
            <CommentText
              comment={comment.content}
              likes={comment.likeCount}
              date={comment.createdAt}
              opinion={comment.selectOption}
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
