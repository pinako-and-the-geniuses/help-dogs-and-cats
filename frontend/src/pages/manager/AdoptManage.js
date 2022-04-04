import st from "./styles/AdoptManage.module.scss";
import React, { useEffect, useState } from "react";
import cn from "classnames";
import axios from "axios";
import { URL } from "public/config";
import { useNavigate } from "react-router-dom";
function AdoptManage() {
  const [adopt, setAdopt] = useState("");
  const [page, setPage] = useState(1);
  const size = 10;
  const [search, setSearch] = useState("");
  const navigate = useNavigate();
  const jwt = sessionStorage.getItem("jwt");
  const getSeq = () => {
    navigate(`/adoptmanage/detail/`);
  };
  useEffect(() => {
    console.log(search, size, page);
    console.log("readadopt", adopt);
    axios
      .get(
        `${URL}/admins/adopts/auth?page=1&size=${size}&search=${search}`,
        {headers: { Authorization: `Bearer ${jwt}` },
      })
      .then((response) => {
        console.log(response.data);
        setAdopt("setAdopt" ,response.data);
        console.log("read", adopt);
      })
      .catch((err) => console.log(err));
  }, []); //한번만 해줄때 []넣는다


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

          <select defaultValue="" name="searchCgg" >
            <option value="all">전체</option>
            <option value="title">인증</option>
            <option value="writer">미인증</option>
          </select>
          <div>
            <input className={st.input} type="text" />
            <button>조회</button>
            {/* <button onClick={getRead}>조회</button> */}
          </div>
        </div>
      </div>
      <table className={cn("table table-hover", st.my_table)}>
        <thead>
          <tr>
            <th scope="col" style={{ width: "10rem" }}>처리상태</th>
            <th scope="col" colSpan="3">제목</th>

          </tr>
        </thead>
        <tbody>
          <tr onClick={() => getSeq()}>
            <th scope="row">인증</th>
            <td colSpan="3">Mark</td>
          </tr>
          <tr>
            <th scope="row">미인증</th>
            <td colSpan="3">Jacob</td>
          </tr>
          <tr>
            <th scope="row">미인증</th>
            <td colspan="3">Larry the Bird</td>
          </tr>
        </tbody>
      </table>
    </div>
  );
}

export default AdoptManage;
