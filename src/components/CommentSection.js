// CommentSection.js
import React, { useRef, useState, useEffect } from "react";
import CommentForm from "./CommentForm";
import UserInfo from "./UserInfo";
import CommentText from "./CommentText";
import styles from "./CommentSection.module.css";

const CommentSection = ({ comments: initialComments }) => {
  const [comments, setComments] = useState(initialComments);

  const endOfCommentsRef = useRef(null);

  useEffect(() => {
    if (endOfCommentsRef.current) {
      endOfCommentsRef.current.scrollIntoView({ behavior: "smooth" });
    }
  }, [comments]);

  const handleNewComment = (newCommentData) => {
    setComments((prevComments) => [...prevComments, newCommentData]);
  };

  return (
    <div>
      <div className={styles.commentsContainer}>
        <h3>All Comments</h3>
        {comments.map((comment) => (
          <div
            key={comment.userId}
            className={`${styles.comment} ${
              comment.opinion === "A" ? styles.opinionA : styles.opinionB
            }`}
          >
            <UserInfo
              userImage={comment.userImage}
              userId={comment.userId}
              opinion={comment.opinion}
            />
            <CommentText
              comment={comment.comment}
              likes={comment.likes}
              date={comment.date}
              opinion={comment.opinion}
            />
          </div>
        ))}
      </div>
      <CommentForm onSubmit={handleNewComment} />
      <div ref={endOfCommentsRef}></div>
    </div>
  );
};

export default CommentSection;
