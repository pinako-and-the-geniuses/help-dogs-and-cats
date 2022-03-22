import style from './style/Comment.module.scss';

function Reply(){
    return(
        <div className={style.replyBox}>
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
                        <p>
                            Lorem ipsum dolor sit amet consectetur adipisicing elit. Dolor possimus quae quas temporibus adipisci ad, consequatur ut nesciunt et blanditiis totam harum cupiditate quis aperiam amet molestias deserunt suscipit ipsa. Omnis, perferendis ullam? Numquam molestias unde reiciendis ab voluptate ullam ex amet laborum soluta vitae! Corporis ipsa architecto omnis est?
                        </p>
                    </div>
                </div>
        </div>

    )
}

export default Reply;