import React, { useState, useRef } from 'react';
import { useNavigate } from 'react-router-dom';
import { URL } from '../../public/config';
import axios from 'axios';
import Editor from 'components/Editor';
import style from './styles/VolunteerWrite.module.scss';
import swal from 'sweetalert';

function VolunteerWrite(){
    const navigate = useNavigate();
    const jwt = sessionStorage.getItem('jwt');
    const placeholder=[
        "자세한 내용을 적어주세요\n ex)\n - 활동 장소: 000보호소\n - 활동 기간/시간: 두달간 매주 토요일 오후 2시"
    ];
    // const [value, setValue] = useState("");
    // const quillRef = useRef();\
    const [title, setTitle] = useState("");
    const [cd, setCd] = useState("");
    // const [cgg, setCgg] = useState();
    const [time, setTime] = useState(0);
    const [party, setParty] = useState(3);
    const [contact, setContact] = useState("");
    const [endDate, setEndDate] = useState("");
    const [content, setContent] = useState("");

    const onTitleHandler=(e)=>{
        setTitle(e.target.value);
    }

    const onCdHadler=(e)=>{
        setCd(e.target.value);
    }

    // const onCggHandler=(e)=>{
    //     setCgg(e.target.value);
    // }

    const onTimeHandler=(e)=>{
        setTime(e.target.value);
    }

    const onPartyHandler=(e)=>{
        setParty(e.target.value);
    }

    const onContactHandler=(e)=>{
        setContact(e.target.value);
    }

    const onEndDateHandler=(e)=>{
        setEndDate(e.target.value);
    }

    const onEditorChange = (value) => {
        setContent(value);
    };

    const post = async()=>{
        await axios({
            url: `${URL}/volunteers`,
            method: "post",
            data: {
                title: title,
                content: content,
                activityArea: "",
                authTime: time,
                contact: contact,
                endDate: endDate,
                minParticipantCount: 3,
                maxParticipantCount: party
            },
            headers: {
                Authorization: `Bearer ${jwt}`,
            }
        })
        .then((res)=>{
            navigate('/volunteer/list');
        })
        .catch((err) =>{
            console.log(err);
        }) 
    }

    const onSubmit=(e)=>{
        e.preventDefault();
        if(title === ""){
            swal('제목값은 필수입니다.');
            return;
        }
        if(endDate === ""){
            swal('마감일은 필수입니다.');
            return;
        }
        if(content.length < 15){
            swal('내용을 더 자세히 적어주세요.');
            return;
        }
        post();
    }

    return(
        <div className={style.myContainer}>
            <h1>봉사활동<span> 글 작성</span></h1>

            <div className={style.thisTitle}>
                <input
                    type="text" 
                    placeholder='제목'
                    onChange={onTitleHandler}/>
            </div>

            <div className={style.infoBox}>
                <ul>
                    <li className={style.region}>
                        <span>지역</span>
                        <p className={style.area}>시도</p>
                        <select name='searchCd'>
                            <option value="0">전체</option>
                        </select>

                        {/* <p className={style.area}>시군구</p>
                        <select name='searchCgg'>
                            <option value="0">전체</option>
                        </select> */}
                    </li>
                    <li className={style.vol_time}>
                        <span>봉사인증시간</span>
                        <input 
                            type="number" 
                            placeholder='0' 
                            min="0"
                            onChange={onTimeHandler}/>
                        <p>* 인증이 되지 않는 봉사일 경우 0시간으로 작성해주세요</p>
                    </li>
                    <li className={style.party}>
                        <span>모집 인원</span>
                        <input 
                            type="number" 
                            placeholder='3' 
                            min="3" 
                            max="100"
                            onChange={onPartyHandler}/>
                        <p>* 3명 이상 가능합니다</p>
                    </li>
                    <li className={style.contact}>
                        <span>연락 방법</span>
                        <input
                            type="text" 
                            placeholder='ex. 오픈채팅 주소'
                            onChange={onContactHandler}/>
                    </li>
                    <li className={style.endDate}>
                        <span>모집 마감 날짜</span>
                        <input 
                            type="date"
                            onChange={onEndDateHandler} />
                    </li>
                </ul>
            </div>

            <Editor
                height={"60vh"}
                placeholder={placeholder}
                value={content||""}
                onChange={onEditorChange}
                >
            </Editor>
            <button 
                className={style.addBtn}
                onClick={onSubmit}>등록</button>
        </div>
    )
}

export default VolunteerWrite;