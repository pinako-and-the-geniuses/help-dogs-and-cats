import React, { useState } from "react";
import axios from "axios";
import st from "../styles/userform.module.scss";
import { URL } from "public/config";
import { useSelector } from "react-redux";

export default function EditNickName({
  nickName,
  isNickName,
  setNickName,
  setIsNickName,
}) {
  const [nickNameCheck, setNickNameCheck] = useState("");
  const defaultNickName = useSelector(
    (state) => state.userInfo.userInfo.nickname
  );

  function onNickNameHandler(e) {
    const nickNameCurrent = e.target.value;
    setNickName(nickNameCurrent);
    if (defaultNickName === nickNameCurrent) {
      console.log("같음");
      setNickNameCheck(true);
      setIsNickName(true);
    } else {
      setNickNameCheck(false);
      setIsNickName(false);
    }
  }

  //회원수정일 때 닉네임 중복확인 요청
  const onCheckNickName = (event) => {
    event.preventDefault();
    axios
      .get(`${URL}/members/nickname-duplicate-check/${nickName}`)
      .then((res) => {
        console.log(res);
        setNickNameCheck(res.status);
        isValid(res.status);
      })
      .catch((err) => {
        console.log(err);
        setNickNameCheck(err.response.status);
        isValid(err.response.status);
      });
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
          placeholder="닉네임(4자 이상 / eamil 포함 x)"
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
