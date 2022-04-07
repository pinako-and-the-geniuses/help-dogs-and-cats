import st from "./styles/Home.module.scss";
import React, { useEffect, useState } from "react";
import axios from "axios";
import { URL } from "public/config";
import { useSelector } from "react-redux";
import swal from "sweetalert";
import { useNavigate } from "react-router-dom";
function ManageHome({ setTab }) {
  const jwt = sessionStorage.getItem("jwt");
  const [data, setData] = useState("");
  const isLogin = useSelector((state) => state.userInfo.isLoggedIn);
  const navigate = useNavigate();

  useEffect(() => {
    if (!isLogin) {
      swal({
        title: "권한이 없습니다. ",
        icon: "error",
        closeOnClickOutside: false,
      });
      navigate("/login", { replace: true });
    } else {
      axios({
        url: `${URL}/admins/auth-request-count`,
        method: "GET",
        headers: { Authorization: `Bearer ${jwt}` },
      }).then((res) => {
        const temp = res.data.data;
        setData({ adopt: temp.adoptAuthCount, volun: temp.volunteerAuthCount });
      });
    }
  }, []);

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
            setTab(3);
          }}
        >
          <h3>미인증 : {data.adopt}</h3>
        </div>
      </div>
    </div>
  );
}

export default ManageHome;
