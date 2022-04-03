import GetSido from "components/getdata/GetSido";
import GetSigungu from "components/getdata/GetSigungu";
import React, { useEffect, useState } from "react";
import { ANIMAL, SHELTER } from "public/config/index";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import { shelterGetAction } from "actions/ShelterAction";

import axios from "axios";
import style from "./styles/ShelterList.module.scss";
import cn from "classnames";

function ShelterList() {
  // 외부 요청 key
  const SHELTER_KEY = process.env.REACT_APP_SHELTER_KEY;
  const ANIMAL_KEY = process.env.REACT_APP_ANIMAL_KEY;
  // 상태관리
  const [name, setName] = useState("");
  const [list, setList] = useState("");
  const [newList, setNewList] = useState([]);
  const [find, setFind] = useState(false);
  const [sido, setSido] = useState("");
  const [sigungu, setSigungu] = useState("");
  const [selected, setSelected] = useState({
    sidoCode: "0",
    sigunguCode: "0",
    shelterName: "0",
  });
  // 조건별 요청 url
  const [regionUrl, setRegionUrl] = useState("");
  // 기타
  const dispatch = useDispatch();
  const navigate = useNavigate();

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
    onSetPage();
  }, []);

  // 보호센터명 입력 (지역리셋)
  const setNameSearch = (value) => {
    setName(value);
  };

  //조건별 보호소 목록 가져오기
  const onGetList = () => {
    setFind(true);
    // 보호센터명 검색
    if (name.length > 1) {
      console.log("이름검색들어옴", name);
      axios
        .get(
          `${SHELTER}/shelterInfo?numOfRows=10&pageNo=1&serviceKey=${SHELTER_KEY}&_type=JSON&care_nm=${name}`
        )
        .then((res) => {
          console.log("이름조회결과", res.data);
          const temp = res.data.response.body.items;
          if (temp.item) {
            temp.item.map((item) => {
              setNewList((newList) => [...newList, [item]]);
            });
          }
        })
        .catch((err) => {
          console.log("이름조회에러", err);
          alert("이름 조회에 실패했습니다.");
        });
    } // 지역검색
    else if (!name && regionUrl.length >= 15) {
      if (selected.sigunguCode) {
        axios
          .get(
            `${ANIMAL}/abandonmentPublicSrvc/shelter?serviceKey=${ANIMAL_KEY}&_type=JSON${regionUrl}`
          )
          .then((res) => {
            console.log("res", res.data.response);
            const data = res.data.response.body.items.item;
            console.log(data);
            if (data) {
              data.map((item) => {
                axios
                  .get(
                    `${SHELTER}/shelterInfo?&serviceKey=${SHELTER_KEY}&care_reg_no=${item.careRegNo}&_type=JSON`
                  )
                  .then((res) => {
                    console.log(res.data.response.body);
                    const temp = res.data.response.body.items;
                    // 정보있는경우
                    if (temp.item) {
                      const data = temp.item;
                      console.log(data);
                      setNewList((newList) => [...newList, data]);
                    } else {
                      console.log("정보없음", newList);
                    }
                  })
                  .catch((err) => {
                    console.log(err);
                    alert("서버 에러 발생");
                  });
              });
            } else {
              setList("");
            }
          })
          .catch((err) => {
            console.log("err", err);
            alert("네트워크 에러가 발생했습니다.");
          });
      } else {
        alert("시군구를 선택하세요.");
      }
    } else {
      console.log(regionUrl);
      alert("검색 조건을 확인하세요.");
    }
  };

  //상세페이지로
  const onGoDetail = (item) => {
    dispatch(shelterGetAction(item));
    navigate(`/shelter/detail`);
  };

  // 상태에 따라 목록화면 조절
  const onList = () => {
    // console.log("조회 했는데 리스트 없는상태");
    if (!newList && find) {
      console.log("조회 했는데 리스트 없는상태");
      return (
        <>
          {newList &&
            newList.map((item, index) => {
              return (
                <tr
                  key={index}
                  onClick={() => {
                    onGoDetail(item);
                  }}
                >
                  <td>{item.careNm}</td>
                  <td>{item.careTel}</td>
                  <td>
                    {item.weekOprStime} ~ {item.weekOprEtime}{" "}
                  </td>
                  <td>{item.careAddr}</td>
                </tr>
              );
            })}
        </>
      );
    } //      console.log("조회리스트있는 상태", newList);
    else if (newList && find) {
      console.log("조회리스트있는 상태", newList);
      return (
        <>
          {newList &&
            newList.map((item, index) => {
              console.log("newList.map.item", item);
              return (
                <tr
                  key={index}
                  onClick={() => {
                    onGoDetail(item[0]);
                  }}
                >
                  <td>{item[0].careNm}</td>
                  <td>{item[0].careTel}</td>
                  <td>
                    {item[0].weekOprStime} ~ {item[0].weekOprEtime}{" "}
                  </td>
                  <td>{item[0].careAddr}</td>
                </tr>
              );
            })}
        </>
      );
    } else {
      console.log("전체리스트 출력");
      return (
        <>
          {list &&
            list.map((item, index) => {
              return (
                <tr
                  key={index}
                  onClick={() => {
                    onGoDetail(item);
                  }}
                >
                  <td>{item.careNm}</td>
                  <td>{item.careTel}</td>
                  <td>
                    {item.weekOprStime} ~ {item.weekOprEtime}{" "}
                  </td>
                  <td>{item.careAddr}</td>
                </tr>
              );
            })}
        </>
      );
    }
  };

  return (
    <div className={style.main_container}>
      <header>
        <h2>동물 보호 센터</h2>
      </header>

      <div className={style.search_bar}>
        <div className={style.search_input}>
          <p>시도</p>
          <GetSido
            sidoData={sido}
            selected={selected}
            setSidoData={setSido}
            setSelected={setSelected}
            setRegionUrl={setRegionUrl}
          />

          <p>시군구</p>
          <GetSigungu
            sigunguData={sigungu}
            setSigunguData={setSigungu}
            selected={selected}
            setSelected={setSelected}
            setRegionUrl={setRegionUrl}
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
        <div>[ 시도 선택시 시군구까지 모두 선택해야 조회 가능합니다. ] </div>
        <div>[ 정확한 보호센터명을 입력해주세요. ] </div>
        <div>[ 지역 조회 또는 보호센터명 조회 가능합니다. ] </div>

        <button
          type="submit"
          onClick={() => {
            setNewList("");
            onGetList();
          }}
        >
          조회
        </button>
        <button
          type="submit"
          onClick={() => {
            setNewList("");
            onSetPage();
            setFind(false);
          }}
        >
          전체보기
        </button>
      </div>

      <table className={cn("table table-bordered table-hover", style.my_table)}>
        <thead>
          <tr>
            <th scope="col">보호센터명</th>
            <th scope="col">전화번호</th>
            <th scope="col">영업시간</th>
            <th scope="col">주소</th>
          </tr>
        </thead>
        <tbody>{onList()}</tbody>
      </table>
    </div>
  );
}

export default ShelterList;
