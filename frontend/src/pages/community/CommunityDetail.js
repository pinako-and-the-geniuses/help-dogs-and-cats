import React, { useEffect, useState } from "react";
import axios from "axios";
import XMLParser from "react-xml-parser";
import st from "./styles/CommunityDetail.module.scss";
import cn from "classnames";
import { URL } from "public/config";
import CommunityComment from "components/Comment/CommunityComment";
// import Comment from "./Comment";
import { useParams, Link, useNavigate } from "react-router-dom";
import moment from "moment";
import { useSelector } from "react-redux";
export default function CommunityDetail() {
  const isLogin = useSelector((state) => state.userInfo.isLoggedIn);
  const memberSeq = useSelector((state) => state.userInfo.userInfo.seq); //useSelector로 로그인한 사람의 memberSeq를 가져와서 비교해서 자신이면 수정 취소 보이게 만든다.
  const [communityDetail, setCommunityDetail] = useState("");
  const [writerSeq, setWriterSeq] = useState("");
  const [comments, setComments] = useState([]);
  const { seq } = useParams();
  const [commentContent, setCommentContent] = useState("");
  // const[Comments, SetComments] = useState(initialState)

  const onCommentChange = (value) => {
    setCommentContent(value);
};
const onClickEvent=(value)=>{
    CommuComment()
  .then(setCommentContent("")); //댓글 쓴 후 빈공간으로 만드는 코드
}


  const navigate = useNavigate();
  const jwt = sessionStorage.getItem("jwt");
  useEffect(() => {
    //시작할떄 나옴 //페이지가 바뀔떄마다 변경해줘야함
    // axios
    //   .get(`${URL}/communities/${communitySeq}`, {headers: {Authorization : `Bearer ${jwt}`}})
    if (!isLogin) {
      alert("로그인 해주세요.");
      navigate("/login", { replace: true });
    } else {
      axios({
        url: `${URL}/communities/${seq}`,
        method: "GET",
        headers: { Authorization: `Bearer ${jwt}` },
      })
        .then((response) => {
          console.log(response.data);
          setWriterSeq(response.data.data.writerSeq);
          setCommunityDetail(response.data);
          setComments(response.data.data.comments);
        }) //엑시오스 보낸 결과
        .catch((err) => console.log(err));
        // console.log(Comments);
    }
  }, []); //한번만 해줄때 []넣는다 //안에 값이 있다면 값이 바뀔떄마다 호출
  // console.log("writer",writerSeq);
  // console.log("member",memberSeq);
  // console.log("detail", communityDetail);
  const getDelete = async () => {
    axios
      .delete(`${URL}/communities/${seq}`, {
        headers: { Authorization: `Bearer ${jwt}` },
      })
      .then((res) => {
        console.log(res);
        if (res.status === 204) {
          alert("게시글 삭제");
          navigate(`/community/community`);
        }
      })
      .catch((err) => console.log(err));
  };

  const CommuComment=async()=>{
    await axios({
        url: `${URL}/communities/${seq}/comments`,
        method: "post",
        data:{
            content: commentContent,
            parentSeq: null,
        },
        headers: {
            Authorization: `Bearer ${jwt}`,
        }
    })
    .then((res)=>{
        // console.log('댓글달기성공');
    })
    .catch((err)=>{
        console.log(err);
    })
}



  const GotoEdit = () => {
    navigate(`/community/communityupdate/${seq}`);
  };
  return (
    <div className={st.community_commentBox}>
      <header>
        <h2>Community</h2>
      </header>
      <section className={st.topContent}>
        {communityDetail ? (
          <>
            <div className={st.alltitle}>
              <p className={st.tag_p}>{communityDetail.data.category}</p>
              <p className={st.title_p}>{communityDetail.data.title}</p>
              <p className={st.read_p}>
                조회수 : {communityDetail.data.viewCount}
              </p>
              <p className={st.date_p}>{communityDetail.data.createdDate}</p>
              <p className={st.author_p}>
                {communityDetail.data.writerNickname}
              </p>
            </div>
            <div
              className={st.content_div}
              dangerouslySetInnerHTML={{
                __html: communityDetail.data.content,
              }}
            ></div>
          </>
        ) : (
          "로딩중"
        )}
        {writerSeq===memberSeq?
        <>
        <div className={st.contentbtn}>
          <button onClick={getDelete} className={st.deletebutton}>
            삭제
          </button>
          <button onClick={GotoEdit} className={st.button}>
            수정
          </button>
        </div>
        </>
        :null}
      </section>
      <CommunityComment 
      id={seq} 
      value={commentContent}
      onChange={onCommentChange}
      eventHandler={onClickEvent}
      comments={comments}
/>
    </div>
  );
}
