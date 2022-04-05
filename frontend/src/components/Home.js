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
  background-color: whitesmoke;
  padding: 20px;
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
            <p>
              <span>도와주개냥</span>은
            </p>
            <p>
              유기동물<strong>입양</strong>과 <strong>봉사활동</strong>의
              활성화를 위한 문화공간입니다
            </p>
          </div>
        </div>
      </div>

      <main id="chart">
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
