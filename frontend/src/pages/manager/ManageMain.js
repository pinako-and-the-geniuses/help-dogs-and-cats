import React, { useState } from 'react';
import ManageHome from './ManageHome';
import VolunteerManage from './VolunteerManage';
import AdoptManage from './AdoptManage';
import style from './manager.module.scss';

function MoveTab({tab, setTab}){
    if( tab === 0 ){
        return (
            <>
                <ManageHome tab={tab} setTab={setTab}/>
            </>
        )
    }
    if( tab === 1 ){
        return (
            <>
                <VolunteerManage />
            </>
        )
    }
    if( tab === 2 ){
        return (
            <>
                <AdoptManage />
            </>
        )
    }
}

function ManageMain() {
    const [tab, setTab] = useState(0);

    return(
        <div className={style.manageHome}>
            <ul className={style.menus}>
                <li
                    className={tab === 0 ? style.on : ""}
                    onClick={()=>{setTab(0)}}>HOME</li>
                <li
                    className={tab == 1 ? style.on : ""}
                    onClick={()=>{setTab(1)}}>봉사 관리</li>
                <li
                    className={tab === 2 ? style.on : ""}
                    onClick={()=>{setTab(2)}}>입양 관리</li>
            </ul>

            <MoveTab tab={tab} setTab={setTab}/>
        </div>
    ) 
}

export default ManageMain;