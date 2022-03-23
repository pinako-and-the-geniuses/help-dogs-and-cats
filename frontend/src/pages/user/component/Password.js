export default function Password({
  URL,
  pwd,
  setPwd,
  pwdConfirm,
  setPwdConfirm,
  isPwd,
  setIsPwd,
  isPwdConfirm,
  setIsPwdConfirm,
}) {
  const inputClass = (boolean) => {
    switch (boolean) {
      case true:
        return "is-valid";
      case false:
        return "is-invalid";
      default:
        return "";
    }
  };

  const onPasswordHandler = (e) => {
    const pwdRegex = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,25}$/;
    const pwdCurrent = e.target.value;
    setPwd(pwdCurrent);

    if (!pwdRegex.test(pwdCurrent)) {
      setIsPwd(false);
    } else {
      setIsPwd(true);
    }
  };

  const onPwdConfirmHandler = (e) => {
    const pwdConfirmCurrent = e.target.value;
    setPwdConfirm(pwdConfirmCurrent);

    if (pwd === e.currentTarget.value) {
      setIsPwdConfirm(true);
    } else {
      setIsPwdConfirm(false);
    }
  };

  const isEnteredPwdValid = () => {
    if (pwd) return isPwd;
  };
  const isEnteredPwdConfirmValid = () => {
    if (pwdConfirm) return isPwdConfirm;
  };

  return (
    <>
      <div>
        <label htmlFor="pwd">비밀번호</label>
        <input
          id="pwd"
          name="pwd"
          type="password"
          placeholder="비밀번호(영문자,특수문자,숫자 조합으로 8자리 이상)"
          value={pwd}
          onChange={onPasswordHandler}
          className={`form-control ${inputClass(isEnteredPwdValid())}`}
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
          value={pwdConfirm}
          onChange={onPwdConfirmHandler}
          className={`form-control ${inputClass(isEnteredPwdConfirmValid())}`}
          required
        />
      </div>
    </>
  );
}
