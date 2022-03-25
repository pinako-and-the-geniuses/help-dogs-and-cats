import { useEffect, useState } from "react";
import st from "../styles/profile.module.scss";
const IMGURL = "http://j6a302.p.ssafy.io:8080/";

export default function ProfileBadge(badgesForProfile) {
  const [badges, setBadges] = useState([]);
  console.log("badgesForProfile", badgesForProfile);
  useEffect(() => {
    if (badgesForProfile) {
      setBadges(badgesForProfile.badgesForProfile);
      console.log(badges);
      console.log(badges[7]);
    } else {
      console.log("실패");
    }
  }, [badgesForProfile]);

  if (badges.length === 10) {
    return (
      <div className={st.btns}>
        <div className="d-flex">
          <div className={st.badgeBox}>
            <img
              className={st.badge}
              src={`${IMGURL}${badges[0].imageFilePath}`}
            />
          </div>
          <div className={st.badgeBox}>
            <img
              className={st.badge}
              src={`${IMGURL}${badges[1].imageFilePath}`}
            />
          </div>
          <div className={st.badgeBox}>
            <img
              className={st.badge}
              src={`${IMGURL}${badges[2].imageFilePath}`}
            />
          </div>
          <div className={st.badgeBox}>
            <img
              className={st.badge}
              src={`${IMGURL}${badges[3].imageFilePath}`}
            />
          </div>
          <div className={st.badgeBox}>
            <img
              className={st.badge}
              src={`${IMGURL}${badges[4].imageFilePath}`}
            />
          </div>
        </div>

        <div className="d-flex mt-2">
          <div className={st.badgeBox}>
            <img
              className={st.badge}
              src={`${IMGURL}${badges[5].imageFilePath}`}
            />
          </div>
          <div className={st.badgeBox}>
            <img
              className={st.badge}
              src={`${IMGURL}${badges[6].imageFilePath}`}
            />
          </div>
          <div className={st.badgeBox}>
            <img
              className={st.badge}
              src={`${IMGURL}${badges[7].imageFilePath}`}
            />
          </div>
          <div className={st.badgeBox}>
            <img
              className={st.badge}
              src={`${IMGURL}${badges[8].imageFilePath}`}
            />
          </div>
          <div className={st.badgeBox}>
            <img
              className={st.badge}
              src={`${IMGURL}${badges[9].imageFilePath}`}
            />
          </div>
        </div>
      </div>
    );
  } else {
    return "";
  }
}
