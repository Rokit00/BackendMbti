//Card.js
import React, { useState, useEffect } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import { FaBookmark, FaShare, FaComment, FaEye } from "react-icons/fa";
import styles from "./DebateCard.module.css";
import { calculatePercentage } from "../../utils/calculatePercent";

const DebateCard = ({ debate }) => {
  const { id, title, A, B, messages, views, likes, isUnderway, hashtags } =
    debate;

  const [commentCount, setCommentCount] = useState(0);
  const [opinionACount, setOpinionACount] = useState(0);
  const [opinionBCount, setOpinionBCount] = useState(0);

  useEffect(() => {
    async function fetchCommentCountAndOpinions() {
      try {
        const commentResponse = await axios.get(`/sec3/3/comment/${id}`);
        setCommentCount(commentResponse.data);

        const opinionResponse = await axios.get(`/sec3/3/commentAorB/${id}`);
        const opinionData = opinionResponse.data;
        setOpinionACount(opinionData.a);
        setOpinionBCount(opinionData.b);
      } catch (error) {
        console.error("Error fetching data for debate", error);
      }
    }
    fetchCommentCountAndOpinions();
  }, [id]);

  const { percentageA, percentageB } = calculatePercentage(
    opinionACount,
    opinionBCount
  );

  return (
    <Link to={`/debate/${id}`} className={styles.container}>
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
          <span>{commentCount}</span>
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
