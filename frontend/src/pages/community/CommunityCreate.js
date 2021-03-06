import React, { useState } from "react";
import axios from "axios";
import { URL } from "public/config";
import st from "./styles/CommunityCreate.module.scss";
import Editor from "components/Editor";
import { useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";
import Swal from "sweetalert2";
export default function CommunityCreate(api) {
  const [category, setCategory] = useState("");
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const navi = useNavigate();
  const userInfo = useSelector((state) => state.userInfo.userInfo);
  const onEditorChange = (value) => {
    setContent(value);
  };

  const onSubmit = (event) => {
    event.preventDefault();
    const jwt = sessionStorage.getItem("jwt");
    axios({
      url: `${URL}/communities`,
      method: "POST",
      headers: { Authorization: `Bearer ${jwt}` },
      data: {
        category: category,
        title: title,
        content: content,
      },
    })
      .then((res) => {
        if (res.status === 201) {
          Swal.fire({
            icon: "success",
            title: "게시글을 생성하였습니다.",
          }).then(function () {
            navi("/community/community");
          });
        }
      })
      .catch((err) => {
        if (category === "") {
          Swal.fire({
            icon: "warning",
            title: "카테고리를 선택해주세요.",
            confirmButtonColor: `#b59d7c`,
          });
        } else if (title === "") {
          Swal.fire({
            icon: "warning",
            title: "제목을 써주세요",
            confirmButtonColor: `#b59d7c`,
          });
        } else if (content === "") {
          Swal.fire({
            icon: "warning",
            title: "내용을 써주세요",
            confirmButtonColor: `#b59d7c`,
          });
        } else {
          Swal.fire({
            icon: "error",
            title: "게시글을 생성하지 못했습니다.",
            confirmButtonColor: `#b59d7c`,
          }).then(function () {
            navi("/community/community");
          });
        }
      });
  };

  return (
    <div className={st.commucreatemain}>
      <header className={st.commuhead}>
        <h1>
          커뮤니티 <span> 글 작성</span>
        </h1>
      </header>
      <div className={st.createtopContent}>
        {userInfo.role !== "ADMIN" ? (
          <select
            className="searchCd me-3"
            onChange={(event) => setCategory(event.target.value)}
          >
            <option value="">카테고리</option>
            <option value="report">제보</option>
            <option value="general">잡담</option>
            <option value="review">후기</option>
          </select>
        ) : (
          <select
            className="searchCd me-3"
            onChange={(event) => setCategory(event.target.value)}
          >
            <option defaultValue>카테고리</option>
            <option value="notice">공지</option>
          </select>
        )}

        <div className={st.Title}>
          <input
            type="text"
            placeholder="제목"
            className={st.Title}
            onChange={(event) => setTitle(event.target.value)}
          />
        </div>
      </div>
      <div className={st.Editorheight}>
        <Editor
          id="content"
          height={"90%"}
          value={content || ""}
          onChange={onEditorChange}
          placeholder={""}
        ></Editor>
      </div>
      <div className={st.createbuttonContent}>
        <>
          <button
            type="button"
            onClick={onSubmit}
            className={st.communitycreatebutton}
          >
            작성
          </button>
        </>
      </div>
    </div>
  );
}
