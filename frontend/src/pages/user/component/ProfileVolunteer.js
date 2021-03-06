import st from "../styles/profile.module.scss";
import cn from "classnames";
import { useEffect, useState, useRef } from "react";
import axios from "axios";
import { URL } from "public/config";
import { useNavigate } from "react-router-dom";
import SmallPaging from "components/SmallPaging";
import { useSelector } from "react-redux";
import Editor from "components/Editor";
import swal from "sweetalert";

export default function ProfileVolunteer({ category, seq, isLogin }) {
  // 활동 목록
  const [list, setList] = useState("");
  //페이지네이션
  const [page, setPage] = useState(1);
  const [totalPageNumber, setTotalPageNumber] = useState(1);
  const size = 4;
  // 모달 정보
  const [modal, setModal] = useState(false);
  const [modalData, setModalData] = useState({});
  const [content, setContent] = useState();
  const [checkedInputs, setCheckedInputs] = useState("");
  // 로그인 관련 정보
  const jwt = sessionStorage.getItem("jwt");
  const userSeq = useSelector((state) => state.userInfo.userInfo.seq);
  // 기능
  const closeRef = useRef(null);
  const hereRef = useRef(null);

  const navigator = useNavigate();

  const getData = () => {
    if (isLogin) {
      axios
        .get(`${URL}/members/${seq}/${category}?page=${page}&size=${size}`, {
          headers: { Authorization: `Bearer ${jwt}` },
        })
        .then((res) => {
          const data = res.data.data;
          setList(data.volunteers);
          setTotalPageNumber(data.totalPageNumber);
        });
    }
  };

  useEffect(() => {
    getData();
  }, [page, seq]);

  // 요청 완료되면 모달 자동으로 닫히게
  const onhandleClose = () => {
    closeRef.current.click();
    hereRef.current.click();
  };

  // 해당 모집 상세페이지로
  const onGoToDetail = (itemSeq) => {
    navigator(`/volunteer/detail/${itemSeq}`);
  };

  // 인원관리
  const changeHandler = (checked, id) => {
    if (checked) {
      setCheckedInputs([...checkedInputs, id]);
    } else {
      // 체크 해제
      setCheckedInputs(checkedInputs.filter((el) => el !== id));
    }
  };

  // 인증 요청
  const onVolunAuth = (props) => {
    const method = props[0];
    const volunteerSeq = props[1];
    if (checkedInputs.length < 1) {
      swal("인원 필수", "인원을 선택하세요.", "info");
    } else if (content.length < 10) {
      swal("내용 부족", "내용을 확인하세요.", "info");
    } else {
      axios({
        url: `${URL}/volunteers/${volunteerSeq}/auth`,
        method: method,
        headers: { Authorization: `Bearer ${jwt}` },
        data: {
          content: content,
          authenticatedParticipantSequences: checkedInputs,
        },
      })
        .then((res) => {
          onhandleClose();
          setContent("");
          getData();
        })
        .then(() => swal("요청완료", "", "success"))
        .catch((err) => {
          if (err.response.status) {
            if (err.response.status === 400) {
              onhandleClose();
              swal("중복에러", "이미 요청된 글입니다.", "error");
            }
          }
        });
    }
  };
  // 인증요청서 내용 가져오기
  const onGetModalData = (props) => {
    const now = props[0];
    const volunteerSeq = props[1];
    axios({
      url: `${URL}/volunteers/${volunteerSeq}/auth`,
      method: "GET",
      headers: { Authorization: `Bearer ${jwt}` },
    }).then((res) => {
      setContent(res.data.data.content);
      if (now !== "REJECT") {
        var temp = res.data.data.participants.filter(function (n) {
          if (n.approve) {
            return n;
          }
        });
        setCheckedInputs(temp);
      }
    });
  };

  // 모달에 데이터 보내기
  const onClickModal = (el) => {
    setCheckedInputs("");
    setModalData(el);
    setModal(true);
  };

  // 인증요청 버튼
  const onModal = (item) => {
    // 처음 인증 신청할때
    if (item.authStatus === null) {
      return (
        <>
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
            인증신청
          </button>
        </>
      );
    } else if (item.authStatus === "REJECT") {
      return (
        <>
          <button
            type="button"
            className="btn"
            style={{ backgroundColor: "#d0a96c" }}
            data-bs-toggle="modal"
            data-bs-target="#volunteerModal"
            onClick={() => {
              onClickModal(item);
              onGetModalData(["REJECT", item.volunteerSeq]);
            }}
          >
            신청거절
          </button>
        </>
      );
    } else {
      return (
        <>
          <button
            type="button"
            className="btn"
            style={{ backgroundColor: "#d0a96c" }}
            data-bs-toggle="modal"
            data-bs-target="#volunteerModal"
            onClick={() => {
              onClickModal(item);
              onGetModalData(["etc", item.volunteerSeq]);
            }}
          >
            {item.authStatus === "REQUEST" ? "신청완료" : ""}
            {item.authStatus === "DONE" ? "인증완료" : ""}
          </button>
        </>
      );
    }
  };

  // 모달 하단 버튼(수정/ 등록/ 닫기/ 취소)
  const onBottomBtn = (item) => {
    if (item === null) {
      return (
        <>
          <button
            type="button"
            className="btn btn-secondary"
            data-bs-dismiss="modal"
            onClick={() => {
              setContent("");
              setCheckedInputs([]);
            }}
          >
            취소
          </button>
          <button
            type="button"
            className="btn btn-primary"
            value={"POST"}
            onClick={(e) => {
              onVolunAuth([e.target.value, modalData.volunteerSeq]);
            }}
          >
            작성
          </button>
        </>
      );
    } else if (item === "REJECT") {
      return (
        <>
          <button
            type="button"
            className="btn btn-secondary"
            data-bs-dismiss="modal"
            onClick={() => {
              setContent("");
              setCheckedInputs([]);
            }}
          >
            취소
          </button>
          <button
            type="button"
            className="btn btn-primary"
            value={"PUT"}
            onClick={(e) => {
              onVolunAuth([e.target.value, modalData.volunteerSeq]);
            }}
          >
            수정
          </button>
        </>
      );
    } else {
      return (
        <button
          type="button"
          className="btn btn-secondary"
          data-bs-dismiss="modal"
          onClick={() => {
            setContent("");
            setCheckedInputs([]);
          }}
        >
          닫기
        </button>
      );
    }
  };

  // 인원관리
  const onMemberCheck = () => {
    // 처음 신청 할때 또는 수정할때
    if (modalData.authStatus === null || modalData.authStatus === "REJECT") {
      return (
        <div name="인원유무에따라" className={st.input}>
          {modalData.participantInfos
            ? modalData.participantInfos.map((data, index) => {
                if (data.approve) {
                  return (
                    <div key={index} className="form-check form-check-inline">
                      <input
                        id={data.memberSeq}
                        type="checkbox"
                        style={{
                          width: "15px",
                        }}
                        onChange={(e) => {
                          changeHandler(
                            e.currentTarget.checked,
                            data.memberSeq
                          );
                        }}
                        checked={
                          checkedInputs.includes(data.memberSeq) ? true : false
                        }
                      />

                      <label className="form-check-label" htmlFor="memberCheck">
                        {data.memberNickname}
                      </label>
                    </div>
                  );
                }
              })
            : "멤버없음"}
        </div>
      );
    } else {
      return (
        <div name="최종참가인원" className={st.input}>
          {checkedInputs.length >= 1
            ? checkedInputs.map((mem) => {
                return (
                  <>
                    <span key={mem.nickname} className="me-2">
                      {mem.nickname} /
                    </span>
                  </>
                );
              })
            : "없음"}
        </div>
      );
    }
  };

  // 에디터 부분 변경
  const onEditorChange = (value) => {
    setContent(value);
  };

  return (
    <div>
      <div className={st.listBox} ref={hereRef}>
        <div className={st.activity}>
          <div name="글 목록" className={st.list}>
            <div></div>
            {list ? (
              list.map((item, index) => {
                return (
                  <div key={index} className={st.listItemDiv}>
                    <button
                      type="button"
                      className={cn("btn", `${st.listItem}`)}
                      onClick={() => onGoToDetail(item.volunteerSeq)}
                    >
                      <div className={st.itemCategory}>
                        <div>
                          {item.volunteerStatus === "ONGOING" ? "진행중" : ""}
                          {item.volunteerStatus === "DONE" ? "모집완료" : ""}
                          {item.volunteerStatus === "RECRUITING"
                            ? "모집중"
                            : ""}
                        </div>
                      </div>
                      <div className="card-body">
                        <h5 className={cn(st.cardTitle, "card-title")}>
                          {item.title}
                        </h5>
                      </div>
                      <div className={st.cardEnd}>{item.createdDate}</div>
                    </button>
                    {parseInt(seq) === userSeq ? (
                      <>
                        <div className={st.volunteer}>
                          {item.memberSeq === userSeq ? (
                            <>{onModal(item)}</>
                          ) : (
                            <button
                              type="button"
                              className="btn"
                              style={{ backgroundColor: "#d0a96c" }}
                            >
                              &nbsp; 참여자 &nbsp;
                            </button>
                          )}

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
                                    봉사 인증 요청서
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
                                        <span>참가인원</span>
                                      </label>
                                    </div>
                                    {onMemberCheck()}
                                  </div>

                                  <div name="내용" className={st.content}>
                                    <div className={st.label}>
                                      <label htmlFor="content">
                                        <span>내용</span>
                                      </label>
                                    </div>
                                    <div className={st.editor}>
                                      {modalData.authStatus === null ||
                                      modalData.authStatus === "REJECT" ? (
                                        <Editor
                                          id="content"
                                          height={"83%"}
                                          value={content || ""}
                                          onChange={onEditorChange}
                                          placeholder={""}
                                        />
                                      ) : (
                                        <div
                                          className={st.htmlDiv}
                                          dangerouslySetInnerHTML={{
                                            __html: content,
                                          }}
                                        ></div>
                                      )}
                                    </div>
                                  </div>
                                </div>

                                <div name="하단버튼" className="modal-footer">
                                  {onBottomBtn(modalData.authStatus)}
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                      </>
                    ) : (
                      ""
                    )}
                  </div>
                );
              })
            ) : (
              <h4 className={st.comment}>봉사 활동이 없습니다.</h4>
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
