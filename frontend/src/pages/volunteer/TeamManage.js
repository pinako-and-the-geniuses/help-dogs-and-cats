import { useState } from 'react';
import style from './styles/TeamModal.module.scss';

function TeamManage(props){
    const [approve, setApprove] = useState(false);

    const getApprove=()=>{
        setApprove(true);
    }

    const cancleApprove=()=>{
        setApprove(false);
    }

    return(
        <>
        {
            props.participants
            ?(
                props.participants.map((p)=>{
                    return(
                        <div className={style.person}>
                            <p>{p.nickname}</p>
                            {
                                approve
                                ? <button className={`${style.btnCancle} ${style.btn}`} type="button" onClick={cancleApprove}>승인 취소</button>
                                : <button className={`${style.btnAprv} ${style.btn}`} type="button" onClick={getApprove}>승인</button>
                            }
                            <button className={style.btn}>미승인</button>
                        </div>
                    )     
                }
            ))
            :(
                <div className={style.person}>
                    <p>신청자가 없습니다</p>
                </div>
            )
        }
        </>
    )
}

export default TeamManage;