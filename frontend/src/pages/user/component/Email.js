import React, { useState } from "react";
import axios from "axios";
import st from "../styles/userform.module.scss";
import cn from "classnames";

export default function Email({ URL, email, setEmail, setIsEmail }) {
  const [emailCheck, setEmailCheck] = useState("");

  // 이메일 유효성 검사
  const onEmailHandler = (e) => {
    const emailRegex =
      /^[\w-]+(\.[\w-]+)*@([a-z0-9-]+(\.[a-z0-9-]+)*?\.[a-z]{2,6}|(\d{1,3}\.){3}\d{1,3})(:\d{4})?$/;
    const emailCurrent = e.target.value;
    setEmail(emailCurrent);

    if (emailCurrent.match(emailRegex)) {
      return <span>이메일형식 완료</span>;
    } else {
      return <span>메일 형식을 지켜주세요!(test@ssafy.com)</span>;
    }
  };

  // 이메일 중복확인 요청
  const onCheckEmail = (event) => {
    event.preventDefault();
    axios
      .get(`${URL}/members/email-duplicate-check/${email}`)
      .then((res) => {
        console.log(res);
        setEmailCheck(res.status);
        isValid();
      })
      .catch((err) => {
        console.log(err);
      });
  };

  // 중복확인 마친 후 재인증
  const isValid = () => {
    console.log("email체크", emailCheck);
    if (emailCheck !== 204) {
      setIsEmail(false);
    } else {
      setIsEmail(true);
    }
  };

  return (
    <>
      <div className="input-group mb-3">
        <label htmlFor="email">아이디 [Email]</label>
        <input
          id="email"
          name="email"
          type="email"
          placeholder="email@ssafy.com"
          onChange={onEmailHandler}
          required
          className={cn("form-control")}
        />
        {onEmailHandler}
        <button
          className="btn btn-outline-secondary"
          type="button"
          id="button-addon"
          onClick={onCheckEmail}
        >
          중복확인
        </button>
      </div>
      <div className={st.msg}>
        {emailCheck === 200 ? <span>사용중인 이메일입니다.</span> : ""}
        {emailCheck === 204 ? <span>사용 가능한 이메일입니다.</span> : ""}
        {emailCheck === 400 ? <span>이메일 형식을 확인하세요.</span> : ""}
        {emailCheck === 500 ? <span>서버에러.</span> : ""}
      </div>
    </>
  );
}
