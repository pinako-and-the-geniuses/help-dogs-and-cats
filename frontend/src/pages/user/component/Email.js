import React, { useState } from "react";
import axios from "axios";
import st from "../styles/userform.module.scss";

export default function Email({
  URL,
  email,
  setEmail,
  setIsEmail,
  isEmail,
  inputClass,
}) {
  const [emailCheck, setEmailCheck] = useState("");

  // 이메일 유효성 검사
  const onEmailHandler = (e) => {
    const emailRegex =
      /^[\w-]+(\.[\w-]+)*@([a-z0-9-]+(\.[a-z0-9-]+)*?\.[a-z]{2,6}|(\d{1,3}\.){3}\d{1,3})(:\d{4})?$/;
    const emailCurrent = e.target.value;
    setEmail(emailCurrent);

    if (emailCurrent.match(emailRegex)) {
      setIsEmail(true);
    } else {
      setIsEmail(false);
    }
  };

  // 검사에 따른 input css 수정
  const isEnteredEmailValid = () => {
    if (email) return isEmail;
  };

  // 이메일 중복확인 요청
  const onCheckEmail = (event) => {
    event.preventDefault();
    axios
      .get(`${URL}/members/email-duplicate-check/`, {
        params: {
          email: `${email}`,
        },
      })
      .then((res) => {
        console.log(res);
        setEmailCheck(res.data);
      })
      .catch((err) => {
        console.log(err);
      });
  };

  return (
    <>
      <div>
        <label htmlFor="email">아이디 [Email]</label>
        <input
          id="email"
          name="email"
          type="email"
          placeholder="email@ssafy.com"
          value={email}
          onChange={onEmailHandler}
          className={`form-control ${inputClass(isEnteredEmailValid())}`}
          required
        />

        <button className={st.checkemail} onClick={onCheckEmail}>
          중복확인
        </button>
        {emailCheck === 200 ? <p>사용중인 이메일입니다.</p> : ""}
        {emailCheck === 204 ? <p>사용 가능한 이메일입니다.</p> : ""}
        {emailCheck === 400 ? <p>이메일 형식을 확인하세요.</p> : ""}
        {emailCheck === 400 ? <p>서버에러.</p> : ""}
      </div>
    </>
  );
}