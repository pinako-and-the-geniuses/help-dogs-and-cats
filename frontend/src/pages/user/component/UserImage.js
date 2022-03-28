import { useEffect } from "react";
import defaultImg from "../../../public/img/default.png";
import st from "../styles/userform.module.scss";

export default function UserImage() {
  // useEffect(()=> {
  //   await axios
  //   .get('')
  // })

  const onEditImg = () => {
    return;
  };

  return (
    <div className={st.userimg}>
      <img className="phoneImage" alt="userImg" src={defaultImg} />
      <div>
        <button className="me-3" onClick={onEditImg}>
          수정
        </button>
        <button>삭제</button>
      </div>
    </div>
  );
}
