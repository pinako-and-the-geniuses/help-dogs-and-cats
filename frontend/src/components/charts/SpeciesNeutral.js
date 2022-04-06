import styled from "styled-components";
import { Bar } from "react-chartjs-2";
import Chart from "chart.js/auto";
import { useQuery } from "react-query";
import { apiTestSpeciesNeutral } from "./api";
import { useState } from "react";
import ChartDataLabels from "chartjs-plugin-datalabels";
import { Col, Container, Row } from "react-bootstrap";

const SpeciesNeutralMain = styled.div`
  margin-top: 120px;
`;

const SpeciesNeutralChart = styled.div`
  margin-right: 50px;
`;
const SpeciesNeutralChartBox = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
`;

const SpeciesNeutralTitle = styled.div`
  text-align: center;
  font-weight: bold;
  font-size: 30px;
`;
const SpeciesNeutralSubTitle = styled.div`
  margin-right: 30px;
  margin-top: 30px;
  margin-bottom: 20px;
  font-size: 20px;
`;
const SpeciesNeutralYears = styled.div`
  margin-top: 50px;
  margin-bottom: 30px;
  display: flex;
  justify-content: center;
  align-items: center;
`;

const SpeciesNeutralInputBox = styled.div`
  margin-right: 15px;
`;
const ChartMessage = styled.div`
  margin-top: 3%;
  margin-bottom: 100px;
  width: 100%;
  height: 100%;
  color: black;
  font-size: 20px;
`;
const SpanBold = styled.span`
  font-size: 25px;
  font-weight: bold;
`;

const SpanRedBold = styled.span`
  color: red;
  font-size: 25px;
  font-weight: bold;
`;

const ChartBox = styled.div``;
const CustomLabel = styled.label`
  color: #7e7d7d;
  &:hover {
    color: #b8a07e;
    cursor: pointer;
  }
`;

function SpeciesNeutral() {
  const { isLoading, data } = useQuery(["key4"], () => apiTestSpeciesNeutral());
  const [index, setIndex] = useState(2021);
  const plugins = [ChartDataLabels];

  const options = {
    indexAxis: "y",
    plugins: {
      title: {
        display: false,
        text: "중성화 여부",
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

  // const dogLabels = data && data.data[index]["개"].map((res) => res.neutral);
  const dogTotal = data && data.data[index]["개Total"];
  const dogChartData = {
    labels: ["안 함", "모름", "함"],
    datasets: [
      {
        datalabels: {
          display: true,
          color: "black",
          labels: {
            title: {
              font: {
                size: 15,
              },
            },
          },
        },
        label: "개",
        data:
          data &&
          data.data[index]["개"].map((res) =>
            ((res.count / dogTotal) * 100).toFixed(1)
          ),

        backgroundColor: [
          "rgba(255, 102, 99, 0.6)",
          "rgba(253, 253, 151, 0.6)",
          "rgba(158, 224, 158, 0.6)",
        ],
      },
    ],
  };
  const catTotal = data && data.data[index]["고양이Total"];
  data && data.data[index]["고양이"].map((res) => res.neutral);
  const catChartData = {
    labels: ["안 함", "모름", "함"],
    datasets: [
      {
        datalabels: {
          display: true,
          color: "black",
          labels: {
            title: {
              font: {
                size: 15,
              },
            },
          },
        },
        label: "고양이",
        data:
          data &&
          data.data[index]["고양이"].map((res) =>
            ((res.count / catTotal) * 100).toFixed(1)
          ),
        backgroundColor: [
          "rgba(255, 102, 99, 0.6)",
          "rgba(253, 253, 151, 0.6)",
          "rgba(158, 224, 158, 0.6)",
        ],
      },
    ],
  };
  return (
    <>
      <SpeciesNeutralMain>
        <SpeciesNeutralTitle>중성화 여부(%) - {index}년</SpeciesNeutralTitle>
        <Container>
          <Row>
            <Col xs={12}>
              <ChartBox>
                <SpeciesNeutralYears>
                  <SpeciesNeutralInputBox>
                    <CustomLabel htmlFor="2017d" onClick={() => setIndex(2017)}>
                      2017
                    </CustomLabel>
                  </SpeciesNeutralInputBox>
                  <SpeciesNeutralInputBox>
                    <CustomLabel htmlFor="2018d" onClick={() => setIndex(2018)}>
                      2018
                    </CustomLabel>
                  </SpeciesNeutralInputBox>
                  <SpeciesNeutralInputBox>
                    <CustomLabel htmlFor="2019d" onClick={() => setIndex(2019)}>
                      2019
                    </CustomLabel>
                  </SpeciesNeutralInputBox>
                  <SpeciesNeutralInputBox>
                    <CustomLabel htmlFor="2020d" onClick={() => setIndex(2020)}>
                      2020
                    </CustomLabel>
                  </SpeciesNeutralInputBox>
                  <SpeciesNeutralInputBox>
                    <CustomLabel htmlFor="2021d" onClick={() => setIndex(2021)}>
                      2021
                    </CustomLabel>
                  </SpeciesNeutralInputBox>
                </SpeciesNeutralYears>
                {isLoading ? (
                  <div>Loading...</div>
                ) : (
                  <SpeciesNeutralChartBox>
                    <Col xs={6}>
                      <SpeciesNeutralSubTitle>강아지</SpeciesNeutralSubTitle>
                      <SpeciesNeutralChart>
                        <Bar
                          data={dogChartData}
                          width={600}
                          height={200}
                          options={options}
                          plugins={plugins}
                        />
                      </SpeciesNeutralChart>
                    </Col>
                    <Col xs={6}>
                      <SpeciesNeutralSubTitle>고양이</SpeciesNeutralSubTitle>
                      <SpeciesNeutralChart>
                        <Bar
                          data={catChartData}
                          width={600}
                          height={200}
                          options={options}
                          plugins={plugins}
                        />
                      </SpeciesNeutralChart>
                    </Col>
                  </SpeciesNeutralChartBox>
                )}
              </ChartBox>
            </Col>
            <Col xs={12}>
              <ChartMessage>
                한 해 유기동물 중 <SpanBold>평균 5퍼센트</SpanBold>의 개체만이
                중성화가 되어있는것을 알 수 있습니다. <br />
                <br />
                중성화를 거치지 않은 동물은
                <SpanBold> 강한 공격적 성향</SpanBold>과
                <SpanBold> 성적 충동</SpanBold>을 갖고 있으며
                <SpanBold> 실종될 확률이 5~10배</SpanBold>
                라고 합니다.
                <br />
                <br /> 중성화가 되지 않은 동물들이 유기 또는 실종되어 야생으로
                나가게 되고,
                <br />
                <SpanBold>
                  같은 유기동물들끼리 번식 하여 새끼를 낳게 됩니다.
                  <br />
                </SpanBold>
                즉,
                <SpanBold>
                  {" "}
                  유기동물이 유기동물을 만들게 되는{" "}
                  <SpanRedBold>악순환</SpanRedBold>
                </SpanBold>
                이 생기게 됩니다.
                <br /> 그리고 이러한
                <SpanBold> 악순환이 어린 유기동물 개체수의 증가</SpanBold>를
                가져옵니다.
                <br />
                <br /> 이에 도와주개냥은 <br />
                어린 유기동물 개체수의 증가를 막기위해 <br />
                <SpanBold>반려동물의 중성화 수술 의무화</SpanBold>를 적극
                지지합니다.
              </ChartMessage>
            </Col>
          </Row>
        </Container>
      </SpeciesNeutralMain>
    </>
  );
}

export default SpeciesNeutral;
