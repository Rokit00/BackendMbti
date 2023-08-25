// SearchResults.js
import React from "react";
import Card from "./card/Card";
import SortSelect from "./SortSelect";
import commentsData from "../utils/commentsData";
import styles from "./card/DebateCard.module.css";
import { calculatePercentage } from "../utils/calculatePercent";

const SearchResults = ({ results, onSortChange }) => {
  return (
    <div>
      <h2>검색 결과</h2>
      <div className={styles.sortSelectContainer}>
        <SortSelect onSortChange={onSortChange} />
      </div>
      <div className={styles.cardRow}>
        {results.map((debate) => {
          const commentsForDebate = commentsData[debate.id] || [];
          const opinionACount = commentsForDebate.filter(
            (comment) => comment.opinion === "A"
          ).length;
          console.log("Opinion A Count:", opinionACount);

          const opinionBCount = commentsForDebate.filter(
            (comment) => comment.opinion === "B"
          ).length;
          console.log("Opinion B Count:", opinionBCount);
          const { percentageA, percentageB } = calculatePercentage(
            opinionACount,
            opinionBCount
          );

          return (
            <Card
              key={debate.id}
              debate={debate}
              percentageA={percentageA}
              percentageB={percentageB}
            />
          );
        })}
      </div>
    </div>
  );
};

export default SearchResults;
