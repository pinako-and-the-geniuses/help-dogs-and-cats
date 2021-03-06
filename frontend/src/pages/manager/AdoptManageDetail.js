import st from "./styles/AdoptManageDetail.module.scss";
import React, { useEffect, useState } from "react";
import axios from "axios";
import { URL } from "public/config";
import Swal from "sweetalert2";

export default function AdoptManageDetail({ adoptSeq, setTab }) {
  const [adoptManageDetail, setAdoptManageDetail] = useState("");
  const jwt = sessionStorage.getItem("jwt");

  const onGetDetail = () => {
    axios({
      url: `${URL}/admins/adopts/auth/${adoptSeq}`,
      method: "GET",
      headers: { Authorization: `Bearer ${jwt}` },
    }).then((response) => {
      setAdoptManageDetail(response.data);
    }); //엑시오스 보낸 결과
  };

  useEffect(() => {
    onGetDetail();
  }, []);

  const onGoApproval = (approve) => {
    axios({
      url: `${URL}/admins/adopts/auth/${adoptSeq}`,
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

  const onSwal = (approve) => {
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
        <h1 className={st.header}>입양 인증 상세</h1>
      </header>
      <div className={st.topContent}>
        {adoptManageDetail ? (
          <>
            <div className={st.alltitle}>
              <div>
                {adoptManageDetail.data.status === "REQUEST" && (
                  <p className={st.tag_p}>미인증</p>
                )}
                {adoptManageDetail.data.status === "REJECT" && (
                  <p className={st.tag_p}>거부</p>
                )}
                {adoptManageDetail.data.status === "DONE" && (
                  <p className={st.tag_p}>인증</p>
                )}

                <p className={st.title_p}>
                  제목 : {adoptManageDetail.data.title}
                </p>
              </div>
              <div>
                <span>작성자 : {adoptManageDetail.data.nickname}</span> |{" "}
                <span>{adoptManageDetail.data.createdDate}</span>
              </div>
            </div>
            <div
              className={st.content_div}
              dangerouslySetInnerHTML={{
                __html: adoptManageDetail.data.content,
              }}
            ></div>
            <div className={st.contentbtn}>
              <div>
                <button className={st.listbutton} onClick={() => setTab(3)}>
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
      </div>
    </div>
  );
}
