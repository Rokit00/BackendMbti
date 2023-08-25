// Home.js
import React, { useRef, useState } from "react";
import SectionOne from "./sections/SectionOne";
import SectionTwo from "./sections/SectionTwo";
import SectionThree from "./sections/SectionThree";

const Home = () => {
  const sectionTwoRef = useRef(null);
  const [sharedData, setSharedData] = useState(null);

  const handleScrollToSectionTwo = () => {
    sectionTwoRef.current.scrollIntoView({ behavior: "smooth" });
  };

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
      <SectionThree />
    </div>
  );
};

export default Home;
