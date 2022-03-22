import React, { useState } from "react";
import axios from "axios";
import st from "../styles/userform.module.scss";

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
  };

  // 닉네임 중복확인 요청
  const onCheckNickName = (event) => {
    event.preventDefault();
    axios
      .get(`${URL}/members/nickname-duplicate-check/${nickName}`, {})
      .then((res) => {
        console.log(res);
        setNickNameCheck(res.status);
        isValid();
      })
      .catch((err) => {
        console.log(err);
      });
  };

  const isValid = () => {
    if (nickNameCheck !== 204) {
      setIsNickName(false);
    } else {
      setIsNickName(true);
    }
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
      </div>
      <div className={st.msg}>
        {nickNameCheck === 200 ? <span>사용중인 닉네임입니다.</span> : ""}
        {nickNameCheck === 204 ? <span>사용 가능한 닉네임입니다.</span> : ""}
        {nickNameCheck === 400 ? <span>닉네임 형식을 확인하세요.</span> : ""}
        {nickNameCheck === 500 ? <span>서버에러.</span> : ""}
      </div>
    </>
  );
}
