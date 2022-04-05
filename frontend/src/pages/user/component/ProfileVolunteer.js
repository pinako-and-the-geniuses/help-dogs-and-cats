// import VolunteerModal from "../component/ProfileVolunteerModal";
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
  // 기타
  const navigator = useNavigate();
  const jwt = sessionStorage.getItem("jwt");
  const userSeq = useSelector((state) => state.userInfo.userInfo.seq);
  const [modal, setModal] = useState(false);
  const [modalData, setModalData] = useState({});
  const [content, setContent] = useState();
  const [checkedInputs, setCheckedInputs] = useState("");
  const closeRef = useRef(null);

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
        })
        .catch((err) => console.log(err));
    }
  };

  useEffect(() => {
    getData();
  }, [page]);

  // 요청 완료되면 모달 자동으로 닫히게
  const onhandleClose = () => {
    closeRef.current.click();
    setCheckedInputs("");
    setContent("");
    console.log("닫혀라");
  };

  // 해당 모집 상세페이지로
  const onGoToDetail = (itemSeq) => {
    console.log(itemSeq);
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
          console.log("봉사인증요청성공", res);
          getData();
        })
        .catch((err) => {
          console.log(err);
          swal("서버에러", "요청에 실패했습니다.", "error");
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
    })
      .then((res) => {
        setContent(res.data.data.content);
        if (now !== "REJECT") {
          var temp = res.data.data.participants.filter(function (n) {
            if (n.approve) {
              return n;
            }
          });
          setCheckedInputs(temp);
        }
      })
      .then(console.log(content, "======", checkedInputs))
      .catch((err) => {
        console.log(err);
        swal("서버에러", "요청에 실패했습니다.", "error");
      });
  };

  // 모달에 데이터 보내기
  const onClickModal = (el) => {
    setContent("");
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
                return (
                  <div key={index} className="form-check form-check-inline">
                    <input
                      id={data.memberSeq}
                      type="checkbox"
                      style={{
                        width: "15px",
                      }}
                      onChange={(e) => {
                        changeHandler(e.currentTarget.checked, data.memberSeq);
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
    console.log(value);
    setContent(value);
  };

  return (
    <div>
      <div className={st.listBox}>
        <div className={st.activity}>
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
                                          // className={st.classEditor}
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
