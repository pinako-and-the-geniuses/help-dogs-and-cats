import axios from "axios";
import st from "../styles/userform.module.scss";
import { URL } from "../../config/";
import { useNavigate } from "react-router-dom";

export default function DeleteUser() {
  const navi = useNavigate();

  //회원정보
  const userId = "0101";
  const jwt = "0101";

  //탈퇴요청
  const onDeleteUser = () => {
    axios
      .delete(`${URL}/members/${userId}`, {
        headers: { authorization: jwt },
      })
      .then((res) => {
        console.log(res);
        if (res.status === 24) {
          alert("다음에 뵙겠습니다. 꾸벅");
          navi("/");
        }
      })
      .catch((err) => {
        console.log(err);
        alert("잘못된 접근입니다.");
      });
  };

  return (
    <div>
      <a href="/" id="delete" onClick={onDeleteUser} className={st.deleteuser}>
        회원탈퇴
      </a>
    </div>
  );
}
