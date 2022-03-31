import React, { useEffect, useState } from 'react';
import { 
    useNavigate, 
    useParams,
} from 'react-router-dom';
import axios from 'axios';
import Comment from 'components/Comment/Comment';
import TeamManage from './TeamManage';
import style from './styles/VolunteerDetail.module.scss';
import { URL } from '../../public/config';
import { useSelector } from 'react-redux';
import Swal from 'sweetalert2';

function VolunteerDetail(){
    const { id } = useParams();
    const jwt = sessionStorage.getItem('jwt');
    //회원번호 확인
    const memSeq = useSelector((state) => state.userInfo.userInfo.seq);
    const navigate = useNavigate();

    const [post, setPost] = useState([]);
    const [commentContent, setCommentContent] = useState("");
    const [changed, setChanged] = useState(true);
    const [comments, setComments] = useState([]);
    const [join, setJoin] = useState(false);
    const [dd, setDd] = useState(1); //test용
    const [volStatus, setVolStatus] = useState("");

    const today = new Date();
    const endDate = new Date(post.endDate);
    const gap = endDate.getTime() - today.getTime();
    const leftdays = Math.ceil(gap/(1000*60*60*24));

    const joinBtn=()=>{
        setJoin(!join);
    }

    const goToEdit=()=>{
        navigate(`/volunteer/update/${id}`)
    }

    //게시글 정보 가져오기
    const getPost=async()=>{
        await axios({
            url: `${URL}/volunteers/${id}`,
            method: "get",
            headers: {
                Authorization: `Bearer ${jwt}`,
            }
        })
        .then((res)=>{
            setPost(res.data.data);
            setComments(res.data.data.comments);
        })
        .catch((err) =>{
            console.log(err);
        })
    }

    //글 작성자: 모집 상태 변경
    const changeStatus=async()=>{
        await axios({
            url: `${URL}/volunteers/${id}/status`,
            method: "patch",
            data :{
                status: volStatus,
            },
            headers: {
                Authorization: `Bearer ${jwt}`,
            }
        })
        .then((res)=>{
            console.log('상태 수정 완료');
        })
        .catch((err)=>{
            console.log(err);
        })
    }

    //일반: 참여 신청
    const apply=async()=>{
        await axios({
            url: `${URL}/volunteers/${id}/apply`,
            method: "post",
            headers: {
                Authorization: `Bearer ${jwt}`,
            }
        })
        .then((res)=>{
            console.log('ok');
            setJoin(true);
        })
        .catch((err) =>{
            console.log(err);
        })
    }

    //댓글 작성
    const volComment=async()=>{
        await axios({
            url: `${URL}/volunteers/${id}/comments`,
            method: "post",
            data:{
                content: commentContent,
                parentSeq: null,
            },
            headers: {
                Authorization: `Bearer ${jwt}`,
            }
        })
        .then((res)=>{
            // console.log('댓글달기성공');
            setChanged(!changed);
            setCommentContent("");
        })
        .catch((err)=>{
            console.log(err);
        })
    }

    const test=async()=>{
        await axios({
            url: `${URL}/volunteers/${id}/participants/${memSeq}`,
            method: "patch",
            data: {
            approve: true,
            },
            headers: {
            Authorization: `Bearer ${jwt}`,
        }
        })
        .then((res) =>{
            console.log('test성공');
        })
        .catch((err)=>{
            console.log(err);
        })
    }   
        
    //게시글 삭제
    const deletePost=async()=>{
        console.log('삭제');
        await axios({
            url: `${URL}/volunteers/${id}`,
            method: "delete",
            headers: { Authorization: `Bearer ${jwt}`}
        })
        .then((res)=>{
            navigate('/volunteer/list');
        })
        .catch((err)=>{
            console.log(err);
        })
    }

    const deleteHandler=()=>{
        Swal.fire({
            title: "글을 삭제하시겠습니까?",
            text: "삭제한 글은 되돌릴 수 없습니다",
            icon: "warning",
            confirmButtonColor: `#b59d7c`,
            cancelButtonColor: '#999999',
            showCancelButton: true,
        })
        .then((res)=>{
            if(res.isConfirmed)
                deletePost();
        })
    }
    
    const onCommentChange = (value) => {
        setCommentContent(value);
    };

    const onClickEvent=()=>{
        volComment()
        .then(getPost());
    }

    const onStatusHandler=()=>{
        if(volStatus === "ONGOING"){
            setVolStatus("RECRUITING").then(changeStatus());
            console.log('changed', volStatus);
        }
        if(volStatus === "RECRUTING"){
            setVolStatus("ONGOING").then(changeStatus());
            console.log('changed', volStatus);
        }
    }

    useEffect(()=>{
        getPost();
    }, [changed]);

    useEffect(()=>{
        console.log(post);
        console.log(comments);
    }, [post, changed]);

    //수정할 것! 제대로 안됨
    // useEffect(()=> {
    //     getPost().then(console.log(post));
    // }, [changed]);

    // useEffect(()=>{
    //     console.log(post);
    //     // console.log(comments);
    // }, [changed]);

    return(
        <div className={style.myContainer}>
            <h1>봉사활동</h1>

            <div className={style.titleBox}>
                {
                    leftdays >= 0
                    ? <p className={style.leftdays}>D-{leftdays}</p>
                    : <p className={style.leftdays}>마감</p>
                }
                {/* <p className={style.leftdays}>D-{leftdays}</p> */}
                {/* 날짜 0일 내려가면 마감 뜸 */}
                {/* 마감돼도 마감 뜸 */}
                <p className={style.title}>{post.title}</p>
                {
                    memSeq === post.writerSeq
                    ?{
                        ONGOING : <button type='button' onClick={()=>{setVolStatus(post.status);onStatusHandler()}} className={`${style.joinBtn} ${style.joinXBtn}`}>모집시작</button>,
                        RECRUITING : <button type='button' onClick={()=>{setVolStatus(post.status);onStatusHandler()}} className={style.joinBtn}>모집마감</button>
                    }[post.status]
                    :(
                        join
                        ? <button type='button' onClick={joinBtn} className={`${style.joinBtn} ${style.joinXBtn}`}>참여취소</button>
                        :<button type='button' onClick={apply} className={style.joinBtn}>참여신청</button>
                    )
                }
                {/* {
                    memSeq === post.writerSeq
                    ?(
                        //모집 변수
                        join
                        ? <button type='button' onClick={joinBtn} className={`${style.joinBtn} ${style.joinXBtn}`}>모집시작</button>
                        :<button type='button' onClick={changeStatus} className={style.joinBtn}>모집마감</button>
                    )
                    :(
                        join
                        ? <button type='button' onClick={joinBtn} className={`${style.joinBtn} ${style.joinXBtn}`}>참여취소</button>
                        :<button type='button' onClick={apply} className={style.joinBtn}>참여신청</button>
                    )
                } */}
            </div>

            <div className={style.infoBox}>
                <ul>
                    <li className={style.first}>
                        <p>활동 지역: {post.activityArea}</p>
                        <p>마감 날짜: {post.endDate}</p>
                    </li>
                    <li>
                        인증 봉사시간 : {
                            post.authTime !== '0'
                            ? <span>{post.authTime}시간</span>
                            : <span>인증 불가</span> //멘트....
                        }
                    </li>
                    <li className={style.people}>
                        <p>모집 인원: {post.approvedCount}명 / {post.maxParticipantCount}명</p>
                        <button type="button" className={style.btn1} data-bs-toggle="modal" data-bs-target="#teamManagement">인원관리</button>
                        {/* 모달 */}
                        <div className="modal fade" id="teamManagement" tabIndex="-1" aria-labelledby="teamModalLabel" aria-hidden="true">
                            <div className="modal-dialog">
                                <div className="modal-content">
                                    <div className="modal-header">
                                        <h5 className="modal-title" id="exampleModalLabel">봉사활동 인원 관리</h5>
                                        <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div className="modal-body">
                                        <TeamManage />
                                    </div>
                                </div>
                            </div>
                        </div>
                        {/* 모달 */}
                    </li>
                    <li className={style.last}>
                        <p className={style.contact}>연락방법: {post.contact}</p>
                        <p className={style.writer}>작성자&nbsp;&nbsp;{post.writerNickname}</p>
                    </li>
                </ul>
            </div>

            <div className={style.mainBox}>
                <div dangerouslySetInnerHTML={{__html:post.content}}></div>
            </div>
            
            {
                memSeq===post.writerSeq
                ?(
                    <div className={style.editPost}>
                    <p onClick={goToEdit}>수정</p>
                    <p onClick={deleteHandler}>삭제</p>
                </div>
                )
                : null
            }
            <br/>

            <Comment 
                id={id} 
                value={commentContent}
                onChange={onCommentChange}
                eventHandler={onClickEvent}
                comments={comments}
                />
            <button 
                className={style.listBtn}
                onClick={()=>{navigate(-1)}}>목록</button>

                <button onClick={test}>patchTest</button>
        </div>
    )
}

export default VolunteerDetail;