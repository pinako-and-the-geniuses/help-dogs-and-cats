import axios from "axios";
import { ANIMAL } from "public/config";
import { useEffect } from "react";

//) [시도 조회] 상세기능명세 테스트 중입니다.
export default function AinimalList() {
  const ANIMALKEY = process.env.REACT_APP_ANIMAL_KEY;

  const getData = () => {
    console.log(
      "요청 URL",
      `${ANIMAL}/abandonmentPublicSrvc/sido?numOfRows=3&pageNo=1&serviceKey=${ANIMALKEY}`
    );
    if (ANIMALKEY) {
      axios({
        url: `${ANIMAL}/abandonmentPublicSrvc/sido?numOfRows=3&pageNo=1&serviceKey=${ANIMALKEY}`,
        method: "GET",
      })
        .then((res) => {
          console.log("res");
          console.log(res);
        })
        .catch((err) => {
          console.log(err);
        });
    }
  };

  useEffect(() => {
    getData();
  }, []);

  return (
    <>
      <div>
        <span>하이</span>
      </div>
    </>
  );
}
