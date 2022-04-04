import st from "./styles/VolunteerManage.module.scss";
import React, { useEffect, useState } from "react";
import cn from "classnames";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { URL } from "public/config";
function VolunteerManage(){
    const navigate = useNavigate();
    const jwt = sessionStorage.getItem("jwt");
    const [page, setPage] = useState(1);
    const [totalcount, setTotalcount] = useState("");
    const [search, setSearch] = useState("");
    const [volunteers, setVolunteers] = useState("");
    const size = 10;
  
  useEffect(() => {
    // console.log(search, size, page);
      axios({
        url: `${URL}/admins/volunteer/auth?page=${page}&size=${size}&search=${search}`,
        method: "GET",
        headers: { Authorization: `Bearer ${jwt}` },
      })
      .then((response) => {
        console.log(response.data.data);
        setVolunteers(response.data.data.volunteerAuthForPages);
        // setPage(response.data.data.currentPageNumber);
        // setTotalcount(response.data.data.totalCount);
      })
      .catch((err) => console.log(err));
  }, []); //한번만 해줄때 []넣는다

  const getRead = (e) => {
    axios
      .get(
        `${URL}/admins/volunteers/auth?page=${page}&size=${size}&search=${search}`,
        { headers: { Authorization: `Bearer ${jwt}` } }
      )
      .then((response) => {
        setVolunteers(response.data.data.volunteerAuthForPages);
        // setPage(response.data.data.currentPageNumber);
        // setTotalcount(response.data.data.totalCount);
        console.log(response.data);
      })
      .catch((err) => console.log(err));
  };
  const getSeq = (volunteerAuthSeq) => {
    navigate(`/volunteermanage/detail/${volunteerAuthSeq}`);
  };
  const getSearch = (e) => {
    console.log("search", e.target.value);
    setSearch(e.target.value);
  };
  return (
    <div className={st.VolunteerManage_container}>
      <header className={st.VolunteerManagehead}>
        <h2>봉사 인증 목록</h2>
      </header>
      <div className={st.VolunteerManage_search_bar}>
        <div className={st.search_input}>

          <select defaultValue="" name="searchCgg" onChange={getSearch}>
            <option value="all">전체</option>
            <option value="auth">인증</option>
            <option value="not-auth">미인증</option>
          </select>
          <div>
            <input className={st.input} type="text" />
            <button onClick={getRead}>조회</button>
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
        {volunteers ? (
        <tbody>
          {volunteers.map((volunteer) => (
          <tr key={volunteer.seq} onClick={() => getSeq(volunteer.volunteerAuthSeq)}>
            {volunteer.status === "REQUEST" ? <td>미인증</td> : ""}
            {volunteer.status === "REJECT" ? <td>거부</td> : ""}
            {volunteer.status === "DONE" ? <td>인증</td> : ""}
            <td colSpan="3">{volunteer.title}</td>
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

export default VolunteerManage;