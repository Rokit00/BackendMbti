//DebateList.js
import React, { useState, useEffect } from "react";
import NavBar from "../../components/nav_bar/NavBar";
import styles from "./DebateList.module.css";
import debateData from "../../utils/debateData"; // Importing the static data
import { sortByLikes, sortByDate, sortByMessages } from "../../utils/sortUtils"; // Importing the sorting functions
import { useNavigate } from "react-router-dom";
import SearchResults from "../../components/SearchResults"; // Importing the SearchResults component

const DibateList = () => {
  const navigate = useNavigate();

  const [searchKeyword, setSearchKeyword] = useState("");
  const [sortOption, setSortOption] = useState(""); // For storing the selected sort option
  const [searchResults, setSearchResults] = useState(debateData); // Start with all debates as initial results

  useEffect(() => {
    let results = [...debateData];

    if (searchKeyword.trim() !== "") {
      results = results.filter((debate) =>
        debate.title.toLowerCase().includes(searchKeyword.toLowerCase())
      );
    }

    switch (sortOption) {
      case "likes":
        results.sort(sortByLikes);
        break;
      case "date":
        results.sort(sortByDate);
        break;
      case "messages":
        results.sort(sortByMessages);
        break;
      default:
        break;
    }

    setSearchResults(results);
  }, [searchKeyword, sortOption]);

  return (
    <div className={styles.listContainer}>
      <NavBar />
      <h2 className={styles.mainTitle}>전체 토론 주제</h2>
      <div className={styles.cardContainer}>
        {searchResults.length > 0 && (
          <SearchResults results={searchResults} onSortChange={setSortOption} />
        )}
      </div>
      <button onClick={() => navigate("/newDebate")}>토론 글 쓰기</button>
    </div>
  );
};

export default DibateList;
