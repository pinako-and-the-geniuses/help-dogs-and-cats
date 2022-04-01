import axios from "axios";
import XMLParser from "react-xml-parser";
import { ANIMAL } from "public/config";
import { useEffect, useState } from "react";
import animal from "./styles/Animal.module.scss";

//) [시군구 조회]
export default function GetShelter(props) {
  const ANIMALKEY = process.env.REACT_APP_ANIMAL_KEY;
  const sidoCode = props.sidoCode;
  const [sigungu, setSigungu] = useState({
    sigunguCode: [],
    sigunguName: [],
  });

  // xml을 json으로 바꾸고 원하는 데이터 뽑아 저장하기
  function parseStr(dataSet) {
    const arr = new XMLParser().parseFromString(dataSet).children;
    const data = arr[1].children[0].children;
    const sigunguCode = data.map((val) => val.children[0].value);
    const sigunguName = data.map((val) => val.children[1].value);

    setSigungu({ sigunguCode, sigunguName });
  }

  // 시도 조회하기
  async function getAPI() {
    if (ANIMALKEY) {
      axios({
        url: `${ANIMAL}/abandonmentPublicSrvc/sigungu?upr_cd=${sidoCode}&serviceKey=${ANIMALKEY}&_type=0`,
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
  return (
    <select className={animal.textBox} aria-label="보호센터">
      <option selected>보호센터</option>
      <option value="1">One</option>
      <option value="2">Two</option>
      <option value="3">Three</option>
    </select>
  );
}
