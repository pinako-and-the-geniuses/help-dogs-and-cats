import React, { useState, useRef } from 'react';
import style from './styles/VolunteerWrite.module.scss';
import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css';
// import Editor from 'components/Editor';
import axios from 'axios';
import { URL } from '../../public/config';
import { useNavigate } from 'react-router-dom';

function VolunteerWrite(){
    const navigate = useNavigate();
    const jwt = sessionStorage.getItem('jwt');
    const placeholder=[
        "자세한 내용을 적어주세요\n ex)\n - 활동 장소: 000보호소\n - 활동 기간/시간: 두달간 매주 토요일 오후 2시"
    ];
    // const [value, setValue] = useState("");
    // const quillRef = useRef();\
    const [title, setTitle] = useState("");
    const [cd, setCd] = useState();
    // const [cgg, setCgg] = useState();
    const [time, setTime] = useState(0);
    const [party, setParty] = useState(0);
    const [contact, setContact] = useState("");
    const [endDate, setEndDate] = useState();
    const [content, setContent] = useState();

    const onTitleHandelr=(e)=>{
        setTitle(e.target.value);
        console.log('title', title);
    }

    const onCdHadler=(e)=>{
        setCd(e.target.value);
    }

    // const onCggHandler=(e)=>{
    //     setCgg(e.target.value);
    // }

    const onTimeHandler=(e)=>{
        setTime(e.target.value);
        console.log('봉사시간', time);
    }

    const onPartyHandler=(e)=>{
        setParty(e.target.value);
        console.log('파티원', party);
    }

    const onContactHandler=(e)=>{
        setContact(e.target.value);
        console.log('contact', contact);
    }

    const onEndDateHandler=(e)=>{
        setEndDate(e.target.value);
        console.log('date', endDate);
    }

    ///일단 만들어만 놨음... htmlContent 받아와서 넣어줘야 함
    //하위 컴포넌트에서 상위 컴포넌트로 데이터 보내기 가능?
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
                maxParticipantCount: 100
            },
            headers: {
                Authorization: `Bearer ${jwt}`,
            }
        })
        .then((res)=>{
            console.log('전송 성공!', res.data);
            navigate('/volunteer/list');
        })
        .catch((err) =>{
            console.log(err);
        }) 
    }

    const onSubmit=(e)=>{
        e.preventDefault();
        post();
    }

    return(
        <div className={style.myContainer}>
            <h1>봉사활동<span> 글 작성</span></h1>

            <div className={style.thisTitle}>
                <input
                    type="text" 
                    placeholder='제목'
                    onChange={onTitleHandelr}/>
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

            <ReactQuill 
                theme="snow"
                htmlContent={content}
                onChange={(value)=>{setContent(value)}}
            />
            {/* <Editor
                placeholder={placeholder}
                htmlContent={content}
                onChange={(e)=>{console.log('111',e.target.value)}}>
            </Editor> */}

            <button 
                className={style.addBtn}
                onClick={onSubmit}>등록</button>
        </div>
    )
}

export default VolunteerWrite;