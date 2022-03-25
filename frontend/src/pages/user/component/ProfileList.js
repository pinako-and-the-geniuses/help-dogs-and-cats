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
    <>
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
    </>
  );
}
