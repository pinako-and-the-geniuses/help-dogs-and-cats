import st from "./styles/VolunteerManage.module.scss";
import React, { useEffect, useState } from "react";
import cn from "classnames";
import { useNavigate } from "react-router-dom";

function VolunteerManage(){
    const navigate = useNavigate();
  const getSeq = () => {
    //console.log(seq);
    navigate(`/volunteermanage/detail/`);
  };
  return (
    <div className={st.VolunteerManage_container}>
      <header className={st.VolunteerManagehead}>
        <h2>봉사 인증 목록</h2>
      </header>
      <div className={st.VolunteerManage_search_bar}>
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

export default VolunteerManage;