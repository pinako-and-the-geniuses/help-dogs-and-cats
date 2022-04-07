import st from "./styles/AdoptManageDetail.module.scss";
import React, { useEffect, useState } from "react";
import axios from "axios";
import { URL } from "public/config";
import Swal from "sweetalert2";

function VolunteerManageDetail({ volunSeq, setTab }) {
  const [volunteerManageDetail, setVolunteerManageDetail] = useState("");
  const jwt = sessionStorage.getItem("jwt");

  const onGetDetail = () => {
    axios({
      url: `${URL}/admins/volunteers/auth/${volunSeq}`,
      method: "GET",
      headers: { Authorization: `Bearer ${jwt}` },
    }).then((response) => {
      setVolunteerManageDetail(response.data);
    }); //엑시오스 보낸 결과
  };

  useEffect(() => {
    onGetDetail();
  }, []);

  const onGoApproval = (approve) => {
    axios({
      url: `${URL}/admins/volunteers/auth/${volunSeq}`,
      method: "patch",
      data: {
        status: approve,
      },
      headers: {
        Authorization: `Bearer ${jwt}`,
      },
    }).then((res) => {
      onGetDetail();
    });
  };

  const onSwal = async (approve) => {
    if (approve === "REQUEST") {
      Swal.fire({
        title: "인증 하시겠습니까?",
        icon: "success",
        confirmButtonColor: `#b59d7c`,
        showCancelButton: true,
      }).then((res) => {
        if (res.isConfirmed) {
          onGoApproval("DONE");
        }
      });
    } else if (approve === "REJECT") {
      Swal.fire({
        title: "반려 하시겠습니까?",
        icon: "error",
        confirmButtonColor: `#b59d7c`,
        showCancelButton: true,
      }).then((res) => {
        if (res.isConfirmed) {
          onGoApproval("REJECT");
        }
      });
    }
  };

  return (
    <div className={st.adopt_commentBox}>
      <header>
        <h1 className={st.header}>봉사 인증 상세</h1>
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
              <div>
                <button className={st.listbutton} onClick={() => setTab(1)}>
                  목록으로
                </button>
              </div>
              <div>
                <button
                  onClick={() => onSwal("REJECT")}
                  className={st.deletebutton}
                >
                  반려
                </button>
                <button onClick={() => onSwal("REQUEST")} className={st.button}>
                  인증
                </button>
              </div>
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
