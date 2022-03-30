import React, { useEffect, useState, useRef } from "react";
import axios from "axios";
import { URL } from "public/config";
import XMLParser from "react-xml-parser";
import st from "./styles/CommunityCreate.module.scss";
import cn from "classnames";
import QuillEditor from "./QuillEditor";
import Editor from "components/Editor";
import { useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";
//import UploadFiles from "./UploadFiles";
export default function CommunityCreate(api) {
  const isLogin = useSelector((state) => state.userInfo.isLoggedIn);
  const quillRef = useRef();
  
  // function onEditorChange(value) {   
  //     // console.log(desc);  
  //     setDesc(value)
  // }
  //   const uploadReferenece = React.createRef();

  //   async function onClickSearch() {
  //       await uploadReferenece.current.upload().then(function (result) {
  //           const files = result;
  //           alert('저장 완료');
  //       }).catch(function (err) {
  //       });
  //   }
  const [category, setCategory] = useState("");
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  // const [desc, setDesc] = useState('');
  const navi = useNavigate();

  // useEffect(() => {
  //   if (!isLogin) {
  //     alert("로그인 해주세요.");
  //   } else {
    
  //    }
  // }, [isLogin]);
  const onSubmit = (event) => {
    event.preventDefault();
    console.log(category);
    console.log(title);
    console.log(content);
    // console.log(desc);
    const jwt = sessionStorage.getItem("jwt")
    axios({
      url: `${URL}/communities`,
      method: "POST",
      headers: { Authorization: `Bearer ${jwt}` },
      data: {
        category : category,
        title : title,
        // desc : desc
        content : content
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
              <option defaultValue>카테고리</option>
              <option value="report">제보</option>
              <option value="general">잡담</option>
              <option value="review">후기</option>
            </select>
            <div className={st.Title}>
              <input type="text" placeholder='제목' className={st.Title} onChange={(event) =>
              setTitle(event.target.value)}/>
            </div>
      </div>
      <div className={st.Editorheight}>
          <Editor
            height={"90%"}
            value={content}
            setValue={setContent}
            placeholder={""}></Editor>
      </div>
      <div className={st.createbuttonContent}>
        {isLogin?
        <><button type="button" onClick={onSubmit} className={st.communitycreatebutton}>
          작성
        </button>
        <button type="reset" className={st.communitycreatebutton}>
          취소
        </button></>
        : null}
      </div>
    </div>
  );
}
