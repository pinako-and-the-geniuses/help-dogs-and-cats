import st from "./styles/AdoptManage.module.scss";
import React, { useEffect, useState } from "react";
import cn from "classnames";
import axios from "axios";
import { URL } from "public/config";
import { useNavigate } from "react-router-dom";

function AdoptManage({ setTab, setAdoptSeq }) {
  const [word, setWord] = useState("");
  const [adopts, setAdopts] = useState("");
  const [page, setPage] = useState(1);
  const [totalcount, setTotalcount] = useState("");
  const size = 10;
  const [search, setSearch] = useState("all");
  const navigate = useNavigate();
  const jwt = sessionStorage.getItem("jwt");

  // 목록 불러오는 함수
  const getData = () => {
      axios
      .get(
        `${URL}/admins/adopts/auth?page=${page}&size=${size}&search=${search}`,
        { headers: { Authorization: `Bearer ${jwt}` } }
      )
      .then((res) => {
        console.log("res", res.data.data);
        const temp = res.data.data.adoptAuthForPages;
        setPage(res.data.data.currentPageNumber);
        setTotalcount(res.data.data.totalCount);
        if (word) {
          const result = temp.filter((item) => item.title.includes(word));
          setAdopts(result);
          console.log("검색어 있음", word, search, result);
        } else {
          setAdopts(temp);
        }
      })
      .catch((err) => console.log("err", err));
  };

  // 첫 화면에서 전체 목록 불러옴
  useEffect(() => {
      getData();
  }, []);

  return (
    <div className={st.AdoptManage_container}>
      <header>
        <h1 className={st.AdoptManagehead}>입양 인증 목록</h1>
      </header>
      <div className={st.AdoptManage_search_bar}>
        <div className={st.search_input}>
          <select
            defaultValue="all"
            name="searchCgg"
            onChange={(e) => setSearch(e.target.value)}
          >
            <option value="all">전체</option>
            <option value="auth">인증</option>
            <option value="not-auth">미인증</option>
          </select>
          <div>
            <input
              className={st.input}
              type="text"
              value={word}
              onChange={(e) => {
                setWord(e.target.value);
              }}
            />
            <button onClick={getData}>조회</button>
          </div>
        </div>
      </div>
      <table className={cn("table table-hover", st.my_table)}>
        <thead>
          <tr>
            <th scope="col" style={{ width: "10rem" }}>
              처리상태
            </th>
            <th scope="col" colSpan="3">
              제목
            </th>
          </tr>
        </thead>
        {adopts && (
          <tbody>
            {adopts.map((adopt, index) => (
              <tr
                key={index}
                onClick={() => {
                  setTab(4);
                  setAdoptSeq(adopt.adoptAuthSeq);
                }}
              >
                {adopt.status === "REQUEST" ? <td>미인증</td> : ""}
                {adopt.status === "REJECT" ? <td>거부</td> : ""}
                {adopt.status === "DONE" ? <td>인증</td> : ""}
                <td colSpan="3">{adopt.title}</td>
              </tr>
            ))}
          </tbody>
        )}
        {!adopts && (
          <tbody>
            <td colSpan="4">작성된 글이 없습니다.</td>
          </tbody>
        )}
      </table>
    </div>
  );
}

export default AdoptManage;
