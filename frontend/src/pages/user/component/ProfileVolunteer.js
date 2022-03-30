import VolunteerModal from "../component/ProfileVolunteerModal";
import st from "../styles/profile.module.scss";
import cn from "classnames";
import { useEffect, useState } from "react";
import axios from "axios";
import { URL } from "public/config";
import { useNavigate } from "react-router-dom";
import SmallPaging from "components/SmallPaging";

export default function ProfileVolunteer({ category, seq, isLogin }) {
  // 활동 목록
  const [list, setList] = useState("");
  //페이지네이션
  const [page, setPage] = useState(1);
  const [totalPageNumber, setTotalPageNumber] = useState(1);
  const size = 5;
  // 기타
  const navigator = useNavigate();
  const jwt = sessionStorage.getItem("jwt");

  useEffect(() => {
    console.log(page, totalPageNumber);
    if (isLogin) {
      axios
        .get(`${URL}/members/${seq}/${category}?page=${page}&size=${size}`, {
          headers: { Authorization: `Bearer ${jwt}` },
        })
        .then((res) => {
          const data = res.data.data;
          console.log(data);
          setList(data.volunteers);
          setTotalPageNumber(data.totalPageNumber);
        })
        .catch((err) => console.log(err));
    }
  }, []);

  // 해당 모집 상세페이지로
  const onGoToDetail = (itemSeq) => {
    console.log(itemSeq);
    navigator(`/volunteer/detail/${itemSeq}`);
  };

  return (
    <div>
      <div className={st.listBox}>
        <div className={st.adopts}>
          <div name="글 목록" className={st.list}>
            {list ? (
              list.map((item) => {
                return (
                  <div key={item.volunteerSeq} className={st.listItemDiv}>
                    <button
                      type="button"
                      className={cn("btn", `${st.listItem}`)}
                      onClick={() => onGoToDetail(item.volunteerSeq)}
                      data-bs-toggle="modal"
                      data-bs-target="#detailModal"
                    >
                      <div className={st.itemCategory}>
                        <div>
                          {item.volunteerStatus === "RECRUITING"
                            ? "모집중"
                            : ""}
                          {item.volunteerStatus === "ONGOING" ? "진행중" : ""}
                          {item.volunteerStatus === "DONE" ? "모집완료" : ""}
                          {item.volunteerStatus === "DONE" &&
                          item.authStatus ? (
                            <>
                              {item.authStatus === "REQUEST" ? "인증대기" : ""}
                              {item.authStatus === "REJECT" ? "인증거절" : ""}
                              {item.authStatus === "DONE" ? "인증완료" : ""}
                            </>
                          ) : (
                            ""
                          )}
                        </div>
                      </div>
                      <div className="card-body">
                        <h5 className={cn(st.cardTitle, "card-title")}>
                          {item.title}
                        </h5>
                      </div>
                      <div className={st.cardEnd}>{item.createdDate}</div>
                    </button>
                    <VolunteerModal item={item} />
                  </div>
                );
              })
            ) : (
              <h4 className={st.comment}>봉사 활동이 없습니다.</h4>
            )}

            <SmallPaging
              page={page}
              setPage={setPage}
              totalPageNumber={totalPageNumber}
            />
          </div>
        </div>
      </div>
    </div>
  );
}
