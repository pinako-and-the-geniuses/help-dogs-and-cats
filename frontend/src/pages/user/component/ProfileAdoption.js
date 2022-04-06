import SmallPaging from "components/SmallPaging";
import st from "../styles/profile.module.scss";
import cn from "classnames";
import { useEffect, useState, useRef } from "react";
import axios from "axios";
import { URL } from "public/config";
import Editor from "components/Editor";
import { useSelector } from "react-redux";
import swal from "sweetalert";
export default function ProfileAdoption({ category, seq, isLogin }) {
  const [detail, setDetail] = useState({
    title: "",
    content: "",
  });
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [list, setList] = useState({
    adopts: "",
    currentPageNumber: "",
    totalCount: "",
    totalPageNumber: "",
  });
  //페이지네이션
  const [page, setPage] = useState(1);
  const [totalPageNumber, setTotalPageNumber] = useState(1);
  const size = 4;
  // 기타
  const jwt = sessionStorage.getItem("jwt");
  const closeRef = useRef(null);
  const userSeq = useSelector((state) => state.userInfo.userInfo.seq);
  const getData = () => {
    if (isLogin) {
      axios
        .get(`${URL}/members/${seq}/${category}?page=${page}&size=${size}`, {
          headers: { Authorization: `Bearer ${jwt}` },
        })
        .then((res) => {
          const { adopts, currentPageNumber, totalCount, totalPageNumber } =
            res.data.data;
          setList({
            ...list,
            adopts,
            currentPageNumber,
            totalCount,
            totalPageNumber,
          });
          setTotalPageNumber(res.data.data.totalPageNumber);
        })
        .catch((err) => console.log(err));
    }
  };
  useEffect(() => {
    getData();
  }, [page, seq]);

  //해당 내용 클릭시 본인만 상세 내용 볼 수 있음
  const onGoToDetail = (itemSeq) => {
    if (parseInt(seq) === userSeq) {
      axios
        .get(`${URL}/adopts/auth/${itemSeq}`, {
          headers: { Authorization: `Bearer ${jwt}` },
        })
        .then((res) => {
          const { title, content } = res.data.data;

          setDetail({ ...detail, title, content });
          console.log(detail);
        })
        .catch((err) => {
          console.log(err);
        });
    } else {
      swal("권한없음", "접근 권한이 없습니다.", "error");
    }
  };

  // 요청 완료되면 모달 자동으로 닫히게
  const onhandleClose = () => {
    closeRef.current.click();
  };

  // 입양 요청서 서버에 보내기
  const onAdoptAuth = () => {
    axios({
      url: `${URL}/adopts/auth`,
      method: "POST",
      headers: { Authorization: `Bearer ${jwt}` },
      data: {
        title: title,
        content: content,
      },
    })
      .then((res) => {
        console.log(res);
        onhandleClose();
        swal("요청성공", "관리자의 인증을 기다려주세요", "success");
        setTitle("");
        setContent("");
        getData();
      })
      .catch((err) => {
        console.log(err);
        swal("서버에러", "요청에 실패했습니다.", "error");
      });
  };
  // 에디터 부분 변경
  const onEditorChange = (value) => {
    setContent(value);
  };
  return (
    <div>
      <div className={st.listBox}>
        <div className={st.activity}>
          <div name="입양인증요청" className={st.btn}>
            {/* 본인일때만 요청서 작성 가능 */}
            {parseInt(seq) === userSeq ? (
              <>
                <button
                  type="button"
                  className="btn"
                  style={{ backgroundColor: "#d0a96c" }}
                  data-bs-toggle="modal"
                  data-bs-target="#adoptsCreate"
                >
                  입양 인증
                </button>
                <div
                  className="modal fade"
                  id="adoptsCreate"
                  tabIndex="-1"
                  aria-labelledby="adoptsCreate"
                  aria-hidden="true"
                >
                  <div className="modal-dialog modal-dialog-centered">
                    <div className="modal-content">
                      <div className="modal-header">
                        <h5 className="modal-title" id="adoptsCreate">
                          입양완료 인증 요청서
                        </h5>
                        <button
                          ref={closeRef}
                          type="button"
                          className="btn-close"
                          data-bs-dismiss="modal"
                          aria-label="Close"
                        ></button>
                      </div>
                      <div className={cn(`${st.body}`, "modal-body")}>
                        <div className={st.name}>
                          <div className={st.label}>
                            <label htmlFor="아이이름">
                              <span>동물 이름</span>
                            </label>
                          </div>

                          <div className={st.input}>
                            <input
                              id="아이이름"
                              type="text"
                              value={title}
                              onChange={(e) => setTitle(e.target.value)}
                            />
                          </div>
                        </div>
                        <div className={st.content}>
                          <div className={st.label}>
                            <label htmlFor="content">
                              <span>내용</span>
                            </label>
                          </div>
                          <div className={st.editor}>
                            <Editor
                              id="content"
                              height={"83%"}
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
                          onClick={onAdoptAuth}
                        >
                          작성
                        </button>
                      </div>
                    </div>
                  </div>
                </div>
              </>
            ) : (
              "<입양활동 내역은 개인적인 정보가 포함되므로 자세한 내용을 보실 수 없습니다.> "
            )}
          </div>
          <div name="글 목록" className={st.list}>
            {list.adopts ? (
              list.adopts.map((item) => {
                return (
                  <div key={item.seq} className={st.listItemDiv}>
                    {parseInt(seq) !== userSeq ? (
                      <button
                        type="button"
                        className={cn("btn", `${st.listItem}`)}
                        style={{ margin: "10px auto" }}
                        onClick={() => onGoToDetail(item.seq)}
                      >
                        <div className={st.itemCategory}>
                          <div>
                            {item.status === "REQUEST" ? "요청중" : ""}
                            {item.status === "DONE" ? "인증완료" : ""}
                            {item.status === "REJECT" ? "거절" : ""}
                          </div>
                        </div>
                        <div className="card-body">
                          <h5 className={cn(st.cardTitle, "card-title")}>
                            {item.title}
                          </h5>
                        </div>
                        <div className={st.cardEnd}>{item.createdDate}</div>
                      </button>
                    ) : (
                      <>
                        <button
                          type="button"
                          className={cn("btn", `${st.listItem}`)}
                          style={{ margin: "10px auto" }}
                          onClick={() => onGoToDetail(item.seq)}
                          data-bs-toggle="modal"
                          data-bs-target="#detailModal"
                        >
                          <div className={st.itemCategory}>
                            <div>
                              {item.status === "REQUEST" ? "요청중" : ""}
                              {item.status === "DONE" ? "인증완료" : ""}
                              {item.status === "REJECT" ? "거절" : ""}
                            </div>
                          </div>
                          <div className="card-body">
                            <h5 className={cn(st.cardTitle, "card-title")}>
                              {item.title}
                            </h5>
                          </div>
                          <div className={st.cardEnd}>{item.createdDate}</div>
                        </button>
                        {/* 모달창 */}
                        <div
                          className="modal fade"
                          id="detailModal"
                          tabIndex="-1"
                          aria-labelledby="detailModal"
                          aria-hidden="true"
                        >
                          <div className="modal-dialog modal-dialog-centered">
                            <div className="modal-content">
                              <div className="modal-header">
                                <h5 className="modal-title" id="detailModal">
                                  입양완료 인증 요청서
                                </h5>
                                <button
                                  type="button"
                                  className="btn-close"
                                  data-bs-dismiss="modal"
                                  aria-label="Close"
                                  onClick={() => setDetail("")}
                                ></button>
                              </div>
                              <div className={cn(`${st.body}`, "modal-body")}>
                                <div className={st.name}>
                                  <div className={st.label}>
                                    <label htmlFor="아이이름">
                                      <span>동물 이름</span>
                                    </label>
                                  </div>

                                  <div className={st.input}>{detail.title}</div>
                                </div>
                                <div className={st.content}>
                                  <div className={st.label}>
                                    <label htmlFor="content">
                                      <span>내용</span>
                                    </label>
                                  </div>
                                  <div className={st.editor}>
                                    <div
                                      className={st.htmlDiv}
                                      dangerouslySetInnerHTML={{
                                        __html: detail.content,
                                      }}
                                    ></div>
                                  </div>
                                </div>
                              </div>
                              <div className="modal-footer">
                                <button
                                  type="button"
                                  className="btn btn-secondary"
                                  data-bs-dismiss="modal"
                                  onClick={() => setDetail("")}
                                >
                                  닫기
                                </button>
                              </div>
                            </div>
                          </div>
                        </div>{" "}
                      </>
                    )}
                  </div>
                );
              })
            ) : (
              <h4 className={st.comment}>입양 활동이 없습니다.</h4>
            )}
          </div>
        </div>
        <div className={st.paging}>
          <SmallPaging
            page={page}
            setPage={setPage}
            totalPageNumber={totalPageNumber}
          />
        </div>
      </div>
    </div>
  );
}
