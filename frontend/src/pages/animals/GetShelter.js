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
  setRegionUrl,
}) {
  // 보호소 조회하기
  function getAPI() {
    if (selected.sidoCode != "0") {
      axios({
        url: `${URL}/external-api/shelters/part?page=1&size=12&sidoCode=${selected.sidoCode}&sigunguCode=${selected.sigunguCode}`,
        method: "GET",
      })
        .then((res) => {
          console.log("보호소조회", res.data.data.shelterDtos);
          setShelter(res.data.data.shelterDtos);
        })
        .catch((err) => {
          console.log(err);
        });
    }
  }

  // 먼저!
  useEffect(() => {
    setShelter("");
    if (selected.sigunguCode.length > 2) {
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
        // setRegionUrl(
        //   `&upr_cd=${selected.sidoCode}&org_cd=${selected.sigunguCode}&care_reg_no=${e.target.value}`
        // );
      }}
    >
      <option value="">보호소 전체</option>
      {shelter.length > 1 ? (
        shelter.map((item, index) => {
          return (
            <option key={index} value={item.code}>
              {item.shelterName}
            </option>
          );
        })
      ) : shelter.code ? (
        <option value={shelter.code}>{shelter.shelterName}</option>
      ) : (
        ""
      )}
    </select>
  );
}
