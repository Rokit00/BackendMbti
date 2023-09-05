import React, { useState } from "react";
import styles from "../components/NewDebate.module.css"
import NavBar from "./nav_bar/NavBar";
import { useNavigate } from "react-router-dom";
import axios from "axios";

function NewDebate() {
  const [newDebateData, setNewDebateData] = useState({
    title: "",
    A: "",
    B: "",
    hashtags: "",
    messages: 0,
    likes: 0,
    createdDate: new Date().getTime(),
  });

  const navigate = useNavigate();

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setNewDebateData({
      ...newDebateData,
      [name]: value,
    });
  };

  const handleCreate = async () => {
    const { title, A, B, hashtags, createdDate, likes } = newDebateData;

    if (!title || !A || !B || !hashtags) {
      return;
    }

    const newDebate = {
      title,
      optionA: A,
      optionB: B,
      hashtags,
      createAt: createdDate,
      messages: 0,
      hit: likes,
    };

    try {
      await axios.post("/sec3/writer", newDebate);
      handleCancel();
      navigate("/lists");
    } catch (error) {
      console.error("Error", error);
    }
  };

  const handleCancel = () => {
    setNewDebateData({
      title: "",
      A: "",
      B: "",
      hashtags: "",
    });
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
          <div className={styles.buttonWrapper}>
            <button onClick={() => navigate("/lists")}>취소</button>
            <button onClick={handleCreate}>작성하기</button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default NewDebate;
