import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import style from './styles/VolunteerList.module.scss';
import cn from 'classnames';
import axios from 'axios';
import { useSelector } from 'react-redux';
import { URL } from '../../public/config';

function VolunteerList(){
    const isLogin = useSelector((state) => state.userInfo.isLoggedIn);
    const [id, setId] = useState("");
    const [volunteers, setVolunteers] = useState("");
    const [seq, setSeq] = useState(0);
    const [page, setPage] = useState(1);
    const [keyword, setKeyword] = useState("");

    const navigate = useNavigate();

    const getID = () =>{
        console.log(id);
        navigate(`/volunteer/detail/${id}`);
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
            // console.log(res.data.data.volunteersForPage);
            console.log(res.data.data.volunteersForPage);
            setVolunteers(res.data.data.volunteersForPage);
        })
        .catch((err) => {
            console.log(err);
        })
    }

    useEffect(()=>{
        getList();
    }, []);

    const goToVolunteer=(seq)=>{
        setSeq(seq);
        if(seq !== 0) navigate(`/volunteer/detail/${seq}`);

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
                            <tr 
                                key={volunteer.seq} 
                                onClick={()=>{goToVolunteer(volunteer.seq)}}>
                                <td>{volunteer.status}</td>
                                <td>{volunteer.title}</td>
                                <td>{volunteer.maxParticipantCount}</td>
                                <td>{volunteer.nickname}</td>
                                <td>날짜</td>
                                {/* 게시글 작성 날짜, 마감 날짜, 현재인원 필요 */}
                                {/* 현재 인원 말고..그..뭐야.. 인증시간 써도 될듯? 암튼 필요 */}
                                {/* 상태에 대한 정의 필요, RECRUITING 말고 나머지 두개 잘 모르겠음 */}
                                {/* 조회수도? */}
                            </tr>
                        )
                    })
                }
                </tbody>
            </table>

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