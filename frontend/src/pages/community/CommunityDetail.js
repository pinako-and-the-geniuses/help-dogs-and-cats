import React, { useEffect, useState } from "react";
import axios from "axios";
import st from "./styles/CommunityDetail.module.scss";
import { URL } from "public/config";
import CommunityComment from "components/Comment/CommunityComment";
// import Comment from "./Comment";
import { useParams, useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";

export default function CommunityDetail() {
  const isLogin = useSelector((state) => state.userInfo.isLoggedIn);
  const memberSeq = useSelector((state) => state.userInfo.userInfo.seq); //useSelector로 로그인한 사람의 memberSeq를 가져와서 비교해서 자신이면 수정 취소 보이게 만든다.
  const [communityDetail, setCommunityDetail] = useState("");
  const [writerSeq, setWriterSeq] = useState("");
  const [comments, setComments] = useState("");
  const { seq } = useParams();
  const [commentContent, setCommentContent] = useState("");
  // const[Comments, SetComments] = useState(initialState)

  const navigate = useNavigate();
  const jwt = sessionStorage.getItem("jwt");

  const getComment = () => {
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
    }
  };
  useEffect(() => {
    //시작할떄 나옴 //페이지가 바뀔떄마다 변경해줘야함
    getComment();
  }, []); //한번만 해줄때 []넣는다 //안에 값이 있다면 값이 바뀔떄마다 호출
  const onCommentChange = (value) => {
    setCommentContent(value);
  };
  const onClickEvent = (value) => {
    // .then(setCommentContent('')); //댓글 쓴 후 빈공간으로 만드는 코드
    CommuComment();
    // window.location.replace(`/community/communitydetail/${seq}`);
  };

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

  const CommuComment = async () => {
    await axios({
      url: `${URL}/communities/${seq}/comments`,
      method: "post",
      data: {
        content: commentContent,
        parentSeq: null,
      },
      headers: {
        Authorization: `Bearer ${jwt}`,
      },
    })
      .then((res) => {
        // console.log('댓글달기성공');
        // setChanged(!changed);
        // setCommentContent('');
        // console.log(res.data);
        console.log(commentContent);
        getComment();
        setCommentContent("");
      })
      .catch((err) => {
        console.log(err);
      });
  };

  const GotoEdit = () => {
    navigate(`/community/communityupdate/${seq}`);
  };

  return (
    <div className={st.community_commentBox}>
      <header>
        <h2>Community</h2>
      </header>
      <div className={st.topContent}>
        <div>
          {communityDetail ? (
            <div className={st.mainDiv}>
              <div className={st.alltitle}>
                <div>
                  <p className={st.tag_p}>
                    {communityDetail.data.category === "REPORT" ? "제보" : ""}
                    {communityDetail.data.category === "REVIEW" ? "후기" : ""}
                    {communityDetail.data.category === "GENERAL" ? "잡담" : ""}
                  </p>
                  <p className={st.title_p}>
                    제목 : {communityDetail.data.title}
                  </p>
                </div>
                <div className={st.rightInfo}>
                  <p className={st.p}>
                    조회수 : {communityDetail.data.viewCount}
                  </p>
                  <p className={st.p}> | </p>
                  <p className={st.p}>{communityDetail.data.createdDate} </p>
                  <p className={st.p}> | </p>
                  <p className={st.p}>{communityDetail.data.writerNickname} </p>
                </div>
              </div>
              <div
                className={st.content_div}
                dangerouslySetInnerHTML={{
                  __html: communityDetail.data.content,
                }}
              ></div>
            </div>
          ) : (
            "로딩중"
          )}
        </div>

        {writerSeq === memberSeq ? (
          <div className={st.contentbtn}>
            <button onClick={getDelete} className={st.deletebutton}>
              삭제
            </button>
            <button onClick={GotoEdit} className={st.button}>
              수정
            </button>
          </div>
        ) : null}
      </div>
      <CommunityComment
        id={seq}
        commentContent={commentContent}
        onChange={onCommentChange}
        eventHandler={onClickEvent}
        comments={comments}
        getComment={getComment}
      />
    </div>
  );
}
