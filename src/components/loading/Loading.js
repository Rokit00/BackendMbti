import React, { useMemo } from "react";
import styles from "./Loading.module.css";

const Loading = () => {
  const satellites = useMemo(
    () => [
      { i: 0, top: 80, src: "https://cdn.simpleicons.org/javascript" },
      { i: 20, top: 30, src: "https://cdn.simpleicons.org/javascript" },
      { i: 40, top: 120, src: "https://cdn.simpleicons.org/javascript" },
      { i: 60, top: 70, src: "https://cdn.simpleicons.org/javascript" },
      { i: 80, top: 10, src: "https://cdn.simpleicons.org/javascript" },
      { i: 100, top: 90, src: "https://cdn.simpleicons.org/javascript" },
      { i: 120, top: 40, src: "https://cdn.simpleicons.org/javascript" },
      { i: 140, top: 30, src: "https://cdn.simpleicons.org/javascript" },
      { i: 160, top: 20, src: "https://cdn.simpleicons.org/javascript" },
      { i: 180, top: 90, src: "https://cdn.simpleicons.org/javascript" },
      { i: 200, top: 110, src: "https://cdn.simpleicons.org/javascript" },
      { i: 220, top: 20, src: "https://cdn.simpleicons.org/javascript" },
      { i: 240, top: 70, src: "https://cdn.simpleicons.org/javascript" },
      { i: 260, top: 80, src: "https://cdn.simpleicons.org/javascript" },
      { i: 280, top: 10, src: "https://cdn.simpleicons.org/javascript" },
      { i: 300, top: 20, src: "https://cdn.simpleicons.org/javascript" },
      { i: 320, top: 120, src: "https://cdn.simpleicons.org/javascript" },
      { i: 340, top: 20, src: "https://cdn.simpleicons.org/javascript" },
    ],
    []
  );

  return (
    <div className={styles["satellites-container"]}>
      <div className={styles["loading"]}>Loading...</div>
      <div className={styles["satellites"]}>
        {satellites.map((satellite, index) => (
          <img
            key={index}
            className={styles["satellite"]}
            style={{
              "--i": satellite.i,
              top: satellite.top,
            }}
            src={satellite.src}
            alt={`Satellite ${index}`}
          />
        ))}
      </div>
    </div>
  );
};

export default Loading;
