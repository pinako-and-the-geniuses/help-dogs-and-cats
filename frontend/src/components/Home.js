// import style from './styles/Home.module.scss';
import { useEffect } from "react";
import { useSelector } from "react-redux";
import "./styles/Home.css";
// import './main.js';

function Home() {
  const userInfo = useSelector((state) => state.userInfo);

  useEffect(() => {
    console.log(userInfo);
  }, []);
  return (
    <>
      <div id="hero" className="home">
        <div className="container">
          <div className="hero-content">
            <h1>
              I'm <span className="typed">안녕</span>
            </h1>
            <p>도와주개냥은 <br/>유기동물입양과 봉사활동의 활성화를 위한 문화공간입니다.</p>
          </div>
        </div>
      </div>

      <main>
        <div className="div1"></div>
        <div className="div2"></div>
      </main>
    </>
  );
}

export default Home;
