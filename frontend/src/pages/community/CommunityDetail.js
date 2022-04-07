import React, { useEffect, useState } from "react";
import axios from "axios";
import st from "./styles/CommunityDetail.module.scss";
import { URL } from "public/config";
import CommunityComment from "components/Comment/CommunityComment";
import { useParams, useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";
import Swal from "sweetalert2";
import swal from "sweetalert";
export default function CommunityDetail() {
  const isLogin = useSelector((state) => state.userInfo.isLoggedIn);
  const memberSeq = useSelector((state) => state.userInfo.userInfo.seq);
  const [communityDetail, setCommunityDetail] = useState("");
  const [writerSeq, setWriterSeq] = useState("");
  const [comments, setComments] = useState("");
  const { seq } = useParams();
  const [commentContent, setCommentContent] = useState("");
  const navigate = useNavigate();
  const jwt = sessionStorage.getItem("jwt");

  const getComment = () => {
    if (!isLogin) {
      Swal.fire({ icon: "warning", title: "로그인해주세요" }).then(function () {
        navigate("/login");
      });
    } else {
      axios({
        url: `${URL}/communities/${seq}`,
        method: "GET",
        headers: { Authorization: `Bearer ${jwt}` },
      }).then((response) => {
        setWriterSeq(response.data.data.writerSeq);
        setCommunityDetail(response.data);
        setComments(response.data.data.comments);
      });
    }
  };
  useEffect(() => {
    getComment();
  }, []);
  const onCommentChange = (value) => {
    setCommentContent(value);
  };
  const onClickEvent = () => {
    if (
      commentContent.length < 1 ||
      commentContent.replace(/\s| /gi, "").length === 0
    ) {
      swal("입력값은 필수입니다");
      return;
    }
    CommuComment();
  };

  const getDelete = async () => {
    axios
      .delete(`${URL}/communities/${seq}`, {
        headers: { Authorization: `Bearer ${jwt}` },
      })
      .then((res) => {
        if (res.status === 204) {
          navigate(`/community/community`);
        }
      });
  };

  const deleteHandler = () => {
    Swal.fire({
      title: "글을 삭제하시겠습니까?",
      text: "삭제한 글은 되돌릴 수 없습니다",
      icon: "warning",
      confirmButtonColor: `#b59d7c`,
      cancelButtonColor: "#999999",
      showCancelButton: true,
    }).then((res) => {
      if (res.isConfirmed) getDelete();
    });
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
        getComment();
        setCommentContent("");
      })
      .catch((err) => {});
  };

  const GotoEdit = () => {
    navigate(`/community/communityupdate/${seq}`);
  };

  return (
    <div className={st.community_commentBox}>
      <header className={st.communitydetailheader}>
        <h1>커뮤니티</h1>
      </header>

      {communityDetail ? (
        <>
          <div className={st.titleBox}>
            {
              communityDetail.data.category === "REPORT"
              ? <p className={st.category}>제보</p>
              : null
            }
            {
              communityDetail.data.category === "REVIEW"
              ? <p className={st.category}>후기</p>
              : null
            }
            {
              communityDetail.data.category === "GENERAL"
              ? <p className={st.category}>잡담</p>
              : null
            }
            {
              communityDetail.data.category === "NOTICE"
              ? <p className={st.category}>공지</p>
              : null
            }
            <p className={st.title}>{communityDetail.data.title}</p>

            <div className={st.rightInfo}>
              <p>조회수 : {communityDetail.data.viewCount}</p>
              <p> | </p>
              <p>{communityDetail.data.createdDate}</p>
              <p> | </p>
              <p>{communityDetail.data.writerNickname}</p>
            </div>
          </div>

          <div className={st.mainBox}>
            <div dangerouslySetInnerHTML={{__html: communityDetail.data.content}}></div>
          </div>

          {writerSeq === memberSeq ? (
              <div className={st.editPost}>
                <p onClick={GotoEdit}>수정</p>
                <p onClick={deleteHandler}>삭제</p>
              </div>
        ) : null}
        </>
      ) : null}

      <br/>

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
