import axios from "axios";
import XMLParser from "react-xml-parser";
import { ANIMAL } from "public/config";
import { useEffect } from "react";
import animal from "./styles/Animal.module.scss";

const ANIMALKEY = process.env.REACT_APP_ANIMAL_KEY;

// [시군구 조회]
export default function GetSigunguList({
  sigunguData,
  setSigunguData,
  selected,
  setSelected,
}) {
  // 원하는 데이터 뽑아 저장하기
  const parseStr = (dataSet) => {
    const arr = new XMLParser().parseFromString(dataSet).children;
    const dataArr = arr[1].children[0].children;
    const sidoDataArr = dataArr.map((item) => {
      return {
        code: item.children[1].value,
        name: item.children[2].value,
      };
    });
    setSigunguData(sidoDataArr);
  };

  // 조회하기
  function getAPI() {
    if (ANIMALKEY) {
      axios({
        url: `${ANIMAL}/abandonmentPublicSrvc/sigungu?upr_cd=${selected.sidoCode}&serviceKey=${ANIMALKEY}&_type=0`,
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
  // 먼저!
  useEffect(() => {
    setSigunguData("");
    if (selected.sidoCode !== "0") {
      getAPI();
    }
  }, [selected.sidoCode]);

  return (
    <select
      defaultValue="0"
      className={animal.textBox}
      aria-label="시군구"
      onChange={(e) =>
        setSelected({
          sidoCode: selected.sidoCode,
          sigunguCode: e.target.value,
          shelterCode: "",
        })
      }
    >
      <option value="0">시군구</option>
      {sigunguData
        ? sigunguData.map((item) => {
            return (
              <option key={item.code} value={item.code}>
                {item.name}
              </option>
            );
          })
        : ""}
    </select>
  );
}
