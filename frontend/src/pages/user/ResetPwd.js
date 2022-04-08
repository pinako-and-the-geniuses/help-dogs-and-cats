import st from "./styles/find.module.scss";
import cn from "classnames";
import { useState } from "react";
import axios from "axios";
import { URL } from "public/config";
import { useNavigate } from "react-router-dom";
import swal from "sweetalert";

export default function ResetPwd() {
  const [newPwd, setNewPwd] = useState("");
  const [newPwdConfirm, setNewPwdConfirm] = useState("");
  const navigator = useNavigate();

  const onPasswordHandler = (e) => {
    const currentInput = e.target.value;
    setNewPwd(currentInput);
  };

  const onPwdConfirmHandler = (e) => {
    const currentInput = e.target.value;
    setNewPwdConfirm(currentInput);
  };

  const onSubmit = (e) => {
    e.preventDefault();
    const jwt = sessionStorage.getItem("jwt");
    if (newPwd !== newPwdConfirm) {
      alert("비밀번호가 서로 다릅니다!");
    } else {
      axios({
        url: `${URL}/members/password-reset`,
        method: "put",
        headers: { Authorization: jwt },
        data: {
          newPassword: newPwd,
        },
      }).then((res) => {
        const status = res.status;
        if (status === 200) {
          swal("재설정 완료", "로그인 화면으로 이동합니다.", "success");
          navigator("/login");
        }
      });
    }
  };

  return (
    <div className={st.userformPage}>
      <form onSubmit={onSubmit} className={cn(`${st.userform} ${st.findForm}`)}>
        <h2>비밀번호 재설정</h2>
        <>
          <div>
            <label htmlFor="pwd">비밀번호</label>
            <input
              id="pwd"
              name="pwd"
              type="password"
              placeholder="비밀번호(영문자,특수문자,숫자 조합으로 8자리 이상) / 이메일 포함 x"
              value={newPwd}
              onChange={onPasswordHandler}
              required
            />
          </div>
          <div>
            <label htmlFor="pwdConfirm">비밀번호 확인</label>
            <input
              id="pwdConfirm"
              name="pwdConfirm"
              type="password"
              placeholder="비밀번호 확인"
              value={newPwdConfirm}
              onChange={onPwdConfirmHandler}
              required
            />
          </div>
        </>
        <button type="submit" className={cn(st.longButton, "mt-4")}>
          비밀번호 재설정
        </button>
        <p className={st.message}>
          아직 회원이 아니신가요? <a href="/signup">회원가입</a>
        </p>
      </form>
    </div>
  );
}
