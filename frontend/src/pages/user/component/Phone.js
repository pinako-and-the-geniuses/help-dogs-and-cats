import React, { useState } from "react";
import axios from "axios";
import st from "../styles/userform.module.scss";
import { URL } from "public/config";

export default function Phone({ phone, isPhone, setPhone, setIsPhone }) {
  const [phoneCheck, setPhoneCheck] = useState("");

  // 값입력
  const onPhoneHandler = (e) => {
    const phoneCurrent = e.target.value;
    setPhone(phoneCurrent);
    setPhoneCheck(false);
    setIsPhone(false);
  };

  // 회원가입일때 폰번호 중복확인 요청
  const onCheckPhone = (event) => {
    event.preventDefault();
    if (phone) {
      axios
        .get(`${URL}/members/tel-duplicate-check/${phone}`)

        .then((res) => {
          setPhoneCheck(res.status);
          isValid(res.status);
        })
        .catch((err) => {
          setPhoneCheck(err.response.status);
          isValid(err.response.status);
        });
    } else {
      setPhoneCheck(400);
    }
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
          defaultValue={phone}
          pattern="[0-9]{3}-[0-9]{4}-[0-9]{4}"
          placeholder="( - 포함 입력)"
          onChange={onPhoneHandler}
          required
          className={"form-control"}
        />
        {isPhone ? (
          <button
            className="btn btn-outline-secondary"
            type="button"
            id="button-addon-phone"
            disabled
          >
            확인완료
          </button>
        ) : (
          <button
            className="btn btn-outline-secondary"
            type="button"
            id="button-addon-phone"
            onClick={onCheckPhone}
          >
            중복확인
          </button>
        )}
      </div>
      <div className={st.msg}>
        {phoneCheck === 200 ? <span>사용중인 핸드폰 번호입니다.</span> : ""}
        {phoneCheck === 204 ? <span>사용 가능한 핸드폰 번호입니다.</span> : ""}
        {phoneCheck === 400 ? (
          <span>번호 형식(010-0000-0000)을 확인하세요.</span>
        ) : (
          ""
        )}
        {phoneCheck === 404 ? <span>서버에러.</span> : ""}
        {phoneCheck === 500 ? <span>서버에러.</span> : ""}
      </div>
    </>
  );
}
