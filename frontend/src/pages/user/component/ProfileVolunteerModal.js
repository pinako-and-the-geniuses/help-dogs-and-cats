import st from "../styles/profile.module.scss";
import cn from "classnames";
import { useState } from "react";

import Editor from "components/Editor";

export default function ProfileVolunteer(item) {
  const [content, setContent] = useState();
  return (
    <div className={st.volunteer}>
      <button
        type="button"
        className="btn"
        style={{ backgroundColor: "#d0a96c" }}
        data-bs-toggle="modal"
        data-bs-target="#volunteerModal"
      >
        봉사 인증
      </button>
      <div
        className="modal fade"
        id="volunteerModal"
        tabindex="-1"
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
                <div className={st.input}>{item.title}</div>
              </div>

              <div name="인원관리" className={st.name}>
                <div className={st.label}>
                  <label htmlFor="인원관리">
                    <span>인원관리</span>
                  </label>
                </div>
                <div className={st.input}>{item.title}</div>
              </div>

              <div name="내용" className={st.content}>
                <div className={st.label}>
                  <label htmlFor="content">
                    <span>내용</span>
                  </label>
                </div>
                <div className={st.editor}>
                  <Editor
                    id="content"
                    height={"90%"}
                    value={content}
                    setValue={setContent}
                    placeholder={""}
                  />
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
              <button type="button" className="btn btn-primary">
                작성
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
