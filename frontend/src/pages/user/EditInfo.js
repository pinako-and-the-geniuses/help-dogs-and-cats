import React, { useState, useEffect } from "react";
import DeleteUser from "./component/DeleteUser";
import EditImage from "./component/EditImage";
import Password from "./component/Password";
import EditNickName from "./component/EditNickName";
import EditPhone from "./component/EditPhone";
import Region from "./component/Region";
import st from "./styles/userform.module.scss";
import cn from "classnames";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { URL } from "../../public/config/";
import { useSelector } from "react-redux";
import { useDispatch } from "react-redux";
import { loginAction } from "../../actions/UserAction";
import swal from "sweetalert";
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
  const [isNickName, setIsNickName] = useState(true);
  const [isPhone, setIsPhone] = useState(true);
  // 저장 정보 가져오기
  const info = useSelector((state) => state.userInfo.userInfo);
  const isLogin = useSelector((state) => state.userInfo.isLoggedIn);
  const jwt = sessionStorage.getItem("jwt");
  const pagename = "회원정보 수정";
  // 추가기능
  const navi = useNavigate();
  const dispatch = useDispatch();

  useEffect(() => {
    if (!isLogin) {
      swal("접근불가", "로그인해주세요", "error");
    } else {
      axios.get(`${URL}/members/${info.seq}`).then((res) => {
        const data = res.data.data;
        setImg(data.profileImageFilePath);
        setEmail(data.email);
        setNickName(data.nickname);
        setPhone(data.tel);
        setRegion(data.activityArea);
      });
    }
  }, [isLogin]);

  const onEditSubmit = (event) => {
    event.preventDefault();
    const data = {
      password: pwd,
      nickname: nickName,
      tel: phone,
      activityArea: region,
    };
    if (!isPwd || !isPwdConfirm) {
      swal("확인필요", "비밀번호를 확인해주세요.", "info");
    } else if (!isNickName) {
      swal("확인필요", "닉네임 중복확인이 필요합니다.", "info");
    } else if (!isPhone) {
      swal("확인필요", "핸드폰 번호 인증이 필요합니다.", "info");
    } else if (isPwd && isPwdConfirm && isNickName && isPhone) {
      axios({
        url: `${URL}/members/${info.seq}`,
        method: "PUT",
        headers: { Authorization: `Bearer ${jwt}` },
        data: data,
      })
        .then((res) => {
          if (res.status === 200) {
            swal("수정완료", "", "success");
            const userInfo = {
              seq: info.seq,
              email: email,
              role: info.role,
              nickname: nickName,
            };
            dispatch(loginAction(userInfo));
            navi(`/user/profile/${info.seq}`);
          }
        })
        .catch((err) => {
          swal("수정실패", "", "error");
        });
    } else {
      swal("형식오류", "형식을 다시 확인해 입력해주세요.", "info");
    }
  };

  return (
    <form
      onSubmit={onEditSubmit}
      className={`${st.userinfoForm} ${st.userform}`}
    >
      <h2>{pagename}</h2>
      <EditImage seq={info.seq} img={img} setImg={setImg} />
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
      <EditNickName
        nickName={nickName}
        isNickName={isNickName}
        setNickName={setNickName}
        setIsNickName={setIsNickName}
      />
      <EditPhone
        phone={phone}
        isPhone={isPhone}
        setPhone={setPhone}
        setIsPhone={setIsPhone}
      />
      <Region URL={URL} region={region} setRegion={setRegion} />
      <button type="submit" className={cn(`${st.longButton}`)}>
        {pagename}
      </button>
      <DeleteUser />
    </form>
  );
}
