import st from "./styles/AdoptManageDetail.module.scss";
import cn from "classnames";
import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
export default function AdoptManageDetail() {
  const navigate = useNavigate();
  const getlist = () => {
    //console.log(seq);
    navigate(`/adoptmanage`);
  };
    return(
        <div className={st.adopt_commentBox}>
      <header>
        <h2>입양 인증 상세</h2>
      </header>
      <section className={st.topContent}>
        
            <div className={st.alltitle}>
              <p className={st.tag_p}>
                상태
              </p>
              <p className={st.title_p}>제목 : 인증</p>
            </div>
            <div
              className={st.content_div}
            >인증해주세요</div>
            <div className={st.contentbtn}>
              <button  className={st.listbutton} onClick={() => getlist()}>
                목록으로
              </button>
              <button  className={st.deletebutton}>
                반려
              </button>
              <button  className={st.button}>
                인증
              </button>
            </div>
      </section>

    </div>
    );
}