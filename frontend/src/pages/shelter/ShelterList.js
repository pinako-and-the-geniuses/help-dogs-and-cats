import React from 'react';
import './styles/ShelterList.scss';

function ShelterList(){

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
                    <p><label for="serachCd">시도</label></p>
                    <select name='searchCd'>
                        <option value="0">전체</option>
                    </select>

                    <p><label for="searchCgg">시군구</label></p>
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