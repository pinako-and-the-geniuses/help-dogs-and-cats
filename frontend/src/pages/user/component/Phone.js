import React, { useState } from "react";
import axios from "axios";
import st from "../styles/userform.module.scss";
import cn from "classnames";

export default function Phone({ URL, phone, setPhone, setIsPhone }) {
  const [phoneCheck, setPhoneCheck] = useState("");

  // 값입력
  const onPhoneHandler = (e) => {
    // const phoneRegex = /^\d{3}-\d{3,4}-\d{4}$/;
    const phoneCurrent = e.target.value;
    setPhone(phoneCurrent);
    setPhoneCheck(false);
    setIsPhone(false);

    // if (!phoneRegex.test(phoneCurrent)) {
    //   setIsPhone(false);
    // } else {
    //   setIsPhone(true);
    // }
  };

  // 폰번호 중복확인 요청
  const onCheckPhone = (event) => {
    event.preventDefault();
    axios
      .get(`${URL}/members/tel-duplicate-check/${phone}`)

      .then((res) => {
        console.log("핸드폰 중복 확인 요청결과", res);
        setPhoneCheck(res.status);
        isValid(res.status);
      })
      .catch((err) => {
        console.log("핸드폰 중복 확인 요청결과", err.response.status);
        setPhoneCheck(err.response.status);
        isValid(err.response.status);
      });
  };

  // 중복확인 마친 후 재인증
  const isValid = (res) => {
    if (res !== 204) {
      setIsPhone(false);
    } else {
      setIsPhone(true);
    }
  };

  return (
    <>
      <div className="input-group mb-3">
        <label htmlFor="phone">휴대폰 번호</label>
        <input
          id="phone"
          name="phone"
          type="tel"
          value={phone}
          pattern="[0-9]{3}-[0-9]{4}-[0-9]{4}"
          placeholder="( - 포함 입력)"
          onChange={onPhoneHandler}
          required
          className={cn("form-control")}
        />
        <button
          className="btn btn-outline-secondary"
          type="button"
          id="button-addon"
          onClick={onCheckPhone}
        >
          중복확인
        </button>
      </div>
      <div className={st.msg}>
        {phoneCheck === 200 ? <span>사용중인 핸드폰 번호입니다.</span> : ""}
        {phoneCheck === 204 ? <span>사용 가능한 핸드폰 번호입니다.</span> : ""}
        {phoneCheck === 400 ? <span>핸드폰 번호 형식을 확인하세요.</span> : ""}
        {phoneCheck === 404 ? <span>서버에러.</span> : ""}
        {phoneCheck === 500 ? <span>서버에러.</span> : ""}
      </div>
    </>
  );
}
