import st from "../styles/profile.module.scss";
import cn from "classnames";
import { useEffect, useState } from "react";
import axios from "axios";
import { URL } from "public/config";
import { useNavigate } from "react-router-dom";
import Editor from "components/Editor";
export default function ProfileAdoption({ category, seq, isLogin }) {
  const [list, setList] = useState();
  const [page, setPage] = useState(1);
  const [total, setTotal] = useState([]);
  const size = 5;
  const jwt = sessionStorage.getItem("jwt");
  const navigator = useNavigate();

  useEffect(() => {
    if (isLogin) {
      axios
        .get(`${URL}/members/${seq}/${category}?page=${page}&size=${size}`, {
          headers: { Authorization: `Bearer ${jwt}` },
        })
        .then((res) => {
          const data = res.data.data;
          setTotal([data.totalCount, data.totalPageNumber]);
        })
        .catch((err) => console.log(err));
    }
  }, [page]);

  const onGoToDetail = (itemSeq) => {
    console.log(itemSeq);
    navigator(`/volunteer/detail/${itemSeq}`);
  };

  return (
    <div>
      <div name="글목록" className={st.listBox}>
        <div className={st.adopts}>
          <div className={st.btn}>
            <button
              type="button"
              className="btn"
              style={{ backgroundColor: "#d0a96c" }}
              data-bs-toggle="modal"
              data-bs-target="#exampleModal"
            >
              입양 인증
            </button>
            <div
              className="modal fade"
              id="exampleModal"
              tabindex="-1"
              aria-labelledby="adoptsCreate"
              aria-hidden="true"
            >
              <div className="modal-dialog modal-dialog-centered">
                <div className="modal-content">
                  <div className="modal-header">
                    <h5 className="modal-title" id="adoptsCreate">
                      입양완료 인증 요청
                    </h5>
                    <button
                      type="button"
                      className="btn-close"
                      data-bs-dismiss="modal"
                      aria-label="Close"
                    ></button>
                  </div>
                  <div className="modal-body row">
                    <div className="row">
                      <label htmlFor="아이이름">
                        <h4>아이 이름</h4>
                      </label>
                      <input id="아이이름" type="text" />
                    </div>
                    <div className="row">
                      <Editor height={"80%"} />
                    </div>
                  </div>
                  <div className="modal-footer">
                    <button
                      type="button"
                      className="btn btn-secondary"
                      data-bs-dismiss="modal"
                    >
                      Close
                    </button>
                    <button type="button" className="btn btn-primary">
                      신청
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div>
            <div name="임시">
              <div className={st.listItem} onClick={() => onGoToDetail(3)}>
                <div className="card-body">
                  <h5 className={cn(st.cardTitle, "card-title")}>제목이다</h5>
                </div>
                <div className={st.cardEnd}>작성일</div>
              </div>
            </div>
            {list ? (
              list.map((item) => {
                return (
                  <div
                    key={item.seq}
                    className={st.listItem}
                    onClick={() => onGoToDetail(item.seq)}
                  >
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
    </div>
  );
}
