import React, { useState } from "react";
import axios from "axios";
import st from "../styles/userform.module.scss";

export default function Email({ BASEURL }) {
  const [email, setEmail] = useState("");
  const [emailCheck, setEmailCheck] = useState("");

  const onEmailHandler = (event) => {
    console.log(event.currentTarget.value);
    setEmail(event.currentTarget.value);
  };

  const onCheckEmail = (event) => {
    axios
      .get(`${BASEURL}/members/email-duplicate-check/`, {
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
