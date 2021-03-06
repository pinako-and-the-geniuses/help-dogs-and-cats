import React, { useEffect, useState } from "react";
import Map from "../../components/Map";
import style from "./styles/ShelterDetail.module.scss";
import cn from "classnames";
import { useSelector } from "react-redux";

function ShelterDetail() {
  const info = useSelector((state) => state.shelterInfo.info);
  const [data, setData] = useState("");

  useEffect(() => {
    setData(info);
  }, []);

  return (
    <div className={style.shelter_container}>
      <main className={style.detailmain}>
        <header>
          <h2>동물 보호 센터</h2>
        </header>
        <section className={style.topContent}>
          <div className={style.row}>
            <div className={cn(style.main_info)}>
              <div className={style.text_box}>
                <p className={style.title}>보호센터명</p>
                <p>{data.shelterName}</p>
              </div>
              <div className={style.text_box}>
                <p className={style.title}>전화번호</p>
                <p>{data.tel}</p>
              </div>
              <div className={style.text_box}>
                <p className={style.title}>주소</p>
                <p>{data.address}</p>
              </div>
              <div className={style.text_box}>
                <p className={style.title}>관리기관명</p>
                <p>{data.organizationName}</p>
              </div>
              <div className={style.text_box}>
                <p className={style.title}>운영시간</p>
                <p>
                  {data.weekOperationStartTime} ~ {data.weekOperationEndTime}
                </p>
              </div>
              <div className={style.text_box}>
                <p className={style.title}>유형</p>
                <p>{data.divisionName}</p>
              </div>
              <div className={style.text_box}>
                <p className={style.title}>구조대상동물</p>
                <p>{data.saveTargetAnimal}</p>
              </div>
            </div>
            {data.lat !== "null" && data.lng !== "null" && (
              <div className={cn(style.map, "col-6")}>
                <Map lat={info.lat} lng={info.lng} />
              </div>
            )}
          </div>
        </section>
        <div className={style.gotoList}>
          <a type="button" className={style.button} href="/shelter/list">
            목록으로
          </a>
        </div>
      </main>
    </div>
  );
}

export default ShelterDetail;
