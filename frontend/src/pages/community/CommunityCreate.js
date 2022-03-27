import React, { useEffect, useState, useRef } from "react";
import axios from "axios";
import XMLParser from "react-xml-parser";
import st from "./styles/CommunityCreate.module.scss";
import cn from "classnames";
import QuillEditor from "./QuillEditor";
export default function CommunityCreate(api) {
  const [htmlContent, setHtmlContent] = useState("");
  const quillRef = useRef();

  return (
    <div className={st.commucreatemain}>
      <header className={st.commuhead}>
        <h2>Community</h2>
      </header>
      <div className={st.createtopContent}>
            <select className="searchCd me-3">
              <option selected>카테고리</option>
              <option value="1">제보</option>
              <option value="2">잡담</option>
              <option value="3">입양후기</option>
              <option value="4">봉사후기</option>
            </select>
            <div className={st.Title}>
              <input type="text" placeholder='제목' className={st.Title} />
            </div>
      </div>
        <div className={st.quill}>
          <QuillEditor
            quillRef={quillRef}
            htmlContent={htmlContent}
            setHtmlContent={setHtmlContent}
            api={api}
          />
        </div>
      <div className={st.createbuttonContent}>
        <button type="button" className={st.communitycreatebutton}>
          작성
        </button>
        <button type="button" className={st.communitycreatebutton}>
          취소
        </button>
      </div>
    </div>
  );
}
