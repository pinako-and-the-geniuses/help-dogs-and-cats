import st from "./styles/AdoptManageDetail.module.scss";
import React, { useEffect, useState } from "react";
import axios from "axios";
import { URL } from "public/config";
import Swal from "sweetalert2";
import { useNavigate } from "react-router-dom";
export default function AdoptManageDetail({ adoptSeq, setTab }) {
  const [adoptManageDetail, setAdoptManageDetail] = useState("");
  const [stateChanged, setStateChanged] = useState("REQUEST");
  const jwt = sessionStorage.getItem("jwt");
  const navi = useNavigate();
  const read = () => {
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
  };
  useEffect(() => {
    read();
  }, []);

  // const getApproval = async (approve) => {
  //   await axios({
  //     url: `${URL}/admins/adopts/auth/${adoptSeq}`,
  //     method: "patch",
  //     data: {
  //       status: approve,
  //     },
  //     headers: {
  //       Authorization: `Bearer ${jwt}`,
  //     },
  //   })
  //     .then((res) => {
  //       if (setStateChanged(stateChanged === "REQUEST" || stateChanged === "REJECT")) {
  //         Swal.fire({
  //           title: "인증 하시겠습니까?",
  //           icon: "success",
  //           confirmButtonColor: `#b59d7c`,
  //           showCancelButton: true,
  //         }).then((res) => {
  //           setStateChanged(stateChanged === "DONE");
  //         });
  //       }else if(setStateChanged(stateChanged === "REQUEST" || stateChanged === "DONE")){
  //         Swal.fire({
  //           title: "반려 하시겠습니까?",
  //           icon: "warning",
  //           confirmButtonColor: `#b59d7c`,
  //           showCancelButton: true,
  //         }).then((res) => {
  //           setStateChanged(stateChanged === "REJECT");
  //         });
  //       }
  //     })
  // }
    

  const getApproval2 = async (approve) => {
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
        console.log(res.data);
        if(setStateChanged(approve === "REQUEST")){
        Swal.fire({
          title: "인증 하시겠습니까?",
          text: "인증하면 되돌릴 수 없습니다",
          icon: "success",
          confirmButtonColor: `#b59d7c`,
          cancelButtonColor: "#999999",
          showCancelButton: true,
        }).then((res) => {
          if (res.value) {
            setStateChanged(approve === "DONE");
            console.log(stateChanged);
            navi(setTab(3));
          } else if (res.dismiss === "cancel") {
            setStateChanged(approve);
            console.log(approve);
          }
        });
      }
      })
      .catch((err) => console.log(err));
  };

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
        console.log(res.data);
        if(setStateChanged(approve === "REQUEST")){
        Swal.fire({
          title: "반려 하시겠습니까?",
          text: "반려하면 되돌릴 수 없습니다",
          icon: "warning",
          confirmButtonColor: `#b59d7c`,
          cancelButtonColor: "#999999",
          showCancelButton: true,
        }).then((res) => {
          if (res.value) {
            setStateChanged(approve === "DONE");
            navi(setTab(3));
          } else if (res.dismiss === "cancel") {
            setStateChanged(approve);
          }
        });
      }
      })
      .catch((err) => console.log(err));
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
              {stateChanged === "REQUEST" ? (
                <div>
                  <button
                    onClick={() => getApproval("REQUEST")}
                    className={st.deletebutton}
                  >
                    반려
                  </button>
                  <button
                    onClick={() => getApproval2("REQUEST")}
                    className={st.button}
                  >
                    인증
                  </button>
                </div>
              ) : (
                ""
              )}
            </div>
          </>
        ) : (
          "로딩 중"
        )}
      </div>
    </div>
  );
}
