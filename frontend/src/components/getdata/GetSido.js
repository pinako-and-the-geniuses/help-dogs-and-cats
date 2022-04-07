import axios from "axios";
import { URL } from "public/config";
import { useEffect } from "react";
import animal from "pages/animals/styles/Animal.module.scss";

// [시도 조회]
export default function GetSido({
  sidoData,
  setSidoData,
  selected,
  setSelected,
}) {
  // 시도 조회하기
  function getAPI() {
    axios({
      url: `${URL}/external-api/sido`,
      method: "GET",
    }).then((res) => {
      setSidoData(res.data.data);
    });
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
