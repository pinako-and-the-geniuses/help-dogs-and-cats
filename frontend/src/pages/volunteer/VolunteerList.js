import React, { useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import style from './styles/VolunteerList.module.scss';
import cn from 'classnames';
import axios from 'axios';

function VolunteerList(){
    const [id, setId] = useState();
    // const { id } = useParams();

    const navigate = useNavigate();

    const getID = () =>{
        console.log(id);
        navigate(`/volunteer/detail/${id}`);
    }

    const goToWrite =()=>{
        navigate('/volunteer/write');
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
                        <input type="text" />
                    </div>

                    <button>조회</button>
                </div>
            </div>

            <p className={style.writeBtn} onClick={goToWrite}>🖊 글쓰기</p>

            <table className={cn("table table-hover")}>
                <tbody>
                    <tr onClick={getID}>
                        <td scope="row">상태[d-day]</td>
                        <td>[지역]제목</td>
                        <td>3/5</td>
                        <td>작성자</td>
                        <td>2022.03.21</td>
                    </tr>
                    <tr>
                        <td scope="row">상태[d-day]</td>
                        <td>[지역]제목</td>
                        <td>3/5</td>
                        <td>작성자</td>
                        <td>2022.03.21</td>
                    </tr>
                    <tr>
                        <td scope="row">상태[d-day]</td>
                        <td>[지역]제목</td>
                        <td>3/5</td>
                        <td>작성자</td>
                        <td>2022.03.21</td>
                    </tr>
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