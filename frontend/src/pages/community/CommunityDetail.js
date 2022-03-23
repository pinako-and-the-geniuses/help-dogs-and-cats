import React, { useEffect, useState } from "react";
import axios from "axios";
import XMLParser from "react-xml-parser";
import st from "./styles/CommunityDetail.module.scss";
import cn from "classnames";

export default function CommunityDetail() {
  return (
    <div className={st.body}>
      <header>
        <h2>Community</h2>
      </header>
      <section className={st.topContent}>
        <p className={st.tag_p}>[잡담]</p>
        <p className={st.title_p}>강아지 간식으로 이거 최곱니다.</p>
        <p className={st.read_p}>조회수</p>
        <p className={st.date_p}>2022.03.03</p>
        <p className={st.author_p}>김싸피</p>
        <div className={st.content_div}>이거 정말 맛있습니다.</div>
        {/* <textarea className={st.content_div}>
            이거 정말 맛있습니다.
          </textarea> */}
        <button type="button" className={cn(st.btn, st.button, "btn btn")}>
          수정
        </button>
      </section>
      <div className={st.middle_comment}>댓글</div>
      <section className={st.bottomContent}>
        <div className={st.onecomment}>
          <img
            src="https://mdbootstrap.com/img/Photos/Others/images/43.webp"
            className={st.img_thumbnail}
            alt="..."
          ></img>
          <p className={st.author_p}>아이디</p>
          <textarea className={st.content_div}></textarea>
          <button type="button" className={cn(st.button, "btn btn")}>
            작성
          </button>
        </div>
      </section>
    </div>
  );
}
