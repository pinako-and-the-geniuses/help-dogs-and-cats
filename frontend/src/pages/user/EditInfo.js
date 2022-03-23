import React, { useState, useEffect } from "react";
import DeleteUser from "./component/DeleteUser";
import UserImage from "./component/UserImage";
import Password from "./component/Password";
import NickName from "./component/NickName";
import Phone from "./component/Phone";
import Region from "./component/Region";
import st from "./styles/userform.module.scss";
import cn from "classnames";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { URL } from "../config/index";

export default function Editinfo() {
  // 입력정보
  const [img, setImg] = useState("");
  const [email, setEmail] = useState("");
  const [pwd, setPwd] = useState("");
  const [pwdConfirm, setPwdConfirm] = useState("");

  const [nickName, setNickName] = useState("");
  const [phone, setPhone] = useState("");
  const [region, setRegion] = useState("전체");
  // 유효성 검사
  const [isPwd, setIsPwd] = useState(false);
  const [isPwdConfirm, setIsPwdConfirm] = useState(false);
  const [isNickName, setIsNickName] = useState(false);
  const [isPhone, setIsPhone] = useState(false);
  const pagename = "회원정보 수정";
  const navi = useNavigate();

  const uId = 1;

  // useEffect(async () => {
  //   await axios
  //     .get(`URL/members/${uID}`)
  //     .then((res) => {
  //       console.log(res);
  //     })
  //     .catch((err) => {
  //       console.log(err);
  //     });
  // }, []);

  const onSubmit = (event) => {
    event.preventDefault();
    if (!isPwd || !isPwdConfirm) {
      alert("비밀번호를 확인해주세요");
    } else if (!isNickName) {
      alert("닉네임 중복확인이 필요합니다.");
    } else if (!isPhone) {
      alert("핸드폰 번호 인증이 필요합니다.");
    } else if (isPwd && isPwdConfirm && isNickName && isPhone) {
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
          if (res.status === 201) {
            navi("/", { replace: true });
          }
        })
        .catch((err) => {
          alert("수정 실패");
          console.log(err.response.status);
          // navi("/NotFound")
        });
    } else {
      alert("형식을 다시 확인해 입력해주세요");
    }
  };

  return (
    <form className={`${st.userinfoForm} ${st.userform}`}>
      <h2>{pagename}</h2>
      <UserImage uId={uId} />
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
        URL={URL}
        nickName={nickName}
        setNickName={setNickName}
        isNickName={isNickName}
        setIsNickName={setIsNickName}
      />
      <Phone
        URL={URL}
        phone={phone}
        setPhone={setPhone}
        isPhone={isPhone}
        setIsPhone={setIsPhone}
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
