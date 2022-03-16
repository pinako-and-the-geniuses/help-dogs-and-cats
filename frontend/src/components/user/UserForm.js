import "../styles/userform.scss";
export default function UserForm({
  email,
  password,
  confirmPassword,
  nickName,
  phone,
  region,
  policy,
  onEmailHandler,
  onPasswordHandler,
  onConfirmPasswordHandler,
  onNickNameHandler,
  onRegionHandler,
  onPhoneHandler,
  onPolicyHandler,
  onSubmit,
}) {
  return (
    <form className="userform form">
      <h2>회원가입</h2>
      <div className="">
        <label htmlFor="email">아이디 [Email]</label>
        <input
          id="email"
          name="email"
          type="email"
          placeholder="email@ssafy.com"
          value={email}
          onChange={onEmailHandler}
        />
      </div>
      <div>
        <label htmlFor="password">비밀번호</label>
        <input
          id="password"
          name="password"
          type="password"
          placeholder="비밀번호"
          value={password}
          onChange={onPasswordHandler}
        />
      </div>
      <div>
        <label htmlFor="confirmPassword">비밀번호 확인</label>
        <input
          id="confirmPassword"
          name="confirmPassword"
          type="password"
          placeholder="비밀번호 확인"
          value={confirmPassword}
          onChange={onConfirmPasswordHandler}
        />
      </div>
      <div>
        <label htmlFor="nickname">닉네임</label>
        <input
          id="nickname"
          name="nickname"
          type="text"
          placeholder="닉네임"
          value={nickName}
          onChange={onNickNameHandler}
        />
      </div>
      <div>
        <label htmlFor="phone">휴대폰 번호</label>
        <input
          id="phone"
          name="phone"
          type="text"
          placeholder="휴대폰 번호"
          value={phone}
          onChange={onPhoneHandler}
        />
      </div>

      <div className="form-check">
        <label className="form-check-label" htmlFor="policy">
          이용약관에 동의합니다.
        </label>
        <input
          className="form-check-input"
          value={policy}
          type="checkbox"
          id="policy"
          onChange={onPhoneHandler}
        />
      </div>
      <div>
        <button onClick={onSubmit}>회원가입</button>
      </div>
    </form>
  );
}
