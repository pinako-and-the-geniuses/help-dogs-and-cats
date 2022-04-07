import GetSido from "components/getdata/GetSido";
import GetSigungu from "components/getdata/GetSigungu";
import React, { useEffect, useState } from "react";
import { URL } from "public/config/index";
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
  // 상태관리
  const [search, setSearch] = useState("");
  const [list, setList] = useState("");
  const [sido, setSido] = useState("");
  const [sigungu, setSigungu] = useState("");
  const [selected, setSelected] = useState({
    sidoCode: "",
    sigunguCode: "",
  });
  const [page, setPage] = useState(1);
  const [totalItemCount, setTotalItemCount] = useState(0);
  const size = 12;

  // 기타
  const dispatch = useDispatch();
  const navigate = useNavigate();

  // 첫 페이지 모든 목록 출력 + 보호소명 검색
  const onSetPage = () => {
    axios
      .get(
        `${URL}/external-api/shelters?page=${page}&size=${size}&shelterName=${search}`
      )
      .then((res) => {
        setList(res.data.data.shelterDtos);
        setTotalItemCount(res.data.data.totalCount);
      });
  };
  useEffect(() => {
    onSetPage();
  }, [page]);

  //조건별 보호소 목록 가져오기
  const onGetList = () => {
    // 시도+시군구 조회 // 시도+ 시군구 + 검색어 조회
    if (selected.sigunguCode) {
      axios
        .get(
          `${URL}/external-api/shelters/part?page=${page}&size=${size}&sidoCode=${selected.sidoCode}&sigunguCode=${selected.sigunguCode}`
        )
        .then((res) => {
          const data = res.data.data.shelterDtos;
          if (search) {
            const temp = data.filter((item) => {
              return item.shelterName.includes(search);
            });
            setList(temp);
            setTotalItemCount(res.data.data.totalCount);
          } else {
            setList(data);
            setTotalItemCount(res.data.data.totalCount);
          }
        });
    } // 시도만 선택했을때
    else if (selected.sidoCode && !selected.sigunguCode) {
      swal("", "시군구를 선택해주세요.", "info");
    } // 검색어로만 조회
    else if (search) {
      onSetPage();
    }
  };

  //상세페이지로
  const onGoDetail = (item) => {
    dispatch(shelterGetAction(item));
    navigate(`/shelter/detail`);
  };

  const enterKey = () => {
    if (window.event.keyCode === 13) {
      onGetList();
      setPage(1);
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
            value={search}
            onKeyUp={enterKey}
            onChange={(e) => {
              setSearch(e.target.value);
            }}
          />
        </div>
        <div>
          <button
            type="submit"
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
        <table
          className={cn("table table-bordered table-hover", style.my_table)}
        >
          <thead>
            <tr>
              <th scope="col">관할구역</th>
              <th scope="col">보호센터명</th>
              <th scope="col">전화번호</th>
              <th scope="col">운영시간</th>
              <th scope="col">주소</th>
            </tr>
          </thead>
          <tbody>
            {list ? (
              list.map((item, index) => {
                return (
                  <tr
                    key={index}
                    onClick={() => {
                      onGoDetail(item);
                    }}
                  >
                    <td>{item.organizationName}</td>
                    <td>{item.shelterName}</td>
                    <td>{item.tel}</td>
                    <td>
                      {item.weekOperationStartTime} ~{" "}
                      {item.weekOperationEndTime}{" "}
                    </td>
                    <td>{item.address}</td>
                  </tr>
                );
              })
            ) : (
              <tr>
                <td></td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
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
