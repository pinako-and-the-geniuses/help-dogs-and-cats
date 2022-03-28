import st from "../styles/profile.module.scss";
import defaultImg from "../../../public/img/default.png";
import { IMGURL } from "public/config";
export default function ProfileImage({ profileImageFilePath }) {
  console.log("프로필이미지경로", profileImageFilePath);
  if (profileImageFilePath == null) {
    return (
      <>
        <img className={st.image} src={defaultImg} />
      </>
    );
  } else {
    return (
      <>
        <img className={st.image} src={`${IMGURL}${profileImageFilePath}`} />
      </>
    );
  }
}
