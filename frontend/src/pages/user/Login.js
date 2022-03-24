import React, { useState } from "react";
import { useSelector, useDispatch } from "react-redux";
import axios from "axios";
import { URL } from "../../public/config/index";
import st from "./styles/userform.module.scss";
import cn from "classnames";

export default function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const dispatch = useDispatch();

  const onEmailHandler = (event) => {
    setEmail(event.currentTarget.value);
  };

  const onPasswordHandler = (event) => {
    setPassword(event.currentTarget.value);
  };

  const onSubmit = (event) => {
    event.preventDefault();
    const data = {
      email: email,
      password: password,
    };
    axios
      .post(`${URL}/members/login`, { data: data })
      .then((res) => {
        console.log("res", res);
        const { token, userSeq, email, nickName, auth } = res.data;

        if (res.status == 200) {
          alert("로그인 성공");
          dispatch({
            type: "LOGIN",
            userInfo: {
              userSeq,
              email,
              nickName,
              auth,
            },
          });
          // navigator("/");
        } else if (res.status == 204) {
          alert("이메일 또는 패스워드가 잘못 입력되었습니다.");
        }
      })
      .catch((err) => {
        if (err.response.status == 400) {
          alert("회원 정보가 없습니다.");
        } else {
          alert("잘못된 접근입니다.");
        }
      });
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
