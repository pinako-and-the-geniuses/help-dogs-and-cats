import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import style from './styles/VolunteerList.module.scss';
import cn from 'classnames';
import axios from 'axios';
import { useSelector } from 'react-redux';
import { URL } from '../../public/config';
import swal from 'sweetalert';

function VolunteerList(){
    const isLogin = useSelector((state) => state.userInfo.isLoggedIn);
    const navigate = useNavigate();

    const [volunteers, setVolunteers] = useState("");
    const [seq, setSeq] = useState(0);
    const [page, setPage] = useState(1);
    const [keyword, setKeyword] = useState("");

    //남은 날짜
    const leftDays=(enddate, workStatus)=>{
        const today = new Date();
        const endDate = new Date(enddate);
        const gap = endDate.getTime() - today.getTime();
        const leftdays = Math.ceil(gap/(1000*60*60*24));

        if(leftdays < 0  || workStatus !== "RECRUITING") return null;
        else if(leftdays === 0) return(`[D-DAY]`);
        else return(`[D-${leftdays}]`);
    }

    //봉사 모집 상태
    const workStatus=(workStatus)=>{
        if(workStatus === "RECRUITING") return "모집중";
        else if(workStatus === "ONGOING") return "봉사중";
        else if(workStatus === "DONE") return "봉사완료"; //수정필요
    }

    const goToWrite =()=>{
        navigate('/volunteer/write');
    }


    //봉사활동 목록 받아오기
    const getList=async()=>{
        await axios({
            url: `${URL}/volunteers?page=${page}&size=10&keyword=${keyword}`,
            method: "get",
        })
        .then((res)=>{
            console.log(res.data);
            setVolunteers(res.data.data.volunteersForPage);
        })
        .catch((err) => {
            console.log(err);
        })
    }

    //페이지 넘어갈때마다 새로 목록 불러오기
    useEffect(()=>{
        getList();
    }, [page]);

    const goToVolunteer=(seq)=>{
        if(isLogin){
            setSeq(seq);
            if(seq !== 0) navigate(`/volunteer/detail/${seq}`);
        }
        else{
            //회원만 글을 읽을 수 있음
            swal('권한이 없습니다');
        }

    }

    const enterKey=()=>{
        if(window.event.keyCode === 13) getList();
    }


    return(
        <div className={style.myContainer}>
            <h1>봉사활동</h1>

            <div className={style.searchBar}>
                <div className={style.top}>
                    <div className={style.volunteer}>
                        <p className={style.title}>봉사시간</p>
                        <label htmlFor="radio1">
                            <input type="radio" name="vol_time" id="radio1" className={style.vol_time} checked/><span>무관</span>
                        </label>
                        <label htmlFor="radio2">
                            <input type="radio" name="vol_time" id="radio2" className={style.vol_time}/><span>인정</span>
                        </label>
                    </div>

                    <div className={style.endDate}>
                        <p className={style.title}>마감날짜</p>
                        <input type="date" name="" id="" />
                    </div>
                </div>

                <div className={style.bottom}>
                    <div className={style.areaBox}>
                        <p className={style.title}>지역</p>

                        <p className={style.area}>시도</p>
                        <select name='searchCd'>
                            <option value="0">전체</option>
                        </select>

                        <p className={style.area}>시군구</p>
                        <select name='searchCgg'>
                            <option value="0">전체</option>
                        </select>
                    </div>

                    <div className={style.titleBox}>
                        <p className={style.title}>제목</p>
                        <input 
                            type="text"
                            onKeyUp={enterKey}
                            onChange={(e)=>setKeyword(e.target.value)} />
                    </div>

                    <button onClick={getList}>조회</button>
                </div>
            </div>

            {
                isLogin
                ? <p className={style.writeBtn} onClick={goToWrite}>🖊 글쓰기</p>                
                : null
            }

            <table className={cn("table table-hover")}>
                <thead>
                    <tr>
                        <th scope="col">상태</th>
                        <th scope="col">제목</th>
                        <th scope="col">모집인원</th>
                        <th scope="col">작성자</th>
                        <th scope="col">작성일</th>
                    </tr>
                </thead>
                <tbody>
                {
                    volunteers && volunteers.map((volunteer)=>{
                        return(
                            <tr key={volunteer.seq} 
                                onClick={()=>{goToVolunteer(volunteer.seq)}}>
                            <td>{workStatus(volunteer.status)}&nbsp;{leftDays(volunteer.endDate, volunteer.status)}</td>
                            <td>{volunteer.title}</td>
                            <td>0/{volunteer.maxParticipantCount}</td>
                            <td>{volunteer.nickname}</td>
                            <td>{volunteer.createdDate.slice(0,10)}</td>
                            </tr>
                        )
                    })
                }
                </tbody>
            </table>

            {/* 전체 페이지의 수를 가져오는 방법? */}
            {/* 밑에는 test입니다 */}
            <nav>
                <ul>
                    <li onClick={()=>{setPage(page-1)}}>🌛</li>
                    <li>{page}</li>
                    <li onClick={()=>{setPage(page+1)}}>🌜</li>
                </ul>
            </nav>
            <nav aria-label="Page navigation example">
                <ul className="pagination">
                    <li className="page-item">
                    <a className="page-link" href="#" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                    </li>
                    <li className="page-item"><a className="page-link" href="#">1</a></li>
                    <li className="page-item"><a className="page-link" href="#">2</a></li>
                    <li className="page-item"><a className="page-link" href="#">3</a></li>
                    <li className="page-item">
                    <a className="page-link" href="#" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                    </li>
                </ul>
            </nav>
        </div>
    )
}

export default VolunteerList;