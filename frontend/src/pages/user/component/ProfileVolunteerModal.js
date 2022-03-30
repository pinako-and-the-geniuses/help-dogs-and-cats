// import st from "../styles/profile.module.scss";
// import cn from "classnames";
// import { useEffect, useState, useRef } from "react";
// import axios from "axios";
// import { URL } from "public/config";
// import { useSelector } from "react-redux";
// import { useNavigate } from "react-router-dom";
// import Editor from "components/Editor";
// import { get } from "pages/community/uploadRouter";

// export default function ProfileVolunteer({ category, seq, isLogin }) {
//   const [list, setList] = useState("");
//   const [page, setPage] = useState(1);
//   const [getData, setGetData] = useState({
//     title: "",
//     volunteerSeq: "",
//   });
//   const [content, setContent] = useState("");
//   // const [total, setTotal] = useState([]);
//   const size = 5;
//   const jwt = sessionStorage.getItem("jwt");
//   const navigator = useNavigate();
//   const userSeq = useSelector((state) => state.userInfo.userInfo.seq);
//   const closeRef = useRef(null);

//   // 요청 완료되면 모달 자동으로 닫히게
//   const onhandleClose = () => {
//     closeRef.current.click();
//   };

//   // 인증 요청 보내기
//   const onVolunteerAuth = (volunteerSeq) => {
//     console.log(volunteerSeq);
//     axios({
//       url: `${URL}/voluteers/${volunteerSeq}/auth`,
//       method: "PUT",
//       headers: { Authorization: `Bearer ${jwt}` },
//       data: {
//         content: content,
//         authenticatedMemberSequences: [0],
//       },
//     })
//       .then((res) => {
//         console.log(res);
//         onhandleClose();
//         alert("인증 요청 완료");
//       })
//       .catch((err) => {
//         console.log(err);
//         alert("요청에 실패했습니다.");
//       });
//   };

//   const onGetMembers = (volunteerSeq) => {
//     if (volunteerSeq) {
//       axios
//         .get(`${URL}/volunteers/${volunteerSeq}/participants`, {
//           headers: { Authorization: `Bearer ${jwt}` },
//         })
//         .then((res) => {
//           console.log("getMemebers", res.data.data);
//         })
//         .catch((err) => {
//           console.log(err);
//         });
//     }
//   };

//   // 인증 모달 출력
//   const onBtnOpen = (item) => {
//     const volunteerSeq = item.volunteerSeq;
//     const title = item.title;
//     setModal(true);
//     setGetData({
//       ...getData,
//       title,
//       volunteerSeq,
//     });
//     // onGetMembers(volunteerSeq);
//   };

//   return (
//     <div>
//       <div className={st.listBox}>
//         <div className={st.adopts}>
//           <div name="글 목록" className={st.list}>
//             {list ? (
//               list.map((item) => {
//                 return (
//                   <div key={item.volunteerSeq} className={st.listItemDiv}>
//                     <button
//                       type="button"
//                       className={cn("btn", `${st.listItem}`)}
//                       onClick={() => onGoToDetail(item.seq)}
//                       data-bs-toggle="modal"
//                       data-bs-target="#detailModal"
//                     >
//                       <div className={st.itemCategory}>
//                         <div>
//                           {item.volunteerStatus === "RECRUITING"
//                             ? "모집중"
//                             : ""}
//                           {item.volunteerStatus === "ONGOING" ? "진행중" : ""}
//                           {item.volunteerStatus === "DONE" ? "모집완료" : ""}
//                           {item.volunteerStatus === "DONE" &&
//                           item.authStatus ? (
//                             <>
//                               {item.authStatus === "REQUEST" ? "인증대기" : ""}
//                               {item.authStatus === "REJECT" ? "인증거절" : ""}
//                               {item.authStatus === "DONE" ? "인증완료" : ""}
//                             </>
//                           ) : (
//                             ""
//                           )}
//                         </div>
//                       </div>
//                       <div className="card-body">
//                         <h5 className={cn(st.cardTitle, "card-title")}>
//                           {item.title}
//                         </h5>
//                       </div>
//                       <div className={st.cardEnd}>{item.createdDate}</div>
//                     </button>
//                     {/* 본인만 보이는 인증요청버튼  */}
//                     {parseInt(item.memberSeq) == userSeq ? (
//                       <>
//                         <button
//                           type="button"
//                           className="btn"
//                           style={{ backgroundColor: "#d0a96c" }}
//                           onClick={() => onBtnOpen(item)}
//                           data-bs-toggle="modal"
//                           data-bs-target="#volunteerModal"
//                         >
//                           봉사 인증
//                         </button>
//                         {getData ? (
//                           <>
//                             <div
//                               className="modal fade"
//                               id="volunteerModal"
//                               tabindex="-1"
//                               aria-labelledby="volunteerModalLabel"
//                               aria-hidden="true"
//                             >
//                               <div className="modal-dialog modal-dialog-centered">
//                                 <div className="modal-content">
//                                   <div className="modal-header">
//                                     <h5
//                                       className="modal-title"
//                                       id="volunteerModalLabel"
//                                     >
//                                       봉사 인증 요청
//                                     </h5>
//                                     <button
//                                       type="button"
//                                       className="btn-close"
//                                       data-bs-dismiss="modal"
//                                       aria-label="Close"
//                                       onClick={() => setModal(false)}
//                                     ></button>
//                                   </div>
//                                   <div
//                                     className={cn(`${st.body}`, "modal-body")}
//                                   >
//                                     <div name="봉사제목" className={st.name}>
//                                       <div className={st.label}>
//                                         <label htmlFor="모집제목">
//                                           <span>제목</span>
//                                         </label>
//                                       </div>
//                                       <div className={st.input}>
//                                         {getData.title}
//                                       </div>
//                                     </div>

//                                     <div name="인원관리" className={st.name}>
//                                       <div className={st.label}>
//                                         <label htmlFor="인원관리">
//                                           <span>인원관리</span>
//                                         </label>
//                                       </div>
//                                       <div className={st.input}>
//                                         {getData.title}
//                                       </div>
//                                     </div>

//                                     <div name="내용" className={st.content}>
//                                       <div className={st.label}>
//                                         <label htmlFor="content">
//                                           <span>내용</span>
//                                         </label>
//                                       </div>
//                                       <div className={st.editor}>
//                                         <Editor
//                                           id="content"
//                                           height={"90%"}
//                                           value={content}
//                                           setValue={setContent}
//                                           placeholder={""}
//                                         />
//                                       </div>
//                                     </div>
//                                   </div>
//                                   <div className="modal-footer">
//                                     <button
//                                       type="button"
//                                       className="btn btn-secondary"
//                                       data-bs-dismiss="modal"
//                                     >
//                                       취소
//                                     </button>
//                                     <button
//                                       type="button"
//                                       className="btn btn-primary"
//                                       // onClick={onAdoptAuth}
//                                     >
//                                       작성
//                                     </button>
//                                   </div>
//                                 </div>
//                               </div>
//                             </div>
//                           </>
//                         ) : (
//                           "Data없음"
//                         )}
//                       </>
//                     ) : (
//                       <>
//                         <button type="button" className="btn">
//                           참여중
//                         </button>
//                       </>
//                     )}
//                   </div>
//                 );
//               })
//             ) : (
//               <h4 className={st.comment}>봉사 활동이 없습니다.</h4>
//             )}

//             <div name="페이저" className={st.pager}>
//               <li>
//                 {page === 1 ? (
//                   <button href="#" disabled>
//                     Previous
//                   </button>
//                 ) : (
//                   <button href="#" onClick={() => setPage(page - 1)}>
//                     Previous
//                   </button>
//                 )}
//               </li>
//               <li>
//                 {list ? (
//                   <button href="#" onClick={() => setPage(page + 1)}>
//                     Next
//                   </button>
//                 ) : (
//                   <button href="#" disabled>
//                     Next
//                   </button>
//                 )}
//               </li>
//             </div>
//           </div>
//         </div>
//       </div>
//     </div>
//   );
// }
