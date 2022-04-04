import st from "./styles/Home.module.scss";
import React, { useEffect, useState } from "react";
import axios from "axios";
import { URL } from "public/config";

function ManageHome({ setTab }) {
  const jwt = sessionStorage.getItem("jwt");
  const [data, setData] = useState("");

  useEffect(() => {
    axios({
      url: `${URL}/admins/auth-request-count`,
      method: "GET",
      headers: { Authorization: `Bearer ${jwt}` },
    })
      .then((res) => {
        console.log(res.data.data);
        const temp = res.data.data;
        setData({ adopt: temp.adoptAuthCount, volun: temp.volunteerAuthCount });
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);
  console.log(data);
  return (
    <div className={st.adimincontainer}>
      <div className={st.left}>
        <h3 className={st.managetext}>봉사 현황</h3>
        <div
          className={st.leftbox}
          onClick={() => {
            setTab(1);
          }}
        >
          <h3>미인증 : {data.volun}</h3>
        </div>
      </div>

      <div className={st.right}>
        <h3 className={st.managetext}>입양 현황</h3>
        <div
          className={st.leftbox}
          onClick={() => {
            setTab(2);
          }}
        >
          <h3>미인증 : {data.adopt}</h3>
        </div>
      </div>
    </div>
  );
}

export default ManageHome;
