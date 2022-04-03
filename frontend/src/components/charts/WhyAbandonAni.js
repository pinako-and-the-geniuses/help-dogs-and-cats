import styled from "styled-components";
import { Bar } from "react-chartjs-2";
import Chart from "chart.js/auto";

const WhyAbandonAniMain = styled.div`
  margin-top: 70px;
`;

const WhyAbandonAniTitle = styled.div`
  font-size: 30px;
  margin-bottom: 20px;
`;

const WhyAbandonAniChart = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 70%;
  margin: auto;
`;

function WhyAbandonAni() {
  const options = {
    plugins: {
      title: {
        display: false,
        text: "동물을 유기하는 이유",
      },
      legend: {
        display: false, // label 보이기 여부
      },
    },

    indexAxis: "y",
    scales: {
      x: {
        grid: {
          borderColor: "black",
        },
      },
      y: {
        grid: {
          borderColor: "black",
        },
      },
    },

    // false : 사용자 정의 크기에 따라 그래프 크기가 결정됨.
    // true : 크기가 알아서 결정됨.
    maintainAspectRatio: false,
  };
  const data = {
    // 각 막대별 라벨
    labels: [
      "동물의 행동 문제",
      "예상보다 높은 지출",
      "동물의 질병 및 사고",
      "양육 여건의 변화",
      "예상보다 많은 시간 소요",
      "성장 후 기대와 다른 외모",
      "기타",
    ],
    datasets: [
      {
        borderWidth: 2, // 테두리 두께
        data: [27.8, 22.2, 18.9, 17.8, 6.2, 1.2, 5.9], // 수치
        backgroundColor: [
          "#ff6663",
          "#feb144",
          "#fdfd97",
          "#9ee09e",
          "#d0d0fe",
          "#9ec1cf",
          "#cc99c9",
        ],
      },
    ],
  };
  return (
    <>
      <WhyAbandonAniMain>
        <WhyAbandonAniTitle>동물을 유기하는 이유</WhyAbandonAniTitle>
        <WhyAbandonAniChart>
          <Bar data={data} width={50} height={400} options={options} />
        </WhyAbandonAniChart>
      </WhyAbandonAniMain>
    </>
  );
}

export default WhyAbandonAni;
