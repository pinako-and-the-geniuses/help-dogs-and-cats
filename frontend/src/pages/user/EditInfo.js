import React, { useState, useEffect } from "react";
import DeleteUser from "./component/DeleteUser";
import UserImage from "./component/UserImage";
import Password from "./component/Password";
import NickName from "./component/NickName";
import EditPhone from "./component/EditPhone";
import Region from "./component/Region";
import st from "./styles/userform.module.scss";
import cn from "classnames";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { URL } from "../../public/config/";
import { useSelector } from "react-redux";

export default function Editinfo() {
  // 입력정보
  const [img, setImg] = useState("");
  const [email, setEmail] = useState("");
  const [pwd, setPwd] = useState("");
  const [pwdConfirm, setPwdConfirm] = useState("");
  const [nickName, setNickName] = useState("");
  const [phone, setPhone] = useState("");
  const [newPhone, setNewPhone] = useState("");
  const [region, setRegion] = useState("전체");
  // 유효성 검사
  const [isPwd, setIsPwd] = useState(false);
  const [isPwdConfirm, setIsPwdConfirm] = useState(false);
  const [isNickName, setIsNickName] = useState(true);
  const [isPhone, setIsPhone] = useState(true);

  // 저장 정보 가져오기
  const seq = useSelector((state) => state.userInfo.userInfo.seq);
  const isLogin = useSelector((state) => state.userInfo.isLoggedIn);
  const jwt = sessionStorage.getItem("jwt");
  const pagename = "회원정보 수정";
  const navi = useNavigate();

  useEffect(() => {
    if (!isLogin) {
      alert("로그인 해주세요.");
    } else {
      axios
        .get(`${URL}/members/${seq}`)
        .then((res) => {
          const data = res.data.data;
          setEmail(data.email);
          setNickName(data.nickname);
          setPhone(data.tel);
          setRegion(data.activityArea);
        })
        .catch((err) => {
          console.log(err);
        });
    }
  }, [isLogin]);

  console.log("data", pwd, nickName, phone, region);

  const onSubmit = (event) => {
    event.preventDefault();
    setPhone(newPhone);
    if (!isPwd || !isPwdConfirm) {
      alert("비밀번호를 확인해주세요");
    } else if (!isNickName) {
      alert("닉네임 중복확인이 필요합니다.");
    } else if (!isPhone) {
      alert("핸드폰 번호 인증이 필요합니다.");
    } else if (isPwd && isPwdConfirm && isNickName && isPhone) {
      axios({
        url: `${URL}/members/${seq}`,
        method: "PUT",
        headers: { Authorization: jwt },
        data: {
          password: pwd,
          nickname: nickName,
          tel: newPhone,
          activityArea: region,
        },
      })
        .then((res) => {
          console.log(res);
          if (res.status === 201) {
            alert("수정 완료");
            navi("/");
          }
        })
        .catch((err) => {
          alert("수정 실패");
          // navi("/NotFound")
        });
    } else {
      alert("형식을 다시 확인해 입력해주세요");
    }
  };

  return (
    <form className={`${st.userinfoForm} ${st.userform}`}>
      <h2>{pagename}</h2>
      <UserImage seq={seq} />
      <div name="아이디[이메일]">
        <p>
          <label htmlFor="email">아이디 [Email]</label>
        </p>
        <input id="email" type="email" defaultValue={email} readOnly />
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
      <EditPhone
        URL={URL}
        phone={phone}
        newPhone={newPhone}
        setNewPhone={setNewPhone}
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
