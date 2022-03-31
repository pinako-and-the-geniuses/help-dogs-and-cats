import React, { useState } from 'react';
import axios from "axios";
import { useParams } from "react-router-dom";
import { useSelector } from "react-redux";
function Comment(props) {

    const { communitySeq } = useParams();
    const [commentValue, setcommentValue] = useState("");
    const [writer, setWriter] = useState("");
    const [seq, setSeq] = useState("");
    const jwt = sessionStorage.getItem("jwt");
    const user = useSelector((state) => state.userInfo.userInfo);
    const handleClick = (event) => {
        setcommentValue(event.target.value);
      };
    // console.log(user);
    const onSubmit = (event) => {
        event.preventDefault();
        axios({
            url: `${URL}/communities/${communitySeq}/comments`,
            method: "POST",
            headers: { Authorization: `Bearer ${jwt}` },
            data: {
                content: commentValue,
                writer: writer,
                // desc : desc
                seq: seq,
            },
          })
        .then((response) => {
            if (response.data.success) {
                console.log(response.data);
              } else {
                alert('커멘트를 저장하지 못했습니다.');
              }    
        })
    }

  return (
    <div>
      <br />
      <p>Replies</p>
      <hr />

      {/* Comment Lists */}
      
      {/* Root Comment Form */}

      <form style={{ display: 'flex' }} onSubmit={onSubmit} onChange={(event) =>
              setWriter(event.target.value)}>
        <textarea
          style={{ width: '100%', borderRadius: '5px' }}
          onChange={handleClick}
          value={commentValue}
          placeholder="코멘트를 작성해 주세요"
        />
        <br />
        <button style={{ width: '20%', height: '52px' }} onClick={onSubmit}>
          Submit
        </button>
      </form>
    </div>
  );
}

export default Comment;