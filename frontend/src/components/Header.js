import { useNavigate } from 'react-router-dom';
// import style from "./styles/Header.module.scss";
// import './main.js';
import './styles/Header.css';
import cn from 'classnames';

export default function Header() {
  const navigate = useNavigate();
  const onHomeClick = () =>{
    navigate("/");
  }

  return (
    // <div className={style.header}>
    //   {/* <h1>header부분!!!!</h1> */}
    //   <div 
    //     className={style.logo} 
    //     onClick={()=>{navigate("/")}}>로고</div>

    //   <div className={style.menus}>
    //     <p 
    //       className={style.menu_item}
    //       onClick={()=>{}}>유기동물현황</p>
    //     <ul 
    //       className={cn(style.menu_item, 'dropdown')}
    //       onClick={()=>{}}>동물 프로필</ul>
    //     <p 
    //       className={style.menu_item}
    //       onClick={()=>{}}>봉사활동</p>
    //     <p 
    //       className={style.menu_item}
    //       onClick={()=>{}}>커뮤니티</p>
    //     <p 
    //       className={style.menu_item}
    //       onClick={()=>{}}>후원하기</p>
    //   </div>

      
    // </div>
    <div id="header" className='fixed-top header-inner-pages'>
      <div className="container d-flex align-items-center justify-content-between">

        <a href="/" className="logo">LOGO</a>

        <nav id="navbar" className="navbar">
          <ul>
            <li><a className="nav-link scrollto active" href="#hero">유기동물현황</a></li>
            <li className="dropdown"><a href="#"><span>동물 프로필</span> <i className="bi bi-chevron-down"></i></a>
              <ul>
                <li><a href="#">입양 가이드</a></li>
                <li><a href="/animals/animal">유기동물 조회</a></li>
                <li><a href="/shelter/list">보호소 조회</a></li>
              </ul>
            </li>
            <li><a className="nav-link scrollto" href="/volunteer/list">봉사활동</a></li>
            <li><a className="nav-link scrollto" href="/community/community">커뮤니티</a></li>
          </ul>
          <i className="bi bi-list mobile-nav-toggle"></i>
        </nav>

      </div>
    </div>
  );
}
