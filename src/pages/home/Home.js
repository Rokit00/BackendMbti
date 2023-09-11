// Home.js
import React, { useRef, useState, useEffect } from "react";
import SectionOne from "./sections/SectionOne";
import SectionTwo from "./sections/SectionTwo";
import SectionThree from "./sections/SectionThree";
import axios from "axios";

const Home = () => {
  const sectionTwoRef = useRef(null);
  const [sharedData, setSharedData] = useState(null);
  const [randomDebate, setRandomDebate] = useState(null);

  const handleScrollToSectionTwo = () => {
    sectionTwoRef.current.scrollIntoView({ behavior: "smooth" });
  };

  useEffect(() => {
    async function fetchRandomDebate() {
      try {
        const response = await axios.get("/post/lists");
        const debates = response.data;
        const randomIndex = Math.floor(Math.random() * debates.length);
        setRandomDebate(debates[randomIndex]);
      } catch (error) {
        console.error("Error fetching random debate", error);
      }
    }

    fetchRandomDebate();
  }, []);

  return (
    <div>
      <SectionOne handleScrollToSectionTwo={handleScrollToSectionTwo} />
      <div ref={sectionTwoRef}>
        <SectionTwo setSharedData={setSharedData} />
      </div>
      {sharedData && (
        <div className="sharedData">
          <h2>Shared Data:</h2>
          <pre>{JSON.stringify(sharedData, null, 2)}</pre>
        </div>
      )}
      {randomDebate && <SectionThree debate={randomDebate} />}
    </div>
  );
};

export default Home;
