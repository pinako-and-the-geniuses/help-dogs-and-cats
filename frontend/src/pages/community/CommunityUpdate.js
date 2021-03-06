import React, { useEffect, useState } from "react";
import axios from "axios";
import st from "./styles/CommunityUpdate.module.scss";
import cn from "classnames";
import { URL } from "public/config";
import Editor from "components/Editor";
import { useNavigate, useParams } from "react-router-dom";
import { useSelector } from "react-redux";
import Swal from "sweetalert2";
export default function CommunityCreate(api) {
  const [category, setCategory] = useState("");
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const isLogin = useSelector((state) => state.userInfo.isLoggedIn);
  const navi = useNavigate();
  const jwt = sessionStorage.getItem("jwt");
  const { seq } = useParams();
  const userInfo = useSelector((state) => state.userInfo.userInfo);
  const onEditorChange = (value) => {
    setContent(value);
  };
  useEffect(() => {
    if (!isLogin) {
      Swal.fire({
        icon: "error",
        title: "로그인 필수",
      });
    } else {
      axios({
        url: `${URL}/communities/${seq}`,
        method: "GET",
        headers: { Authorization: `Bearer ${jwt}` },
      }).then((res) => {
        const data = res.data.data;
        setTitle(data.title);
        setCategory(data.category);
        setContent(data.content);
      });
    }
  }, [isLogin]);

  const onSubmit = (event) => {
    event.preventDefault();
    axios({
      url: `${URL}/communities/${seq}`,
      method: "PUT",
      headers: { Authorization: `Bearer ${jwt}` },
      data: {
        category: category,
        title: title,
        content: content,
      },
    })
      .then((res) => {
        if (res.status === 200) {
          // alert("수정 완료");
          // navi(`/community/communitydetail/${seq}`);
          Swal.fire({
            icon: "success",
            title: "게시글을 수정하였습니다.",
            confirmButtonColor: `#b59d7c`,
          }).then(function () {
            navi(`/community/communitydetail/${seq}`);
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
            title: "게시글을 수정하지 못했습니다.",
            confirmButtonColor: `#b59d7c`,
          }).then(function () {
            navi(`/community/communitydetail/${seq}`);
          });
        }
      });
  };
  return (
    <div className={st.commuupdatemain}>
      <header className={st.commuhead}>
        <h1>
          커뮤니티 <span> 글 수정</span>
        </h1>
      </header>
      <div className={st.createtopContent}>
        {/* <select
          className="searchCd me-3"
          value={category}
          onChange={(event) => setCategory(event.target.value)}
        >
          <option value="">카테고리</option>
          <option value="report">제보</option>
          <option value="general">잡담</option>
          <option value="review">후기</option>
          <option value="notice">공지</option>
        </select> */}
        {userInfo.role !== "ADMIN" ? (
          <select
            className="searchCd me-3"
            onChange={(event) => setCategory(event.target.value)}
          >
            <option defaultValue>
              {category === "REPORT" ? "제보" : ""}
              {category === "REVIEW" ? "후기" : ""}
              {category === "GENERAL" ? "잡담" : ""}
            </option>
            <option value="report">제보</option>
            <option value="general">잡담</option>
            <option value="review">후기</option>
          </select>
        ) : (
          <select
            className="searchCd me-3"
            onChange={(event) => setCategory(event.target.value)}
          >
            {/* <option defaultValue>카테고리</option> */}
            <option value="notice">공지</option>
          </select>
        )}
        <div className={st.Title}>
          <input
            value={title}
            type="text"
            placeholder="제목"
            className={st.Title}
            onChange={(event) => setTitle(event.target.value)}
          />
        </div>
      </div>
      <div className={cn(`${st.Editorheight}`, "col-sm-12 col-md-10")}>
        <Editor
          id="content"
          height={"90%"}
          value={content}
          onChange={onEditorChange}
          placeholder={""}
        ></Editor>
      </div>
      <div className={st.createbuttonContent}>
        <button
          type="button"
          onClick={onSubmit}
          className={st.communitycreatebutton}
        >
          수정
        </button>
      </div>
    </div>
  );
}
