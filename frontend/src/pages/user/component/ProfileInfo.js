import ProfileImage from "./ProfileImage";

import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { URL } from "../../../public/config/index";
import st from "../styles/profile.module.scss";
import ProfileBadge from "./ProfileBadge";
export default function ProfileInfo({ isLogin, seq }) {
  const navi = useNavigate();
  const [inputs, setInputs] = useState({
    nickname: "",
    level: "",
    exp: 0,
    activityArea: "",
    profileImageFilePath: "",
    badgesForProfile: [],
  });
  const {
    nickname,
    level,
    exp,
    activityArea,
    profileImageFilePath,
    badgesForProfile,
  } = inputs; // 비구조화 할당을 통해 값 추출

  useEffect(() => {
    // 로그인 안한 사람은 누구 프로필도 볼 수 없음.
    if (!isLogin) {
      alert("로그인 해주세요");
      navi("/login");
    } else if (seq) {
      axios
        .get(`${URL}/members/${seq}`)
        .then((res) => {
          if (res.status === 200) {
            console.log(res.data.data);
            const {
              nickname,
              level,
              activityArea,
              badgesForProfile,
              profileImageFilePath,
            } = res.data.data;
            const exp = res.data.data.exp % 100;

            setInputs({
              ...inputs,
              nickname,
              level,
              activityArea,
              badgesForProfile,
              exp,
              profileImageFilePath,
            });
          }
        })

        .catch((err) => {
          console.log(err);
        });
    }
  }, []);
  return (
    <>
      <ProfileImage profileImageFilePath={profileImageFilePath} />
      <div className={st.info}>
        <div>
          <span className={st.nickname}>{nickname}</span>
          <span className={st.region}>{activityArea}</span>
        </div>
        <div className={st.level}>
          <p>Lv {level}</p>

          <div className="progress">
            <div
              className="progress-bar bg-success"
              role="progressbar"
              style={{ width: `${exp}%` }}
              aria-valuenow="50"
              aria-valuemin="0"
              aria-valuemax="100"
            ></div>
          </div>
        </div>
        <ProfileBadge badgesForProfile={badgesForProfile} />
      </div>
    </>
  );
}
