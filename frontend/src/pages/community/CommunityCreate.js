import React, { useEffect, useState, useRef } from "react";
import axios from "axios";
import XMLParser from "react-xml-parser";
import st from "./styles/CommunityCreate.module.scss";
import cn from "classnames";
import QuillEditor from "./QuillEditor";
import { useNavigate } from "react-router-dom";
export default function CommunityCreate(api) {
  const [htmlContent, setHtmlContent] = useState("");
  const quillRef = useRef();
  const [category, setCategory] = useState("");
  const [title, setTitle] = useState("");
  const navi = useNavigate();
  const onSubmit = (event) => {
    event.preventDefault();
    console.log(category);
    console.log(title);
    const jwt = sessionStorage.getitem("jwt")
    axios({
      url: `${URL}/communities`,
      method: "POST",
      headers: {
        Authorization : jwt
      },
      data: {
        category : category,
        title : title
      },
    })
    .then((res) => {
      console.log(res);
      if (res.status === 201) {
        alert("게시글 생성");
        navi("/community/community");
      }
    })
    .catch((err) => {
      alert("게시글 실패");
      console.log(err.response.status);
    });
  }
  return (
    <div className={st.commucreatemain}>
      <header className={st.commuhead}>
        <h2>Community</h2>
      </header>
      <div className={st.createtopContent}>
            <select className="searchCd me-3" onChange={(event) =>
              setCategory(event.target.value)}>
              <option selected>카테고리</option>
              <option value="information">제보</option>
              <option value="talk">잡담</option>
              <option value="adoption">입양후기</option>
              <option value="volunteer">봉사후기</option>
            </select>
            <div className={st.Title}>
              <input type="text" placeholder='제목' className={st.Title} onChange={(event) =>
              setTitle(event.target.value)}/>
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
        <button type="button" onClick={onSubmit} className={st.communitycreatebutton}>
          작성
        </button>
        <button type="button" className={st.communitycreatebutton}>
          취소
        </button>
      </div>
    </div>
  );
}
