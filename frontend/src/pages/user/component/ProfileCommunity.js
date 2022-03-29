import st from "../styles/profile.module.scss";
import cn from "classnames";
import { useEffect, useState } from "react";
import axios from "axios";
import { URL } from "public/config";
import { useNavigate } from "react-router-dom";

export default function ProfileList(props) {
  const [category, setCategory] = useState("community");
  const [list, setList] = useState();
  const [page, setPage] = useState(1);
  const [total, setTotal] = useState([]);
  const size = 5;
  const jwt = sessionStorage.getItem("jwt");
  const navigator = useNavigate();

  useEffect(() => {
    if (props.isLogin) {
      axios
        .get(
          `${URL}/members/${props.seq}/communities?page=${page}&size=${size}`,
          {
            headers: { Authorization: `Bearer ${jwt}` },
          }
        )
        .then((res) => {
          const data = res.data.data;
          setList(data.communities);
          setTotal([data.totalCount, data.totalPageNumber]);
        })
        .catch((err) => console.log(err));
    }
  }, [page]);

  const onGoToDetail = (itemSeq) => {
    console.log(itemSeq);
    navigator(`/community/communitydetail/${itemSeq}`);
  };

  return (
    <div>
      <div name="카테고리">
        <ul className={st.category}>
          <li
            className={category === "community" ? st.now : st.notnow}
            onClick={() => setCategory("community")}
          >
            커뮤니티 활동
          </li>
          <li
            className={category === "volunteer" ? st.now : st.notnow}
            onClick={() => setCategory("volunteer")}
          >
            봉사활동
          </li>
          <li
            className={category === "adoption" ? st.now : st.notnow}
            onClick={() => setCategory("adoption")}
          >
            입양활동
          </li>
        </ul>
      </div>
      <div name="글목록">
        <div className={st.listBox}>
          {list ? (
            list.map((item) => {
              return (
                <div
                  key={item.seq}
                  className={st.listItem}
                  onClick={() => onGoToDetail(item.seq)}
                >
                  <div className={st.itemCategory}>
                    <div>
                      {item.category === "GENERAL" ? "제너럴" : ""}
                      {item.category === "REVIEW" ? "후기" : ""}
                      {item.category === "REPORT" ? "제보?" : ""}
                    </div>
                  </div>
                  <div className="card-body">
                    <h5 className={cn(st.cardTitle, "card-title")}>
                      {item.title}
                    </h5>
                  </div>
                  <div className={st.cardEnd}>{item.createdDate}</div>
                </div>
              );
            })
          ) : (
            <h4 className={st.comment}>작성한 글이 없습니다.</h4>
          )}

          {/* <div name="페이저" className={st.pager}>
            <li>
              {page === 1 ? (
                <button href="#" disabled>
                  Previous
                </button>
              ) : (
                <button href="#" onClick={() => setPage(page - 1)}>
                  Previous
                </button>
              )}
            </li>
            <li>
              {list ? (
                <button href="#" onClick={() => setPage(page + 1)}>
                  Next
                </button>
              ) : (
                <button href="#" disabled>
                  Next
                </button>
              )}
            </li>
          </div> */}
        </div>
      </div>
    </div>
  );
}
