import React from "react";
import styles from "./Start.module.css"; // Start 컴포넌트에 적용할 CSS 스타일 임포트

//솔지님 이미지 세트//
import Solji_A from "../assets/Solji_A.png";
import Solji_B from "../assets/Solji_B.png";

//수훈님 이미지 세트//
import Suhun_A from "../assets/Suhun_A.png";
import Suhun_B from "../assets/Suhun_B.png";

//연진님 이미지 세트//
import Yeonjin_A from "../assets/Yeonjin_A.png";
import Yeonjin_B from "../assets/Yeonjin_B.png";

//성환님 이미지 세트//
import Seonghwan_a from "../assets/Seonghwan_a.png";
import Seonghwan_b from "../assets/Seonghwan_b.png";

const Start = ({ handleStart }) => {
  return (
    // Start 컴포넌트의 주요 컨테이너
    <div className={styles.content}>
      <div className={styles["left-content"]}>
        <div className={styles.imgBox}>
          <img src={Solji_A} className={styles.Solji_A}></img>
          <img src={Solji_B} className={styles.Solji_B}></img>
        </div>

        <div className={styles.imgBox}>
          <img src={Suhun_A} className={styles.Suhun_A}></img>
          <img src={Suhun_B} className={styles.Suhun_B}></img>
        </div>
      </div>

      <div className={styles["middle-content"]}>
        <h3>케미 확인하기 쳌</h3> {/* 제목 */}
        <p>
          상대방과의 얼마나 잘맞는지 궁금한가요? <br></br>지금 바로 결과를
          확인해 보세요.
        </p>{" "}
        {/* 설명 */}
        {/* 시작하기 버튼, 'handleStart' 함수 실행 */}
        <button onClick={handleStart}>시작하기</button>{" "}
      </div>

      {/* 오른쪽 컨텐츠: 제목, 설명, 시작하기 버튼 */}
      <div className={styles["right-content"]}>
        <div className={styles.imgBox}>
          <img src={Seonghwan_a} className={styles.Seonghwan_a}></img>
          <img src={Seonghwan_b} className={styles.Seonghwan_b}></img>
        </div>
        <div className={styles.imgBox}>
          <img src={Yeonjin_A} className={styles.Yeonjin_A}></img>
          <img src={Yeonjin_B} className={styles.Yeonjin_B}></img>
        </div>
      </div>
    </div>
  );
};

export default Start;
