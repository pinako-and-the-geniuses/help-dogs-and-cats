import React, { useState } from "react";
import "./styles/user.css";
import UserForm from "../../components/user/UserForm";

export default function Signup() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [nickName, setNickName] = useState("");
  const [phone, setPhone] = useState("");
  const [region, setRegion] = useState("");

  const onEmailHandler = (event) => {
    setEmail(event.currentTarget.value);
  };

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

  const onSubmit = (event) => {
    event.preventDefault();
    if (password !== confirmPassword) {
      return alert("비밀번호와 비밀번호확인은 같아야 합니다.");
    }
  };
  return (
    <div className="loginregister">
      <h2>회원가입</h2>
      <UserForm
        email={email}
        password={password}
        confirmPassword={confirmPassword}
        nickName={nickName}
        phone={phone}
        region={region}
        onEmailHandler={onEmailHandler}
        onPasswordHandler={onPasswordHandler}
        onConfirmPasswordHandler={onConfirmPasswordHandler}
        onNickNameHandler={onNickNameHandler}
        onPhoneHandler={onPhoneHandler}
        onRegionHandler={onRegionHandler}
        onSubmit={onSubmit}
      />
    </div>
  );
}
