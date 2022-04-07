import { useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";
import { URL } from "../../public/config";
import style from "./style/CummuComment.module.scss";
import swal from "sweetalert";
import axios from "axios";
import ProfileImage from "pages/user/component/ProfileImage";

function CommunityComment(props) {
  const jwt = sessionStorage.getItem("jwt");
  const memSeq = useSelector((state) => state.userInfo.userInfo.seq);
  const { seq } = useParams();
  const [rereply, setRereply] = useState(false);
  const [reply, setReply] = useState(false);
  const navigate = useNavigate();
  const openReply = () => {
    setRereply(true);
  };

  const closeReply = () => {
    setRereply(false);
  };

  const deleteHandler = (commentSeq) => {
    swal("댓글을 삭제합니다", {
      buttons: ["취소", "확인"],
    }).then((willDelete) => {
      if (willDelete) {
        deleteComment(commentSeq);
      } else {
      }
    });
  };

  const deleteComment = async (commentSeq) => {
    await axios({
      url: `${URL}/communities/${seq}/comments/${commentSeq}`,
      method: "delete",
      headers: {
        Authorization: `Bearer ${jwt}`,
      },
    }).then((res) => {
      // window.location.replace(`/community/communitydetail/${seq}`);
      props.getComment();
    });
  };

  const enterKey = (e) => {
    e.preventDefault();
    if (window.event.keyCode === 13) {
      props.eventHandler();
    }
  };

  return (
    <div className={style.commentBox}>
      {props.comments &&
        props.comments.map((comment) => {
          return (
            <>
              <div key={comment.commments} className={style.comment}>
                {/* 이미지 수정 필요 */}
                <ProfileImage
                  profileImageFilePath={comment.writerProfileImagePath}
                />
                <div className={style.content}>
                  <div className={style.top}>
                    <div className={style.left}>
                      <span className={style.writer}>
                        {comment.writerNickname}
                      </span>
                      <span className={style.date}>{comment.createdDate}</span>
                    </div>
                    {memSeq === comment.writerSeq ? (
                      <p
                        className={style.delete}
                        onClick={() => {
                          deleteHandler(comment.commentSeq);
                        }}
                      >
                        삭제
                      </p>
                    ) : null}
                  </div>
                  <p onClick={openReply}>{comment.content}</p>
                </div>
              </div>
              {/* <Reply />
                        {
                            rereply
                            ?(
                                <div className={style.addReply}>
                                    <div></div>
                                    <textarea type="text"/>
                                    <button type="submit" className={style.replyBtn}>등록</button>
                                    <button className={style.XBtn} onClick={closeReply}>X</button>
                                </div>
                            )
                            : ""
                        } */}
              <hr />
            </>
          );
        })}

      <div className={style.addComment}>
        <textarea
          cols="30"
          rows="3"
          value={props.commentContent}
          onChange={(e) => props.onChange(e.target.value)}
          onKeyUp={enterKey}
        ></textarea>
        <button
          type="submit"
          onClick={() => {
            props.eventHandler();
          }}
        >
          댓글 작성
        </button>
      </div>
    </div>
  );
}

export default CommunityComment;
