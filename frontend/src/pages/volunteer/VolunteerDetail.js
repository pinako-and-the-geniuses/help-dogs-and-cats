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
    const [join, setJoin] = useState(false);

    const today = new Date();
    const endDate = new Date(post.endDate);
    const gap = endDate.getTime() - today.getTime();
    const leftdays = Math.ceil(gap/(1000*60*60*24));

    const joinBtn=()=>{
        setJoin(!join);
    }

    const goToEdit=()=>{
        navigate(`/volunteer/write/${id}`)
    }

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
        })
        .catch((err) =>{
            console.log(err);
        })
    }

    const volReply=async()=>{
        await axios({
            url: `${URL}/volunteers/${id}/comment`,
            method: "post",
            data:{
                content: commentContent,
                parentSeq: id
            }
        })
        .then((res)=>{
            console.log('댓글달기성공');
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

    useEffect(()=>{
        getPost();
    }, []);

    //지울부분
    useEffect(()=>{
        console.log(post);
    }, [post]);

    return(
        <div className={style.myContainer}>
            <h1>봉사활동</h1>

            <div className={style.titleBox}>
                <p className={style.leftdays}>D-{leftdays}</p>
                {/* 날짜 0일 내려가면 마감 뜸 */}
                {/* 마감돼도 마감 뜸 */}
                <p className={style.title}>{post.title}</p>
                {
                    join
                    ? <button type='button' onClick={joinBtn} className={`${style.joinBtn} ${style.joinXBtn}`}>참여취소</button>
                    :<button type='button' onClick={joinBtn} className={style.joinBtn}>참여신청</button>
                }
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
                        <p>모집 인원: {post.approvedCount}명/5명</p>
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
                    {/* 사진이 크면 칸을 넘어가는 문제 발생..omg! */}
                    {/* 사진 크기를 조정해줘야겠다 */}
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

            <Comment id={id} volReply={volReply}/>

            <button 
                className={style.listBtn}
                onClick={()=>{navigate(-1)}}>목록</button>
        </div>
    )
}

export default VolunteerDetail;