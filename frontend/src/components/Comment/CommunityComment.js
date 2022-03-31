import { useState } from 'react';
import { useParams } from 'react-router-dom';
import { useSelector } from 'react-redux';
import { URL } from '../../public/config';
import Reply from './Reply';
import style from './style/Comment.module.scss';
import swal from 'sweetalert';
import axios from 'axios';

function CommunityComment(props){
    const jwt = sessionStorage.getItem('jwt');
    const memSeq = useSelector((state) => state.userInfo.userInfo.seq);
    const { seq } = useParams();
    const [rereply, setRereply] = useState(false);
    const [reply, setReply] = useState(false);

    const openReply=()=>{
        setRereply(true);
    }

    const closeReply=()=>{
        setRereply(false);
    }

    const deleteHandler=(commentSeq)=>{
        console.log('11',commentSeq);
        swal("댓글을 삭제합니다",{
            buttons: ['취소', '확인']
        })
        .then((willDelete)=>{
            if(willDelete){
                deleteComment(commentSeq);
            } else{
            }
        });
    }

    const deleteComment=async(commentSeq)=>{
        await axios({
            url: `${URL}/communities/${seq}/comments/${commentSeq}`,
            method: "delete",
            headers: {
                Authorization: `Bearer ${jwt}`,
            }
        })
        .then((res)=>{
            console.log('삭제완료');
        })
        .catch((err) =>{
            console.log(err);
        })
    }

    return(
        <div className={style.commentBox}>
            {
                props.comments&&(props.comments).map((comment)=>{
                    return(
                        <>
                        <div key={comment.commmentSeq} className={style.comment}>
                            {/* 이미지 수정 필요 */}
                            <img src={comment.writerProfileImagePath} />
                            <div className={style.content}>
                                <div className={style.top}>
                                    <div className={style.left}>
                                        <span className={style.writer}>{comment.writerNickname}</span>
                                        <span className={style.date}>{comment.createdDate}</span>
                                    </div>
                                    {
                                        memSeq === comment.writerSeq
                                        ? <p className={style.delete} onClick={()=>{deleteHandler(comment.commentSeq)}}>삭제</p>
                                        : null
                                    }
                                </div>
                                <p onClick={openReply}>
                                    {comment.content}
                                </p>
                            </div>
                        </div>
                        {/* <Reply />
                        {
                            rereply
                            ?(
                                <div className={style.addReply}>
                                    <div></div>
                                    <textarea type="text"/>
                                    <button type="submit" className={style.replyBtn}>등록</button>
                                    <button className={style.XBtn} onClick={closeReply}>X</button>
                                </div>
                            )
                            : ""
                        } */}
                        <hr />
                        </>
                    )
                })
            }


            <div className={style.addComment}>
                <textarea 
                    cols="30" rows="3"
                    value={props.commentContent}
                    onChange={(e)=>props.onChange(e.target.value)}
                    ></textarea>
                <button type="submit" onClick={()=>{props.eventHandler();}}>댓글 작성</button>
            </div>
        </div>
    )

}

export default CommunityComment;
