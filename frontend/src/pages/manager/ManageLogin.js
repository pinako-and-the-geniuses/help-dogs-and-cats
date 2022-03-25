import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import style from './manager.module.scss';

function ManageLogin(){
    const [id, setID] = useState("");
    const [pw, setPW] = useState("");
    const navigate = useNavigate();

    const onSubmit=()=>{
        navigate('/manage/home')
    }
    return(
        <div className={style.mBody}>
            <h1>관리자 로그인</h1>

            <div className={style.id}>
                <p>아이디</p>
                <input type="text" />
            </div>
            <div className={style.password}>
                <p>비밀번호</p>
                <input type="text" />
            </div>
            <button type="submit" onClick={onSubmit}>로그인</button>
        </div>
    )
}

export default ManageLogin;