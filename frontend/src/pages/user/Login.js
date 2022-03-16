import React, { useState } from "react";
import "./styles/user.css";
import { FormGroup, Label, Input, Button } from "reactstrap";

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
    <div className="loginregister">
      <h2>로그인</h2>
      <form className="userform">
        <FormGroup>
          <Label for="email">Email address</Label>
          <Input
            id="email"
            name="email"
            type="email"
            placeholder="이메일"
            value={email}
            onChange={onEmailHandler}
          />
        </FormGroup>
        <FormGroup>
          <Label for="password">Password</Label>
          <Input
            id="password"
            name="password"
            type="password"
            placeholder="비밀번호"
            value={password}
            onChange={onPasswordHandler}
            autoComplete="off"
          />
        </FormGroup>
        <div className="d-flex justify-content-center">
          <Button type="submit" onSubmit={onSubmit} color="primary">
            로그인
          </Button>
        </div>
      </form>
    </div>
  );
}
