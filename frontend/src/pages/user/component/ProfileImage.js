import st from "../styles/profile.module.scss";
import defaultImg from "../../../public/img/default.png";

export default function ProfileImage({ profileImageFilePath }) {
  if (profileImageFilePath == null) {
    return (
      <>
        <img className={st.image} src={defaultImg} />
      </>
    );
  } else {
    return (
      <>
        <img className={st.image} src={profileImageFilePath} />
      </>
    );
  }
}
