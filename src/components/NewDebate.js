import React, { useState } from "react";
import styles from "./NewDebate.module.css";
import NavBar from "./nav_bar/NavBar";
import { useNavigate } from "react-router-dom";
import debateData from "../utils/debateData";

function NewDebate({ onCreate }) {
  const [newDebateData, setNewDebateData] = useState({
    title: "",
    A: "",
    B: "",
    hashtags: "",
    messages: 0,
    likes: 0,
  });

  const navigate = useNavigate();

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setNewDebateData({
      ...newDebateData,
      [name]: value,
    });
  };

  const handleCreate = () => {
    const newDebate = {
      ...newDebateData,
      id: debateData.length + 1,
      createdDate: new Date().getTime(),
      fileImageA, 
      fileImageB
    };
    debateData.push(newDebate);
    onCreate(newDebate);
    handleCancel();
    navigate("/lists");
  };

  const handleCancel = () => {
    setNewDebateData({
      title: "",
      A: "",
      B: "",
      hashtags: "",
    });
  };

  const [fileImageA, setFileImageA] = useState(null); 
  const [fileImageB, setFileImageB] = useState(null); 

  const saveFileImageA = (e) => {
    const file = e.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = (e) => {
        setFileImageA(e.target.result); 
      };
      reader.readAsDataURL(file);
    }
  };

  const saveFileImageB = (e) => {
    const file = e.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = (e) => {
        setFileImageB(e.target.result); 
      };
      reader.readAsDataURL(file);
    }
  };

  return (
    <div className={styles.newDebate}>
      <NavBar />
      <div className={styles.container}>
        <h2 className={styles.title}>토론 작성하기</h2>
        <div className={styles.formWrpper}>
          <div className={styles.formContext}>
            <p className={styles.label}>토론 주제:</p>
            <input
              type="text"
              name="title"
              value={newDebateData.title}
              onChange={handleInputChange}
              className={styles.input}
            ></input>
          </div>
          <div className={styles.formContext}>
            <p className={styles.label}>해쉬 태그:</p>
            <input
              type="text"
              name="hashtags"
              value={newDebateData.hashtags}
              onChange={handleInputChange}
              className={styles.input}
            ></input>
          </div>
          <div className={styles.formContext}>
            <p className={styles.label}>A 선택자:&nbsp;</p>
            <input
              type="text"
              name="A"
              value={newDebateData.A}
              onChange={handleInputChange}
              className={styles.input}
            ></input>
          </div>
          <div className={styles.formContext}>
            <p className={styles.label}>B 선택자:&nbsp;</p>
            <input
              type="text"
              name="B"
              value={newDebateData.B}
              onChange={handleInputChange}
              className={styles.input}
            ></input>
          </div>
          <div className={styles.formContext}>
            <p className={styles.label}>A 선택자 썸네일:</p>
            <div>
            {fileImageA && (
                  <img
                    alt=""
                    src={fileImageA}
                    className={styles.fileImage}
                  />
                )}
            </div>
            <input 
              type="file" 
              accept="image/jpg, image/jpeg, image/png" 
              name="fileImageA"
              onChange={saveFileImageA}/>
        </div>
          <div className={styles.formContext}>
            <p className={styles.label}>B 선택자 썸네일:</p>
            <div>
            {fileImageB && (
                  <img
                    alt=""
                    src={fileImageB}
                    className={styles.fileImage}
                  />
                )}
            </div>
            <input 
              type="file" 
              accept="image/jpg, image/jpeg, image/png"
              name="fileImageB"
              onChange={saveFileImageB}/>
          </div>
          <div className={styles.buttonWrapper}>
            <button onClick={handleCancel}>취소</button>
            <button onClick={handleCreate}>작성하기</button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default NewDebate;
