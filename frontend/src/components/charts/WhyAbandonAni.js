import styled from "styled-components";
import { Bar } from "react-chartjs-2";
import Chart from "chart.js/auto";
import ChartDataLabels from "chartjs-plugin-datalabels";
import { Col, Container, Row } from "react-bootstrap";

const WhyAbandonAniMain = styled.div`
  margin-top: 120px;
`;

const WhyAbandonAniTitle = styled.div`
  text-align: center;
  font-weight: bold;
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

const ChartMessage = styled.div`
  margin-top: 3%;
  width: 100%;
  height: 100%;
  color: black;
  font-size: 20px;
`;

const SpanBold = styled.span`
  font-size: 25px;
  font-weight: bold;
`;

const ChartBox = styled.div`
  margin-top: 50px;
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
    maintainAspectRatio: false,
  };
  const plugins = [ChartDataLabels];
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
        datalabels: {
          display: true,
          color: "black",
          labels: {
            title: {
              font: {
                size: 18,
              },
            },
          },
        },
        borderWidth: 2, // 테두리 두께
        data: [27.8, 22.2, 18.9, 17.8, 6.2, 1.2, 5.9], // 수치
        backgroundColor: [
          "rgba(255, 102, 99, 0.6)",
          "rgba(254, 177, 68, 0.6)",
          "rgba(253, 253, 151, 0.6)",
          "rgba(158, 224, 158, 0.6)",
          "rgba(208, 208, 254, 0.6)",
          "rgba(158, 193, 207, 0.6)",
          "rgba(204, 153, 201, 0.6)",
        ],
      },
    ],
  };
  return (
    <>
      <WhyAbandonAniMain>
        <WhyAbandonAniTitle>동물을 파양하는 이유(%)</WhyAbandonAniTitle>
        <Container>
          <Row>
            <Col xs={12}>
              <ChartBox>
                <WhyAbandonAniChart>
                  <Bar
                    data={data}
                    width={50}
                    height={400}
                    options={options}
                    plugins={plugins}
                  />
                </WhyAbandonAniChart>
              </ChartBox>
            </Col>
            <Col xs={12}>
              <ChartMessage>
                1위는 동물의 배변, 짖음과 같은 <SpanBold>행동문제</SpanBold>
                입니다. <br />
                2~3위는 지출, 치료비와같은
                <SpanBold> 금전적인 문제</SpanBold>입니다.
                <br />
                <br />
                <br />
                <SpanBold>적절한 훈육</SpanBold>이 있다면 1위, 행동문제는 충분히
                해결될 수 있습니다. <br />
                또한
                <SpanBold> 반려동물 관련 정보를 사전에 숙지</SpanBold>
                했다면 2위, 3위와 같은 문제도 발생하지 않았을 것입니다.
                <br />
                <br />
                <br />
                훈육에대한 <SpanBold>책임 결여</SpanBold>와<br /> 반려동물에
                관한
                <SpanBold> 정보 미숙</SpanBold>, <br />
                너무나 쉬운
                <SpanBold> 믹스품종 분양 환경</SpanBold>은 <br />
                높은 믹스동물 유기율의 원인이라 할 수 있습니다.
                <br />
                <br />
                <br /> 이에 도와주개냥은 <br />
                믹스동물의 유기율을 낮추기 위해
                <br />
                <SpanBold>반려동물 입양 전 사전교육 의무화,</SpanBold>
                <br />
                <SpanBold>반려동물 등록제 미 이행시 처벌 강화</SpanBold>를
                강력히 촉구합니다.
              </ChartMessage>
            </Col>
          </Row>
        </Container>
      </WhyAbandonAniMain>
    </>
  );
}

export default WhyAbandonAni;
