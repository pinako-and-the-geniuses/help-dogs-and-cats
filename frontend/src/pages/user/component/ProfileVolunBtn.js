import st from "../styles/profile.module.scss";
import cn from "classnames";
import { useSelector } from "react-redux";
import { useEffect, useState, useRef } from "react";

export default function ProfileVolunBtn({ item }) {
  const [data, setData] = useState();

  const [content, setContent] = useState();
  const [checkedInputs, setCheckedInputs] = useState("");

  const closeRef = useRef(null);
  const userSeq = useSelector((state) => state.userInfo.userInfo.seq);

  const onClickModal = (item) => {
    setContent("");
    setCheckedInputs("");
    setData(item);
  };

  return (
    <>
      <div className={st.volunteer}>
        {item.memberSeq === userSeq ? (
          <button
            type="button"
            className="btn"
            style={{ backgroundColor: "#d0a96c" }}
            data-bs-toggle="modal"
            data-bs-target="#volunteerModal"
            onClick={() => {
              onClickModal(item);
              // onGetModalData(["etc", item.volunteerSeq]);
            }}
          >
            {item.authStatus === null ? "인증신청" : ""}
            {item.authStatus === "REJECT" ? "인증거절" : ""}
            {item.authStatus === "REQUEST" ? "신청완료" : ""}
            {item.authStatus === "DONE" ? "인증완료" : ""}
          </button>
        ) : (
          <button
            type="button"
            className="btn"
            style={{ backgroundColor: "#d0a96c" }}
          >
            &nbsp; 참여자 &nbsp;
          </button>
        )}
        {data ? (
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
                    봉사 인증 요청서
                  </h5>
                  <button
                    ref={closeRef}
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
                    <div className={st.input}>{data.title}</div>
                    {/* <div className={st.input}>제목</div> */}
                  </div>

                  <div name="인원관리" className={st.name}>
                    <div className={st.label}>
                      <label htmlFor="인원관리">
                        <span>참가인원</span>
                      </label>
                    </div>
                    {/* {onMemberCheck()} */}
                  </div>

                  <div name="내용" className={st.content}>
                    <div className={st.label}>
                      <label htmlFor="content">
                        <span>내용</span>
                      </label>
                    </div>
                    <div className={st.editor}>
                      데이터
                      {/* {modalData.authStatus === null ||
                    modalData.authStatus === "REJECT" ? (
                      <Editor
                        id="content"
                        height={"83%"}
                        value={content || ""}
                        onChange={onEditorChange}
                        placeholder={""}
                        // className={st.classEditor}
                      />
                    ) : (
                      <div
                        className={st.htmlDiv}
                        dangerouslySetInnerHTML={{
                          __html: content,
                        }} 
                      ></div>
                    )}*/}
                    </div>
                  </div>
                </div>

                <div name="하단버튼" className="modal-footer">
                  <button>버튼</button>
                  {/* {onBottomBtn(modalData.authStatus)} */}
                </div>
              </div>
            </div>
          </div>
        ) : (
          ""
        )}
      </div>
    </>
  );
}
