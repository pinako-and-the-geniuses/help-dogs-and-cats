import React, { useState } from "react";
import DeleteUser from "./component/DeleteUser";
import NickName from "./component/NickName";
import Phone from "./component/Phone";
import Region from "./component/Region";
import st from "./styles/userform.module.scss";
import cn from "classnames";

const URL = "http/";

export default function Editinfo() {
  // 입력정보
  const [img, setImg] = useState("");
  const [email, setEmail] = useState("");
  const [pwd, setPwd] = useState("");
  const [nickName, setNickName] = useState("");
  const [phone, setPhone] = useState("");
  const [region, setRegion] = useState("전체");
  const [policy, setPolicy] = useState(0);
  // 유효성 검사
  const [isPwd, setIsPwd] = useState(false);
  const [isNickName, setIsNickName] = useState(false);
  const [isPhone, setIsPhone] = useState(false);

  const pagename = "회원정보 수정";
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

  const onPasswordHandler = (e) => {
    const pwdRegex = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,25}$/;
    const pwdCurrent = e.target.value;
    setPwd(pwdCurrent);
    if (!pwdRegex.test(pwdCurrent)) {
      setIsPwd(false);
    } else {
      setIsPwd(true);
    }
  };
  const isEnteredPwdValid = () => {
    if (pwd) return isPwd;
  };

  const onPolicyHandler = (event) => {
    console.log(event.currentTarget.value);
    setPolicy(event.currentTarget.value);
  };

  const onSubmit = (event) => {
    event.preventDefault();
  };

  return (
    <form className={`${st.userinfoForm} ${st.form}`}>
      <h2>{pagename}</h2>
      <div>
        <p>
          <label htmlFor="email">아이디 [Email]</label>
        </p>
        <input
          id="email"
          name="email"
          type="email"
          placeholder="email@ssafy.com"
          defaultValue={email}
          readOnly
        />
      </div>
      <div>
        <label htmlFor="pwd">비밀번호</label>
        <input
          id="pwd"
          name="pwd"
          type="password"
          placeholder="비밀번호(영문자,특수문자,숫자 조합으로 8자리 이상)"
          onChange={onPasswordHandler}
          className={cn(`form-control, ${inputClass(isEnteredPwdValid())}`)}
          required
        />
      </div>
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
      <button
        type="submit"
        className={cn(`${st.longButton}`)}
        onClick={onSubmit}
      >
        {pagename}
      </button>{" "}
      <DeleteUser />
    </form>
  );
}
