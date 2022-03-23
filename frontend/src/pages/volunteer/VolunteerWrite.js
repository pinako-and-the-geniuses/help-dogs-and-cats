import style from './styles/VolunteerWrite.module.scss';
import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css';
import Editor from 'components/Editor';

function VolunteerWrite(){
    return(
        <div className={style.myContainer}>
            <div className={style.thisTitle}>
                <input type="text" placeholder='제목'/>
            </div>

            <div className={style.infoBox}>
                <ul>
                    <li className={style.region}>
                        <span>지역</span>
                        <p className={style.area}>시도</p>
                        <select name='searchCd'>
                            <option value="0">전체</option>
                        </select>

                        <p className={style.area}>시군구</p>
                        <select name='searchCgg'>
                            <option value="0">전체</option>
                        </select>
                    </li>
                    <li className={style.vol_time}>
                        <span>봉사인증시간</span>
                        <input type="number" placeholder='0' min="0"/>
                        <p>* 인증이 되지 않는 봉사일 경우 0시간으로 작성해주세요</p>
                    </li>
                    <li className={style.party}>
                        <span>모집 인원</span>
                        <input type="number" placeholder='3' min="3" max="100"/>
                        <p>* 3명 이상 가능합니다</p>
                    </li>
                    <li className={style.contact}>
                        <span>연락 방법</span>
                        <input type="text" placeholder='ex. 오픈채팅 주소'/>
                    </li>
                    <li className={style.endDate}>
                        <span>모집 마감 날짜</span>
                        <input type="date" />
                    </li>
                </ul>
            </div>

            <ReactQuill theme="snow"/>

            <button className={style.addBtn}>등록</button>
        </div>
    )
}

export default VolunteerWrite;