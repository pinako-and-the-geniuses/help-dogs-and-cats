import React, { useState } from "react";
import "./styles/userform.module.scss";
import UserForm from "./component/UserForm";
// import { useSelector, useDispatch } from "react-redux"; // 리덕스 후크 가져오기
// 리덕스 액션가져와야함! import { 액션명 } from '../actions/index';

const BASEURL = "http/";

export default function Signup() {
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [nickName, setNickName] = useState("");
  const [phone, setPhone] = useState("");
  const [region, setRegion] = useState("");
  const [policy, setPolicy] = useState(0);
  const pagename = "회원가입";

  const onPasswordHandler = (event) => {
    setPassword(event.currentTarget.value);
  };

  const onConfirmPasswordHandler = (event) => {
    setConfirmPassword(event.currentTarget.value);
  };

  const onNickNameHandler = (event) => {
    setNickName(event.currentTarget.value);
  };

  const onPhoneHandler = (event) => {
    setPhone(event.currentTarget.value);
  };

  const onRegionHandler = (event) => {
    setRegion(event.currentTarget.value);
  };

  const onPolicyHandler = (event) => {
    console.log(event.currentTarget.value);
    setPolicy(event.currentTarget.value);
  };

  const onSubmit = (event) => {
    event.preventDefault();
    if (password !== confirmPassword) {
      return alert("비밀번호와 비밀번호확인은 같아야 합니다.");
    }
  };
  return (
    <div className="userform-page">
      <UserForm
        BASEURL={BASEURL}
        pagename={pagename}
        password={password}
        confirmPassword={confirmPassword}
        nickName={nickName}
        phone={phone}
        region={region}
        policy={policy}
        onPasswordHandler={onPasswordHandler}
        onConfirmPasswordHandler={onConfirmPasswordHandler}
        onNickNameHandler={onNickNameHandler}
        onPhoneHandler={onPhoneHandler}
        onRegionHandler={onRegionHandler}
        onPolicyHandler={onPolicyHandler}
        onSubmit={onSubmit}
      />
    </div>
  );
}
