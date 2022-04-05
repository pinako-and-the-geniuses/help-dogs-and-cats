import styled from "styled-components";
import { Bar } from "react-chartjs-2";
import Chart from "chart.js/auto";
import { useQuery } from "react-query";
import { apiTestAnnualBreed } from "./api";
import { useState } from "react";
import ChartDataLabels from "chartjs-plugin-datalabels";
import { Col, Container, Row } from "react-bootstrap";

const AnnualBreedMain = styled.div`
  margin-top: 70px;
`;

const AnnualBreedTitle = styled.div`
  font-weight: bold;
  text-align: center;
  font-size: 30px;
`;
const AnnualBreedSubTitle = styled.div`
  margin-top: 30px;
  margin-bottom: 20px;
  font-size: 20px;
`;
const AnnualBreedYears = styled.div`
  margin-top: 50px;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 20px;
`;

const AnnualBreedInputBox = styled.div`
  margin-right: 15px;
`;
const ChartBox = styled.div`
  margin-right: 50px;
`;
const ChartMessage = styled.div`
  margin-top: 60%;
  width: 100%;
  height: 100%;
  color: black;
  font-size: 20px;
`;

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

function AnnualBreed() {
  const { isLoading, data } = useQuery(["key1"], () => apiTestAnnualBreed());
  const [index, setIndex] = useState(2021);
  const plugins = [ChartDataLabels];

  const options = {
    plugins: {
      title: {
        display: false,
        text: "연간 품종 마리수",
      },

      legend: {
        display: false,
        position: "right",
      },
    },
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

  const dogTotal = data && data.data[index]["개Total"];
  const dogLabels = data && data.data[index]["개"].map((res) => res.breed);
  const dogChartData = {
    labels: dogLabels,
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
          "rgba(158, 193, 207, 0.6)",
          "rgba(204, 153, 201, 0.6)",
        ],
      },
    ],
  };
  const catTotal = data && data.data[index]["고양이Total"];
  const catLabels = data && data.data[index]["고양이"].map((res) => res.breed);
  const catChartData = {
    labels: catLabels,
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
          "rgba(158, 193, 207, 0.6)",
          "rgba(204, 153, 201, 0.6)",
        ],
      },
    ],
  };
  return (
    <>
      <AnnualBreedMain>
        <AnnualBreedTitle>연간 품종 마리수(%) - {index}년</AnnualBreedTitle>
        <AnnualBreedYears>
          <AnnualBreedInputBox>
            <CustomLabel htmlFor="2017a" onClick={() => setIndex(2017)}>
              2017
            </CustomLabel>
          </AnnualBreedInputBox>
          <AnnualBreedInputBox>
            <CustomLabel htmlFor="2018a" onClick={() => setIndex(2018)}>
              2018
            </CustomLabel>
          </AnnualBreedInputBox>
          <AnnualBreedInputBox>
            <CustomLabel htmlFor="2019a" onClick={() => setIndex(2019)}>
              2019
            </CustomLabel>
          </AnnualBreedInputBox>
          <AnnualBreedInputBox>
            <CustomLabel htmlFor="2020a" onClick={() => setIndex(2020)}>
              2020
            </CustomLabel>
          </AnnualBreedInputBox>
          <AnnualBreedInputBox>
            <CustomLabel htmlFor="2021a" onClick={() => setIndex(2021)}>
              2021
            </CustomLabel>
          </AnnualBreedInputBox>
        </AnnualBreedYears>
        <Container>
          <Row>
            <Col xs={7}>
              <ChartBox>
                {isLoading ? (
                  <div>Loading...</div>
                ) : (
                  <div>
                    <AnnualBreedSubTitle>강아지</AnnualBreedSubTitle>
                    <div>
                      <Bar
                        data={dogChartData}
                        width={50}
                        height={350}
                        options={options}
                        plugins={plugins}
                      />
                    </div>
                    <AnnualBreedSubTitle>고양이</AnnualBreedSubTitle>
                    <div>
                      <Bar
                        data={catChartData}
                        width={50}
                        height={350}
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
                <p>
                  <SpanBold>믹스견</SpanBold>의 비율은
                  <br />
                  <SpanBold>평균 65.2%</SpanBold>
                  <br />
                  <br /> <SpanBold>믹스묘</SpanBold>의 비율은
                  <br /> <SpanBold>평균 94.2%</SpanBold>
                  <br />
                  <br /> 타 품종에 비해
                  <SpanBold> 믹스종</SpanBold>이<br />
                  <SpanBold>훨씬 많은 이유</SpanBold>는 무엇일까요?
                </p>
              </ChartMessage>
            </Col>
          </Row>
        </Container>
      </AnnualBreedMain>
    </>
  );
}

export default AnnualBreed;
