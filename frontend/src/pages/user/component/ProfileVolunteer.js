import VolunteerModal from "../component/ProfileVolunteerModal";
import st from "../styles/profile.module.scss";
import cn from "classnames";
import { useEffect, useState } from "react";
import axios from "axios";
import { URL } from "public/config";
import { useNavigate } from "react-router-dom";
import SmallPaging from "components/SmallPaging";
import { useSelector } from "react-redux";
import Editor from "components/Editor";

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
  const userSeq = useSelector((state) => state.userInfo.userInfo.seq);
  const [modal, setModal] = useState(false);
  const [modalData, setModalData] = useState({});
  const [content, setContent] = useState();

  // 에디터 부분 변경
  const onEditorChange = (value) => {
    setContent(value);
  };

  useEffect(() => {
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

  const onClickModal = (el) => {
    setModalData(el);
    setModal(true);
  };
  console.log("렌더링수");
  return (
    <div>
      <div className={st.listBox}>
        <div className={st.adopts}>
          <div name="글 목록" className={st.list}>
            {list ? (
              list.map((item) => {
                return (
                  <div key={item.title} className={st.listItemDiv}>
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
                    <div className={st.volunteer}>
                      <button
                        type="button"
                        className="btn"
                        style={{ backgroundColor: "#d0a96c" }}
                        data-bs-toggle="modal"
                        data-bs-target="#volunteerModal"
                        onClick={() => {
                          onClickModal(item);
                        }}
                      >
                        봉사 인증
                      </button>
                      {modal ? (
                        <div
                          className="modal fade"
                          id="volunteerModal"
                          tabIndex="-1"
                          aria-labelledby="volunteerModalLabel"
                          aria-hidden="true"
                        >
                          <div className="modal-dialog modal-dialog-centered">
                            <div className="modal-content">
                              <div className="modal-header">
                                <h5
                                  className="modal-title"
                                  id="volunteerModalLabel"
                                >
                                  봉사 인증 요청
                                </h5>
                                <button
                                  type="button"
                                  className="btn-close"
                                  data-bs-dismiss="modal"
                                  aria-label="Close"
                                ></button>
                              </div>
                              <div className={cn(`${st.body}`, "modal-body")}>
                                <div name="봉사제목" className={st.name}>
                                  <div className={st.label}>
                                    <label htmlFor="모집제목">
                                      <span>제목</span>
                                    </label>
                                  </div>
                                  <div className={st.input}>
                                    {modalData.title}
                                  </div>
                                </div>

                                <div name="인원관리" className={st.name}>
                                  <div className={st.label}>
                                    <label htmlFor="인원관리">
                                      <span>인원관리</span>
                                    </label>
                                  </div>
                                  <div className={st.input}>
                                    {modalData.volunteerSeq}
                                  </div>
                                </div>

                                <div name="내용" className={st.content}>
                                  <div className={st.label}>
                                    <label htmlFor="content">
                                      <span>내용</span>
                                    </label>
                                  </div>
                                  <div className={st.editor}>
                                    <Editor
                                      id="content"
                                      height={"90%"}
                                      value={content}
                                      onChange={onEditorChange}
                                      placeholder={""}
                                    />
                                  </div>
                                </div>
                              </div>
                              <div className="modal-footer">
                                <button
                                  type="button"
                                  className="btn btn-secondary"
                                  data-bs-dismiss="modal"
                                >
                                  취소
                                </button>
                                <button
                                  type="button"
                                  className="btn btn-primary"
                                  // onClick={onVolunAuth}
                                >
                                  작성
                                </button>
                              </div>
                            </div>
                          </div>
                        </div>
                      ) : (
                        ""
                      )}
                    </div>
                    {/* {parseInt(seq) === userSeq ? (
                      <VolunteerModal
                        volunteerSeq={item.volunteerSeq}
                        title={item.title}
                        seq={parasInt(seq)}
                        userSeq={userSeq}
                      />
                    ) : (
                      ""
                    )} */}
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
