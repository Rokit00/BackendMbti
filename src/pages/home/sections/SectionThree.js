// SectionThree.js
import React from "react";
import { useNavigate } from "react-router-dom";
import OpinionBarChart from "../../../components/OpinionBarChart";
import CountdownTimer from "../../../components/Timer";
import styles from "./SectionThree.module.css";
import "./SectionOne.css";
const SectionThree = ({ debate }) => {
  const navigate = useNavigate();
  if (!debate) {
    return <div>Loading...</div>;
  }
  const handleWriteDebate = () => {
    navigate("/lists");
  };
  return (
    <div className="section-one">
      <h2>{debate.title}</h2>
      <OpinionBarChart
        opinionA={debate.opinionACount}
        opinionB={debate.opinionBCount}
        choiceA={debate.optionA}
        choiceB={debate.optionB}
      />
      <p>토론 종료까지 남은 시간</p>
      <CountdownTimer createdDate={debate.createAt} />
      <button className={styles.button} onClick={handleWriteDebate}>
        참여하기
      </button>
    </div>
  );
};

export default SectionThree;
