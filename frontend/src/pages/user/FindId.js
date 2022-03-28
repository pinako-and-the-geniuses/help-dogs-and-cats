import st from "./styles/find.module.scss";
import cn from "classnames";
import { useState } from "react";
import axios from "axios";
import { URL } from "public/config";
import { useNavigate } from "react-router-dom";

export default function FindId() {
  const [tel, setTel] = useState("");
  const [findId, setFindId] = useState("");
  const navigator = useNavigate();

  const onInputHandler = (e) => {
    const currentInput = e.target.value;
    console.log(e.target.value);
    setTel(currentInput);
  };

  const onSubmit = (e) => {
    e.preventDefault();
    if (tel.length < 11) {
      alert("번호를 입력하세요");
    } else {
      axios
        .get(`${URL}/members/find-email-by/${tel}`)
        .then((res) => {
          const status = res.status;
          if (status === 200) {
            console.log(res.data.data.email);
            setFindId(res.data.data.email);
          } else {
            alert("이메일이 존재하지 않습니다.");
            setTel("");
          }
        })
        .catch((err) => {
          const status = err.response.status;
          if (status === 400) {
            alert("번호 형식을 확인해주세요");
            setTel("");
          } else {
            alert("서버에러");
          }
        });
    }
  };

  const onLoginBtn = () => {
    navigator("/login");
  };

  return (
    <div className={st.userformPage}>
      <form onSubmit={onSubmit} className={cn(`${st.userform} ${st.findForm}`)}>
        <h2>아이디 찾기</h2>

        <div className={cn("row", st.findCategory)}>
          <div className="col-4 now">
            <a href="/user/findid">아이디찾기</a>
            <hr />
          </div>

          <div className="col-4">
            <a href="/user/findpwd">비밀번호 찾기</a>
          </div>
        </div>
        {findId ? (
          <>
            <div className={st.emailBox}>
              고객님의 이메일은 <span>{findId}</span> 입니다.
            </div>
            <button className={cn(st.longButton, "mt-4")} onClick={onLoginBtn}>
              로그인
            </button>
          </>
        ) : (
          <>
            <div className={st.findId}>
              <p>가입 시 핸드폰 번호를 입력하세요.</p>

              <input
                type="tel"
                pattern="[0-9]{3}-[0-9]{4}-[0-9]{4}"
                placeholder="010-0000-0000 ( - 포함 작성)"
                required
                value={tel}
                onChange={onInputHandler}
              />
            </div>

            <button type="submit" className={cn(st.longButton, "mt-4")}>
              아이디 찾기
            </button>
            <p className={st.message}>
              아직 회원이 아니신가요? <a href="/signup">회원가입</a>
            </p>
          </>
        )}
      </form>
    </div>
  );
}
