import st from "../styles/profile.module.scss";
// import cn from "classnames";
import { useState } from "react";
export default function ProfileList() {
  const [page, setPage] = useState("0");
  const [list, setList] = useState();

  const onPage = (e) => {
    setPage(e.target.value);
  };

  return (
    <div>
      <div>
        <ul className={st.category}>
          <li value="0" className={page == "0" ? st.now : ""} onClick={onPage}>
            커뮤니티 활동
          </li>
          <li value="1" className={page == "1" ? st.now : ""} onClick={onPage}>
            봉사활동
          </li>
          <li value="2" className={page == "2" ? st.now : ""} onClick={onPage}>
            입양활동
          </li>
        </ul>
      </div>
      <div name="글목록">
        {list ? (
          list.map(() => {})
        ) : (
          <h4 className={st.comment}>작성한 글이 없습니다.</h4>
        )}
      </div>

      <button
        type="button"
        className="btn btn-primary"
        data-bs-toggle="modal"
        data-bs-target="#exampleModal"
      >
        Launch demo modal
      </button>

      <div
        className="modal fade"
        id="exampleModal"
        tabIndex="-1"
        aria-labelledby="exampleModalLabel"
        aria-hidden="true"
      >
        <div className="modal-dialog">
          <div className="modal-content">
            <div className="modal-header">
              <h5 className="modal-title" id="exampleModalLabel">
                Modal title
              </h5>
              <button
                type="button"
                className="btn-close"
                data-bs-dismiss="modal"
                aria-label="Close"
              ></button>
            </div>
            <div className="modal-body">...</div>
            <div className="modal-footer">
              <button
                type="button"
                className="btn btn-secondary"
                data-bs-dismiss="modal"
              >
                Close
              </button>
              <button type="button" className="btn btn-primary">
                Save changes
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
