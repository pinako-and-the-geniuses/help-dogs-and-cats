import GetSido from "components/getdata/GetSido";
import GetSigungu from "components/getdata/GetSigungu";
import GetShelter from "./GetShelter";
import GetKind from "./GetKind";
import AnimalBox from "components/animals/AnimalBox";
import { useEffect, useState } from "react";
import axios from "axios";
import XMLParser from "react-xml-parser";
import st from "./styles/AnimalList.module.scss";
import Paging from "../../components/Paging";

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
  const [page, setPage] = useState(1);
  const [totalItemCount, setTotalItemCount] = useState(0);
  const [limit, setLimit] = useState(12);

  // 원하는 데이터 뽑아 저장하기
  const parseStr = (dataSet) => {
    const arr = new XMLParser().parseFromString(dataSet).children[1];
    const listData = arr.children[0].children;
    console.log("조회결과", listData);
    setTotalItemCount(arr.children[3].value);
    setList(listData);
  };

  const onGetList = () => {
    console.log("regionURl", regionUrl);

    axios
      .get(
        `${ANIMAL}?pageNo=${page}&numOfRows=${limit}${regionUrl}${kindUrl}${stateUrl}&serviceKey=${ANIMALKEY}`
      )
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
  }, [page]);

  return (
    <div className={st.div}>
      <header name="페이지이름">
        <h2>유기동물 조회</h2>
      </header>
      <div name="상세조회Box" className={st.inquiry}>
        <div name="조건box" className={st.condition}>
          <div name="조건1줄" className={st.firstCon}>
            <div name="시도" className={st.couple}>
              <p>시도</p>
              <GetSido
                sidoData={sidoData}
                selected={selected}
                setSidoData={setSidoData}
                setSelected={setSelected}
                setRegionUrl={setRegionUrl}
              />
            </div>
            <div name="시군구" className={st.couple}>
              <p>시군구</p>
              <GetSigungu
                sigunguData={sigunguData}
                setSigunguData={setSigunguData}
                selected={selected}
                setSelected={setSelected}
                setRegionUrl={setRegionUrl}
              />
            </div>
            <div name="보호소" className={st.couple}>
              <p>보호소</p>
              <GetShelter
                shelter={shelter}
                setShelter={setShelter}
                selected={selected}
                setSelected={setSelected}
                setRegionUrl={setRegionUrl}
              />
            </div>
          </div>

          <div name="조건2줄" className={st.secondCon}>
            <div name="축종" className={st.couple}>
              <p>축종</p>
              <GetKind setKind={setKind} setKindUrl={setKindUrl} />
            </div>
            <div name="상태" className="ms-4">
              <form
                onChange={(e) => {
                  setState(e.target.value);
                  setStateUrl(`&state=${e.target.value}`);
                }}
              >
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
          <button type="button" className={st.btn} onClick={onGetList}>
            조회
          </button>
        </div>
      </div>
      <div className="row row-cols-1 row-cols-md-4 g-5">
        <AnimalBox list={list} />
      </div>

      <Paging
        total={totalItemCount}
        limit={limit}
        page={page}
        setPage={setPage}
      />
    </div>
  );
}
