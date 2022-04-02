// import style from './styles/Home.module.scss';
import { useEffect } from "react";
import { useSelector } from "react-redux";
import "./styles/Home.css";
// import "./main.js";
import HowToGetAni from "./charts/HowToGetAni";
import WhyAbandonAni from "./charts/WhyAbandonAni";
import AnnualBreed from "./charts/AnnualBreed";
import AnnualState from "./charts/AnnualState";
import AgeState from "./charts/AgeState";
import SpeciesNeutral from "./charts/SpeciesNeutral";
import styled from "styled-components";

const ChartMain = styled.div`
  width: 60%;
  margin: auto;
`;

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
            <p>
              도와주개냥은 <br />
              유기동물입양과 봉사활동의 활성화를 위한 문화공간입니다.
            </p>
          </div>
        </div>
      </div>

      <main>
        <ChartMain>
          <div>
            <AnnualBreed />
          </div>
          <div>
            <HowToGetAni />
          </div>
          <div>
            <WhyAbandonAni />
          </div>
          <div>
            <AnnualState />
          </div>
          <div>
            <AgeState />
          </div>
          <div>
            <SpeciesNeutral />
          </div>
        </ChartMain>
      </main>
    </>
  );
}

export default Home;
