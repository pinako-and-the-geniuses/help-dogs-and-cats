import axios from "axios";
import st from "../styles/userform.module.scss";
import { URL } from "../../../public/config";
import { useNavigate } from "react-router-dom";
import swal from "sweetalert";
import { useDispatch, useSelector } from "react-redux";
import { logoutAction } from "actions/UserAction";

export default function DeleteUser() {
  const navi = useNavigate();
  const dispatch = useDispatch();
  //회원정보
  const userSeq = useSelector((state) => state.userInfo.userInfo.seq);
  const jwt = sessionStorage.getItem("jwt");
  console.log(`${URL}/members/${userSeq}`);
  //탈퇴요청
  const onDeleteUser = () => {
    axios({
      url: `${URL}/members/${userSeq}`,
      method: "delete",
      headers: {
        Authorization: `Bearer ${jwt}`,
      },
    })
      .then((res) => {
        console.log(res);
        if (res.status === 204) {
          dispatch(logoutAction());
          sessionStorage.removeItem("jwt");
          swal("회원 탈퇴", "다음에 만나요", "success");
          navi("/");
        }
      })
      .catch((err) => {
        console.log(err);
        swal("", "다시 확인해주세요", "error");
      });
  };

  return (
    <div>
      <div id="delete" onClick={onDeleteUser} className={st.deleteuser}>
        회원탈퇴
      </div>
    </div>
  );
}
