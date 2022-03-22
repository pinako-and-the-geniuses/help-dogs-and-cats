import React, { useState } from "react";
import axios from "axios";

export default function NickName({
  URL,
  nickName,
  setNickName,
  setIsNickName,
}) {
  const [nickNameCheck, setNickNameCheck] = useState("");

  const onNickNameHandler = (e) => {
    const nickName = e.currentTarget.value;
    setNickName(nickName);

    if (nickName.length < 2 || nickName.length > 8) {
      setIsNickName(false);
    } else {
      setIsNickName(true);
    }
  };
  // 닉네임 중복확인 요청
  const onCheckNickName = (event) => {
    event.preventDefault();
    axios
      .get(`${URL}/members/nickName-duplicate-check/`, {
        params: {
          nickName: `${nickName}`,
        },
      })
      .then((res) => {
        console.log(res);
        setNickNameCheck(res.data);
      })
      .catch((err) => {
        console.log(err);
      });
  };

  return (
    <>
      <div className="input-group mb-3">
        <label htmlFor="nickname">닉네임</label>
        <input
          id="nickname"
          name="nickname"
          type="text"
          placeholder="닉네임"
          defaultValue={nickName}
          onChange={onNickNameHandler}
          required
          className={"form-control"}
        />
        <button
          className="btn btn-outline-secondary"
          type="button"
          id="button-addon2"
          onClick={onCheckNickName}
        >
          중복확인
        </button>
        {nickNameCheck === 200 ? <p>사용중인 닉네임입니다.</p> : ""}
        {nickNameCheck === 204 ? <p>사용 가능한 닉네임입니다.</p> : ""}
        {nickNameCheck === 400 ? <p>닉네임 형식을 확인하세요.</p> : ""}
        {nickNameCheck === 400 ? <p>서버에러.</p> : ""}
      </div>
    </>
  );
}
