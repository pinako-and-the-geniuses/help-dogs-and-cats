import React, { useState } from 'react';
import style from './style/Comment.module.scss';

function AddComment(){
    const [reply, setReply] = useState("");

    return(
        <div className={style.addComment}>
            <textarea 
                cols="30" rows="6"
                onChange={(e)=>{setReply(e.target.value)}}></textarea>
            <button type="submit">댓글 작성</button>
       </div>
    )
}

export default AddComment;