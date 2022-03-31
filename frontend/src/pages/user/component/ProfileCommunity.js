import st from "../styles/profile.module.scss";
import cn from "classnames";
import { useEffect, useState } from "react";
import axios from "axios";
import { URL } from "public/config";
import { useNavigate } from "react-router-dom";
import SmallPaging from "components/SmallPaging";

export default function ProfileCommunity({ category, seq, isLogin }) {
  const [list, setList] = useState();
  //페이지
  const [page, setPage] = useState(1);
  const [totalPageNumber, setTotalPageNumber] = useState(1);
  const size = 4;
  //기타
  const jwt = sessionStorage.getItem("jwt");
  const navigator = useNavigate();

  const getData = () => {
    if (isLogin) {
      axios
        .get(`${URL}/members/${seq}/${category}?page=${page}&size=${size}`, {
          headers: { Authorization: `Bearer ${jwt}` },
        })
        .then((res) => {
          const data = res.data.data;
          setList(data.communities);
          setTotalPageNumber(res.data.data.totalPageNumber);
        })
        .catch((err) => console.log(err));
    }
  };
  useEffect(() => {
    getData();
  }, [page]);

  const onGoToDetail = (itemSeq) => {
    console.log(itemSeq);
    navigator(`/community/communitydetail/${itemSeq}`);
  };

  return (
    <div>
      <div name="글목록" className={st.listBox}>
        <div className={st.activity}>
          <div className={st.list}>
            {list ? (
              list.map((item) => {
                return (
                  <div key={item.seq} className={st.listItemDiv}>
                    <button
                      type="button"
                      style={{ margin: "10px auto" }}
                      className={cn("btn", `${st.listItem}`)}
                      onClick={() => onGoToDetail(item.seq)}
                    >
                      <div className={st.itemCategory}>
                        <div>
                          {item.category === "GENERAL" ? "잡담" : ""}
                          {item.category === "REVIEW" ? "후기" : ""}
                          {item.category === "REPORT" ? "제보" : ""}
                        </div>
                      </div>
                      <div className="card-body">
                        <h5 className={cn(st.cardTitle, "card-title")}>
                          {item.title}
                        </h5>
                      </div>
                      <div className={st.cardEnd}>{item.createdDate}</div>
                    </button>
                  </div>
                );
              })
            ) : (
              <h4 className={st.comment}>작성한 글이 없습니다.</h4>
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
