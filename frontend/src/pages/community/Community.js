import React, { useEffect, useState } from "react";
import axios from "axios";
import XMLParser from "react-xml-parser";
import { useParams, useNavigate } from 'react-router-dom';
import style from "./styles/Community.module.scss";
import cn from "classnames";
import Pagination from "./Pagination";
import moment from 'moment';

export default function Community() {
  const [communitys, setCommunity] = useState([]);
  const [page, setPage] = useState([]);
  //const [size, setSize] = useState([]);
  const size = 10;
  const [search, setSearch] = useState([]);
  const [tag, setTag] = useState([]);
  const [keyword, setKeyword] = useState([]);
  // const [currentPage, setCurrentPage] = useState(1);
  // const [communitysPerPage] = useState(10);

  // const indexOfLastPost = currentPage * communitysPerPage;
  // const indexOfFirstPost = indexOfLastPost - communitysPerPage;
  // const currentCommunitys = communitys.slice(indexOfFirstPost, indexOfLastPost);
  // const paginate = (pageNumber) => setCurrentPage(pageNumber);
  useEffect(() => { //시작할떄 나옴 //페이지가 바뀔떄마다 변경해줘야함
    axios.get(`${URL}/communities?page=${page}&size=${size}&tag=${tag}&search=${search}&keyword=${keyword}`)
    .then(response => setCommunity(response.data))
    .catch(err => console.log(err))
  }, []); //한번만 해줄때 []넣는다

  const getRead = (e) => {
    console.log("read",e.target.value);
    axios.get(`${URL}/communities?page=${page}&size=${size}&tag=${tag}&search=${search}&keyword=${keyword}`)
    .then(response => setCommunity(response.data))
    .catch(err => console.log(err))
  }

  const seq = useParams();
    console.log(seq);

    const navigate = useNavigate();

    const getSeq = () =>{
        console.log(seq);
        navigate(`/community/communitydetail/:${seq}`);
    }

    const getWrite = () =>{
      navigate(`/community/communitycreate`);
  }
    const getTag = (e) => {
      console.log("tag",e.target.value);
      setTag(e.target.value);
    }

    const getSearch = (e) => {
      console.log("search",e.target.value);
      setSearch("search",e.target.value);
    }

    const getKeyword = (e) => {
      console.log("keyword",e.target.value);
      setKeyword("keyword",e.target.value);
    }

    
  return (
    <div className={style.cummunity_container}>
      <header>
        <h2>Community</h2>
      </header>

      <div className={style.community_search_bar}>
        <div className={style.search_input}>
          <select name="searchCd" onChange={getTag}>
            <option defaultValue>태그</option>
            <option value="공지">공지</option>
            <option value="제보">제보</option>
            <option value="잡담">잡담</option>
            <option value="입양후기">입양후기</option>
            <option value="봉사후기">봉사후기</option>
          </select>

          <select name="searchCgg" onChange={getSearch}>
            <option defaultValue>검색</option>
            <option value="">전체</option>
            <option value="title">글 제목</option>
            <option value="writer">작성자</option>
          </select>
          <div>
          <input className={style.input} type="text" onChange={getKeyword}/>
          <button onClick={getRead}>조회</button>
        </div>
        </div>
        
      </div>
      <div className="d-md-flex justify-content-md-end">
        <button className={style.communitybutton} type="submit" onClick={getWrite}>글쓰기</button>
      </div>
      
      {/* <table className="table table-bordered table-hover"> */}
      <table className={cn("table table-hover", style.my_table)}>
        <thead>
          <tr>
            <th scope="col">태그</th>
            <th scope="col">제목</th>
            <th scope="col">작성자</th>
            <th scope="col">작성일</th>
            <th scope="col">조회수</th>
          </tr>
        </thead>
        <tbody>
          <tr onClick={getSeq}>
              <td>제보</td>
              <td>Mark</td>
              <td>Otto</td>
              <td>@mdo</td>
              <td>@mdo</td>
            </tr>
          {communitys.map(community => (
            <tr key={community.seq} onClick={getSeq}>
              <td>{community.tag}</td>
              <td>{community.title}</td>
              <td>{community.writer}</td>
              <td>{moment(community.date).format('YYYY-MM-DD')}</td>
              <td>{community.readCount}</td>
            </tr>
          ))}   
        </tbody>
        {/* <tbody>
          
          <tr onClick={getID}>
            <td scope="row">제보</td>
            <td>Mark</td>
            <td>Otto</td>
            <td>@mdo</td>
            <td>@mdo</td>
          </tr>
          <tr>
            <td scope="row">잡담</td>
            <td>Jacob</td>
            <td>Thornton</td>
            <td>@fat</td>
            <td>@mdo</td>
          </tr>
          <tr>
            <td scope="row">봉사후기</td>
            <td>어쩌고저쩌고</td>
            <td>Larry the Bird</td>
            <td>@twitter</td>
            <td>@mdo</td>
          </tr>
        </tbody> */}
      </table>
      <nav aria-label="Page navigation example">
        <ul className="pagination justify-content-center">
          <li className="page-item">
            <a className="page-link" href="#" aria-label="Previous">
              <span aria-hidden="true">&laquo;</span>
            </a>
          </li>
          <li className="page-item">
            <a className="page-link" href="#">
              1
            </a>
          </li>
          <li className="page-item">
            <a className="page-link" href="#">
              2
            </a>
          </li>
          <li className="page-item">
            <a className="page-link" href="#">
              3
            </a>
          </li>
          <li className="page-item">
            <a className="page-link" href="#" aria-label="Next">
              <span aria-hidden="true">&raquo;</span>
            </a>
          </li>
        </ul>
      </nav>
      {/* <Pagination communitysPerPage={communitysPerPage} totalCommunitys={communitys.length} 
      currentPage={currentPage} paginate={paginate}></Pagination> */}
    </div>
  );
}
