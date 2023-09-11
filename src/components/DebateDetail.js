import React, { useState, useEffect } from "react";
import axios from "axios";
import styles from "./DebateDetail.module.css";
import OpinionBarChart from "./OpinionBarChart";
import TopComments from "./comment/TopComments";
import CommentSection from "./comment/CommentSection";
import { useParams } from "react-router-dom";
import NavBar from "./nav_bar/NavBar";

const DebateDetail = () => {
  const { id } = useParams();
  const [debate, setDebate] = useState(null);
  const [comments, setComments] = useState([]);

  useEffect(() => {
    async function fetchData() {
      try {
        const debateResponse = await axios.get(`/post/${id}`);
        setDebate(debateResponse.data);

        const commentsResponse = await axios.get(`/comment/${id}`);
        setComments(commentsResponse.data);
      } catch (error) {
        console.error("Error fetching the data", error);
      }
    }

    fetchData();
  }, [id]);
  if (!debate) {
    return <div>Loading...</div>;
  }

  const opinionCounts = comments.reduce(
    (acc, comment) => {
      if (comment.selectOption === "A") acc.A++;
      if (comment.selectOption === "B") acc.B++;
      return acc;
    },
    { A: 0, B: 0 }
  );

  const topCommentsA = comments
    .filter((c) => c.optionSelected === "A")
    .sort((a, b) => b.likes - a.likes)[0];
  const topCommentsB = comments
    .filter((c) => c.optionSelected === "B")
    .sort((a, b) => b.likes - a.likes)[0];
  console.log(comments);
  return (
    <div className={styles.detailContainer}>
      <NavBar />
      <h2>{debate.title}</h2>
      <OpinionBarChart
        opinionA={opinionCounts.A}
        opinionB={opinionCounts.B}
        choiceA={debate.optionA}
        choiceB={debate.optionB}
      />
      <TopComments topCommentsA={topCommentsA} topCommentsB={topCommentsB} />
      <CommentSection comments={comments} postId={id} />
    </div>
  );
};

export default DebateDetail;
