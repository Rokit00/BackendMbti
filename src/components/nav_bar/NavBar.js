import React, { useState } from "react";
import logo_login from "../../assets/logo_login.png";
import { Link } from "react-router-dom";
import styles from "./NavBar.module.css";
import { useAuth } from "../AuthContext";
import LoginModal from "../login/LoginModal";

const NavBar = () => {
  const { isLoggedIn, setIsLoggedIn } = useAuth();
  const [isModalOpen, setIsModalOpen] = useState(false);

  const openModal = () => {
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
  };

  const handleLogout = () => {
    // localStorage에서 token 삭제
    sessionStorage.removeItem("token");
    setIsLoggedIn(false);
  };

  return (
    <>
      <nav className={styles.navBar}>
        <div className={styles.navList}>
          <div>
            <Link to="/">
              <img className={styles.logo_login} src={logo_login} alt="logo" />
            </Link>
          </div>
          <div className={styles.navItem}>
            <Link className={styles.navLink} to="/lists">
              토론 리스트🔥
            </Link>
          </div>
          <div className={styles.navItem}>
            <Link className={styles.navLink} to="/section2">
              케미확인
            </Link>
          </div>
        </div>
        <div className={styles.navList}>
          {isLoggedIn ? (
            <>
              <div className={styles.navItem}>
                <span> 환영합니다</span>
              </div>
              <div className={styles.navItem}>
                <Link
                  className={`${styles.navLink} ${styles.smallText}`}
                  to="/lists"
                  onClick={handleLogout}
                >
                  로그아웃
                </Link>
              </div>
            </>
          ) : (
            <>
              <div className={styles.navItem}>
                <Link
                  className={`${styles.navLink} ${styles.smallText}`}
                  onClick={openModal}
                >
                  로그인
                </Link>
              </div>
              <div className={styles.navItem}>
                <Link
                  className={`${styles.navLink} ${styles.smallText}`}
                  to="/signup"
                >
                  회원가입
                </Link>
              </div>
            </>
          )}
        </div>
      </nav>
      {isModalOpen && <LoginModal closeModal={closeModal} />}
    </>
  );
};

export default NavBar;
