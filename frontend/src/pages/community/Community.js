import React, { useEffect, useState } from "react";
import axios from "axios";
import XMLParser from "react-xml-parser";
import style from "./styles/Community.module.scss";
import cn from "classnames";

export default function Community() {
  return (
    <div className={style.main_container}>
      <header>
        <h2>Community</h2>
      </header>

      <div className={style.search_bar}>
        <div className={style.search_input}>
          <select name="searchCd">
            <option selected>전체</option>
            <option value="1">공지</option>
            <option value="2">제보</option>
            <option value="3">잡담</option>
            <option value="4">입양후기</option>
            <option value="5">봉사후기</option>
          </select>

          <select name="searchCgg">
            <option selected>전체</option>
            <option value="1">글 제목</option>
            <option value="2">작성자</option>
          </select>
          <input type="text" />
        </div>

        <button type="submit">조회</button>
        
      </div>
      <div className="d-md-flex justify-content-md-end">
        <button type="submit">글쓰기</button>
      </div>
      
      {/* <table className="table table-bordered table-hover"> */}
      <table className={cn("table table-bordered table-hover", style.my_table)}>
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
          <tr>
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
        </tbody>
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
    </div>
  );
}
