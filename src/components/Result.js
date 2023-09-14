import React, { useState } from "react";
import { checkCompatibility, mbtiToAlphabet } from "../utils/compatibility";
import LZString from "lz-string";
import styles from "./Result.module.css";
import LinkModal from "./LinkModal";
import axios from "axios";
import { useAuth } from "./AuthContext";

const UserData = ({
  savedData,
  selectedUserIndex,
  setSelectedUserIndex,
  mbtiTexts,
}) =>
  !savedData || savedData.length === 0 ? (
    <p>데이터가 없습니다.</p>
  ) : (
    <table className={styles["saved-data-table"]}>
      <tbody>
        {savedData.map((_, index) => {
          if (index % 2 !== 0) return null;
          return (
            <tr key={index}>
              <td>
                <div
                  key={index}
                  className={`${styles.user} ${
                    index === selectedUserIndex ? styles.selected : ""
                  }`}
                  onClick={() => setSelectedUserIndex(index)}
                >
                  <div className={styles.userContainer}>
                    <p className={styles.userName}>{savedData[index].name}님</p>
                    {savedData[index].mbti.map((textIndex, rowIndex) => {
                      const textValue = mbtiTexts[textIndex];
                      if (textIndex !== -1 && textValue) {
                        return (
                          <span
                            key={rowIndex}
                            className={`${styles.resultText} ${
                              styles[textValue]
                            } ${
                              index === selectedUserIndex ? styles.selected : ""
                            }`}
                          >
                            {textValue}
                          </span>
                        );
                      }
                      return null;
                    })}
                  </div>
                </div>
              </td>
              <td>
                <div
                  key={index + 1}
                  className={`${styles.user} ${
                    index + 1 === selectedUserIndex ? styles.selected : ""
                  }`}
                  onClick={() => setSelectedUserIndex(index + 1)}
                >
                  <div className={styles.userContainer}>
                    <p className={styles.userName}>
                      {savedData[index + 1]?.name}님
                    </p>
                    {savedData[index + 1]?.mbti.map((textIndex, rowIndex) => {
                      const textValue = mbtiTexts[textIndex];
                      if (textIndex !== -1 && textValue) {
                        return (
                          <span
                            key={rowIndex}
                            className={`${styles.resultText} ${
                              styles[textValue]
                            } ${
                              index + 1 === selectedUserIndex
                                ? styles.selected
                                : ""
                            }`}
                          >
                            {textValue}
                          </span>
                        );
                      }
                      return null;
                    })}
                  </div>
                </div>
              </td>
            </tr>
          );
        })}
      </tbody>
    </table>
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
                {name1}님({mbti1})과 {name2}님({mbti2}) 의 궁합 결과: <br></br>
                <br></br>
                <p className={styles.result}>{result} </p>
              </p>
            </div>
          );
        }
        return null;
      })}
    </div>
  );
};
const SaveGroupModal = ({
  closeModal,
  groupName,
  setGroupName,
  handleSaveGroup,
}) => {
  return (
    <div className={styles.modalOverlay}>
      <div className={styles.modalContent}>
        <h2>그룹 저장하기</h2>
        <input
          type="text"
          value={groupName}
          onChange={(e) => setGroupName(e.target.value)}
          placeholder="그룹명을 입력하세요."
        />
        <button onClick={handleSaveGroup}>그룹 저장</button>
        <button onClick={closeModal}>닫기</button>
      </div>
    </div>
  );
};
const Result = ({
  savedData,
  mbtiTexts,
  setShowContent,
  setShowResult,
  setSavedData,
}) => {
  const [showModal, setShowModal] = useState(false);
  const [sharedLink, setSharedLink] = useState("");
  const [selectedUserIndex, setSelectedUserIndex] = useState(0);
  const [groupName, setGroupName] = useState("");
  const [showSaveModal, setShowSaveModal] = useState(false);
  const { isLoggedIn } = useAuth();
  const token = sessionStorage.getItem("token");
  const handleGoBack = () => {
    setShowContent(false);
    setShowResult(false);
    setSavedData([]);
  };
  console.log(savedData);
  const saveMbtiGroup = async (groupName) => {
    const headers = {
      Authorization: `Bearer ${token}`,
    };
    const mbtiAndMemberRequests = savedData.map((member) => ({
      name: member.name,
      mbtiType: member.mbtiType,
    }));
    const data = {
      groupName: groupName,
      mbtiAndMembers: mbtiAndMemberRequests,
    };
    try {
      const response = await axios.post("/mypage/mbti", data, { headers });
      return response.data;
    } catch (error) {
      throw error;
    }
  };

  // 저장하기 버튼에 기능 추가
  const handleSaveGroup = async () => {
    try {
      const result = await saveMbtiGroup(groupName, savedData);
      console.log("그룹이 저장 되었습니다. ", result);
    } catch (error) {
      console.error("저장중 에러가 발생했습니다 ", error);
    }
  };
  const handleShareLink = () => {
    const host = window.location.host;
    const compressedData = LZString.compressToEncodedURIComponent(
      JSON.stringify(savedData)
    );
    const link = `http://mbtichemi.com/section2/${compressedData}`;
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
        <button className={styles.contentsbutton} onClick={handleShareLink}>
          링크 공유
        </button>
        {isLoggedIn && (
          <button
            className={styles.contentsbutton}
            onClick={() => setShowSaveModal(true)}
          >
            저장하기
          </button>
        )}
        <button className={styles.contentsbutton} onClick={handleGoBack}>
          처음화면으로 돌아가기
        </button>
      </div>
      {showModal && (
        <LinkModal
          sharedLink={sharedLink}
          handleCopyLink={handleCopyLink}
          closeModal={() => setShowModal(false)}
        ></LinkModal>
      )}
      {showSaveModal && (
        <SaveGroupModal
          groupName={groupName}
          setGroupName={setGroupName}
          handleSaveGroup={handleSaveGroup}
          closeModal={() => setShowSaveModal(false)}
        />
      )}
    </div>
  );
};

export default Result;
