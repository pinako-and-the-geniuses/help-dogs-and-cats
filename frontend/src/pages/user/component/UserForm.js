import DeleteUser from "./DeleteUser";
import Email from "./Email";
import st from "../styles/userform.module.scss";

export default function UserForm({
  BASEURL,
  pagename,
  password,
  confirmPassword,
  nickName,
  phone,
  region,
  onPasswordHandler,
  onConfirmPasswordHandler,
  onNickNameHandler,
  onRegionHandler,
  onPhoneHandler,
  onIsChekedHandler,
  onSubmit,
}) {
  return (
    <form className={`${st.userinfoForm} ${st.form}`}>
      <h2>{pagename}</h2>
      <Email BASEURL={BASEURL}></Email>
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
          type="tel"
          pattern="[0-9]{3}-[0-9]{4}-[0-9]{4}"
          placeholder="010-1234-5678"
          value={phone}
          onChange={onPhoneHandler}
        />
      </div>
      <div>
        <label htmlFor="region">활동 지역</label>
        <div className="input-group mb-3">
          <select
            className="form-select"
            id="region"
            aria-label="Example select with button addon"
            value={region}
            onChange={onRegionHandler}
          >
            <option value="전체">전체</option>
            <option value="서울">서울</option>
            <option value="광주">광주</option>
            <option value="부산">부산</option>
            <option value="울산">울산</option>
            <option value="경주">경주</option>
          </select>
        </div>
      </div>

      <div className="form-check">
        <label className="form-check-label" htmlFor="policy">
          이용약관에 동의합니다.
        </label>
        <input
          className="form-check-input"
          type="checkbox"
          style={{ width: "auto", marginRight: "5px" }}
          onChange={onIsChekedHandler}
        />
      </div>

      <div>
        <button className={st.longButton} onClick={onSubmit}>
          {pagename}
        </button>
      </div>
      <DeleteUser />
    </form>
  );
}
