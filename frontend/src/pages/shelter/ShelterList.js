import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { URL, ANIMAL } from '../../public/config/index';
import axios from 'axios';
import style from './styles/ShelterList.module.scss';
import cn from 'classnames';

function ShelterList(){
    const navigate =useNavigate();
    const SHELTER_KEY = process.env.REACT_APP_SHELTER_KEY;
    const ANIMAL_KEY = process.env.REACT_APP_ANIMAL_KEY;
    const all = [
        {
          orgCd: '0',
          orgdownNm: "전체",
        },
      ];

    const [cds, setCds] = useState();
    const [selectCd, setSelectCd] = useState();
    const [cggs, setCggs] = useState([...all]);
    const [selectCgg, setSelectCgg] = useState();
    const [areaSearch, setAreaSearch] = useState();
    const [nameSearch, setNameSearch] = useState();

    //시도 가져오기
    const getSido = async() =>{
        await axios({
            url:`${ANIMAL}/abandonmentPublicSrvc/sido?numOfRows=20&pageNo=1&_type=json&serviceKey=${ANIMAL_KEY}`,
            method: "get",
        })
        .then((res)=>{
            // console.log(res.data.response.body.items.item);
            setCds([...all, ...res.data.response.body.items.item]);
        })
        .catch((err)=>{
            console.log(err);
        })
    }
    
    //시군구 가져오기
    const getSigungu = async() =>{
        await axios({
            url: `${ANIMAL}/abandonmentPublicSrvc/sigungu?upr_cd=${selectCd}&_type=json&serviceKey=${ANIMAL_KEY}`,
            method: "get",
        })
        .then((res)=>{
            // console.log(...res.data.response.body.items.item);
            setCggs([...all, ...res.data.response.body.items.item]);
            // console.log(res.data.response.body.items.item)
        })
        .catch((err)=>{
            console.log(err)
        })
    }
    
    //보호소목록 가져오기
    const shelterList = async() => {
        await axios({
            url: `${ANIMAL}/abandonmentPublicSrvc/sigungu?upr_cd=6110000&_type=json&serviceKey=${SHELTER_KEY}`,
            method:"get",
        })
        .then((res) => {
            console.log(res.data);
        })
        .catch((err) => {
            console.log(err);
        })
    }

    const goToShelter=(shelter)=>{
        navigate(`/shelter/detail/${shelter}`);
    }

    useEffect(() =>{
        getSido();
    }, []);

    useEffect(()=>{
        getSigungu();
    }, [selectCd]);

    //지울 부분
    // useEffect(()=>{
    //     console.log('시군구', cggs);
    // }, [cggs]);

    const handleCd = (e) =>{
        setSelectCd(e.target.value);
        // console.log(e.target.value);
    }

    const handleCgg =(e)=>{
        setSelectCgg(e.target.value);
        // console.log(selectCgg);
    }

    //지역 검색 필터
    let cdQuery = "";
    let cggQuery = "";

    ///아...생각이 좀 필요하다
    //그리고...어떻게..........정보를 가져올지? 그것도 고민~
    const onSubmit=()=>{
        // console.log('등록!');
        //1. 시도 전체일 때
        if(selectCd !== '0') cdQuery =`&upr_cd=${selectCd}`;
        else cdQuery="";
        //2. 시군구 전체일 때
        if(selectCgg !== '0') cggQuery = `&org_cd=${selectCgg}`;
        else cggQuery="";
        //3. 둘다 검색일 때
        if(selectCd !== '0' && selectCgg !== '0'){
            axios({
                url: `${ANIMAL}/abandonmentPublicSrvc/shelter?upr_cd=${selectCd}&org_cd=${selectCgg}&_type=json&serviceKey=${ANIMAL_KEY}`,
                method: "get",
            })
            .then((res)=>{
                setAreaSearch([...res.data.response.body.items.item]);
                console.log(areaSearch);
            })
            .catch((err) =>{
                console.log(err);
            })
        }
    }

    return(
        <div className={style.main_container}>
            <header>
                <h2>동물 보호 센터</h2>
            </header>

            <div className={style.my_city}>
                <p>우리 지역 보호소 찾기</p>
            </div>

            <div className={style.search_bar}>
                <div className={style.search_input}>
                    <p>시도</p>
                    <select
                        name='searchCd'
                        value={selectCd}
                        onChange={handleCd}>
                        {
                            cds && cds.map((cd)=>(
                                <option
                                    value={cd.orgCd}
                                    key={cd.orgCd}>
                                {cd.orgdownNm}
                                </option>
                            ))
                        }
                    </select>

                    <p>시군구</p>
                    <select
                        name='searchCgg'
                        value={selectCgg}
                        onChange={handleCgg}>
                        {
                            cggs && cggs.map((cgg)=>(
                                <option
                                    value={cgg.orgCd}
                                    key={cgg.orgCd}>
                                {cgg.orgdownNm}
                                </option>
                            ))
                        }
                    </select>

                    <p>보호센터명</p>
                    <input 
                        type="text"
                        onChange={(e)=>{setNameSearch(e.target.value)}} />
                </div>

                <button type='submit' onClick={onSubmit}>조회</button>
            </div>

            <table className={cn("table table-bordered table-hover", style.my_table)}>
                <thead>
                    <tr>
                    <th scope="col">관할구역</th>
                    <th scope="col">보호센터명</th>
                    <th scope="col">전화번호</th>
                    <th scope="col">보호센터 주소</th>
                    </tr>
                </thead>
                <tbody>
                {
                    areaSearch && areaSearch.map((i)=>{
                        return(
                                <tr key={i.careNm} onClick={()=>{goToShelter(i.careRegNo)}}>
                                <td>f</td>
                                <td>f</td>
                                <td>f</td>
                                <td>{i.careNm}</td>
                                </tr>
                        )
                    })
                }
                </tbody>
                {/* <tbody>
                    <tr>
                    <td>어디</td>
                    <td>Mark</td>
                    <td>Otto</td>
                    <td>@mdo</td>
                    </tr>
                    <tr>
                    <td>어디</td>
                    <td>Jacob</td>
                    <td>Thornton</td>
                    <td>@fat</td>
                    </tr>
                    <tr>
                    <td>어디</td>
                    <td>어쩌고저쩌고</td>
                    <td>Larry the Bird</td>
                    <td>@twitter</td>
                    </tr>
                </tbody> */}
            </table>
        </div>
    )
}


export default ShelterList;