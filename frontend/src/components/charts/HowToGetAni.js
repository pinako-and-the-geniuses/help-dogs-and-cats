import styled from "styled-components";
import { PolarArea } from "react-chartjs-2";
import Chart from "chart.js/auto";

const HowToGetAniMain = styled.div`
  margin-top: 70px;
`;

const HowToGetAniTitle = styled.div`
  font-size: 30px;
  margin-bottom: 20px;
`;

const HowToGetAniChart = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 40%;
  margin: auto;
`;

function HowToGetAni() {
  const options = {
    plugins: {
      title: {
        display: false,
        text: "반려 동물을 기르게 된 경로(%)",
      },
      legend: {
        display: true,
      },
      scales: {
        yAxes: [
          {
            ticks: {
              min: 0,
              stepSize: 1,
            },
          },
        ],
      },
      maintainAspectRatio: false,
    },
  };
  const data = {
    // 각 막대별 라벨
    labels: [
      "반려동물 있다",
      "동물판매업소",
      "아는사람",
      "친지가 무상제공",
      "유기동물 입양",
      "원래 기르던 동물의 새끼",
    ],
    datasets: [
      {
        borderWidth: 2, // 테두리 두께
        data: [12.7, 39.6, 15.4, 27.9, 13.6, 3.5], // 수치
        backgroundColor: [
          "#ff6663",
          "#feb144",
          "#fdfd97",
          "#9ee09e",
          "#9ec1cf",
          "#cc99c9",
        ],
      },
    ],
  };
  return (
    <>
      <HowToGetAniMain>
        <HowToGetAniTitle>반려동물을 기르게 된 경로(%)</HowToGetAniTitle>
        <HowToGetAniChart>
          <PolarArea data={data} width={50} height={100} options={options} />
        </HowToGetAniChart>
      </HowToGetAniMain>
    </>
  );
}

export default HowToGetAni;
