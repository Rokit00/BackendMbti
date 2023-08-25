// SectionOne.js
import React from "react";
import logoImage from "../../../assets/logo.png";
import "./SectionOne.css";

const SectionOne = ({ handleScrollToSectionTwo }) => {
  return (
    <div className="section-one">
      <div className="logo">
        <img src={logoImage} alt="Logo" />
      </div>
      <div className="banner">
        <img src={logoImage} alt="Banner" />
        <p className="banner-title">WE-checking</p>
        <p className="banner-info">
          내가 속한 그룹 또는 상대방과의 케미를 확인해보세요
        </p>{" "}
        <button onClick={handleScrollToSectionTwo}>확인하기</button>
      </div>
    </div>
  );
};

export default SectionOne;
