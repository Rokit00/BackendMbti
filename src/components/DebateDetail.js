import React from "react";
import styles from "./DebateDetail.module.css";
import debateData from "../utils/debateData";
import commentsData from "../utils/commentsData";
import OpinionBarChart from "./OpinionBarChart";
import TopComments from "./TopComments";
import CommentSection from "./CommentSection";
import { useParams } from "react-router-dom";
import NavBar from "./nav_bar/NavBar";
import CountdownTimer from "./Timer";

const DebateDetail = () => {
  const { id } = useParams();
  const debate = debateData.find((d) => d.id === Number(id));
  const comments = commentsData[id] || [];

  const opinionCounts = comments.reduce(
    (acc, comment) => {
      if (comment.opinion === "A") acc.A++;
      if (comment.opinion === "B") acc.B++;
      return acc;
    },
    { A: 0, B: 0 }
  );

  const topCommentsA = comments
    .filter((c) => c.opinion === "A")
    .sort((a, b) => b.likes - a.likes)[0];
  const topCommentsB = comments
    .filter((c) => c.opinion === "B")
    .sort((a, b) => b.likes - a.likes)[0];

  return (
    <div className={styles.detailContainer}>
      <NavBar />
      <h2>{debate.title}</h2>
      <OpinionBarChart
        opinionA={opinionCounts.A}
        opinionB={opinionCounts.B}
        choiceA={debate.A}
        choiceB={debate.B}
      />
      <p className={styles.deadline}>토론 종료까지 남은 시간</p>
      <CountdownTimer createdDate={debate.createdDate} />
      <TopComments topCommentsA={topCommentsA} topCommentsB={topCommentsB} />
      <CommentSection comments={comments} />
    </div>
  );
};

export default DebateDetail;
