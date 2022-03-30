import st from "../styles/profile.module.scss";
import cn from "classnames";
import { useEffect, useState, useRef } from "react";
import { URL } from "public/config";
// import Editor from "components/Editor";
import axios from "axios";

export default function ProfileVolunteer({
  volunteerSeq,
  title,
  seq,
  userSeq,
}) {
  const [nowTitle, setNowTitle] = useState();
  const [content, setContent] = useState();
  // const [getData, setGetData] = useState();
  const jwt = sessionStorage.getItem("jwt");
  const closeRef = useRef(null);
  console.log("자식", title);
  useEffect(() => {
    setNowTitle(title);
  }, []);

  const onGetMembers = () => {
    setNowTitle(title);
    // console.log("zmfflr", volunteerSeq, title);
    if (volunteerSeq)
      axios
        .get(`${URL}/volunteers/${volunteerSeq}/participants`, {
          headers: { Authorization: `Bearer ${jwt}` },
        })
        .then((res) => {
          console.log("getMemebers", res.data.data);
        })
        .catch((err) => {
          console.log(err);
        });
  };

  // 에디터 부분 변경
  const onEditorChange = (value) => {
    setContent(value);
  };

  // 요청 완료되면 모달 자동으로 닫히게
  const onhandleClose = () => {
    closeRef.current.click();
  };

  // 인증 요청
  const onVolunAuth = () => {
    axios({
      url: `${URL}/voluteers/${volunteerSeq}/auth`,
      method: "POST",
      headers: { Authorization: `Bearer ${jwt}` },
      data: {
        content: "string",
        authenticatedMemberSequences: [],
      },
    })
      .then((res) => {
        console.log(res);
        onhandleClose();
        alert("요청 성공");
      })
      .catch((err) => {
        console.log(err);
        alert("요청에 실패했습니다.");
      });
  };

  if (seq === userSeq) {
    return (
      <div className={st.volunteer}>
        <button
          type="button"
          className="btn"
          style={{ backgroundColor: "#d0a96c" }}
          data-bs-toggle="modal"
          data-bs-target="#volunteerModal"
          onClick={onGetMembers}
        >
          봉사 인증
        </button>
        <div
          className="modal fade"
          id="volunteerModal"
          tabIndex="-1"
          aria-labelledby="volunteerModalLabel"
          aria-hidden="true"
        >
          <div className="modal-dialog modal-dialog-centered">
            <div className="modal-content">
              <div className="modal-header">
                <h5 className="modal-title" id="volunteerModalLabel">
                  봉사 인증 요청
                </h5>
                <button
                  type="button"
                  className="btn-close"
                  data-bs-dismiss="modal"
                  aria-label="Close"
                ></button>
              </div>
              <div className={cn(`${st.body}`, "modal-body")}>
                <div name="봉사제목" className={st.name}>
                  <div className={st.label}>
                    <label htmlFor="모집제목">
                      <span>제목</span>
                    </label>
                  </div>
                  <div className={st.input}>{nowTitle}</div>
                </div>

                <div name="인원관리" className={st.name}>
                  <div className={st.label}>
                    <label htmlFor="인원관리">
                      <span>인원관리</span>
                    </label>
                  </div>
                  <div className={st.input}>{title}</div>
                </div>

                <div name="내용" className={st.content}>
                  <div className={st.label}>
                    <label htmlFor="content">
                      <span>내용</span>
                    </label>
                  </div>
                  <div className={st.editor}>
                    {/* <Editor
                    id="content"
                    height={"90%"}
                    value={content}
                    onChange={onEditorChange}
                    placeholder={""}
                  /> */}
                  </div>
                </div>
              </div>
              <div className="modal-footer">
                <button
                  type="button"
                  className="btn btn-secondary"
                  data-bs-dismiss="modal"
                >
                  취소
                </button>
                <button
                  type="button"
                  className="btn btn-primary"
                  onClick={onVolunAuth}
                >
                  작성
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  } else {
    return (
      <div className={st.volunteer}>
        <button
          type="button"
          className="btn"
          style={{ backgroundColor: "#d0a96c" }}
        >
          봉사 참가자
        </button>
      </div>
    );
  }
}
