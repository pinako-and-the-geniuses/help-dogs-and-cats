import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { ANIMAL } from '../../public/config';
import axios from 'axios';
import Map from '../../components/Map';
import style from './styles/ShelterDetail.module.scss';
import cn from 'classnames';

function ShelterDetail(){
    const { id } = useParams();
    const SHELTER_KEY = process.env.REACT_APP_SHELTER_KEY;

    const getShelter=async()=>{
        await axios({
            url: `${ANIMAL}/animalShelterSrvc/shelterInfo?care_reg_no=${id}&_type=json&serviceKey=${SHELTER_KEY}`,
            method: "get"
        })
        .then((res)=>{
            console.log(res)
        })
        .catch((err) =>{
            console.log(err);
        })
    }

    useEffect(()=>{
        getShelter();
    }, []);

    return(
        <div className={style.shelter_container}>
            <header>
                <h2>동물 보호 센터</h2>
            </header>

            <section className={cn('container')}>
                <div className={cn('row')}>
                    <div className={cn(style.main_info, 'col-6')}>
                        <div className={style.text_box}>
                            <p className={style.title}>보호센터명</p>
                            <p>멀티캠퍼스</p>
                        </div>
                        <div className={style.text_box}>
                            <p className={style.title}>전화번호</p>
                            <p>02-000-0000</p>
                        </div>
                        <div className={style.text_box}>
                            <p className={style.title}>주소</p>
                            <p>서울특별시 강남구 테헤란로 121</p>
                        </div>
                        <div className={style.text_box}>
                            <p className={style.title}>관리기관명</p>
                            <p>삼성멀티캠퍼스 (법인)</p>
                        </div>
                        <div className={style.text_box}>
                            <p className={style.title}>운영시간</p>
                            <p>9:00 ~ 18:00</p>
                        </div>
                        <div className={style.text_box}>
                            <p className={style.title}>휴무일</p>
                            <p></p>
                        </div>
                        <div className={style.text_box}>
                            <p className={style.title}>구조대상동물</p>
                            <p>개+고양이+기타</p>
                        </div>
                    </div>

                    <div className={cn(style.map, 'col-6')}>
                        <Map />
                    </div>
                </div>
            </section>
        </div>
    )
}

export default ShelterDetail;