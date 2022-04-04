import st from "./styles/AdoptManage.module.scss";
import React, { useEffect, useState } from "react";
import cn from "classnames";
import axios from "axios";
import { URL } from "public/config";
import { useNavigate } from "react-router-dom";
function AdoptManage() {
  const [adopts, setAdopts] = useState("");
  const [page, setPage] = useState(1);
  const [totalcount, setTotalcount] = useState("");
  const size = 10;
  const [search, setSearch] = useState("");
  const navigate = useNavigate();
  const jwt = sessionStorage.getItem("jwt");
  useEffect(() => {
    console.log(search, size, page);
    axios
      .get(
        `${URL}/admins/adopts/auth?page=${page}&size=${size}&search=${search}`,
        { headers: { Authorization: `Bearer ${jwt}` } }
      )
      .then((response) => {
        setAdopts(response.data.data.adoptAuthForPages);
        // setPage(response.data.data.currentPageNumber);
        // setTotalcount(response.data.data.totalCount);
        console.log(response.data);
      })
      .catch((err) => console.log(err));
  }, []); //한번만 해줄때 []넣는다

  const getRead = (e) => {
    axios
      .get(
        `${URL}/admins/adopts/auth?page=${page}&size=${size}&search=${search}`,
        { headers: { Authorization: `Bearer ${jwt}` } }
      )
      .then((response) => {
        setAdopts(response.data.data.adoptAuthForPages);
        setPage(response.data.data.currentPageNumber);
        setTotalcount(response.data.data.totalCount);
        console.log(response.data);
      })
      .catch((err) => console.log(err));
  };

  const getSeq = (adoptAuthSeq) => {
    navigate(`/adoptmanage/detail/${adoptAuthSeq}`);
  };
  const getSearch = (e) => {
    console.log("search", e.target.value);
    setSearch(e.target.value);
  };

  return (
    <div className={st.AdoptManage_container}>
      <header className={st.AdoptManagehead}>
        <h2>입양 인증 목록</h2>
      </header>
      <div className={st.AdoptManage_search_bar}>
        <div className={st.search_input}>
          <select defaultValue="" name="searchCgg" onChange={getSearch}>
            <option value="all">전체</option>
            <option value="auth">인증</option>
            <option value="not-auth">미인증</option>
          </select>
          <div>
            <input className={st.input} type="text" />
            <button onClick={getRead}>조회</button>
            {/* <button onClick={getRead}>조회</button> */}
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
        {adopts ? (
          <tbody>
            {adopts.map((adopt) => (
              <tr key={adopt.seq} onClick={() => getSeq(adopt.adoptAuthSeq)}>
                {/* <td>{adopt.status}</td> */}
                {adopt.status === "REQUEST" ? <td>미인증</td> : ""}
                {adopt.status === "REJECT" ? <td>거부</td> : ""}
                {adopt.status === "DONE" ? <td>인증</td> : ""}
                <td colSpan="3">{adopt.title}</td>
              </tr>
            ))}
          </tbody>
        ) : (
          <tbody>
            <td colSpan="4">작성된 글이 없습니다.</td>
          </tbody>
        )}
      </table>
    </div>
  );
}

export default AdoptManage;
