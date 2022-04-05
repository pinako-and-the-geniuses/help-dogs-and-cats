import styled from "styled-components";
import { Doughnut } from "react-chartjs-2";
import Chart from "chart.js/auto";
import ChartDataLabels from "chartjs-plugin-datalabels";
import { Container, Row, Col } from "react-bootstrap";

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
  width: 80%;
  margin: auto;
`;
const SpanBold = styled.span`
  font-size: 25px;
  font-weight: bold;
`;

const ChartBox = styled.div``;
const ChartMessage = styled.div`
  margin-top: 5%;
  width: 100%;
  height: 100%;
  color: black;
  font-size: 20px;
`;

function HowToGetAni() {
  const options = {
    plugins: {
      legend: {
        display: true,
        position: "bottom",
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
  const plugins = [ChartDataLabels];

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
        data: [12.7, 39.6, 15.4, 27.9, 13.6, 3.5], // 수치
        backgroundColor: [
          "rgba(255, 102, 99, 0.6)",
          "rgba(254, 177, 68, 0.6)",
          "rgba(253, 253, 151, 0.6)",
          "rgba(158, 224, 158, 0.6)",
          "rgba(158, 193, 207, 0.6)",
          "rgba(204, 153, 201, 0.6)",
        ],
      },
    ],
  };
  return (
    <>
      <HowToGetAniMain>
        <HowToGetAniTitle>반려동물을 기르게 된 경로(%)</HowToGetAniTitle>
        <Container>
          <Row>
            <Col xs={7}>
              <ChartBox>
                <HowToGetAniChart>
                  <Doughnut
                    data={data}
                    width={50}
                    height={450}
                    options={options}
                    plugins={plugins}
                  />
                </HowToGetAniChart>
              </ChartBox>
            </Col>
            <Col xs={5}>
              <ChartMessage>
                애완동물 분양샵은 대체로 순종견, 순종묘를 분양합니다. 믹스동물에
                비해 희소성이 높고 분양가격이 비싸기 때문이죠 <br />
                <br />
                그렇기에, 대부분의 믹스동물들은
                <br />
                <SpanBold>친척이나 아는사람 등을 통해 </SpanBold>
                분양되었다고
                <br />볼 수 있습니다.
              </ChartMessage>
            </Col>
            <Col xs={12}>
              <ChartMessage>
                <SpanBold>문제점</SpanBold>은 친척, 지인을 통한 분양에는
                <br /> 입양 전
                <SpanBold> 사전교육, 반려동물 등록, 중성화 수술 동의</SpanBold>
                와 같은 과정 없이 분양이 이루어진다는 점입니다.
                <br /> 게다가 가격까지 무료에 가까우니
                <SpanBold> 쉽고 빈번하게 분양이되죠.</SpanBold>
              </ChartMessage>
            </Col>
          </Row>
        </Container>
      </HowToGetAniMain>
    </>
  );
}

export default HowToGetAni;
