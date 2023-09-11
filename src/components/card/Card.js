import React, { useState, useEffect } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import { FaBookmark, FaShare, FaComment, FaEye } from "react-icons/fa";
import styles from "./DebateCard.module.css";

const DebateCard = ({ debate }) => {
  const { id, title, A, B, messages, views, likes, isUnderway, hashtags } =
    debate;

  const [comments, setComments] = useState([]);

  useEffect(() => {
    async function fetchComments() {
      try {
        const response = await axios.get(`/comment/${id}`);
        setComments(response.data);
      } catch (error) {
        console.error("Error fetching the comments", error);
      }
    }

    fetchComments();
  }, [id]);

  const opinionCounts = comments.reduce(
    (acc, comment) => {
      if (comment.selectOption === "A") acc.A++;
      if (comment.selectOption === "B") acc.B++;
      return acc;
    },
    { A: 0, B: 0 }
  );

  const totalOpinions = opinionCounts.A + opinionCounts.B;
  const percentageA =
    totalOpinions === 0
      ? 0
      : Math.round((opinionCounts.A / totalOpinions) * 100);
  const percentageB =
    totalOpinions === 0
      ? 0
      : Math.round((opinionCounts.B / totalOpinions) * 100);

  return (
    <Link to={`/post/${id}`} className={styles.container}>
      <div>
        {isUnderway && (
          <small className={styles.underwayText}>현재 진행 중인 토론</small>
        )}
      </div>

      <div className={styles.cardInfo}>
        <h3 className={styles.title}>{title}</h3>
        <p className={styles.percent}>
          A. {percentageA}% vs B. {percentageB}%
        </p>
        <div className={styles.options}>
          <p className={styles.option}>{A}</p>
          <p className={styles.option}>{B}</p>
        </div>
        <div className={styles.hashtags}>{hashtags}</div>
      </div>

      <div className={styles.icons}>
        <div className={styles.iconWrapper}>
          <FaComment />
          <span>{messages}</span>
        </div>
        <div className={styles.iconWrapper}>
          <FaEye />
          <span>{views}</span>
        </div>
        <div className={styles.iconWrapper}>
          <FaBookmark />
        </div>
        <div className={styles.iconWrapper}>
          <FaShare />
        </div>
      </div>
    </Link>
  );
};

export default DebateCard;
