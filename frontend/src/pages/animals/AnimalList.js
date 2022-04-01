import GetSidoList from "./GetSidoList";
import GetSigungu from "./GetSigungu";
import GetShelter from "./GetShelter";
import GetKind from "./GetKind";
import AnimalBox from "../../components/animals/AnimalBox";
import { useEffect, useState } from "react";
import axios from "axios";
import XMLParser from "react-xml-parser";
import st from "./styles/AnimalList.module.scss";

const ANIMAL =
  "http://apis.data.go.kr/1543061/abandonmentPublicSrvc/abandonmentPublic";
const ANIMALKEY = process.env.REACT_APP_ANIMAL_KEY;

export default function AnimalList() {
  const [list, setList] = useState("");
  const [sidoData, setSidoData] = useState("");
  const [sigunguData, setSigunguData] = useState("");
  const [shelter, setShelter] = useState("");
  // 선택 조건
  const [selected, setSelected] = useState({
    sidoCode: "0",
    sigunguCode: "0",
    shelterCode: "0",
  });
  const [kind, setKind] = useState("");
  const [state, setState] = useState("");
  // 요청 url
  const [regionUrl, setRegionUrl] = useState("");
  const [kindUrl, setKindUrl] = useState("");
  const [stateUrl, setStateUrl] = useState("");

  // 원하는 데이터 뽑아 저장하기
  const parseStr = (dataSet) => {
    const arr = new XMLParser().parseFromString(dataSet).children[1];
    const listData = arr.children[0].children;
    console.log("동물 리스트 데이터", listData);
    setList(listData);
  };

  function onGetChoice() {
    //지역 관련 선택
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
      } // 시도 + 시군구 + 보호소 선택
      else if (selected.shelterCode !== "" || selected.shelterCode !== "0") {
        console.log("시도 + 시군구 + 보호소 선택");
        const region = `&upr_cd=${selected.sidoCode}&org_cd=${selected.sigunguCode}&care_reg_no=${selected.shelterCode}`;
        setRegionUrl(region);
      }
    } else {
      // 지역을 선택하지 않은 경우
      console.log(" // 지역을 선택하지 않은 경우");
      const region = "";
      setRegionUrl(region);
    }

    // 축종 관련 선택
    if (kind) {
      console.log(" 축종 선택");
      const upkind = `&upkind=${kind}`;
      setKindUrl(upkind);
    } else {
      console.log(" 축종 선택안함");
      setKindUrl("");
    }

    // 성별 관련 선택
    if (state) {
      console.log("상태 선택");
      const upstate = `&state=${state}`;
      setStateUrl(upstate);
    } else {
      console.log("상태 선택안함");
      setStateUrl("");
    }

    const SUBURL = `${regionUrl}${kindUrl}${stateUrl}`;
    onGetList(SUBURL);
  }
  const onGetList = (SUBURL) => {
    axios
      .get(`${ANIMAL}?pageNo=1&numOfRows=12${SUBURL}&serviceKey=${ANIMALKEY}`)
      .then((res) => {
        const dataSet = res.data;
        parseStr(dataSet);
      })
      .catch((err) => {
        console.log("err", err);
      });
  };

  useEffect(() => {
    onGetList("");
  }, []);

  return (
    <div className={st.div}>
      <div name="페이지이름">
        <h2>유기동물 조회</h2>
      </div>
      <div name="상세조회Box" className={st.inquiry}>
        <div name="조건box" className={st.condition}>
          <div name="조건1줄" className={st.firstCon}>
            <div name="시도">
              <GetSidoList
                sidoData={sidoData}
                selected={selected}
                setSidoData={setSidoData}
                setSelected={setSelected}
              />
            </div>
            <div name="시군구">
              <GetSigungu
                sigunguData={sigunguData}
                setSigunguData={setSigunguData}
                selected={selected}
                setSelected={setSelected}
              />
            </div>
            <div name="보호소">
              <GetShelter
                shelter={shelter}
                setShelter={setShelter}
                selected={selected}
                setSelected={setSelected}
              />
            </div>
            {/* <div name="날짜" className="ms-5">
              <span>
                <input type="date" />
              </span>
            </div> */}
          </div>

          <div name="조건2줄" className={st.secondCon}>
            <div name="축종">
              <GetKind setKind={setKind} />
            </div>
            <div name="성별" className="ms-4">
              <form onChange={(e) => setState(e.target.value)}>
                <div className="form-check form-check-inline">
                  <input
                    className="form-check-input"
                    type="radio"
                    name="inlineRadioOptions"
                    id="inlineRadio1"
                    value=""
                    defaultChecked
                  />
                  <label className="form-check-label" htmlFor="inlineRadio1">
                    전체
                  </label>
                </div>
                <div className="form-check form-check-inline">
                  <input
                    className="form-check-input"
                    type="radio"
                    name="inlineRadioOptions"
                    id="inlineRadio2"
                    value="notice"
                  />
                  <label className="form-check-label" htmlFor="inlineRadio2">
                    공고중
                  </label>
                </div>
                <div className="form-check form-check-inline">
                  <input
                    className="form-check-input"
                    type="radio"
                    name="inlineRadioOptions"
                    id="inlineRadio3"
                    value="protect"
                  />
                  <label className="form-check-label" htmlFor="inlineRadio3">
                    보호중
                  </label>
                </div>
              </form>
            </div>
          </div>
        </div>

        <div name="조회버튼">
          <button type="button" className={st.btn} onClick={onGetChoice}>
            조회
          </button>
        </div>
      </div>
      <div className="row row-cols-1 row-cols-md-4 g-5">
        <AnimalBox list={list} />{" "}
      </div>
      {/* <div name="리스트Box">
        <Box />
      </div> */}
    </div>
  );
}
