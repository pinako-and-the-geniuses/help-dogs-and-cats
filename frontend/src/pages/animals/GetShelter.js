import axios from "axios";
import XMLParser from "react-xml-parser";
import { ANIMAL } from "public/config";
import { useEffect } from "react";
import animal from "./styles/Animal.module.scss";

const ANIMALKEY = process.env.REACT_APP_ANIMAL_KEY;

// [시군구 조회]
export default function GetShelter({
  shelter,
  setShelter,
  selected,
  setSelected,
}) {
  // 원하는 데이터 뽑아 저장하기
  const parseStr = (dataSet) => {
    const arr = new XMLParser().parseFromString(dataSet).children[1];
    const data = arr.children[0].children;
    if (data.length > 1) {
      const shelterDataArr = data.map((items) => {
        return {
          code: items.children[0].value,
          name: items.children[1].value,
        };
      });
      setShelter(shelterDataArr);
    } else if ((data.length = 1)) {
      const shelterDataArr = {
        code: data[0].children[0].value,
        name: data[0].children[1].value,
      };
      setShelter(shelterDataArr);
    } else {
      setShelter("");
    }
  };

  // 보호소 조회하기
  function getAPI() {
    if (selected.sidoCode != "0") {
      axios({
        url: `${ANIMAL}/abandonmentPublicSrvc/shelter?upr_cd=${selected.sidoCode}&org_cd=${selected.sigunguCode}&serviceKey=${ANIMALKEY}&_type=0`,
        method: "GET",
      })
        .then((res) => {
          const dataSet = res.data;
          console.log(res);
          parseStr(dataSet);
        })
        .catch((err) => {
          console.log(err);
        });
    }
  }

  // 먼저!
  useEffect(() => {
    setShelter("");
    if (selected.sigunguCode.length != "0") {
      getAPI();
    }
  }, [selected.sigunguCode, selected.sidoCode]);

  return (
    <select
      defaultValue="0"
      className={animal.textBox}
      aria-label="보호소"
      onChange={(e) =>
        setSelected({
          sidoCode: selected.sidoCode,
          sigunguCode: selected.sigunguCode,
          shelterCode: e.target.value,
        })
      }
    >
      <option value="0">보호소</option>
      {shelter.length > 1 ? (
        shelter.map((item) => {
          return (
            <option key={item.code} value={item.code}>
              {item.name}
            </option>
          );
        })
      ) : shelter.code ? (
        <option value={shelter.code}>{shelter.name}</option>
      ) : (
        ""
      )}
    </select>
  );
}
