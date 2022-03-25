import React, { useState } from 'react';
import style from './manager.module.scss';

function MoveTab(props){
    if( props.tab === 0 ){
        return (
            <div>홈</div>
        )
    }
    if( props.tab === 1 ){
        return (
            <div>봉사 관리</div>
        )
    }
    if( props.tab === 2 ){
        return (
            <div>입양 관리</div>
        )
    }
    if( props.tab === 3 ){
        return (
            <div>로그 아웃</div>
        )
    }
}

function ManageHome() {
    const [tab, setTab] = useState(0);

    return(
        <div className={style.manageHome}>
            <ul className={style.menus}>
                <li
                    className={style.menu}
                    onClick={()=>{setTab(0)}}>HOME</li>
                <li
                    className={style.menu}
                    onClick={()=>{setTab(1)}}>봉사 관리</li>
                <li
                    className={style.menu}
                    onClick={()=>{setTab(2)}}>입양 관리</li>
                <li
                    className={style.menu}
                    onClick={()=>{setTab(3)}}>로그아웃</li>
            </ul>

            <MoveTab tab={tab} className={style.content}/>
        </div>
    ) 
}

export default ManageHome;