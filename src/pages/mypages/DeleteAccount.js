import React from "react";
import axios from "axios";
import styles from "./Mypage.module.css";
import { Mypage } from "./Mypage";
import { useAuth } from "../../components/AuthContext";

const DeleteAccount = () => {
  const { user } = useAuth();

  const handleDeleteAccount = () => {
    if (
      window.confirm(
        "정말로 계정을 삭제하시겠습니까? 이 작업은 되돌릴 수 없습니다."
      )
    ) {
      const token = sessionStorage.getItem("token");
      axios
        .delete("/mypage/delete", {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        })
        .then((response) => {
          if (response.status === 204) {
            alert("계정이 성공적으로 삭제되었습니다.");
            // 여기서 로그아웃 로직 또는 로그인 페이지로 리다이렉트하는 로직을 추가할 수 있습니다.
          } else {
            alert("계정 삭제 중 문제가 발생했습니다.");
          }
        })
        .catch((error) => {
          alert("계정 삭제 중 오류가 발생했습니다. 다시 시도해 주세요.");
        });
    }
  };

  return (
    <section className={styles.Myinfo}>
      <Mypage />
      <div className={styles.Box2}>
        <button onClick={handleDeleteAccount}>계정 삭제</button>
      </div>
    </section>
  );
};

export default DeleteAccount;
