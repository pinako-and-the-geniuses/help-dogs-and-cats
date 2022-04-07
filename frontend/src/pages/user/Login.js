import React, { useEffect, useState } from "react";
import { useDispatch } from "react-redux";
import { loginAction } from "../../actions/UserAction";
import axios from "axios";
import { URL } from "../../public/config/index";
import st from "./styles/userform.module.scss";
import cn from "classnames";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import swal from "sweetalert";

export default function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [submitLoading, setSubmitLoading] = useState(false); // 로그인성공하고 useEffetct에 안걸리기 위한 값
  const isLogin = useSelector((state) => state.userInfo.isLoggedIn); //로그인상태 불러오기
  const dispatch = useDispatch();
  const navi = useNavigate();

  useEffect(() => {
    if (isLogin) {
      if (!submitLoading) {
        swal("", "로그인 완료된 상태입니다.", "error");
      }
      navi("/");
    }
  });

  const onEmailHandler = (event) => {
    setEmail(event.currentTarget.value);
  };

  const onPasswordHandler = (event) => {
    setPassword(event.currentTarget.value);
  };

  const onSubmit = (event) => {
    event.preventDefault();
    const user = {
      email: email,
      password: password,
    };
    if (email && password) {
      axios
        .post(`${URL}/members/login`, user)
        .then((res) => {
          if (res.status === 200) {
            setSubmitLoading(true);
            dispatch(loginAction(res.data.data.memberInfo));
            sessionStorage.setItem("jwt", res.data.data.jwtToken);
          } else if (res.status === 204) {
            swal("", "이메일 또는 패스워드가 잘못 입력되었습니다.", "error");
          }
        })
        .catch((err) => {
          if (err.response.status === 400) {
            swal("", "정보를 확인해주세요.", "error");
          }
        });
    } else {
      swal("", "정보를 확인해주세요.", "error");
    }
  };

  return (
    <div className={st.userformPage}>
      <form className={st.loginForm}>
        <h2>로그인</h2>
        <label htmlFor="email"> 아이디 [Email]</label>
        <input
          name="email"
          type="email"
          value={email}
          onChange={onEmailHandler}
          placeholder="email@ssafy.com"
          required
        />
        <label htmlFor="password">비밀번호</label>
        <input
          id="password"
          name="password"
          type="password"
          placeholder="password"
          value={password}
          onChange={onPasswordHandler}
          autoComplete="off"
          required
        />
        <button className={cn(st.longButton, "mt-4")} onClick={onSubmit}>
          login
        </button>
        <p className={st.message}>
          아직 회원이 아니신가요? <a href="/signup">회원가입</a>
        </p>
        <div className={st.message}>
          <div className={st.find}>
            <a href="/user/findid">아이디 찾기</a>
            <span> | </span>
            <a href="/user/findpwd">비밀번호 찾기</a>
          </div>
        </div>
      </form>
    </div>
  );
}
