import { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { URL } from '../../public/config';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import style from './styles/TeamModal.module.scss';
import swal from 'sweetalert';

function TeamManage(props){
    const { id } = useParams();
    const jwt = sessionStorage.getItem('jwt');
    const memSeq = useSelector((state) => state.userInfo.userInfo.seq);
    const [participants, setParticipants] = useState([]);
    const [change, setChange] = useState(true);

    //신청자 조회
    const getParticipants=async()=>{
        await axios({
            url:`${URL}/volunteers/${id}/participants`,
            method: "get",
            headers: { Authorization : `Bearer ${jwt}`}
        })
        .then((res) =>{
            setParticipants(res.data.data);
        })
        .catch((err)=>{
            console.log(err);
        })
    }

    //참가자 승인.미승인
    const partyApprove=async(apv, memSeq)=>{
        await axios({
            url: `${URL}/volunteers/${id}/participants/${memSeq}`,
            method: "patch",
            data: {
                approve: apv,
            },
            headers: {
            Authorization: `Bearer ${jwt}`,
        }
        })
        .then((res) =>{

        })
        .catch((err)=>{
            console.log(err);
        })
    }   

    //참가자 삭제
    const partyDelete=async(memSeq)=>{
        await axios({
            url: `${URL}/volunteers/${id}/participants/${memSeq}`,
            method: "delete",
            headers:{ Authorization: `Bearer ${jwt}`}
        })
        .then((res)=>{
            //getParticipants();
        })
        .catch((err)=>{
            console.log(err);
        })
    }

    const getApprove=async(seq)=>{
        if(props.approvedCount >= props.maxParticipantCount){
            swal('모집인원을 초과했습니다');
            return;
        }
        partyApprove(true, seq)
        .then(getParticipants());
        getParticipants();
    }

    const cancleApprove=(seq)=>{
        partyApprove(false, seq)
        .then(getParticipants());
        getParticipants();
    }

    const deleteHandler=(memSeq)=>{
        swal("목록에서 삭제합니다", {
            buttons: ['취소', '확인']
        })
        .then((willDelete)=>{
            if(willDelete){
                partyDelete(memSeq);
                getParticipants();
            }else{

            }
        })
    }

    useEffect(()=>{
        getParticipants();
    }, [])

    return(
        <>
        {
            participants
            ?(
                participants.map((p)=>{
                    return(
                        <div className={style.person} key={p.seq}>
                            <p>{p.nickname}</p>
                            {
                                p.seq === memSeq
                                ? null
                                :(
                                    p.approve
                                    ? <button className={`${style.btnCancle} ${style.btn}`} type="button" onClick={()=>{cancleApprove(p.seq)}}>승인 취소</button>
                                    : <button className={`${style.btnAprv} ${style.btn}`} type="button" onClick={()=>{getApprove(p.seq)}}>승인</button>
                                )
                            }
                            {
                                p.seq === memSeq
                                ? null
                                : <button className={style.btn} onClick={()=>{deleteHandler(p.seq)}}>삭제</button>
                            }
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