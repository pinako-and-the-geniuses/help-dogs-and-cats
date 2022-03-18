import "./styles/userform.scss";

export default function FindId() {
  return (
    <div className="userform-page">
      <form className="find-form form">
        <h2>아이디 찾기</h2>

        <div className="row find-category">
          <div className="col-4 now">
            <a href="/user/findid">아이디찾기</a>
            <hr />
          </div>

          <div className="col-4">
            <a href="/user/findpwd">비밀번호 찾기</a>
          </div>
        </div>

        <div className="find-id">
          <p>가입 시 핸드폰 번호를 입력하세요. ( '-' 제외)</p>

          <input
            type="text"
            // onChange={onEmailHandler}
            placeholder="01012345678"
          />
        </div>

        <button type="submit">아이디 찾기</button>
        <p className="message">
          아직 회원이 아니신가요? <a href="/signup">회원가입</a>
        </p>
      </form>
    </div>
  );
}
