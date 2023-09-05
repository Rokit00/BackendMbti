//DebateList.js
import React, { useState, useEffect } from "react";
import NavBar from "../../components/nav_bar/NavBar";
import styles from "./DebateList.module.css";
import { sortByLikes, sortByDate, sortByMessages } from "../../utils/sortUtils";
import axios from "axios";
import SearchResults from "../../components/SearchResults";
import { useAuth } from "../../components/AuthContext";
import { useNavigate } from "react-router-dom";

const DebateList = () => {
  const navigate = useNavigate();
  const { isLoggedIn } = useAuth();
  const [searchKeyword, setSearchKeyword] = useState("");
  const [sortOption, setSortOption] = useState("");
  const [debateComments, setDebateComments] = useState([]);
  const [searchResults, setSearchResults] = useState([]);
  useEffect(() => {
    async function fetchAllDebates() {
      try {
        const response = await axios.get("/sec3/posts");

        const promises = response.data.map(async (post) => {
          const postDate = new Date(post.createAt);
          const currentDate = new Date();
          const timeDiff = currentDate - postDate;
          const daysDiff = timeDiff / (1000 * 60 * 60 * 24);

          // 각 게시물에 대한 의견 A와 B의 댓글 수 가져오기
          const opinionResponse = await axios.get(
            `/sec3/3/commentAorB/${post.writerNum}`
          );
          const opinionData = opinionResponse.data;

          // 각 게시물의 댓글 수 가져오기
          const commentCountResponse = await axios.get(
            `/sec3/3/comment/${post.writerNum}`
          );
          const commentCount = commentCountResponse.data;

          return {
            id: post.writerNum,
            title: post.title,
            nickname: post.nickname,
            A: post.optionA,
            B: post.optionB,
            messages: commentCount,
            views: post.hit,
            likes: post.good,
            bookMark: post.bookmark,
            isUnderway: daysDiff <= 7,
            hashtags: [],
            opinionACount: opinionData.a,
            opinionBCount: opinionData.b,
            dates: post.createAt,
          };
        });

        const transformedData = await Promise.all(promises);
        setDebateComments(transformedData);
      } catch (error) {
        console.error("Error fetching all the debate comments", error);
      }
    }

    fetchAllDebates();
  }, []);
  const handleWriteDebate = () => {
    navigate("/newDebate");
  };
  useEffect(() => {
    let results = [...debateComments];

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
  }, [searchKeyword, sortOption, debateComments]);

  return (
    <div className={styles.listContainer}>
      <NavBar />
      <h2 className={styles.mainTitle}>전체 토론 주제</h2>
      <input
        type="text"
        onChange={(e) => setSearchKeyword(e.target.value)}
        placeholder="토론 주제 검색..."
      />
      <button>search</button>
      <div className={styles.cardContainer}>
        {searchResults.length > 0 && (
          <SearchResults results={searchResults} onSortChange={setSortOption} />
        )}
      </div>
      {isLoggedIn && <button onClick={handleWriteDebate}>토론 글 쓰기</button>}
    </div>
  );
};

export default DebateList;
