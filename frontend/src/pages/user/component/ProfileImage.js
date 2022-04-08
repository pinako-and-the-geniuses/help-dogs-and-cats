import st from "../styles/profile.module.scss";
import defaultImg from "../../../public/img/default.png";
import { IMGURL } from "public/config";
export default function ProfileImage({ profileImageFilePath }) {
  if (profileImageFilePath == null) {
    return (
      <>
        <img alt="defaultImg" className={st.image} src={defaultImg} />
      </>
    );
  } else if (profileImageFilePath) {
    return (
      <>
        <img
          alt="ProfileImg"
          className={st.image}
          src={`${IMGURL}${profileImageFilePath}`}
        />
      </>
    );
  } else {
    return <></>;
  }
}
