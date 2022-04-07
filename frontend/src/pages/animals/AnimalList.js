import GetSido from "components/getdata/GetSido";
import GetSigungu from "components/getdata/GetSigungu";
import GetShelter from "./GetShelter";
import GetKind from "./GetKind";
import AnimalBox from "components/animals/AnimalBox";
import { useEffect, useState } from "react";
import axios from "axios";
import st from "./styles/AnimalList.module.scss";
import "../../components/styles/Paging.css";
import Pagination from "react-js-pagination";
import swal from "sweetalert";
import { URL } from "public/config";

export default function AnimalList() {
  const [list, setList] = useState("");
  const [sidoData, setSidoData] = useState("");
  const [sigunguData, setSigunguData] = useState("");
  const [shelter, setShelter] = useState("");
  // 선택 조건
  const [selected, setSelected] = useState({
    sidoCode: "",
    sigunguCode: "",
    shelterCode: "",
  });
  const [kind, setKind] = useState("");
  const [state, setState] = useState("");
  const [postState, setPostState] = useState("");
  const [page, setPage] = useState(1);
  const limit = 12;
  const [totalItemCount, setTotalItemCount] = useState(0);

  const onGetList = () => {
    axios
      .get(
        `${URL}/external-api/animals?page=${page}&size=${limit}&sidoCode=${selected.sidoCode}&sigunguCode=${selected.sigunguCode}&shelterCode=${selected.shelterCode}&upkind=${kind}&state=${state}`
      )
      .then((res) => {
        const data = res.data.data;
        setTotalItemCount(res.data.data.totalCount)
        setList(data.animalDtos);
        if (state === "notice") {
          setPostState("notice");
        } else {
          setPostState("");
        }
      })
      .catch((err) => {
        console.log("err", err);
        swal("", "조건을 확인하세요", "error");
      });
  };

  useEffect(() => {
    onGetList();
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
              />
            </div>
            <div name="시군구" className={st.couple}>
              <p>시군구</p>
              <GetSigungu
                sigunguData={sigunguData}
                setSigunguData={setSigunguData}
                selected={selected}
                setSelected={setSelected}
              />
            </div>
            <div name="보호소" className={st.couple}>
              <p>보호소</p>
              <GetShelter
                shelter={shelter}
                setShelter={setShelter}
                selected={selected}
                setSelected={setSelected}
              />
            </div>
          </div>
          <div className="mb-3">[ 시도 선택시 시군구 필수 선택입니다. ]</div>

          <div name="조건2줄" className={st.secondCon}>
            <div name="축종" className={st.couple}>
              <p>축종</p>
              <GetKind setKind={setKind} />
            </div>
            <div name="상태" className="ms-4">
              <form
                onChange={(e) => {
                  setState(e.target.value);
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
          <button
            type="button"
            className={st.btn}
            onClick={() => {
              onGetList();
              setPage(1);
            }}
          >
            조회
          </button>
        </div>
      </div>
      <div style={{ minHeight: "40vh" }}>
        <div className="row row-cols-1 row-cols-md-4 g-5">
          {list && <AnimalBox list={list} postState={postState} />}
        </div>
      </div>

      <Pagination
        activePage={page}
        itemsCountPerPage={limit}
        totalItemsCount={totalItemCount}
        pageRangeDisplayed={10}
        onChange={setPage}
      ></Pagination>
    </div>
  );
}
