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
import { URL } from "../../public/config";
import swal from "sweetalert";

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

  const onPolicyHandler = (e) => {
    if (policy === 0) {
      setPolicy(1);
    } else {
      setPolicy(0);
    }
  };

  const onSubmit = (event) => {
    event.preventDefault();
    if (!isEmail) {
      swal("확인필요", "이메일 중복확인이 필요합니다.", "info");
    } else if (!isPwd || !isPwdConfirm) {
      swal("확인필요", "비밀번호를 확인해주세요.", "info");
    } else if (!isNickName) {
      swal("확인필요", "닉네임 중복확인이 필요합니다.", "info");
    } else if (!isPhone) {
      swal("확인필요", "핸드폰 번호 인증이 필요합니다.", "info");
    } else if (policy === 0) {
      swal("확인필요", "약관 동의가 필요합니다.", "info");
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
      }).then((res) => {
        if (res.status === 201) {
          swal("반가워요", "", "success");
          navi("/login", { replace: true });
        }
      });
    } else {
      alert("형식을 다시 확인해 입력해주세요");
    }
  };

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
        ></Password>
        <NickName
          nickName={nickName}
          isNickName={isNickName}
          setNickName={setNickName}
          setIsNickName={setIsNickName}
        />
        <Phone
          phone={phone}
          isPhone={isPhone}
          setPhone={setPhone}
          setIsPhone={setIsPhone}
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
