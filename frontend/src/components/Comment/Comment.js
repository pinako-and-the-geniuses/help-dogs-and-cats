import { useState } from 'react';
import Reply from './Reply';
import style from './style/Comment.module.scss';

function Comment(){
    const [rereply, setRereply] = useState(false);
    const [reply, setReply] = useState(false);

    const openReply=()=>{
        setRereply(true);
    }

    const closeReply=()=>{
        setRereply(false);
    }

    return(
        <div className={style.commentBox}>
        <div className={style.comment}>
            <img src="https://cdn-icons-png.flaticon.com/512/2088/2088112.png" />

            <div className={style.content}>
                <div className={style.top}>
                    <div className={style.left}>
                        <span className={style.writer}>싸피강아지</span>
                        <span className={style.date}>2022-03-22</span>
                    </div>
                    <p className={style.delete}>삭제</p>
                </div>
                <p onClick={openReply}>
                    Lorem ipsum dolor sit amet consectetur adipisicing elit. Dolor possimus quae quas temporibus adipisci ad, consequatur ut nesciunt et blanditiis totam harum cupiditate quis aperiam amet molestias deserunt suscipit ipsa. Omnis, perferendis ullam? Numquam molestias unde reiciendis ab voluptate ullam ex amet laborum soluta vitae! Corporis ipsa architecto omnis est?
                </p>
            </div>
        </div>
        <Reply/>
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
        }
        <hr />

        {/* 대댓 수정하기 */}
        <div className={style.addComment}>
            <textarea 
                cols="30" rows="3"
                onChange={(e)=>{setReply(e.target.value)}}></textarea>
            <button type="submit">댓글 작성</button>
       </div>
    </div>
    )

}

export default Comment;