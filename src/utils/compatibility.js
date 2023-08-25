// compatibility.js

// 후에 백엔드에서 데이터를 가져와서 결과 값을 수정해야 함
// MBTI 각 유형의 케미를 정의해야하는 객체
// 현재는 알파벳으로 해놨고 후에 데이터를 불러서 작업할 예정
const mbtiCompatibility = {
  I: "I",
  E: "E",
  S: "S",
  N: "N",
  F: "F",
  T: "T",
  P: "P",
  J: "J",
};

// 두 MBTI 유형의 케미를 확인하는 함수
export const checkCompatibility = (mbti1, mbti2) => {
  // 4개의 MBTI 유형을 순회하며 궁합 확인
  for (let i = 0; i < 4; i++) {
    if (mbtiCompatibility[mbti1[i]] !== mbti2[i]) {
      // 백엔드 기능 구현이 이루어 지지 않은 상황이라 임시로
      // 궁합이 일치하지 않으면 아래의 값을 반환
      return "서로의 궁합이 좋지 않습니다.";
    }
  }
  // 여기도 똑같은 이유로 궁합이 모두 일치하면 해당값을 반환
  return "서로의 궁합이 좋습니다!";
};

// MBTI 유형 배열을 알파벳 형태로 변환하는 함수
export const mbtiToAlphabet = (mbti) => {
  if (!Array.isArray(mbti)) {
    return "";
  }

  // MBTI 유형 배열의 인덱스를 각 알파벳으로 변환하여 연결하여 반환
  return mbti.map((index) => Object.keys(mbtiCompatibility)[index]).join("");
};
