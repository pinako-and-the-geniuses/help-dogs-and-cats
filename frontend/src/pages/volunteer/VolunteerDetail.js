import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import axios from "axios";
import Comment from "components/Comment/Comment";
import style from "./styles/VolunteerDetail.module.scss";
import { URL } from "../../public/config";
import { useSelector } from "react-redux";
import VolunteerInfo from "./VolunteerInfo";
import Swal from "sweetalert2";
import swal from "sweetalert";

function VolunteerDetail() {
  const { id } = useParams();
  const jwt = sessionStorage.getItem("jwt");
  const memSeq = useSelector((state) => state.userInfo.userInfo.seq);
  const navigate = useNavigate();

  const [post, setPost] = useState([]);
  const [commentContent, setCommentContent] = useState("");
  const [changed, setChanged] = useState(true);
  const [comments, setComments] = useState([]);
  const [stateChanged, setStateChanged] = useState(true);
  const [participants, setParticipants] = useState([]);
  const [join, setJoin] = useState(false);
  const [isParty, setIsParty] = useState(true);

  const today = new Date();
  const endDate = new Date(post.endDate);
  const gap = endDate.getTime() - today.getTime();
  const leftdays = Math.ceil(gap / (1000 * 60 * 60 * 24));
  const isLogin = useSelector((state) => state.userInfo.isLoggedIn);

  useEffect(() => {
    if (!isLogin) {
      swal({
        title: "권한이 없습니다. ",
        icon: "error",
        closeOnClickOutside: false,
      });
      navigate("/login", { replace: true });
    }
  });

  function getYyyyMmDdToString(date) {
    var dd = date.getDate();
    var mm = date.getMonth() + 1; //January is 0!

    var yyyy = date.getFullYear();
    if (dd < 10) {
      dd = "0" + dd;
    }
    if (mm < 10) {
      mm = "0" + mm;
    }

    yyyy = yyyy.toString();
    mm = mm.toString();
    dd = dd.toString();

    var s1 = yyyy + "-" + mm + "-" + dd;
    return s1;
  }

  const goToEdit = () => {
    navigate(`/volunteer/update/${id}`);
  };

  //신청자 조회
  const getParticipants = async () => {
    await axios({
      url: `${URL}/volunteers/${id}/participants`,
      method: "get",
      headers: { Authorization: `Bearer ${jwt}` },
    }).then((res) => {
      setParticipants(res.data.data);
      setIsParty(!isParty);
    });
  };

  //게시글 정보 가져오기
  const getPost = async () => {
    await axios({
      url: `${URL}/volunteers/${id}`,
      method: "get",
      headers: {
        Authorization: `Bearer ${jwt}`,
      },
    }).then((res) => {
      setPost(res.data.data);
      setComments(res.data.data.comments);
    });
  };

  //글 작성자: 모집 상태 변경
  const changeStatus = async (volState) => {
    await axios({
      url: `${URL}/volunteers/${id}/status`,
      method: "patch",
      data: {
        status: volState,
      },
      headers: {
        Authorization: `Bearer ${jwt}`,
      },
    }).then((res) => {
      setStateChanged(!stateChanged);
    });
  };

  const testIsApply = () => {
    const isApply = participants.filter((p) => p.seq === memSeq);
  };
  const isApply = participants.filter((p) => p.seq === memSeq);

  //참여자 상태에 따른 버튼 표시
  const setApplyBtn = (status) => {
    if (status !== "RECRUITING")
      return (
        <button className={`${style.joinBtn} ${style.joinXBtn}`}>
          모집완료
        </button>
      );
    else {
      if (isApply.length === 0) {
        //내가 신청이 안된 상태
        return (
          <button
            onClick={() => {
              applyBtn();
            }}
            className={style.joinBtn}
          >
            참여신청
          </button>
        );
      } else if (isApply !== 0) {
        //내가 신청이 된 상태
        return (
          <button
            onClick={() => {
              cancleBtn();
            }}
            className={`${style.joinBtn} ${style.joinXBtn}`}
          >
            참여취소
          </button>
        );
      }
    }
  };

  //일반: 참여 신청
  const apply = async () => {
    await axios({
      url: `${URL}/volunteers/${id}/apply`,
      method: "POST",
      headers: {
        Authorization: `Bearer ${jwt}`,
      },
    }).then((res) => {
      swal("참여 신청 되었습니다");
      getParticipants();
    });
  };
  //일반: 참여 취소
  const applyCancle = async () => {
    await axios({
      url: `${URL}/volunteers/${id}/apply`,
      method: "delete",
      headers: { Authorization: `Bearer ${jwt}` },
    }).then((res) => {
      swal("참여 취소 되었습니다")
      .then((value)=>{
        window.location.reload('/');
      })
    })
  };

  //댓글 작성
  const volComment = async () => {
    await axios({
      url: `${URL}/volunteers/${id}/comments`,
      method: "post",
      data: {
        content: commentContent,
        parentSeq: null,
      },
      headers: {
        Authorization: `Bearer ${jwt}`,
      },
    }).then((res) => {
      setChanged(!changed);
    });
  };

  //게시글 삭제
  const deletePost = async () => {
    await axios({
      url: `${URL}/volunteers/${id}`,
      method: "delete",
      headers: { Authorization: `Bearer ${jwt}` },
    }).then((res) => {
      navigate("/volunteer/list");
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
      if (res.isConfirmed) deletePost();
    });
  };

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
    volComment().then(setCommentContent("")).then(getPost());
  };

  //모집상태변경핸들러
  const onChangeHandler = (volState) => {
    if (getYyyyMmDdToString(today) > post.endDate) {
      swal("모집 마감일이 지났습니다");
      return;
    } else changeStatus(volState);
  };

  const applyBtn = () => {
    apply();

    setJoin(true);
  };

  const cancleBtn = () => {
    applyCancle();

    setJoin(false);
  };

  useEffect(() => {
    getPost();
  }, [changed, stateChanged, isParty]); //댓글, 모집, 참여자조회 //여기 뭔가가 잘못됨

  useEffect(() => {
    getParticipants();
  }, []);

  if (isLogin) {
    return (
      <div className={style.myContainer}>
        <h1>봉사활동</h1>

        <div className={style.titleBox}>
          {post.status === "RECRUITING" ? (
            leftdays >= 0 ? (
              <p className={style.leftdays}>D-{leftdays}</p>
            ) : (
              <p className={style.leftdays}>마감</p>
            )
          ) : (
            <p className={style.leftdays}>마감</p>
          )}
          <p className={style.title}>{post.title}</p>
          {memSeq === post.writerSeq
            ? {
                DONE: (
                  <button
                    type="button"
                    className={`${style.joinBtn} ${style.joinXBtn}`}
                  >
                    모집완료
                  </button>
                ),
                ONGOING: (
                  <button
                    type="button"
                    className={`${style.joinBtn} ${style.joinXBtn}`}
                    onClick={() => {
                      onChangeHandler("RECRUITING");
                    }}
                  >
                    모집시작
                  </button>
                ),
                RECRUITING: (
                  <button
                    type="button"
                    className={style.joinBtn}
                    onClick={() => {
                      onChangeHandler("ONGOING");
                    }}
                  >
                    모집마감
                  </button>
                ),
              }[post.status]
            : setApplyBtn(post.status)}
        </div>

        <VolunteerInfo
          post={post}
          memSeq={memSeq}
          getParticipants={getParticipants}
          getPost={getPost}
        />

        <div className={style.mainBox}>
          <div dangerouslySetInnerHTML={{ __html: post.content }}></div>
        </div>

        {memSeq === post.writerSeq ? (
          <div className={style.editPost}>
            <p onClick={goToEdit}>수정</p>
            <p onClick={deleteHandler}>삭제</p>
          </div>
        ) : null}
        <br />

        <Comment
          id={id}
          value={commentContent}
          onChange={onCommentChange}
          eventHandler={onClickEvent}
          comments={comments}
          getPost={getPost}
        />
        <button
          className={style.listBtn}
          onClick={() => {
            navigate("/volunteer/list");
          }}
        >
          목록
        </button>
      </div>
    );
  } else {
    return <></>;
  }
}

export default VolunteerDetail;
