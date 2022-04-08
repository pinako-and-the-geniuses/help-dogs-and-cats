import st from "./styles/AdoptManage.module.scss";
import React, { useEffect, useState } from "react";
import cn from "classnames";

import axios from "axios";
import { URL } from "public/config";
function VolunteerManage({ setTab, setVolunSeq }) {
  const [word, setWord] = useState("");
  const jwt = sessionStorage.getItem("jwt");
  const [page, setPage] = useState(1);
  const [totalcount, setTotalcount] = useState("");
  const [search, setSearch] = useState("all");
  const [volunteers, setVolunteers] = useState("");
  const size = 10;

  const getData = () => {
    axios({
      url: `${URL}/admins/volunteers/auth?page=${page}&size=${size}&search=${search}`,
      method: "GET",
      headers: { Authorization: `Bearer ${jwt}` },
    }).then((res) => {
      const temp = res.data.data.volunteerAuthForPages;
      setPage(res.data.data.currentPageNumber);
      setTotalcount(res.data.data.totalCount);
      if (word) {
        const result = temp.filter((item) => item.title.includes(word));
        setVolunteers(result);
      } else {
        setVolunteers(temp);
      }
    });
  };
  useEffect(() => {
    getData();
  }, []); //한번만 해줄때 []넣는다

  return (
    <div className={st.AdoptManage_container}>
      <header>
        <h1 className={st.AdoptManagehead}>봉사 인증 목록</h1>
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
        {volunteers && (
          <tbody>
            {volunteers.map((volunteer, index) => (
              <tr
                key={index}
                onClick={() => {
                  setTab(2);
                  setVolunSeq(volunteer.volunteerAuthSeq);
                }}
              >
                {volunteer.status === "REQUEST" ? <td>미인증</td> : ""}
                {volunteer.status === "REJECT" ? <td>거부</td> : ""}
                {volunteer.status === "DONE" ? <td>인증</td> : ""}
                <td colSpan="3">{volunteer.title}</td>
              </tr>
            ))}
          </tbody>
        )}
        {!volunteers && (
          <tbody>
            <td colSpan="4">작성된 글이 없습니다.</td>
          </tbody>
        )}
      </table>
    </div>
  );
}

export default VolunteerManage;
