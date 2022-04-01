import { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { URL } from '../../public/config';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import style from './styles/TeamModal.module.scss';

function TeamManage(){
    const { id } = useParams();
    const jwt = sessionStorage.getItem('jwt');
    const memSeq = useSelector((state) => state.userInfo.userInfo.seq);
    const [approve, setApprove] = useState(false);
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
            console.log('신청자조회', res.data.data);
            setParticipants(res.data.data);
        })
        .catch((err)=>{
            console.log(err);
        })
    }

    //참가자 승인.미승인
    const partyApprove=async(apv, memSeq)=>{
        // console.log(memSeq);
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
            // console.log(approve);
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
            console.log('참가자 삭제');
        })
        .catch((err)=>{
            console.log(err);
        })
    }

    const getApprove=async(seq)=>{
        partyApprove(true, seq);
        getParticipants();
        // setChange(!change);
    }

    const cancleApprove=(seq)=>{
        partyApprove(false, seq);
        getParticipants();
        // setChange(!change);
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
                            {/* {console.log(p)} */}
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
                            {/* {
                                p.approve
                                ? <button className={`${style.btnCancle} ${style.btn}`} type="button" onClick={()=>{cancleApprove(p.seq)}}>승인 취소</button>
                                : <button className={`${style.btnAprv} ${style.btn}`} type="button" onClick={()=>{getApprove(p.seq)}}>승인</button>
                            } */}
                            <button className={style.btn} onClick={()=>{partyDelete(p.seq)}}>미승인</button>
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