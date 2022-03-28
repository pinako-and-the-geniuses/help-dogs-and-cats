import React, { useEffect, useState, useRef } from "react";
import axios from "axios";
import XMLParser from "react-xml-parser";
import st from "./styles/CommunityCreate.module.scss";
import cn from "classnames";
import { URL } from "public/config";
import QuillEditor from "./QuillEditor";
import { useNavigate , useParams} from "react-router-dom";
import { useSelector } from "react-redux";
export default function CommunityCreate(api) {
  const [htmlContent, setHtmlContent] = useState("");
  const communitySeq = useParams();
  const quillRef = useRef();
  const [category, setCategory] = useState("");
  const [title, setTitle] = useState("");
  const seq = useSelector((state) => state.userInfo.userInfo.seq);
  const isLogin = useSelector((state) => state.userInfo.isLoggedIn);
  const navi = useNavigate();

  useEffect(() => {
    if (!isLogin) {
      alert("로그인 해주세요.");
    } else {
      axios
        .get(`${URL}/communities/${communitySeq}`)
        .then((res) => {
          const data = res.data.data;
          setTitle(data.title);
          setCategory(data.category);

        })
        .catch((err) => {
          console.log(err);
        });
    }
  }, [isLogin]);

  console.log("data", title, category);

  const onSubmit = (event) => {
    event.preventDefault();
    const jwt = sessionStorage.getItem("jwt")
    axios({
      url: `${URL}/communities/${communitySeq}`,
      method: "PUT",
      headers: { Authorization: `Bearer ${jwt}` },
      data: {
        category : category,
        title : title
      },
    })
    .then((res) => {
      console.log(res);
      if (res.status === 200) {
        alert("수정 완료");
        navi(`/community/communitydetail/${communitySeq}`);
      }
    })
    .catch((err) => {
      alert("수정 실패");
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
              <option value="report">제보</option>
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
        <button type="reset" className={st.communitycreatebutton}>
          취소
        </button>
      </div>
    </div>
  );
}
