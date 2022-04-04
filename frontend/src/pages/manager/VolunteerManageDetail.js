import st from "./styles/VolunteerManageDetail.module.scss";
import React, { useEffect, useState } from "react";
import axios from "axios";
import { URL } from "public/config";
import { useParams, useNavigate } from "react-router-dom";

function VolunteerManageDetail() {
  const [volunteerManageDetail, setVolunteerManageDetail] = useState("");
  const [stateChanged, setStateChanged] = useState("REQUEST");
  const jwt = sessionStorage.getItem("jwt");
  const { volunteerSeq } = useParams();
  const navigate = useNavigate();
  const getlist = () => {
    navigate(`/volunteermanage`);
  };
  useEffect(() => {
    axios({
      url: `${URL}/admins/volunteers/auth/${volunteerSeq}`,
      method: "GET",
      headers: { Authorization: `Bearer ${jwt}` },
    })
      .then((response) => {
        console.log(response.data);
        setVolunteerManageDetail(response.data);
      }) //엑시오스 보낸 결과
      .catch((err) => console.log(err));
  }, []);

  const getApproval = async (approve) => {
    await axios({
      url: `${URL}/admins/volunteers/auth/${volunteerSeq}`,
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
    <div className={st.volunteer_commentBox}>
      <header>
        <h2>봉사 인증 상세</h2>
      </header>
      <section className={st.topContent}>
        {volunteerManageDetail ? (
          <>
            <div className={st.alltitle}>
              <p className={st.tag_p}>
                {volunteerManageDetail.data.status === "REQUEST"
                  ? "미인증"
                  : ""}
                {volunteerManageDetail.data.status === "REJECT" ? "거부" : ""}
                {volunteerManageDetail.data.status === "DONE" ? "인증" : ""}
              </p>
              <p className={st.title_p}>제목 : 인증</p>
            </div>
            <div
              className={st.content_div}
              dangerouslySetInnerHTML={{
                __html: volunteerManageDetail.data.content,
              }}
            ></div>
            <div className={st.contentbtn}>
              <button className={st.listbutton} onClick={() => getlist()}>
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

export default VolunteerManageDetail;
