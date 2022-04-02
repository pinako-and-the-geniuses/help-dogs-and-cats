import GetSido from "components/getdata/GetSido";
import GetSigungu from "components/getdata/GetSigungu";
import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { ANIMAL, SHELTER } from "public/config/index";
import axios from "axios";
import style from "./styles/ShelterList.module.scss";
import cn from "classnames";

function ShelterList() {
  const navigate = useNavigate();
  const SHELTER_KEY = process.env.REACT_APP_SHELTER_KEY;
  const ANIMAL_KEY = process.env.REACT_APP_ANIMAL_KEY;
  const [name, setName] = useState("");
  const [list, setList] = useState("");
  const [sido, setSido] = useState("");
  const [sigungu, setSigungu] = useState("");
  const [shelter, setShelter] = useState("");
  // 선택 조건
  const [selected, setSelected] = useState({
    sidoCode: "0",
    sigunguCode: "0",
    shelterName: "0",
  });
  // 조건별 요청 url
  const [regionUrl, setRegionUrl] = useState("");

  // 첫 페이지 모든 목록 출력
  const onSetPage = () => {
    axios
      .get(
        `${SHELTER}/shelterInfo?numOfRows=10&pageNo=1&serviceKey=${SHELTER_KEY}&_type=JSON`
      )
      .then((res) => {
        const data = res.data.response.body.items.item;
        setList(data);
      })
      .catch((err) => {
        console.log(err);
      });
  };
  useEffect(() => {
    // onSetPage();
    onGetList("&upr_cd=6110000&org_cd=3220000");
  }, []);

  // 보호센터명 입력
  const setNameSearch = (value) => {
    setName(value);
  };

  // 조건 선택별 url변경
  function onGetChoice() {
    //지역선택
    if (selected.sidoCode != "0") {
      // 시도만 선택
      if (
        selected.sidoCode !== "0" &&
        (selected.sigunguCode === "" || selected.sigunguCode === "0")
      ) {
        console.log("시도만 선택");
        const region = `&upr_cd=${selected.sidoCode}`;
        setRegionUrl(region);
      } // 시도 + 시군구 선택
      else if (
        (selected.sigunguCode !== "" || selected.sigunguCode !== "0") &&
        (selected.shelterCode === "" || selected.shelterCode === "0")
      ) {
        console.log("시도 + 시군구 선택");
        const region = `&upr_cd=${selected.sidoCode}&org_cd=${selected.sigunguCode}`;
        setRegionUrl(region);
      }
    } else {
      // 지역을 선택하지 않은 경우
      console.log(" // 지역을 선택하지 않은 경우");
      const region = "";
      setRegionUrl(region);
    }

    const SUBURL = `${regionUrl}`;
    onGetList(SUBURL);
  }

  //조건별 보호소 목록 가져오기
  const onGetList = (SUBURL) => {
    axios
      .get(
        `${ANIMAL}/abandonmentPublicSrvc/shelter?serviceKey=${ANIMAL_KEY}&_type=JSON${SUBURL}`
      )
      .then((res) => {
        const data = res.data.response.body;
        console.log(data);
        setList(data);
      })
      .catch((err) => {
        console.log("err", err);
      });
  };

  return (
    <div className={style.main_container}>
      <header>
        <h2>동물 보호 센터</h2>
      </header>

      <div className={style.my_city}>
        <p>우리 지역 보호소 찾기</p>
      </div>

      <div className={style.search_bar}>
        <div className={style.search_input}>
          <p>시도</p>
          <GetSido
            sidoData={sido}
            selected={selected}
            setSidoData={setSido}
            setSelected={setSelected}
          />

          <p>시군구</p>

          <GetSigungu
            sigunguData={sigungu}
            setSigunguData={setSigungu}
            selected={selected}
            setSelected={setSelected}
          />

          <p>보호센터명</p>
          <input
            type="text"
            value={name}
            onChange={(e) => {
              setNameSearch(e.target.value);
            }}
          />
        </div>
        {/* onClick={onSubmit} */}
        <button type="submit">조회</button>
      </div>

      <table className={cn("table table-bordered table-hover", style.my_table)}>
        <thead>
          <tr>
            <th scope="col">관할구역</th>
            <th scope="col">보호센터명</th>
            <th scope="col">전화번호</th>
            <th scope="col">보호센터 유형</th>
          </tr>
        </thead>
        <tbody>
          {/* {list &&
            list.map((item, index) => {
              console.log(item);
              return (
                <tr
                  key={index}
                  // onClick={() => {
                  //   goToShelter(i.careRegNo);
                  // }}
                >
                  <td>{item.orgNm}</td>
                  <td>{item.careNm}</td>
                  <td>{item.careTel}</td>
                  <td>{item.divisionNm}</td>
                </tr>
              );
            })} */}
        </tbody>
        {/* <tbody>
                    <tr>
                    <td>어디</td>
                    <td>Mark</td>
                    <td>Otto</td>
                    <td>@mdo</td>
                    </tr>
                    <tr>
                    <td>어디</td>
                    <td>Jacob</td>
                    <td>Thornton</td>
                    <td>@fat</td>
                    </tr>
                    <tr>
                    <td>어디</td>
                    <td>어쩌고저쩌고</td>
                    <td>Larry the Bird</td>
                    <td>@twitter</td>
                    </tr>
          </tbody>*/}
      </table>
    </div>
  );
}

export default ShelterList;
