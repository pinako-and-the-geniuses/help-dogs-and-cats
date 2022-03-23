import { useState } from 'react';
import style from './styles/TeamModal.module.scss';

function TeamManage(){
    const [approve, setApprove] = useState(false);

    const getApprove=()=>{
        setApprove(true);
    }

    const cancleApprove=()=>{
        setApprove(false);
    }

    return(
        <>
            <div className={style.person}>
                <p>싸피강아지</p>
                {
                    approve
                    ? <button className={`${style.btnCancle} ${style.btn}`} type="button" onClick={cancleApprove}>승인 취소</button>
                    : <button className={`${style.btnAprv} ${style.btn}`} type="button" onClick={getApprove}>승인</button>
                }
                <button className={style.btn}>미승인</button>
            </div>
        </>
    )
}

export default TeamManage;