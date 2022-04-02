import axios from "axios";
import XMLParser from "react-xml-parser";
import { ANIMAL } from "public/config";
import { useEffect } from "react";
import animal from "pages/animals/styles/Animal.module.scss";
const ANIMALKEY = process.env.REACT_APP_ANIMAL_KEY;

// [시도 조회]
export default function GetSido({
  setRegionUrl,
  sidoData,
  setSidoData,
  setSelected,
}) {
  // xml을 json으로 바꾸고 원하는 데이터 뽑아 저장하기
  const parseStr = (dataSet) => {
    const arr = new XMLParser().parseFromString(dataSet).children;
    const dataArr = arr[1].children[0].children;
    const sidoDataArr = dataArr.map((item) => {
      return {
        code: item.children[0].value,
        name: item.children[1].value,
      };
    });
    setSidoData(sidoDataArr);
  };

  // 시도 조회하기
  function getAPI() {
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

  return (
    <select
      defaultValue="0"
      className={animal.textBox}
      aria-label="시도"
      onChange={(e) => {
        setRegionUrl(`&upr_cd=${e.target.value}`);
        setSelected({
          sidoCode: e.target.value,
          sigunguCode: "",
          shelterCode: "",
        });
      }}
    >
      <option value="0">시도</option>

      {sidoData ? (
        sidoData.map((item) => {
          return (
            <option key={item.code} value={item.code}>
              {item.name}
            </option>
          );
        })
      ) : (
        <option>서버오류</option>
      )}
    </select>
  );
}
