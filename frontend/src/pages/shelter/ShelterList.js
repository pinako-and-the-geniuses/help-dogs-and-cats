import React, { useEffect, useState } from 'react';
import axios from 'axios';
import XMLParser from 'react-xml-parser';
import './styles/ShelterList.scss';

function ShelterList(){

    const APP_KEY = "YhafXRaEZcUraLySTaruWx1ssdPqeN%2B321AePiKP1W%2Be2gVsPFNUBLDUcRsY3wClbC67IASuWXYx2XFGMezJaA%3D%3D";

    const shelterList = async() => {
        await axios({
            url: `/shelterInfo?numOfRows=3&pageNo=1&serviceKey=${APP_KEY}`,
            method:"get",
        })
        .then((res) => {
            const XMLParser = require('react-xml-parser');
            const xml = new XMLParser().parseFromString(res.data)
            console.log(xml);
        })
        .catch((err) => {
            console.log(err);
        })
    }

    useEffect(() =>{
        shelterList();
    }, []);

    return(
        <div className='main-container'>
            <header>
                <h2>동물 보호 센터</h2>
            </header>

            <div className='my-city'>
                <p>우리 지역 보호소 찾기</p>
            </div>

            <div className='search-bar'>
                <div className='search-input'>
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

            {/* <hr/> */}
            <table className="table table-bordered table-hover">
                <thead>
                    <tr>
                    {/* <th scope="col">#</th> */}
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