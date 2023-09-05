import React, { useState, useEffect } from "react";
import axios from "axios";
import styles from "./DebateDetail.module.css";
import OpinionBarChart from "./OpinionBarChart";
import TopComments from "./TopComments";
import CommentSection from "./CommentSection";
import { useParams } from "react-router-dom";
import NavBar from "./nav_bar/NavBar";
import CountdownTimer from "./Timer";

const DebateDetail = () => {
  const { id } = useParams();
  const [debate, setDebate] = useState(null);
  const [comments, setComments] = useState([]);
  useEffect(() => {
    async function fetchDebateDetail() {
      try {
        const response = await axios.get(`/sec3/${id}`);
        setDebate(response.data);
      } catch (error) {
        console.error("Error fetching the debate detail", error);
      }
    }

    fetchDebateDetail();
  }, [id]);

  useEffect(() => {
    async function fetchComments() {
      try {
        const response = await axios.get(`/sec3/debate/${id}`);
        setComments(response.data);
      } catch (error) {
        console.error("Error fetching the comments", error);
      }
    }

    fetchComments();
  }, [id]);

  if (!debate) {
    return <div>Loading...</div>;
  }

  const opinionCounts = comments.reduce(
    (acc, comment) => {
      if (comment.optionSelected === "A") acc.A++;
      if (comment.optionSelected === "B") acc.B++;
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
      <p className={styles.deadline}>토론 종료까지 남은 시간</p>
      <CountdownTimer createdDate={debate.createAt} />
      <TopComments topCommentsA={topCommentsA} topCommentsB={topCommentsB} />
      <CommentSection comments={comments} />
    </div>
  );
};

export default DebateDetail;
