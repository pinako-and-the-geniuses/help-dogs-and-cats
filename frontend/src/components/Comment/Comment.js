import { useState } from 'react';
import Reply from './Reply';
import AddComment from './AddComment';
import style from './style/Comment.module.scss';

function Comment(){
    const [reply, setReply] = useState(false);

    const openReply=()=>{
        setReply(true);
    }

    const closeReply=()=>{
        setReply(false);
    }

    return(
        <div className={style.commentBox}>
        <div className={style.comment}>
            <img src="https://cdn-icons-png.flaticon.com/512/2088/2088112.png" />

            <div className={style.content} onClick={openReply}>
                <div className={style.top}>
                    <div className={style.left}>
                        <span className={style.writer}>싸피강아지</span>
                        <span className={style.date}>2022-03-22</span>
                    </div>
                    <p className={style.delete}>삭제</p>
                </div>
                <p>
                    Lorem ipsum dolor sit amet consectetur adipisicing elit. Dolor possimus quae quas temporibus adipisci ad, consequatur ut nesciunt et blanditiis totam harum cupiditate quis aperiam amet molestias deserunt suscipit ipsa. Omnis, perferendis ullam? Numquam molestias unde reiciendis ab voluptate ullam ex amet laborum soluta vitae! Corporis ipsa architecto omnis est?
                </p>
            </div>
        </div>
        <Reply/>
        {
            reply
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

        <AddComment />
    </div>
    )

}

export default Comment;