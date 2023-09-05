// SearchResults.js
import React from "react";
import Card from "./card/Card";
import SortSelect from "./SortSelect";

import styles from "./SearchResults.module.css";
import { calculatePercentage } from "../utils/calculatePercent";

const SearchResults = ({ results, onSortChange }) => {
  return (
    <div className={styles.fixedWidthContainer}>
      <h2>검색 결과</h2>
      <div className={styles.sortSelectContainer}>
        <SortSelect onSortChange={onSortChange} />
      </div>
      <div className={styles.cardRow}>
        {results.map((debate) => {
          const opinionACount = debate.opinionACount || 0; // default to 0 if undefined
          const opinionBCount = debate.opinionBCount || 0; // default to 0 if undefined
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
