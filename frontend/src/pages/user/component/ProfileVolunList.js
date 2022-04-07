import st from "../styles/profile.module.scss";
import cn from "classnames";
import { useNavigate } from "react-router-dom";

export default function ProfileVolunList({ item }) {
  const navigator = useNavigate();

  // 해당 모집 상세페이지로
  const onGoToDetail = (itemSeq) => {
    navigator(`/volunteer/detail/${itemSeq}`);
  };

  return (
    <>
      <div key={item.title} className={st.listItemDiv}>
        <button
          type="button"
          className={st.listItem}
          onClick={() => onGoToDetail(item.volunteerSeq)}
        >
          <div className={st.itemCategory}>
            <div>
              {item.volunteerStatus === "ONGOING" ? "진행중" : ""}
              {item.volunteerStatus === "DONE" ? "모집완료" : ""}
              {item.volunteerStatus === "RECRUITING" ? "모집중" : ""}
            </div>
          </div>
          <div className="card-body">
            <h5 className={cn(st.cardTitle, "card-title")}>{item.title}</h5>
          </div>
          <div className={st.cardEnd}>{item.createdDate}</div>
        </button>
      </div>
    </>
  );
}
