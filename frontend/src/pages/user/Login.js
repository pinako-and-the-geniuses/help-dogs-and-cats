import React, { useState } from "react";
import "./styles/userform.scss";

export default function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const onEmailHandler = (event) => {
    setEmail(event.currentTarget.value);
  };

  const onPasswordHandler = (event) => {
    setPassword(event.currentTarget.value);
  };

  const onSubmit = (event) => {
    event.preventDefault();
  };

  return (
    <div className="userform-page">
      <form className="login-form form">
        <label htmlFor="email"> 아이디 [Email]</label>
        <input
          name="email"
          type="email"
          value={email}
          onChange={onEmailHandler}
          placeholder="email@ssafy.com"
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
        />
        <button type="submit" onSubmit={onSubmit}>
          login
        </button>
        <p className="message">
          아직 회원이 아니신가요? <a href="/signup">회원가입</a>
        </p>
      </form>
    </div>
  );
}
