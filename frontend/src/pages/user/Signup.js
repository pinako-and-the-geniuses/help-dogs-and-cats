import React, { useState } from "react";
import UserForm from "./component/UserForm";

export default function Signup() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [nickName, setNickName] = useState("");
  const [phone, setPhone] = useState("");
  const [region, setRegion] = useState("");
  const [policy, setPolicy] = useState(0);
  const pagename = "회원가입";
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
        pagename={pagename}
        email={email}
        password={password}
        confirmPassword={confirmPassword}
        nickName={nickName}
        phone={phone}
        region={region}
        policy={policy}
        onEmailHandler={onEmailHandler}
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
