import st from "./styles/Home.module.scss";
import React from "react";
import { Link } from 'react-router-dom';

function ManageHome({tab, setTab}) {
  return (
    <div className={st.adimincontainer}>
        <span className={st.left} onClick={()=>{setTab(1)}}> 
        {/* <Link to="/volunteermanage"> */}
            <h3 className={st.managetext}>봉사 현황</h3>
            <span><img src="https://mdbootstrap.com/img/Photos/Others/images/43.webp" class="img-thumbnail" alt="봉사 현황"></img></span>
        {/* </Link> */}

        </span>
        <span className={st.right} onClick={()=>{setTab(2)}}>
        {/* <Link to="/adoptmanage">   */}
            <h3 className={st.managetext}>입양 현황</h3>
            <img src="https://mdbootstrap.com/img/Photos/Others/images/43.webp" class="img-thumbnail" alt="입양 현황"></img>
        {/* </Link>   */}
        </span>
    </div>
  );
}

export default ManageHome;
