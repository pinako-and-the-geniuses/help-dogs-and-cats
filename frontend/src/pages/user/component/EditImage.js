import { useEffect, useRef, useState } from "react";
import defaultImg from "../../../public/img/default.png";
import st from "../styles/userform.module.scss";
import axios from "axios";
import { IMGURL, URL } from "public/config";
import swal from "sweetalert";

export default function EditImage({ seq, img, setImg }) {
  const [path, setPath] = useState("");
  const jwt = sessionStorage.getItem("jwt");
  const profileImg = useRef();

  useEffect(() => {
    if (img) {
      setPath(img);
    }
  }, [img]);

  const onEditBtn = (e) => {
    e.preventDefault();
    profileImg.current.click();
  };

  const onEditImg = async (e) => {
    const formData = new FormData();
    formData.append("profileImageFile", e.target.files[0]);
    await axios
      .put(`${URL}/members/${seq}/profile-image`, formData, {
        headers: {
          "Content-type": "multipart/form-data",
          Authorization: `Bearer ${jwt}`,
        },
      })
      .then((res) => {
        setPath(res.data.data.profileImagePath);
        setImg(res.data.data.profileImagePath);
      });
  };

  const onDeleteBtn = (e) => {
    e.preventDefault();
    axios
      .delete(`${URL}/members/${seq}/profile-image`, {
        headers: {
          Authorization: `Bearer ${jwt}`,
        },
      })
      .then((res) => {
        setPath("");
        setImg("");
      })
      .catch((err) => {
        const status = err.response.status;
        if (status === 500) {
          swal("서버에러", "", "error");
        } else {
          swal("검증실패", "", "error");
        }
      });
  };
  return (
    <div className={st.userimg}>
      {img ? (
        <img alt="userImg" src={`${IMGURL}${path}`} />
      ) : (
        <img alt="defatultImg" src={defaultImg} />
      )}

      <div>
        <button className="me-3" onClick={onEditBtn}>
          수정
        </button>
        <input
          type="file"
          ref={profileImg}
          id="profileImg"
          accept="image/*"
          name="file"
          onChange={onEditImg}
          className={st.inputImg}
        />
        {img ? <button onClick={onDeleteBtn}>삭제</button> : ""}
      </div>
    </div>
  );
}
