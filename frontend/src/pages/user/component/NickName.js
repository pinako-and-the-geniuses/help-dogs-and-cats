import React, { useState } from "react";
import axios from "axios";
import st from "../styles/userform.module.scss";
import { URL } from "public/config";

export default function NickName({
  nickName,
  isNickName,
  setNickName,
  setIsNickName,
}) {
  const [nickNameCheck, setNickNameCheck] = useState("");

  const onNickNameHandler = (e) => {
    const nickNameCurrent = e.target.value;
    setNickName(nickNameCurrent);
    setNickNameCheck(false);
    setIsNickName(false);
  };

  //회원가입일 때 닉네임 중복확인 요청
  const onCheckNickName = (event) => {
    event.preventDefault();
    if (nickName) {
      axios
        .get(`${URL}/members/nickname-duplicate-check/${nickName}`)
        .then((res) => {
          setNickNameCheck(res.status);
          isValid(res.status);
        })
        .catch((err) => {
          setNickNameCheck(err.response.status);
          isValid(err.response.status);
        });
    } else {
      setNickNameCheck(400);
    }
  };

  const isValid = (res) => {
    if (res !== 204) {
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
          placeholder="닉네임(4자 이상 / email 포함 x)"
          defaultValue={nickName}
          onChange={onNickNameHandler}
          required
          className={"form-control"}
        />
        {isNickName ? (
          <button
            className="btn btn-outline-secondary"
            type="button"
            id="button-addon2"
            disabled
          >
            확인완료
          </button>
        ) : (
          <button
            className="btn btn-outline-secondary"
            type="button"
            id="button-addon2"
            onClick={onCheckNickName}
          >
            중복확인
          </button>
        )}
      </div>

      <div className={st.msg}>
        {nickNameCheck === 200 ? <span>사용중인 닉네임입니다.</span> : ""}
        {nickNameCheck === 204 ? <span>사용 가능한 닉네임입니다.</span> : ""}
        {nickNameCheck === 400 ? (
          <span>4글자 이상, 이메일포함 x 형식을 확인하세요.</span>
        ) : (
          ""
        )}
        {nickNameCheck === 500 ? <span>서버에러.</span> : ""}
      </div>
    </>
  );
}
