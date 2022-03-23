import React, { useEffect, useState } from "react";
import axios from "axios";
import XMLParser from "react-xml-parser";
import st from "./styles/CommunityDetail.module.scss";
import cn from "classnames";
import Comment from 'components/Comment/Comment';


export default function CommunityDetail() {
  return (
    <div className={st.community_commentBox}>
      <header>
        <h2>Community</h2>
      </header>
      <section className={st.topContent}>
        <div className={st.title}>
          <p className={st.tag_p}>[잡담]</p>
          <p className={st.title_p}>강아지 간식으로 이거 최곱니다.</p>
          <p className={st.read_p}>조회수</p>
          <p className={st.date_p}>2022.03.03</p>
          <p className={st.author_p}>김싸피</p>
        </div >
        
          <div className={st.content_div}>이거 정말 맛있습니다.</div>
          {/* <textarea className={st.content_div}>
              이거 정말 맛있습니다.
            </textarea> */}
        <div className={st.content}>
          <button type="button" className={st.button}>
            수정
          </button>
        </div>
      </section>
      <Comment/>
    </div>
  );
}
