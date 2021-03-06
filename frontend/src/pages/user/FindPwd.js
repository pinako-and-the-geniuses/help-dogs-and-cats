import st from "./styles/find.module.scss";
import cn from "classnames";
import { useState } from "react";
import axios from "axios";
import { URL } from "public/config";
import { useNavigate } from "react-router-dom";
import swal from "sweetalert";

export default function FindPwd() {
  const [email, setEmail] = useState("");
  const navigator = useNavigate();

  const onInputHandler = (e) => {
    const currentInput = e.target.value;
    setEmail(currentInput);
  };

  const onSubmit = (e) => {
    e.preventDefault();
    if (email.length > 5) {
      axios.get(`${URL}/members/password-reset/${email}`).then((res) => {
        const status = res.status;
        if (status === 200) {
          swal("링크전송완료", "메일을 확인하세요.", "success");
          navigator("/");
        } else {
          swal("", "이메일이 존재하지 않습니다.", "error");
          setEmail("");
        }
      });
    } else {
      swal("", "정보를 확인하세요.", "error");
    }
  };

  return (
    <div className={st.userformPage}>
      <div className={cn(`${st.userform} ${st.findForm}`)}>
        <h2>비밀번호 찾기</h2>

        <div className={cn("row", st.findCategory)}>
          <div className="col-4 now">
            <a href="/user/findid">아이디찾기</a>
          </div>

          <div className="col-4">
            <a href="/user/findpwd">비밀번호 찾기</a>
            <hr />
          </div>
        </div>
        <>
          <div className={st.findId}>
            <p>가입 이메일로 비밀번호 재설정 링크를 보내드립니다.</p>
            <input
              type="email"
              value={email}
              onChange={onInputHandler}
              placeholder="email@ssafy.com"
              required
            />
          </div>

          <button onClick={onSubmit} className={cn(st.longButton, "mt-4")}>
            확인
          </button>
          <p className={st.message}>
            아직 회원이 아니신가요? <a href="/signup">회원가입</a>
          </p>
        </>
      </div>
    </div>
  );
}
