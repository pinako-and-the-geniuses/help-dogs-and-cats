import "./styles/userform.scss";

export default function FindPwd() {
  return (
    <div className="userform-page">
      <form className="find-form form">
        <h2>비밀번호 찾기</h2>

        <div className="row find-category">
          <div className="col-4 now">
            <a href="/user/findid">아이디찾기</a>
          </div>

          <div className="col-4">
            <a href="/user/findpwd">비밀번호 찾기</a>
            <hr />
          </div>
        </div>

        <div className="find-id">
          <sapn>가입하신 "이메일 계정"을 입력하시면</sapn>
          <p>메일에서 링크로 비빌번호를 재설정할 수 있습니다.</p>

          <input
            type="email"
            // onChange={onEmailHandler}
            placeholder="email@ssafy.com"
          />
        </div>

        <button type="submit">비밀번호 찾기</button>
        <p className="message">
          아직 회원이 아니신가요? <a href="/signup">회원가입</a>
        </p>
      </form>
    </div>
  );
}
