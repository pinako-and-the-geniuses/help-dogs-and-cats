import axios from "axios";
import { URL } from "public/config";
import { useEffect } from "react";
import animal from "pages/animals/styles/Animal.module.scss";

// [시군구 조회]
export default function GetSigunguList({
  sigunguData,
  setSigunguData,
  selected,
  setSelected,
}) {
  // 조회하기
  function getAPI() {
    axios({
      url: `${URL}/external-api/sigungu/${selected.sidoCode}`,
      method: "GET",
    })
      .then((res) => {
        setSigunguData(res.data.data);
      })
      .catch((err) => {
        console.log(err);
      });
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
      defaultValue=""
      className={animal.textBox}
      aria-label="시군구"
      onChange={(e) => {
        setSelected({
          sidoCode: selected.sidoCode,
          sigunguCode: e.target.value,
          shelterCode: "",
        });
      }}
    >
      <option value="">시군구</option>

      {sigunguData
        ? sigunguData.map((item) => {
            return (
              <option key={item.sigunguCode} value={item.sigunguCode}>
                {item.name}
              </option>
            );
          })
        : ""}
    </select>
  );
}
