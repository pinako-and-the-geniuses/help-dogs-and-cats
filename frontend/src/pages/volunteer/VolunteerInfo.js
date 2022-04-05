import style from './styles/VolunteerDetail.module.scss';
import TeamManage from './TeamManage';

function VolunteerInfo({post, memSeq, getParticipants}){
    return(
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
                {
                    memSeq === post.writerSeq
                    ? <button type="button" className={style.btn1} data-bs-toggle="modal" data-bs-target="#teamManagement">인원관리</button>
                    : null
                }
                {/* 모달 */}
                <div className="modal fade" id="teamManagement" tabIndex="-1" aria-labelledby="teamModalLabel" aria-hidden="true">
                    <div className="modal-dialog">
                        <div className="modal-content">
                            <div className="modal-header">
                                <h5 className="modal-title" id="exampleModalLabel">봉사활동 인원 관리</h5>
                                <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close" onClick={()=>{getParticipants()}}></button>
                            </div>
                            <div className="modal-body">
                                <TeamManage 
                                    approvedCount={post.approvedCount}
                                    maxParticipantCount={post.maxParticipantCount}
                                    />
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
    )
}

export default VolunteerInfo;