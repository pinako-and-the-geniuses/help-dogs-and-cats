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
  console.log(info);

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
                  {data.weekOperationStartTIme} ~ {data.weekOperationEndTime}
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
            {data.lat && data.lng && (
              <div className={cn(style.map, "col-6")}>
                <Map lat={data.lat} lng={data.lng} />
              </div>
            )}
          </div>
        </section>
      </main>
    </div>
  );
}

export default ShelterDetail;
