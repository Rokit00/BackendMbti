// SectionTwo.js
import React from "react";
import useSharedLogic from "../../../hook/useSharedLogic";
import Start from "../../../components/Start";
import Input from "../../../components/Input";
import Result from "../../../components/Result";
import styles from "./SectionTwo.module.css";
import Loading from "../../../components/loading/Loading";

const SectionTwo = ({ handleScrollToSectionTwo }) => {
  // useSharedLogic 커스텀 훅스를 사용하여 상태와 함수들을 가져옴
  const {
    name,
    selectedImageIndexes,
    showContent,
    setShowContent,
    showResult,
    setShowResult,
    showLoading,
    setShowLoading,
    savedData,
    setSavedData,
    images,
    handleNameChange,
    handleImageClick,
    handleStart,
    handleSave,
    handleShowResult,
    renderSelectedImages,
    renderSavedData,
    mbtiTexts, // 추가된 부분
  } = useSharedLogic();
  return (
    <div className={styles.sectionTwo}>
      {showLoading ? (
        <Loading />
      ) : !showContent && !showResult ? (
        <Start handleStart={handleStart} />
      ) : showContent && !showResult ? (
        <Input
          name={name}
          handleNameChange={handleNameChange}
          selectedImageIndexes={selectedImageIndexes}
          handleImageClick={handleImageClick}
          handleSave={handleSave}
          renderSavedData={renderSavedData}
          handleShowResult={handleShowResult}
          mbtiTexts={mbtiTexts}
        />
      ) : showContent && showResult ? (
        <Result savedData={savedData} mbtiTexts={mbtiTexts} />
      ) : null}
    </div>
  );
};

export default SectionTwo;
