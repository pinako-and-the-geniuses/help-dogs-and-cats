import React, { useEffect, useState } from 'react';
import axios from 'axios';
import XMLParser from 'react-xml-parser';
import style from './styles/ShelterList.module.scss';
import cn from 'classnames';

function ShelterList(){

    const APP_KEY = process.env.REACT_APP_SHELTER_API;

    const shelterList = async() => {
        await axios({
            url: `/shelterInfo?numOfRows=3&pageNo=1&serviceKey=${APP_KEY}`,
            method:"get",
        })
        .then((res) => {
            console.log(res.data);
        })
        .catch((err) => {
            console.log(err);
        })
    }

    useEffect(() =>{
        shelterList();
    }, []);

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
                    <select name='searchCd'>
                        <option value="0">전체</option>
                    </select>

                    <p>시군구</p>
                    <select name='searchCgg'>
                        <option value="0">전체</option>
                    </select>

                    <p>보호센터명</p>
                    <input type="text" />
                </div>

                <button type='submit'>조회</button>
            </div>

            {/* <table className="table table-bordered table-hover"> */}
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
                    <tr>
                    <td scope="row">어디</td>
                    <td>Mark</td>
                    <td>Otto</td>
                    <td>@mdo</td>
                    </tr>
                    <tr>
                    <td scope="row">어디</td>
                    <td>Jacob</td>
                    <td>Thornton</td>
                    <td>@fat</td>
                    </tr>
                    <tr>
                    <td scope="row">어디</td>
                    <td>어쩌고저쩌고</td>
                    <td>Larry the Bird</td>
                    <td>@twitter</td>
                    </tr>
                </tbody>
            </table>
        </div>
    )
}


export default ShelterList;