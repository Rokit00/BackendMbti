import React from "react";
import Logo from "../../assets/logo.png";
import { Link } from "react-router-dom";
import styles from "./NavBar.module.css";

const NavBar = () => {
  return (
    <nav className={styles.navBar}>
      <div className={`${styles.navList}`}>
        <div>
          <Link to="/">
            <img className={`${styles.logo}`} src={Logo} alt="logo" />
          </Link>
        </div>
        <div className={`${styles.navItem}`}>
          <Link className={`${styles.navLink}`} to="/dibate-list/hot">
            HOT🔥
          </Link>
        </div>
        <div className={`${styles.navItem}`}>
          <Link className={`${styles.navLink}`} to="/section2">
            케미확인
          </Link>
        </div>
      </div>
      <div className={`${styles.navList}`}>
        <div className={`${styles.navItem}`}>
          <Link className={`${styles.navLink} ${styles.smallText}`} to="/login">
            로그인
          </Link>
        </div>
        <div className={`${styles.navItem}`}>
          <Link
            className={`${styles.navLink} ${styles.smallText}`}
            to="/signup"
          >
            회원가입
          </Link>
        </div>
      </div>
    </nav>
  );
};

export default NavBar;
