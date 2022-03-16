import React from 'react';
import { Pagination, PaginationItem, PaginationLink } from "reactstrap";
import './styles/ShelterList.scss';

function ShelterList(){

    return(
        <div>
            <h2>동물 보호 센터</h2>

            <div>
                <p>우리지역 보호소 찾기</p>
            </div>
            <div>
                <span>시도</span>
                <select name="" id="">
                    <option value="서울">서울</option>
                    <option value="경기">경기</option>
                </select>

                <span>시군구</span>
                <select name="" id="">
                    <option value="어쩌구">어쩌구</option>
                    <option value="저쩌구">저쩌구</option>
                </select>

                <span>보호센터명</span>
                <input type="text" />

                <button>조회</button>
            </div>

            <div>
                <table className='table caption-top'>
                    <tr>
                        <th>관할구역</th>
                        <th>보호센터명</th>
                        <th>전화번호</th>
                        <th>보호센터 주소</th>
                    </tr>
                    <tr>
                        <td>000</td>
                        <td>000</td>
                        <td>000</td>
                        <td>000</td>
                    </tr>
                    <tr>
                        <td>000</td>
                        <td>000</td>
                        <td>000</td>
                        <td>000</td>
                    </tr>
                    <tr>
                        <td>000</td>
                        <td>000</td>
                        <td>000</td>
                        <td>000</td>
                    </tr>
                    <tr>
                        <td>000</td>
                        <td>000</td>
                        <td>000</td>
                        <td>000</td>
                    </tr>
                </table>
            </div>

            <nav aria-label="Page navigation example">
                <Pagination>
                <PaginationItem>
                    <PaginationLink href="#pablo" onClick={e => e.preventDefault()}>
                    Previous
                    </PaginationLink>
                </PaginationItem>
                <PaginationItem>
                    <PaginationLink href="#pablo" onClick={e => e.preventDefault()}>
                    1
                    </PaginationLink>
                </PaginationItem>
                <PaginationItem>
                    <PaginationLink href="#pablo" onClick={e => e.preventDefault()}>
                    2
                    </PaginationLink>
                </PaginationItem>
                <PaginationItem>
                    <PaginationLink href="#pablo" onClick={e => e.preventDefault()}>
                    3
                    </PaginationLink>
                </PaginationItem>
                <PaginationItem>
                    <PaginationLink href="#pablo" onClick={e => e.preventDefault()}>
                    Next
                    </PaginationLink>
                </PaginationItem>
                </Pagination>
            </nav>

        </div>
    )
}


export default ShelterList;