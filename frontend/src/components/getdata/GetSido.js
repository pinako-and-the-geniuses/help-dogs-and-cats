import axios from "axios";
import XMLParser from "react-xml-parser";
import { URL } from "public/config";
import { useEffect } from "react";
import animal from "pages/animals/styles/Animal.module.scss";
const ANIMALKEY = process.env.REACT_APP_ANIMAL_KEY;

// [시도 조회]
export default function GetSido({
  setRegionUrl,
  sidoData,
  setSidoData,
  selected,
  setSelected,
}) {
  // xml을 json으로 바꾸고 원하는 데이터 뽑아 저장하기
  // const parseStr = (dataSet) => {
  //   const arr = new XMLParser().parseFromString(dataSet).children;
  //   const dataArr = arr[1].children[0].children;
  //   const sidoDataArr = dataArr.map((item) => {
  //     return {
  //       code: item.children[0].value,
  //       name: item.children[1].value,
  //     };
  //   });
  //   setSidoData(sidoDataArr);
  // };

  // 시도 조회하기
  function getAPI() {
    if (ANIMALKEY) {
      axios({
        url: `${URL}/external-api/sido`,
        method: "GET",
      })
        .then((res) => {
          console.log("===========getSido=======", res.data.data);
          setSidoData(res.data.data);
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
      defaultValue=""
      className={animal.textBox}
      aria-label="시도"
      onChange={(e) => {
        setRegionUrl(`&upr_cd=${e.target.value}&org_cd=`);
        setSelected({
          sidoCode: e.target.value,
          sigunguCode: "",
          shelterCode: "",
        });
      }}
    >
      <option value="">시도</option>

      {sidoData ? (
        sidoData.map((item, index) => {
          return (
            <option key={index} value={item.sidoCode}>
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
