import React, { useState } from "react";
import Email from "./component/Email";
import Password from "./component/Password";
import NickName from "./component/NickName";
import Phone from "./component/Phone";
import Region from "./component/Region";
import st from "./styles/userform.module.scss";
import cn from "classnames";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { URL } from "../config/index";

export default function Signup() {
  // 입력정보
  const [email, setEmail] = useState("");
  const [pwd, setPwd] = useState("");
  const [pwdConfirm, setPwdConfirm] = useState("");
  const [nickName, setNickName] = useState("");
  const [phone, setPhone] = useState("");
  const [region, setRegion] = useState("전체");
  const [policy, setPolicy] = useState(0);
  // 유효성 검사
  const [isEmail, setIsEmail] = useState(false);
  const [isPwd, setIsPwd] = useState(false);
  const [isPwdConfirm, setIsPwdConfirm] = useState(false);
  const [isNickName, setIsNickName] = useState(false);
  const [isPhone, setIsPhone] = useState(false);
  const pagename = "회원가입";
  const navi = useNavigate();

  const inputClass = (boolean) => {
    switch (boolean) {
      case true:
        return "is-valid";
      case false:
        return "is-invalid";
      default:
        return "";
    }
  };

  const onPolicyHandler = (e) => {
    if (policy === 0) {
      setPolicy(1);
    } else {
      setPolicy(0);
    }
  };

  const onSubmit = (event) => {
    event.preventDefault();
    if (isEmail && isNickName) {
      alert("중복확인이 필요합니다.");
    } else if (policy === 0) {
      alert("약관 동의가 필요합니다.");
    } else if (
      isEmail &&
      isPwd &&
      isPwdConfirm &&
      isNickName &&
      isPhone &&
      policy
    ) {
      axios({
        url: `${URL}/members`,
        method: "POST",
        data: {
          email: email,
          password: pwd,
          nickname: nickName,
          tel: phone,
          activityArea: region,
        },
      })
        .then((res) => {
          console.log(res);
          if (res.data === 201) {
            navi("/login", { replace: true });
          } else {
            console.log("회원가입 실패이유", res.data);
          }
        })
        .catch((err) => {
          alert("회원가입 실패");
          console.log(err);
          // navi("/NotFound")
        });
    } else {
      alert("형식을 다시 확인해 입력해주세요");
    }
  };
  console.log("ismail", isEmail);
  console.log("ispwd", isPwd);
  console.log("isNickName", isNickName);
  console.log("isPhone", isPhone);
  return (
    <div className="userform-page">
      <form className={`${st.userinfoForm} ${st.userform}`}>
        <h2>{pagename}</h2>
        <Email
          URL={URL}
          email={email}
          setEmail={setEmail}
          setIsEmail={setIsEmail}
        ></Email>
        <Password
          URL={URL}
          pwd={pwd}
          setPwd={setPwd}
          pwdConfirm={pwdConfirm}
          setPwdConfirm={setPwdConfirm}
          isPwd={isPwd}
          setIsPwd={setIsPwd}
          isPwdConfirm={isPwdConfirm}
          setIsPwdConfirm={setIsPwdConfirm}
          inputClass={inputClass}
        ></Password>
        <NickName
          URL={URL}
          nickName={nickName}
          setNickName={setNickName}
          setIsNickName={setIsNickName}
        />
        <Phone
          URL={URL}
          phone={phone}
          setPhone={setPhone}
          isPhone={isPhone}
          setIsPhone={setIsPhone}
          inputClass={inputClass}
        />
        <Region URL={URL} region={region} setRegion={setRegion} />

        <div className="form-check mb-4">
          <span className={cn(st.label, "form-check-label")} htmlFor="policy">
            이용약관에 동의합니다.
          </span>
          <input
            className="form-check-input"
            type="checkbox"
            style={{ width: "auto", marginRight: "5px" }}
            onChange={onPolicyHandler}
          />
        </div>

        <div>
          <button className={st.longButton} onClick={onSubmit}>
            {pagename}
          </button>
        </div>
      </form>
    </div>
  );
}
