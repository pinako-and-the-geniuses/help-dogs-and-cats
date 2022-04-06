import styled from "styled-components";
import { Doughnut } from "react-chartjs-2";
import Chart from "chart.js/auto";
import { useQuery } from "react-query";
import { apiTestAnnualState, apiTestSpeciesNeutral } from "./api";
import { useState } from "react";
import SpeciesNeutral from "./SpeciesNeutral";
import ChartDataLabels from "chartjs-plugin-datalabels";
import { Col, Container, Row } from "react-bootstrap";

const AnnualStateMain = styled.div`
  margin-top: 120px;
`;

const AnnualStateTitle = styled.div`
  text-align: center;
  font-weight: bold;
  font-size: 30px;
`;
const AnnualStateSubTitle = styled.div`
  text-align: center;
  margin-top: 30px;
  margin-bottom: 20px;
  font-size: 20px;
`;
const AnnualStateYears = styled.div`
  margin-top: 50px;
  display: flex;
  justify-content: center;
  align-items: center;
`;

const AnnualStateInputBox = styled.div`
  margin-right: 15px;
`;

const ChartMessage = styled.div`
  margin-top: 90%;
  width: 100%;
  height: 100%;
  color: black;
  font-size: 20px;
`;

const ChartBox = styled.div``;
const SpanBold = styled.span`
  font-size: 25px;
  font-weight: bold;
`;
const CustomLabel = styled.label`
  color: #7e7d7d;
  &:hover {
    color: #b8a07e;
    cursor: pointer;
  }
`;

function AnnualState() {
  const { isLoading, data } = useQuery(["key2"], () => apiTestAnnualState());
  const { isLoading: isLoading2, data: data2 } = useQuery(["key4"], () =>
    apiTestSpeciesNeutral()
  );
  const [index, setIndex] = useState(2021);
  const plugins = [ChartDataLabels];

  const options = {
    plugins: {
      title: {
        display: false,
        text: "유기동물 연간 처리 현황",
      },

      legend: {
        display: true,
        position: "bottom",
      },
    },

    maintainAspectRatio: false,
  };

  const dogTotal = data2 && data2.data[index]["개Total"];
  console.log(dogTotal);
  // const dogLabels = data && data.data[index]["개"].map((res) => res.state);
  const dogChartData = {
    labels: [
      "입양",
      "기부",
      "방사",
      "안락사",
      "자연사",
      "보호중",
      "주인에게 반환",
    ],
    datasets: [
      {
        datalabels: {
          display: true,
          color: "black",
          labels: {
            title: {
              font: {
                size: 12,
              },
            },
          },
        },
        data:
          data &&
          data.data[index]["개"].map((res) =>
            ((res.count / dogTotal) * 100).toFixed(1)
          ),

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
  const catTotal = data2 && data2.data[index]["고양이Total"];
  // const catLabels = data && data.data[index]["고양이"].map((res) => res.state);
  const catChartData = {
    labels: [
      "입양",
      "기부",
      "방사",
      "안락사",
      "자연사",
      "보호중",
      "주인에게 반환",
    ],
    datasets: [
      {
        datalabels: {
          display: true,
          color: "black",
          labels: {
            title: {
              font: {
                size: 12,
              },
            },
          },
        },
        data:
          data &&
          data.data[index]["고양이"].map((res) =>
            ((res.count / catTotal) * 100).toFixed(1)
          ),
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
      <AnnualStateMain>
        <AnnualStateTitle>
          유기동물 연간 처리 현황(%) - {index}년
        </AnnualStateTitle>
        <AnnualStateYears>
          <AnnualStateInputBox>
            <CustomLabel htmlFor="2017b" onClick={() => setIndex(2017)}>
              2017
            </CustomLabel>
          </AnnualStateInputBox>
          <AnnualStateInputBox>
            <CustomLabel htmlFor="2018b" onClick={() => setIndex(2018)}>
              2018
            </CustomLabel>
          </AnnualStateInputBox>
          <AnnualStateInputBox>
            <CustomLabel htmlFor="2019b" onClick={() => setIndex(2019)}>
              2019
            </CustomLabel>
          </AnnualStateInputBox>
          <AnnualStateInputBox>
            <CustomLabel htmlFor="2020b" onClick={() => setIndex(2020)}>
              2020
            </CustomLabel>
          </AnnualStateInputBox>
          <AnnualStateInputBox>
            <CustomLabel htmlFor="2021b" onClick={() => setIndex(2021)}>
              2021
            </CustomLabel>
          </AnnualStateInputBox>
        </AnnualStateYears>
        <Container>
          <Row>
            <Col xs={7}>
              <ChartBox>
                {isLoading ? (
                  <div>Loading...</div>
                ) : (
                  <div>
                    <AnnualStateSubTitle>강아지</AnnualStateSubTitle>
                    <div>
                      <Doughnut
                        data={dogChartData}
                        width={50}
                        height={450}
                        options={options}
                        plugins={plugins}
                      />
                    </div>
                    <AnnualStateSubTitle>고양이</AnnualStateSubTitle>
                    <div>
                      <Doughnut
                        data={catChartData}
                        width={50}
                        height={450}
                        options={options}
                        plugins={plugins}
                      />
                    </div>
                  </div>
                )}
              </ChartBox>
            </Col>

            <Col xs={5}>
              <ChartMessage>
                평균적으로,
                <br /> 한 해 발생하는 유기동물의
                <SpanBold> 절반가량</SpanBold>은<br />
                <SpanBold>자연사나 안락사</SpanBold>로<br /> 사망하는 것을 알 수
                있습니다.
              </ChartMessage>
            </Col>
          </Row>
        </Container>
      </AnnualStateMain>
    </>
  );
}

export default AnnualState;
