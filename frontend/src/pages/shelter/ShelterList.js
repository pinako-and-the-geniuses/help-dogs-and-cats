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
import swal from "sweetalert";
import "../../components/styles/Paging.css";
import Pagination from "react-js-pagination";

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
  const [page, setPage] = useState(1);
  const [totalItemCount, setTotalItemCount] = useState(0);

  // 조건별 요청 url
  const [regionUrl, setRegionUrl] = useState("");
  // 기타
  const dispatch = useDispatch();
  const navigate = useNavigate();

  // 첫 페이지 모든 목록 출력
  const onSetPage = () => {
    axios
      .get(
        `${SHELTER}/shelterInfo?numOfRows=10&pageNo=${page}&serviceKey=${SHELTER_KEY}&_type=JSON`
      )
      .then((res) => {
        const data = res.data.response.body.items.item;
        setList(data);
        setTotalItemCount(res.data.response.body.totalCount);
      })
      .catch((err) => {
        console.log(err);
      });
  };
  useEffect(() => {
    onSetPage();
  }, [page]);

  // 보호센터명 입력
  const setNameSearch = (value) => {
    setName(value);
  };

  //조건별 보호소 목록 가져오기
  const onGetList = () => {
    // 보호센터명만 검색       console.log("이름검색들어옴", name);
    if (name.length > 1 && regionUrl.length < 15) {
      axios
        .get(
          `${SHELTER}/shelterInfo?numOfRows=10&pageNo=1&serviceKey=${SHELTER_KEY}&_type=JSON&care_nm=${name}`
        )
        .then((res) => {
          console.log("이름조회결과", res.data.response.body);
          const temp = res.data.response.body.items;
          setTotalItemCount(res.data.response.body.totalCount);
          if (temp.item) {
            temp.item.map((item) => {
              setNewList((newList) => [...newList, [item]]);
            });
          }
        })
        .catch((err) => {
          console.log("이름조회에러", err);
          swal("조회 실패", "이름 조회에 실패했습니다.", "error");
        });
    } else if (name.length < 1 && regionUrl.length >= 15) {
      console.log("지역만검색들어옴", regionUrl);
      console.log("지역만검색들어옴", regionUrl.length);
      setTotalItemCount(regionUrl.length);

      if (selected.sidoCode) {
        // 시도 + 시군구
        if (regionUrl.length > 24) {
          axios
            .get(
              `${ANIMAL}/abandonmentPublicSrvc/shelter?serviceKey=${ANIMAL_KEY}&_type=JSON&${regionUrl}`
            )
            .then((res) => {
              if (res.data.response.body.items.item) {
                const data = res.data.response.body.items.item;
                setTotalItemCount(res.data.response.body.totalCount);
                console.log("시도+시군구 검색 결과", res.data.response);
                if (data) {
                  data.map((item) => {
                    axios
                      .get(
                        `${SHELTER}/shelterInfo?&serviceKey=${SHELTER_KEY}&care_reg_no=${item.careRegNo}&_type=JSON`
                      )
                      .then((res) => {
                        if (res.data.response.body.items) {
                          const temp = res.data.response.body.items;
                          setTotalItemCount(res.data.response.body.totalCount);
                          console.log(temp);
                          if (temp.item) {
                            const data = temp.item;
                            setNewList((newList) => [...newList, data]);
                          }
                        }
                      })
                      .catch((err) => {
                        console.log("지역만결과-상세정보-서버 에러 발생", err);
                        swal(
                          "서버에러",
                          "네트워크 에러가 발생했습니다.",
                          "error"
                        );
                      });
                  });
                }
              }
            })
            .catch((err) => {
              console.log("err", err);
              swal("서버에러", "네트워크 에러가 발생했습니다.", "error");
            });
        }
        // 시도만 선택
        else {
          if (sigungu) {
            sigungu.map((item) => {
              console.log(item);
              axios
                .get(
                  `${ANIMAL}/abandonmentPublicSrvc/shelter?serviceKey=${ANIMAL_KEY}&_type=JSON&${regionUrl}${item.code} `
                )
                .then((res) => {
                  const data = res.data.response.body.items.item;
                  console.log("시도만 검색 결과", res.data.response.body);
                  if (data) {
                    data.map((item) => {
                      console.log(item);
                      axios
                        .get(
                          `${SHELTER}/shelterInfo?&serviceKey=${SHELTER_KEY}&care_reg_no=${item.careRegNo}&_type=JSON`
                        )
                        .then((res) => {
                          console.log(res.data.response.body);
                          if (res.data.response) {
                            const temp = res.data.response.body.items;
                            if (temp.item) {
                              const data = temp.item;
                              setNewList((newList) => [...newList, data]);
                            }
                          }
                        })
                        .catch((err) => {
                          console.log(
                            "지역만결과-상세정보-서버 에러 발생",
                            err
                          );
                          swal(
                            "서버에러",
                            "네트워크 에러가 발생했습니다.",
                            "error"
                          );
                        });
                    });
                  }
                })
                .catch((err) => {
                  console.log("err", err);
                  swal("서버에러", "네트워크 에러가 발생했습니다.", "error");
                });
            });
          }
        }
      } else {
        swal("조건선택", "시군구를 선택하세요.", "info");
      }
    } //      console.log("지역+검색어조회", name, "===", regionUrl);
    else if (name.length > 1 && regionUrl.length >= 15) {
      if (selected.sigunguCode) {
        axios
          .get(
            `${ANIMAL}/abandonmentPublicSrvc/shelter?serviceKey=${ANIMAL_KEY}&_type=JSON${regionUrl}`
          )
          .then((res) => {
            const data = res.data.response.body.items.item;
            console.log("지역+검색어조회-지역검색 결과", data);

            if (data) {
              const result = data.filter((item) => {
                return item.careNm.includes(name);
              });
              console.log(
                "지역+검색어조회-지역검색결과에서 검색어 추리기",
                result
              );
              if (result) {
                result.map((item) => {
                  axios
                    .get(
                      `${SHELTER}/shelterInfo?&serviceKey=${SHELTER_KEY}&care_reg_no=${item.careRegNo}&_type=JSON`
                    )
                    .then((res) => {
                      const temp = res.data.response.body.items;
                      console.log("지역+검색어조회-이름조회결과", temp);

                      if (temp.item) {
                        temp.item.map((item) => {
                          setNewList((newList) => [...newList, [item]]);
                        });
                      }
                    })
                    .catch((err) => {
                      console.log("이름조회에러", err);
                      swal("서버에러", "이름 조회에 실패했습니다.", "error");
                    });
                });
              }
            }
          });
      } else {
        swal("조건선택", "시군구를 선택하세요.", "info");
      }
    } else {
      swal("조건선택", "검색조건을 확인하세요.", "info");
    }
  };

  //상세페이지로
  const onGoDetail = (item) => {
    dispatch(shelterGetAction(item));
    navigate(`/shelter/detail`);
  };

  // 상태에 따라 목록화면 조절
  const onList = () => {
    if (!newList && find) {
      // console.log("조회 했는데 리스트 없는상태");
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
                  <td>{item.orgNm}</td>
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
    } else if (newList && find) {
      //      console.log("조회리스트있는 상태", newList);
      return (
        <>
          {newList &&
            newList.map((item, index) => {
              return (
                <tr
                  key={index}
                  onClick={() => {
                    onGoDetail(item[0]);
                  }}
                >
                  <td>{item[0].orgNm}</td>
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
      // console.log("전체리스트 출력");
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
                  <td>{item.orgNm}</td>
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
        <div>
          <button
            type="submit"
            onClick={() => {
              setNewList("");
              onGetList();
              setFind(true);
              setPage(1);
            }}
          >
            조회
          </button>
          {/* <button
            type="submit"
            onClick={() => {
              setNewList("");
              onSetPage();
              setFind(false);
            }}
          >
            전체보기
          </button> */}
        </div>
      </div>

      <table className={cn("table table-bordered table-hover", style.my_table)}>
        <thead>
          <tr>
            <th scope="col">관할구역</th>

            <th scope="col">보호센터명</th>
            <th scope="col">전화번호</th>
            <th scope="col">영업시간</th>
            <th scope="col">주소</th>
          </tr>
        </thead>
        <tbody>{onList()}</tbody>
      </table>

      <Pagination
        activePage={page}
        itemsCountPerPage={10}
        totalItemsCount={totalItemCount}
        pageRangeDisplayed={10}
        onChange={setPage}
      ></Pagination>
    </div>
  );
}

export default ShelterList;
