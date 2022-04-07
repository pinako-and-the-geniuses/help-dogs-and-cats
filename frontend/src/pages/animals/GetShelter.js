import axios from "axios";
import XMLParser from "react-xml-parser";
import { URL } from "public/config";
import { useEffect } from "react";
import animal from "./styles/Animal.module.scss";

// [보호소 조회]
export default function GetShelter({
  shelter,
  setShelter,
  selected,
  setSelected,
}) {
  // 보호소 조회하기
  function getAPI() {
    if (selected.sidoCode != "0") {
      axios({
        url: `${URL}/external-api/shelters/code-and-name?sidoCode=${selected.sidoCode}&sigunguCode=${selected.sigunguCode}`,
        method: "GET",
      })
        .then((res) => {
          setShelter(res.data.data);
        })
        .catch((err) => {
          console.log(err);
        });
    }
  }

  // 먼저!
  useEffect(() => {
    setShelter("");
    if (selected.sigunguCode) {
      getAPI();
    }
  }, [selected.sigunguCode, selected.sidoCode]);

  return (
    <select
      defaultValue=""
      className={animal.textBox}
      aria-label="보호소"
      onChange={(e) => {
        setSelected({
          sidoCode: selected.sidoCode,
          sigunguCode: selected.sigunguCode,
          shelterCode: e.target.value,
        });
      }}
    >
      <option value="">보호소 전체</option>
      {shelter &&
        shelter.map((item) => {
          return (
            <option key={item.code} value={item.code}>
              {item.name}
            </option>
          );
        })}
    </select>
  );
}
