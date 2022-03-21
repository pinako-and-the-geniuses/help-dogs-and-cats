import animaldetails from "./styles/AnimalDetails.module.scss";
import React, { useEffect, useState } from 'react';
import Map from "components/Map";
import cn from 'classnames'
import axios from 'axios';
import { XMLParser } from 'react-xml-parser';
export default function AnimalDetails() {
  // function parseStr(dataSet){
  //   const dataArr = new XMLParser().parseFromString(dataSet).children;
  //   console.log(dataArr);
  // }

  // async function getAPI(){
  //   await axios({
  //     method: "get",
  //     url: `/abandonmentPublic?bgnde=20211201&endde=20211231&pageNo=1&numOfRows=10&serviceKey=eG8m8qY9CcGy8%2FVFSZwJHkwAJBQydbN%2B6v2dshFjA8dkOWbxsMqmot3fXOHl5ieSa8aXcDydP7PKNhGGrLAy6Q%3D%3D`,
  //   }).then(function(response){
  //     const dataSet = response.data;
  //     parseStr(dataSet);
  //   });
  // }

  // useEffect(() =>{
  //   getAPI();
  // }, []);

  const APP_KEY = process.env.REACT_APP_ANIMAL_API;

    const getAPI = async() => {
        await axios({
            url: `/abandonmentPublicSrvc/abandonmentPublic?bgnde=20211201&endde=20211231&pageNo=1&numOfRows=10&serviceKey=${APP_KEY}`,
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
      getAPI();
    }, []);
  return (
    <body>
      <header>
        <h2>보호 중 동물</h2>
      </header>
      <main>
        <section className={animaldetails.topContent}>
          <div className={animaldetails.mainInfo}>
            <div className={cn(animaldetails.detailscard, "h-50", "mx-5")} style={{ width: "23rem" }}>
              <img
                src="https://mdbootstrap.com/img/Photos/Others/images/43.webp"
                className="card-img-top"
                alt="..."
              />
              <div className="card-body">
                <span className="badge rounded-pill bg-secondary">
                  Secondary
                </span>
                <div className={animaldetails.cardText}>
                  <p className={animaldetails.title}>공고번호</p>
                  <p>0000000000</p>
                </div>
                <div className={animaldetails.cardText}>
                  <p className={animaldetails.title}>접수일시</p>
                  <p>2022-03-22</p>
                </div>
                <div className={animaldetails.cardText}>
                  <p className={animaldetails.title}>발견장소</p>
                  <p>멀티캠퍼스</p>
                </div>
                <button
                  type="button"
                  className="btn btn-secondary"
                  data-bs-toggle="modal"
                  data-bs-target="#exampleModal"
                >
                  입양가이드
                </button>
                <div
                  className="modal fade"
                  id="exampleModal"
                  tabindex="-1"
                  aria-labelledby="exampleModalLabel"
                  aria-hidden="true"
                >
                  <div className="modal-dialog modal-xl modal-dialog-scrollable">
                    <div className="modal-content">
                      <div className="modal-header">
                        <h5 className="modal-title" id="exampleModalLabel">
                          입양 가이드
                        </h5>
                        <button
                          type="button"
                          className="btn-close"
                          data-bs-dismiss="modal"
                          aria-label="Close"
                        ></button>
                      </div>
                      <div className="modal-body">안뇽하세요
                      <br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br />
                      <br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br />
                      <br /></div>
                      <div className="modal-footer">
                        <button
                          type="button"
                          className="btn btn-secondary"
                          data-bs-dismiss="modal"
                        >
                          후원하기
                        </button>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <div className="tablebox mx-5">
              {/* <div className="text-box">
                <p className="title">품종</p>
                <p>멀티캠퍼스</p>
              </div>
              <div className="text-box">
                <p className="title">연생</p>
                <p>02-000-0000</p>
              </div>
              <div className="text-box">
                <p className="title">kg</p>
                <p>서울특별시 강남구 테헤란로 121</p>
              </div>
              <div className="text-box">
                <p className="title">성별</p>
                <p>삼성멀티캠퍼스 (법인)</p>
              </div>
              <div className="text-box">
                <p className="title">중성화여부</p>
                <p>9:00 ~ 18:00</p>
              </div>
              <div className="text-box">
                <p className="title">색상</p>
                <p>노랑</p>
              </div>
              <div className="text-box">
                <p className="title">특징</p>
                <p>개+고양이+기타</p>
              </div> */}
              <table className="table table-hover">
                <thead>
                  <tr>
                    <th className={animaldetails.head} scope="col" colspan="4">
                      동물의 정보
                    </th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <th scope="row">품종</th>
                    <td colspan="3">Mark</td>
                  </tr>
                  <tr>
                    <th scope="row">연생</th>
                    <td colspan="3">Mark</td>
                  </tr>
                  <tr>
                    <th scope="row">kg</th>
                    <td colspan="3">Mark</td>
                  </tr>
                  <tr>
                    <th scope="row">성별</th>
                    <td colspan="3">000-0000-0000</td>
                  </tr>
                  <tr>
                    <th scope="row">중성화여부</th>
                    <td colspan="3">000-0000-0000</td>
                  </tr>
                  <tr>
                    <th scope="row">색상</th>
                    <td colspan="3">000-0000-0000</td>
                  </tr>
                  <tr>
                    <th scope="row">특징</th>
                    <td colspan="3">000-0000-0000</td>
                  </tr>
                </tbody>
              </table>

              <table className="table table-hover">
                <thead>
                  <tr>
                    <th className={animaldetails.head} scope="col" colspan="4">
                      보호정보
                    </th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <th scope="row">관할기관</th>
                    <td colspan="3">Mark</td>
                  </tr>
                  <tr>
                    <th scope="row">보호센터</th>
                    <td colspan="3">Mark</td>
                  </tr>
                  <tr>
                    <th scope="row">보호소 주소</th>
                    <td colspan="3">Mark</td>
                  </tr>
                  <tr>
                    <th scope="row">연락처</th>
                    <td colspan="3">000-0000-0000</td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </section>
        <div className="bottom-content">
            <Map></Map>
        </div>
      </main>
    </body>
  );
}
