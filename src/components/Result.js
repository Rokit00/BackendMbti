import React, { useState } from "react";
import { checkCompatibility, mbtiToAlphabet } from "../utils/compatibility";
import LZString from "lz-string";
import styles from "./Result.module.css";
import LinkModal from "./LinkModal";

const UserData = ({
  savedData,
  selectedUserIndex,
  setSelectedUserIndex,
  mbtiTexts,
}) =>
  !savedData || savedData.length === 0 ? (
    <p>데이터가 없습니다.</p>
  ) : (
    <div className={styles.userData}>
      {savedData.map((user, index) => (
        <div
          key={index}
          className={`${styles.user} ${
            index === selectedUserIndex ? styles.selected : ""
          }`}
          onClick={() => setSelectedUserIndex(index)}
        >
          <div className={styles.userContainer}>
            <p className={styles.userName}>{user.name}님</p>
            <div className={styles.userImages}>
              {user.mbti.map((textIndex, rowIndex) => {
                const textValue = mbtiTexts[textIndex];
                if (textIndex !== -1 && textValue) {
                  return (
                    <div key={rowIndex} className={styles.resultTexts}>
                      <span
                        className={`${styles.resultText} ${styles[textValue]}
                          ${
                            index === selectedUserIndex ? styles.selected : ""
                          }`}
                        onClick={() => setSelectedUserIndex(index)}
                      >
                        {textValue}
                      </span>
                    </div>
                  );
                }
                return null;
              })}
            </div>
          </div>
        </div>
      ))}
    </div>
  );

const CompatibilityResult = ({ savedData, selectedUserIndex }) => {
  if (!savedData || savedData.length < 2)
    return <p>데이터가 충분하지 않습니다.</p>;

  const selectedUserData = savedData[selectedUserIndex];
  const mbti1 = mbtiToAlphabet(selectedUserData.mbti);
  const name1 = selectedUserData.name;

  return (
    <div className={styles.savedDataContainer}>
      {savedData.map((user, index) => {
        if (index !== selectedUserIndex) {
          const mbti2 = mbtiToAlphabet(user.mbti);
          const name2 = user.name;
          const result = checkCompatibility(mbti1, mbti2);
          return (
            <div
              key={`${name1}-${name2}`}
              className={`${styles.compatibilityResult} ${styles.fadeInOut}`}
            >
              <p>
                {name1}님({mbti1})과 {name2}님({mbti2}) 의 궁합 결과: {result}
              </p>
            </div>
          );
        }
        return null;
      })}
    </div>
  );
};

const Result = ({
  savedData,
  mbtiTexts,
  setShowContent,
  setShowResult,
  setSavedData,
  isLoggedIn,
}) => {
  const [showModal, setShowModal] = useState(false);
  const [sharedLink, setSharedLink] = useState("");
  const [selectedUserIndex, setSelectedUserIndex] = useState(0);

  const handleGoBack = () => {
    setShowContent(false);
    setShowResult(false);
    setSavedData([]);
  };

  const handleShareLink = () => {
    const host = window.location.host;
    const compressedData = LZString.compressToEncodedURIComponent(
      JSON.stringify(savedData)
    );
    const link = `http://${host}/section2/${compressedData}`;
    setSharedLink(link);
    setShowModal(true);
  };

  const handleCopyLink = () => {
    navigator.clipboard.writeText(sharedLink);
    setShowModal(false);
  };

  return (
    <div className={styles.resultContainer}>
      <div className={styles.content}>
        <div className={styles.leftContent}>
          <h2>Members</h2>
          <UserData
            savedData={savedData}
            selectedUserIndex={selectedUserIndex}
            setSelectedUserIndex={setSelectedUserIndex}
            mbtiTexts={mbtiTexts}
          />
        </div>
        <div className={styles.rightContent}>
          <h2>결과 화면</h2>
          <CompatibilityResult
            savedData={savedData}
            selectedUserIndex={selectedUserIndex}
            mbtiTexts={mbtiTexts}
          />
        </div>
      </div>
      <div className={styles.buttonContainer}>
        <button className={styles.shareButton} onClick={handleShareLink}>
          링크 공유
        </button>
        {isLoggedIn && (
          <button className={styles.saveButton} onClick={() => {}}>
            저장하기
          </button>
        )}
        <button className={styles.goBackButton} onClick={handleGoBack}>
          처음화면으로 돌아가기
        </button>
      </div>
      {showModal && (
        <LinkModal
          sharedLink={sharedLink}
          handleCopyLink={handleCopyLink}
          closeModal={() => setShowModal(false)}
        />
      )}
    </div>
  );
};

export default Result;
