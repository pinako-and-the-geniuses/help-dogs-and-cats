import axios from "axios";
import XMLParser from "react-xml-parser";
import { ANIMAL } from "public/config";
import { useEffect, useState } from "react";

//) [시도 조회] 상세기능명세 테스트 중입니다.
export default function AinimalList() {
  const ANIMALKEY = process.env.REACT_APP_ANIMAL_KEY;
  const [sido, setSido] = useState({
    sidoCode: [],
    sidoName: [],
  });

  function parseStr(dataSet) {
    const arr = new XMLParser().parseFromString(dataSet).children;
    const data = arr[1].children[0].children;
    const sidoCode = data.map((val) => val.children[0].value);
    const sidoName = data.map((val) => val.children[1].value);

    setSido({ sidoCode, sidoName });
  }

  async function getAPI() {
    if (ANIMALKEY) {
      axios({
        url: `${ANIMAL}/abandonmentPublicSrvc/sido?numOfRows=30&pageNo=1&serviceKey=${ANIMALKEY}&_type=0`,
        method: "GET",
      })
        .then((res) => {
          const dataSet = res.data;
          parseStr(dataSet);
        })
        .catch((err) => {
          console.log(err);
        });
    }
  }

  useEffect(() => {
    getAPI();
  }, []);

  console.log(sido.sidoCode, sido.sidoName);
  return (
    <>
      <div>
        <span>하이</span>
        {/* {getData} */}
      </div>
    </>
  );
}
