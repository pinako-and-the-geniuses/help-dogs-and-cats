import React, { useState } from "react";
import axios from "axios";
import { URL } from "public/config";
import st from "./styles/CommunityCreate.module.scss";
import Editor from "components/Editor";
import { useNavigate } from "react-router-dom";
import Swal from "sweetalert2";
export default function CommunityCreate(api) {
  const [category, setCategory] = useState("");
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const navi = useNavigate();
  const onEditorChange = (value) => {
    setContent(value);
  };
  // useEffect(() => {
  //   if (!isLogin) {
  //     alert("로그인 해주세요.");
  //   } else {

  //    }
  // }, [isLogin]);
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
        console.log(res);
        if (res.status === 201) {
          //alert("게시글 생성");
          // navi("/community/community");
          Swal.fire({ icon: "success", title: "게시글을 생성하였습니다." }).then(
            function () {
              navi("/community/community");
            }
          );
        }
      })
      .catch((err) => {
        // alert("게시글 실패");
        // console.log(err.response.status);
        Swal.fire({ icon: "error", title: "게시글을 생성하지 못했습니다." }).then(
          function () {
            navi("/community/community");
          }
        );
      });
  };
  return (
    <div className={st.commucreatemain}>
      <header className={st.commuhead}>
        <h2>Community</h2>
      </header>
      <div className={st.createtopContent}>
        <select
          className="searchCd me-3"
          onChange={(event) => setCategory(event.target.value)}
        >
          <option defaultValue>카테고리</option>
          <option value="report">제보</option>
          <option value="general">잡담</option>
          <option value="review">후기</option>
        </select>
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
