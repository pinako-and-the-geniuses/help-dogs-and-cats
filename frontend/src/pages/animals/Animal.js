import animal from "./styles/Animal.module.scss";
import React, { useEffect, useState } from "react";
import cn from "classnames";
import axios from "axios";
import { URL, ANIMAL } from "../../public/config/index";
import Box from "../../components/animals/Box";
export default function Animal() {
  const SHELTER_KEY = process.env.REACT_APP_SHELTER_API;
  const ANIMAL_KEY = process.env.REACT_APP_ANIMAL_API;
  const all = [
    {
      orgCd: "0",
      orgdownNm: "전체",
    },
  ];
  const [cds, setCds] = useState();
  const [selectCd, setSelectCd] = useState();
  const [cggs, setCggs] = useState([...all]);
  const [selectCgg, setSelectCgg] = useState();
  const [areaSearch, setAreaSearch] = useState();
  //시도 가져오기
  const getSido = async () => {
    await axios({
      url: `${ANIMAL}/abandonmentPublicSrvc/sido?numOfRows=20&pageNo=1&_type=json&serviceKey=${ANIMAL_KEY}`,
      method: "get",
    })
      .then((res) => {
        console.log(res.data.response.body.items.item);
        setCds([...all, ...res.data.response.body.items.item]);
      })
      .catch((err) => {
        console.log(err);
      });
  };

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
  useEffect(() => {
    getSido();
  }, []);

  useEffect(()=>{
    getSigungu();
  }, [selectCd]);

  useEffect(()=>{
    getSigungu();
  }, [selectCgg]);

  const handleCd = (e) => {
    setSelectCd(e.target.value);
    // console.log(e.target.value);
  };

  const handleCgg =(e)=>{
    setSelectCgg(e.target.value);
    // console.log(selectCgg);
  }

  //지역 검색 필터
  let cdQuery = "";
  let cggQuery = "";

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
  return (
    <div className={animal.animalcontent}>
      <header>
        <h2>유기동물 조회</h2>
      </header>
      <article className={animal.topContent}>
        <div className={animal.box}>
          <span className={animal.date}>
            <input type="date" />
          </span>
          <select className={animal.textBox} aria-label="시도">
            <option selected>시도</option>
            {/* <option value="1">One</option>
            <option value="2">Two</option>
            <option value="3">Three</option> */}
            <select name="searchCd" value={selectCd} onChange={handleCd}>
              {cds &&
                cds.map((cd) => (
                  <option value={cd.orgCd} key={cd.orgCd}>
                    {cd.orgdownNm}
                  </option>
                ))}
            </select>
          </select>
          <select className={animal.textBox} aria-label="시군구">
            <option selected>시군구</option>
            <option value="1">One</option>
            <option value="2">Two</option>
            <option value="3">Three</option>
          </select>
          <select className={animal.textBox} aria-label="보호센터">
            <option selected>보호센터</option>
            <option value="1">One</option>
            <option value="2">Two</option>
            <option value="3">Three</option>
          </select>
          <button type="button" className={cn(animal.btn, "btn btn")}>
            조회
          </button>
          <div className={animal.box2}>
            <select className={animal.textBox} aria-label="축종">
              <option selected>축종</option>
              <option value="1">One</option>
              <option value="2">Two</option>
              <option value="3">Three</option>
            </select>
            <div className="form-check form-check-inline mx-4">
              <input
                className="form-check-input"
                type="radio"
                name="inlineRadioOptions"
                id="inlineRadio1"
                value="option1"
              />
              <label className="form-check-label" for="inlineRadio1">
                남
              </label>
            </div>
            <div className="form-check form-check-inline">
              <input
                className="form-check-input"
                type="radio"
                name="inlineRadioOptions"
                id="inlineRadio2"
                value="option2"
              />
              <label className="form-check-label" for="inlineRadio2">
                여
              </label>
            </div>
          </div>
        </div>
      </article>
      <article className={animal.bottomContent}>
        {/* <div className="container">
          <div className="row">
            <div className="col"><Box></Box></div>
            <div className="col"><Box></Box></div>
            <div className="col"><Box></Box></div>
          </div>
        </div> */}
        <Box></Box>
      </article>
    </div>
  );
}
