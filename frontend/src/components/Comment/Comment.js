import Reply from "./Reply";
import style from "./style/Comment.module.scss";
import React, { useEffect, useState } from "react";
import { URL } from "public/config";
import axios from "axios";
import { useParams } from "react-router-dom";
import { useSelector } from "react-redux";

function Comment() {
  const [input, setInput] = useState();
  const [rereply, setRereply] = useState(false);
  const [reply, setReply] = useState(false);
  const [comments, setComments] = useState([]);
  const openReply = () => {
    setRereply(true);
  };

  const onChange = (e) => {
    setInput(e.target.value);
  };
  const addComment = () => { // 코멘트 추가
    setComments(
      comments.concat({
        id: comments.length + 1,
        content: input,
        // userName: userData[0].id,
      })
    );
    setInput("");
  };
  const closeReply = () => {
    setRereply(false);
  };
  const [parents, setParents] = useState("");
  const [content, setContent] = useState("");

  const nickname = useSelector((state) => state.userInfo.userInfo.NickName);
  const { communitySeq } = useParams();
  const jwt = sessionStorage.getItem("jwt");
  const onSubmit = (event) => {
    event.preventDefault();
    axios({
      url: `${URL}/communities/${communitySeq}/comments`,
      method: "POST",
      headers: { Authorization: `Bearer ${jwt}` },
      data: {
        parents: parents,
        reply: reply,
      },
    })
      .then((response) => {
        console.log(response.data);
        alert("댓글 추가");
      }) //엑시오스 보낸 결과
      .catch((err) => console.log(err));
  };
  return (
    <div className={style.commentBox}>
      <div className={style.comment}>
        <img src="https://cdn-icons-png.flaticon.com/512/2088/2088112.png" />

        <div className={style.content}>
          <div className={style.top}>
            <div className={style.left}>
              <span className={style.writer}>싸피강아지</span>
              <span className={style.date}>2022-03-22</span>
            </div>
            <p className={style.delete}>삭제</p>
          </div>
          <p onClick={openReply}>
            Lorem ipsum dolor sit amet consectetur adipisicing elit. Dolor
            possimus quae quas temporibus adipisci ad, consequatur ut nesciunt
            et blanditiis totam harum cupiditate quis aperiam amet molestias
            deserunt suscipit ipsa. Omnis, perferendis ullam? Numquam molestias
            unde reiciendis ab voluptate ullam ex amet laborum soluta vitae!
            Corporis ipsa architecto omnis est?
          </p>
        </div>
      </div>
      <Reply />
      {rereply ? (
        <div className={style.addReply}>
          <div></div>
          <textarea type="text" />
          <button type="submit" className={style.replyBtn}>
            등록
          </button>
          <button className={style.XBtn} onClick={closeReply}>
            X
          </button>
        </div>
      ) : (
        ""
      )}
      <hr />

      <div className={style.addComment}>
        <textarea
          cols="30"
          rows="3"
          onChange={(e) => {
            setReply(e.target.value);
          }}
        ></textarea>
        <button type="submit" onClick={onSubmit}>댓글 작성</button>
      </div>
    </div>
  );
}

export default Comment;
