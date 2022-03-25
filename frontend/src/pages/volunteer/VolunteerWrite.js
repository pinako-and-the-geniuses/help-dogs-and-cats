import style from './styles/VolunteerWrite.module.scss';
// import ReactQuill from 'react-quill';
// import 'react-quill/dist/quill.snow.css';
import Editor from 'components/Editor';

function VolunteerWrite(){
    const placeholder=[
        "자세한 내용을 적어주세요\n ex)\n - 활동 장소: 000보호소\n - 활동 기간/시간: 두달간 매주 토요일 오후 2시"
    ];

    return(
        <div className={style.myContainer}>
            <h1>봉사활동<span> 글 작성</span></h1>

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

            {/* <ReactQuill theme="snow"/> */}
            <Editor
                placeholder={placeholder}>
            </Editor>

            <button className={style.addBtn}>등록</button>
        </div>
    )
}

export default VolunteerWrite;