import style from './style/Comment.module.scss';

function AddComment(){
    return(
        <div className={style.addComment}>
            <textarea cols="30" rows="6"></textarea>
            <button type="submit">댓글 작성</button>
       </div>
    )
}

export default AddComment;