// SectionOne.js
import React from "react";
import "./SectionOne.css";

const SectionThree = ({ handleScrollToSectionTwo }) => {
  return (
    <div className="section-one">
      <p>토론 종료까지 남은 시간</p>
      <button>참여하기</button>
    </div>
  );
};

export default SectionThree;
