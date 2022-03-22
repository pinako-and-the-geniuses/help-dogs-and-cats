import React, { useState } from 'react';
import style from './styles/VolunteerDetail.module.scss';

function VolunteerDetail(){
    const [join, setJoin] = useState(false);

    const joinBtn=()=>{
        setJoin(!join);
    }

    return(
        <div className={style.myContainer}>
            <p>하이</p>
            <p>하이</p>
            <h1>봉사활동</h1>

            <div className={style.titleBox}>
                <p className={style.leftdays}>D-50</p>
                <p className={style.title}>[서울] 여의도 공원 근처에서 봉사활동 하실 분들 구합니다~</p>
                {
                    join
                    ? <button type='button' onClick={joinBtn} className={`${style.joinBtn} ${style.joinXBtn}`}>참여취소</button>
                    :<button type='button' onClick={joinBtn} className={style.joinBtn}>참여신청</button>
                }
            </div>

            <div className={style.infoBox}>
                <ul>
                    <li className={style.first}>
                        <p>활동 지역: 서울시</p>
                        <p>마감 날짜: 2022-04-08</p>
                    </li>
                    <li>
                        인증 봉사시간 : 00시간
                    </li>
                    <li className={style.people}>
                        <p>모집 인원: 3명/5명</p>
                        <button type="button">인원관리</button>
                    </li>
                    <li className={style.last}>
                        <p className={style.contact}>연락방법: <a>오픈채팅주소~</a></p>
                        <p className={style.writer}>작성자 김싸피</p>
                    </li>
                </ul>
            </div>

            <div className={style.mainBox}>
                <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Earum quos incidunt sapiente, aut quis similique perspiciatis natus laboriosam iusto quibusdam laudantium ab, vitae harum modi omnis soluta quaerat animi repellat quas asperiores adipisci minima quam! Ullam, error. Ducimus sit est quos et dolorum eligendi molestiae eveniet odit illo numquam. Consequatur!</p>
            </div>
            
            <div className={style.editPost}>
                <p>수정</p>
                <p>삭제</p>
            </div>
            <br/>

            {/* <div className={style.commentBox}>
                <div className={style.addComment}>
                    <input type="text" />
                    <button type="submit">댓글 작성</button>
                </div>
                <div className={style.comments}>
                    <div className={style.commentWriter}>
                        <img src="https://cdn-icons-png.flaticon.com/512/2088/2088112.png"/>
                        <p>작성자</p>
                    </div>

                    <div className={style.content}>
                        <p>안녕하세요 채팅 드럈습니다 확인해주세요!</p>
                        댓글 누르면 답글 작성 가능
                        <p>2022-03-22</p>
                    </div>

                    <p>삭제</p>
                </div>
            </div> */}

            <div className={style.commentBox}>
                {/* <div className={style.comment}>
                    <img src="https://cdn-icons-png.flaticon.com/512/2088/2088112.png" />

                    <div className={style.content}>
                        <span className={style.writer}>싸피강아지</span>
                        <span className={style.date}>2022-03-22</span>
                        <span>삭제</span>
                        <p>
                            Lorem ipsum dolor sit amet consectetur adipisicing elit. Dolor possimus quae quas temporibus adipisci ad, consequatur ut nesciunt et blanditiis totam harum cupiditate quis aperiam amet molestias deserunt suscipit ipsa. Omnis, perferendis ullam? Numquam molestias unde reiciendis ab voluptate ullam ex amet laborum soluta vitae! Corporis ipsa architecto omnis est?
                        </p>
                    </div>
                </div>
                <hr /> */}
                {/* 밑에다 작업함!!!!!! */}
                <div className={style.comment}>
                    <img src="https://cdn-icons-png.flaticon.com/512/2088/2088112.png" />

                    <div className={style.content}>
                        <div className={style.top}>
                            <span className={style.writer}>싸피강아지</span>
                            <span className={style.date}>2022-03-22</span>
                            <span className={style.delete}>삭제</span>
                        </div>
                        <p>
                            Lorem ipsum dolor sit amet consectetur adipisicing elit. Dolor possimus quae quas temporibus adipisci ad, consequatur ut nesciunt et blanditiis totam harum cupiditate quis aperiam amet molestias deserunt suscipit ipsa. Omnis, perferendis ullam? Numquam molestias unde reiciendis ab voluptate ullam ex amet laborum soluta vitae! Corporis ipsa architecto omnis est?
                        </p>
                    </div>
                </div>
                <hr />
                
            </div>


        </div>
    )
}

export default VolunteerDetail;