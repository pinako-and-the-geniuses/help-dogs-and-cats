import React, { useState } from "react";
import ManageHome from "./ManageHome";
import VolunteerManage from "./VolunteerManage";
import VolunteerManageDetail from "./VolunteerManageDetail";

import AdoptManage from "./AdoptManage";
import AdoptManageDetail from "./AdoptManageDetail";

import style from "./styles/manager.module.scss";

function MoveTab({ tab, setTab }) {
  const [adoptSeq, setAdoptSeq] = useState();
  const [volunSeq, setVolunSeq] = useState();
  if (tab === 0) {
    return <ManageHome tab={tab} setTab={setTab} />;
  }
  // 1. 봉사인증목록 2.봉사인증상세 3.입양인증목록 4. 입양인증상세
  if (tab === 1) {
    return <VolunteerManage setTab={setTab} setVolunSeq={setVolunSeq} />;
  }
  if (tab === 2) {
    return <VolunteerManageDetail setTab={setTab} volunSeq={volunSeq} />;
  }
  if (tab === 3) {
    return <AdoptManage setTab={setTab} setAdoptSeq={setAdoptSeq} />;
  }
  if (tab === 4) {
    return <AdoptManageDetail adoptSeq={adoptSeq} setTab={setTab} />;
  }
}

function ManageMain() {
  const [tab, setTab] = useState(0);

  return (
    <div className={style.manageHome}>
      <ul className={style.menus}>
        <li
          className={tab === 0 ? style.on : ""}
          onClick={() => {
            setTab(0);
          }}
        >
          HOME
        </li>
        <li
          className={tab === 1 || tab === 2 ? style.on : ""}
          onClick={() => {
            setTab(1);
          }}
        >
          봉사 관리
        </li>
        <li
          className={tab === 3 || tab === 4 ? style.on : ""}
          onClick={() => {
            setTab(3);
          }}
        >
          입양 관리
        </li>
      </ul>

      <MoveTab tab={tab} setTab={setTab} />
    </div>
  );
}

export default ManageMain;
