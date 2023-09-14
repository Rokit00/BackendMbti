// CommentText.js
import React from "react";
import styles from "./CommentText.module.css";

import TimeAgo from 'react-timeago'
import koreanStrings from 'react-timeago/lib/language-strings/ko'
import buildFormatter from 'react-timeago/lib/formatters/buildFormatter'

const formatter = buildFormatter(koreanStrings);

/*
const formatDate = (inputDate) => {
  const date = new Date(inputDate);
  if (isNaN(date)) {
    return '';
  }
  
  const year = date.getFullYear();
  const month = (date.getMonth() + 1).toString().padStart(2, '0');
  const day = date.getDate().toString().padStart(2, '0');
  
  return `${year}-${month}-${day}`;
};*/

const CommentText = ({ comment, likes, date, opinion, onLike}) => (
  
  <div
    className={`${styles.text} ${
      opinion === "A" ? styles.opinionA : styles.opinionB
    }`}
  >
    {comment}
    <div className={styles.commentActions}>
      <span className={styles.likeButton} onClick={onLike}>👍 {likes || 0}</span>
      <span className={styles.reportButton}>🚫 신고하기</span>
      {/*<span className={styles.commentDate}>{formatDate(date)}</span>*/}
      <span className={styles.commentDate}>
        <TimeAgo date={date}  formatter={formatter}/>
      </span>
    </div>
  </div>
);

export default CommentText;
