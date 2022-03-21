import React, { useState } from "react";
import DeleteUser from "./component/DeleteUser";
import Email from "./component/Email";
import Password from "./component/Password";
import NickName from "./component/NickName";
import Phone from "./component/Phone";
import Region from "./component/Region";
import st from "./styles/userform.module.scss";

const URL = "http/";

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

  const onPolicyHandler = (event) => {
    console.log(event.currentTarget.value);
    setPolicy(event.currentTarget.value);
  };

  const onSubmit = (event) => {
    event.preventDefault();
  };
  return (
    <div className="userform-page">
      <form className={`${st.userinfoForm} ${st.form}`}>
        <h2>{pagename}</h2>
        <Email
          URL={URL}
          email={email}
          setEmail={setEmail}
          isEmail={isEmail}
          setIsEmail={setIsEmail}
          inputClass={inputClass}
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
          isNickName={isNickName}
          setIsNickName={setIsNickName}
          inputClass={inputClass}
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

        <div className="form-check">
          <label className="form-check-label" htmlFor="policy">
            이용약관에 동의합니다.
          </label>
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
        <DeleteUser />
      </form>
    </div>
  );
}
