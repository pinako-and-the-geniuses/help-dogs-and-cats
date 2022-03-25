import st from "../styles/profile.module.scss";

export default function ProfileInfo() {
  //   - 회원 seq
  // - 이메일
  // - 닉네임
  // - 활동지역
  // - 프로필사진
  // - 온도
  // - 뱃지
  // - 레벨
  // - 경험치

  return (
    <div className={st.info}>
      <div>
        <span className={st.nickname}>닉네임</span>
        <span className={st.region}>활동지역</span>
      </div>
      <p className={st.level}>레벨?경험지?</p>
      <div className={st.btns}>
        <div>
          <button>뱃지</button>
          <button>뱃지</button>
          <button>뱃지</button>
          <button>뱃지</button>
          <button>뱃지</button>
        </div>
        <div>
          <button>뱃지</button>
          <button>뱃지</button>
          <button>뱃지</button>
          <button>뱃지</button>
          <button>뱃지</button>
        </div>
      </div>
    </div>
  );
}
