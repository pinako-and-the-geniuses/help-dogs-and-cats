import React, { useEffect, useState } from "react";
import axios from "axios";
import XMLParser from "react-xml-parser";
import st from "./styles/CommunityDetail.module.scss";
import cn from "classnames";
import Comment from "components/Comment/Comment";
import { useParams, Link, useNavigate } from "react-router-dom";
import moment from "moment";

export default function CommunityDetail() {
  const [communityDetail, setCommunityDetail] = useState([]);
  const communitySeq = useParams();
  const navigate = useNavigate();
  // console.log("detail", communitySeq);
  useEffect(() => {
    //시작할떄 나옴 //페이지가 바뀔떄마다 변경해줘야함
    const jwt = sessionStorage.getitem("jwt")
    axios
      .get(`${URL}/communities/${communitySeq}`, {headers: {jwt}})
      .then((response) => setCommunityDetail(response.data)) //엑시오스 보낸 결과
      .catch((err) => console.log(err));
  }, []); //한번만 해줄때 []넣는다 //안에 값이 있다면 값이 바뀔떄마다 호출
  if (communityDetail === null) {
    return <h1>값을 받아오는 중입니다.</h1>;
  }

  const getDelete = (e) => {
    axios.delete(`${URL}/communities/${communitySeq}`)
    .then(response => setCommunityDetail('Delete successful'))
    .catch(err => console.log(err))
  }
  const GotoEdit=()=>{
    navigate(`/community/communityupdate/${communitySeq}`)
  } 
  
  return (
    <div className={st.community_commentBox}>
      <header>
        <h2>Community</h2>
      </header>
      <section className={st.topContent}>
        <div className={st.title}>
          <p className={st.tag_p}>{communityDetail.tag}</p>
          <p className={st.title_p}>{communityDetail.title}</p>
          <p className={st.read_p}>{communityDetail.number}</p>
          <p className={st.date_p}>
            {moment(communityDetail.date).format("YYYY-MM-DD")}
          </p>
          <p className={st.author_p}>{communityDetail.name}</p>
        </div>

        <div className={st.content_div}>{communityDetail.description}</div>
        {/* <textarea className={st.content_div}>
              이거 정말 맛있습니다.
            </textarea> */}
        <div className={st.content}>
        <Link to="/community/community"> 
          <button onClick={getDelete} className={st.deletebutton}>
            삭제
          </button>
        </Link>
          <button onClick={GotoEdit} className={st.button}>
            수정
          </button>
        </div>
      </section>
      {/* <section className={st.topContent}>
        <div className={st.title}>
          <p className={st.tag_p}>[잡담]</p>
          <p className={st.title_p}>강아지 간식으로 이거 최곱니다.</p>
          <p className={st.read_p}>조회수</p>
          <p className={st.date_p}>2022.03.03</p>
          <p className={st.author_p}>김싸피</p>
        </div>

        <div className={st.content_div}>이거 정말 맛있습니다.</div>
        <div className={st.content}>
          <button type="button" className={st.button}>
            수정
          </button>
        </div>
      </section> */}
      <Comment />
    </div>
  );
}
