import React, { useState } from "react";
import UserForm from "./component/SignForm";
import "./styles/userform.module.scss";

export default function EditInfo() {
  // const [email, setEmail] = useState("");
  // const [password, setPassword] = useState("");
  // const [confirmPassword, setConfirmPassword] = useState("");
  // const [nickName, setNickName] = useState("");
  // const [phone, setPhone] = useState("");
  // const [region, setRegion] = useState("");
  // const [isCheked, setIsCheked] = useState(false);
  const pagename = "회원정보 수정";

  // const onEmailHandler = (event) => {
  //   setEmail(event.currentTarget.value);
  // };

  // const onPasswordHandler = (event) => {
  //   setPassword(event.currentTarget.value);
  // };

  // const onConfirmPasswordHandler = (event) => {
  //   setConfirmPassword(event.currentTarget.value);
  // };

  // const onNickNameHandler = (event) => {
  //   setNickName(event.currentTarget.value);
  // };

  // const onPhoneHandler = (event) => {
  //   setPhone(event.currentTarget.value);
  // };

  // const onRegionHandler = (event) => {
  //   console.log(event.currentTarget.value);
  //   setRegion(event.currentTarget.value);
  // };

  // const onIsChekedHandler = (event) => {
  //   setIsCheked(!isCheked);
  // };

  // const onSubmit = (event) => {
  //   event.preventDefault();
  //   if (password !== confirmPassword) {
  //     return alert("비밀번호와 비밀번호 확인은 같아야 합니다.");
  //   }
  // };

  return <div className="userform-page"></div>;
}
