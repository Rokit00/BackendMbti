import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { useAuth } from "../AuthContext";
const KakaoCallback = () => {
  const navigate = useNavigate();
  const { isLoggedIn, setIsLoggedIn } = useAuth();
  useEffect(() => {
    const code = new URL(window.location.href).searchParams.get("code");
    console.log(code);

    axios
      .get(`/kakao?code=${code}`)
      .then((res) => {
        sessionStorage.setItem("token", res.data);
        setIsLoggedIn(true);
        navigate("/lists");
      })
      .catch((error) => {
        console.error("Error during Kakao login:", error);
      });
  }, []);

  return <div>로그인 중입니다.</div>;
};

export default KakaoCallback;
