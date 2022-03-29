import React, { useEffect, useState } from "react";
import axios from "axios";
import { URL } from "public/config";
import XMLParser from "react-xml-parser";
import { useParams, useNavigate } from "react-router-dom";
import style from "./styles/Community.module.scss";
import cn from "classnames";
import "./styles/Paging.css";
import Pagination from "react-js-pagination";
export default function Community() {
  const [communitys, setCommunity] = useState("");
  const [page, setPage] = useState("");
  //const [size, setSize] = useState("");
  const [totalcount, setTotalcount] = useState("");
  const [totalPageNumber, setTotalPageNumber] = useState("");
  const size = 10;
  const [search, setSearch] = useState("");
  const [category, setCategory] = useState("");
  const [keyword, setKeyword] = useState("");
  const [seq, setSeq] = useState("");
  
  //const [arr, setArr] = useState();
  
  console.log(page, totalcount, totalPageNumber);
  useEffect(() => {
    //시작할떄 나옴 //페이지가 바뀔떄마다 변경해줘야함
    axios
      .get(
        `${URL}/communities?page=1&size=${size}&category=${category}&search=${search}&keyword=${keyword}`
      )
      .then((response) => {
        setCommunity(response.data.data.communitiesForPage);
        setPage(response.data.data.currentPageNumber);
        setTotalcount(response.data.data.totalCount);
        setTotalPageNumber(response.data.data.totalPageNumber);
        //setArr([response.data.data.currentPageNumber, response.data.data.totalCount, response.data.data.totalPageNumber])
        console.log(response.data);
      })
      .catch((err) => console.log(err));
  }, []); //한번만 해줄때 []넣는다

  const getRead = (e) => {
    console.log(category, search, keyword, size, page);
    console.log("read", e.target.value);
    axios
      .get(
        `${URL}/communities?page=${page}&size=${size}&category=${category}&search=${search}&keyword=${keyword}`
      )
      .then((response) => {
        setCommunity(response.data.data.communitiesForPage);
        setPage(response.data.data.currentPageNumber);
        console.log(response.data);
      }) //콘솔에 있는 것에서 data를 한번 더 들어가려면 이렇게 쓰면 된다.
      .catch((err) => console.log(err));
  };

  const getPage = (e) => {
    // const key = e.target.value;
    // console.log("page",e.target.value);
    // setPage(key);
  };

  const navigate = useNavigate();

  const getSeq = (seq) => {
    //console.log(seq);
    navigate(`/community/communitydetail/${seq}`);
  };

  const getWrite = () => {
    navigate(`/community/communitycreate`);
  };
  const getCategory = (e) => {
    //console.log("tag",e.target.value);
    setCategory(e.target.value);
  };

  const getSearch = (e) => {
    console.log("search", e.target.value);
    setSearch(e.target.value);
  };

  const getKeyword = (e) => {
    const key = e.target.value;
    console.log("keyword", key);
    setKeyword(key);
  };

  //console.log( category, search, keyword, size, page);
  return (
    <div className={style.cummunity_container}>
      <header className={style.communhead}>
        <h2>Community</h2>
      </header>

      <div className={style.community_search_bar}>
        <div className={style.search_input}>
          <select defaultValue="" name="searchCd" onChange={getCategory}>
            <option value="">카테고리</option>
            <option value="report">제보</option>
            <option value="general">잡담</option>
            <option value="review">후기</option>
          </select>

          <select defaultValue="" name="searchCgg" onChange={getSearch}>
            <option value="all">전체</option>
            <option value="title">글 제목</option>
            <option value="writer">작성자</option>
          </select>
          <div>
            <input className={style.input} type="text" onChange={getKeyword} />
            <button onClick={getRead}>조회</button>
          </div>
        </div>
      </div>
      <div className="d-md-flex justify-content-md-end">
        <button
          className={style.communitybutton}
          type="submit"
          onClick={getWrite}
        >
          글쓰기
        </button>
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
        {communitys ? (
          <tbody>
            {communitys.map((community) => (
              <tr key={community.seq} onClick={()=>getSeq(community.seq)}> 
                {community.category === "REPORT" ? <td>제보</td> : ""}
                {community.category === "REVIEW" ? <td>후기</td> : ""}
                {community.category === "GENERAL" ? <td>잡담</td> : ""}
                <td>{community.title}</td>
                <td>{community.memberNickname}</td>
                <td>{community.createdDate}</td>
                <td>{community.viewCount}</td>
              </tr>
            ))}
          </tbody>
        ) : (
          <td colSpan="5">작성 글이 없습니다.</td>
        )}
      </table>
      <Pagination
      onClick={getPage}
      activePage={page}
      itemsCountPerPage={size}
      totalItemsCount={totalcount}
      pageRangeDisplayed={totalPageNumber}
      prevPageText={"‹"}
      nextPageText={"›"}
    />
      {/* <nav aria-label="Page navigation example">
        <ul className="pagination justify-content-center" onClick={getPage}>
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
      </nav> */}
      
    </div>
  );
}
