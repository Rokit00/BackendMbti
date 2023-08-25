//Card.js
import React from "react";
import { Link } from "react-router-dom";
import { calculatePercentage } from "../../utils/calculatePercent";
import { FaBookmark, FaShare, FaComment, FaThumbsUp } from "react-icons/fa";
import styles from "./DebateCard.module.css";

const DebateCard = ({ debate, percentageA, percentageB }) => {
  const { title, A, B, messages, likes, isUnderway, hashtags, fileImageA, fileImageB } = debate;


  return (
    <Link to={`/debate/${debate.id}`} className={styles.container}>
      <div>
        {isUnderway && (
          <small className={styles.underwayText}>현재 진행 중인 토론</small>
        )}
      </div>
      <div className={styles.thumbnails}>
        <div className={styles.thumbnail}>
            {fileImageA && (
              <img 
                src={fileImageA}
                alt=""
                className={`${styles.thumbnailImage} ${styles.thumbnailA}`}
              />
            )}
          </div>
          <div className={styles.thumbnail}>
            {fileImageB && (
              <img 
                src={fileImageB}
                alt=""
                className={`${styles.thumbnailImage} ${styles.thumbnailB}`}
              />
            )}
        </div>
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
          <FaThumbsUp />
          <span>{likes}</span>
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
