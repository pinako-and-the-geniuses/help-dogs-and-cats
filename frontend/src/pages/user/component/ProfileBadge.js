import st from "../styles/profile.module.scss";
import { URL } from "../../../public/config/index";
export default function ProfileBadge({ badgesForProfile }) {
  console.log(badgesForProfile);
  const getBadge = badgesForProfile.filter((badge) => badge.achieve);
  const notBadge = badgesForProfile.filter((badge) => !badge.achieve);
  const Badges = getBadge.concat(notBadge);

  // console.log(Badges[0].imageFilePath);

  return (
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

      <div className={st.box} stle="background: #BDBDBD;">
        {/* <img className={st.profile} src={`${URL}/${Badges[0].imageFilePath}`} /> */}
      </div>
    </div>
  );
}
