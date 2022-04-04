import st from "./styles/AdoptManageDetail.module.scss";
import React, { useEffect, useState } from "react";
import axios from "axios";
import { useParams, useNavigate } from "react-router-dom";
import { URL } from "public/config";

export default function AdoptManageDetail() {
  const [adoptManageDetail, setAdoptManageDetail] = useState("");
  const [stateChanged, setStateChanged] = useState("REQUEST");
  const jwt = sessionStorage.getItem("jwt");
  const navigate = useNavigate();
  const { adoptSeq } = useParams();

  const getlist = () => {
    navigate(`/manage`);
  };

  useEffect(() => {
    axios({
      url: `${URL}/admins/adopts/auth/${adoptSeq}`,
      method: "GET",
      headers: { Authorization: `Bearer ${jwt}` },
    })
      .then((response) => {
        console.log(response.data);
        setAdoptManageDetail(response.data);
      }) //엑시오스 보낸 결과
      .catch((err) => console.log(err));
  }, []);

  const getApproval = async (approve) => {
    await axios({
      url: `${URL}/admins/adopts/auth/${adoptSeq}`,
      method: "patch",
      data: {
        status: approve,
      },
      headers: {
        Authorization: `Bearer ${jwt}`,
      },
    })
      .then((res) => {
        console.log("변경 완료");
        console.log(res);
        if (stateChanged === "REQUEST" || stateChanged === "REJECT") {
          setStateChanged(stateChanged === "DONE");
        } else if (stateChanged === "REQUEST" || stateChanged === "DONE") {
          setStateChanged(stateChanged === "REJECT");
        }
      })
      .catch((err) => console.log(err));
  };

  return (
    <div className={st.adopt_commentBox}>
      <header>
        <h2>입양 인증 상세</h2>
      </header>
      <section className={st.topContent}>
        {adoptManageDetail ? (
          <>
            <div className={st.alltitle}>
<<<<<<< HEAD
              <p className={st.tag_p}>
                {adoptManageDetail.data.status === "REQUEST" ? "미인증" : ""}
                {adoptManageDetail.data.status === "REJECT" ? "거부" : ""}
                {adoptManageDetail.data.status === "DONE" ? "인증" : ""}
              </p>
=======
              <p className={st.tag_p}>상태</p>
>>>>>>> front/fix/admin/list/#S06P22A302-176
              <p className={st.title_p}>
                제목 : {adoptManageDetail.data.title}
              </p>
            </div>
            <div
              className={st.content_div}
              dangerouslySetInnerHTML={{
                __html: adoptManageDetail.data.content,
              }}
            ></div>
            <div className={st.contentbtn}>
<<<<<<< HEAD
              <button className={st.listbutton} onClick={() => getlist()}>
=======
              <button
                className={st.listbutton}
                onClick={() => getlist("adopts")}
              >
>>>>>>> front/fix/admin/list/#S06P22A302-176
                목록으로
              </button>
              <button
                onClick={() => getApproval("REJECT")}
                className={st.deletebutton}
              >
                반려
              </button>
              <button onClick={() => getApproval("DONE")} className={st.button}>
                인증
              </button>
            </div>
          </>
        ) : (
          "로딩 중"
        )}
      </section>
    </div>
  );
}
