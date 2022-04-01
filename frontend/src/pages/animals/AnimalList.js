import GetSidoList from "./GetSidoList";
import GetSigungu from "./GetSigungu";
import GetShelter from "./GetShelter";
import GetType from "./GetType";
import Box from "../../components/animals/Box";
import st from "./styles/AnimalList.module.scss";
import { useState } from "react";

export default function AnimalList() {
  const [sidoData, setSidoData] = useState("");
  const [sigunguData, setSigunguData] = useState("");
  const [selected, setSelected] = useState({
    sidoCode: "0",
    sigunguCode: "0",
  });
  const [type, setType] = useState("");
  const [gender, setGender] = useState("");
  const [selectedTypes, setSelectedTypes] = useState({
    type: "0",
    gender: "0",
  });
  console.log("seleted", selected);
  console.log("seletedTypes", selectedTypes);

  // console.log(sigu);
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
              <GetShelter />
            </div>
            {/* <div name="날짜" className="ms-5">
              <span>
                <input type="date" />
              </span>
            </div> */}
          </div>

          <div name="조건2줄" className={st.secondCon}>
            <div name="축종">
              <GetType />
            </div>
            <div name="성별">
              <>
                <div className="form-check form-check-inline mx-4">
                  <input
                    className="form-check-input"
                    type="radio"
                    id="inlineRadio1"
                    value="1"
                  />
                  <label className="form-check-label" for="inlineRadio1">
                    남
                  </label>
                </div>
                <div className="form-check form-check-inline">
                  <input
                    className="form-check-input"
                    type="radio"
                    id="inlineRadio2"
                    value="2"
                  />
                  <label className="form-check-label" for="inlineRadio2">
                    여
                  </label>
                </div>
              </>
            </div>
          </div>
        </div>

        <div name="조회버튼">
          <button type="button" className={st.btn}>
            조회
          </button>
        </div>
      </div>
      <div name="리스트Box">
        <Box />
      </div>
    </div>
  );
}
