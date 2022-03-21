import style from './styles/VolunteerList.module.scss';
import cn from 'classnames';

function VolunteerList(){
    return(
        <div className={style.myContainer}>
            <div>안녕</div>
            <div>안녕</div>
            <div>안녕</div>
            <div>안녕</div>
            <div>안녕</div>
            <h1>봉사활동</h1>

            <div className={style.searchBar}>
                <div className={style.top}>
                    <div className={style.volunteer}>
                        <p className={style.title}>봉사시간</p>
                        <input type="radio" name="vol_time" className={style.vol_time}/>무관
                        <input type="radio" name="vol_time" className={style.vol_time}/>인정
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

            <table className={cn("table table-hover")}>
                <tbody>
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