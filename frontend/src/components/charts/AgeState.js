import styled from "styled-components";
import { Bar } from "react-chartjs-2";
import Chart from "chart.js/auto";
import { useQuery } from "react-query";
import { apiTestAgeState } from "./api";
import { useState } from "react";
import { Col, Container, Row } from "react-bootstrap";

const AgeStateMain = styled.div`
  margin-top: 120px;
`;

const AgeStateTitle = styled.div`
  text-align: center;
  font-weight: bold;
  font-size: 30px;
`;
const AgeStateSubTitle = styled.div`
  margin-top: 30px;
  margin-bottom: 20px;
  font-size: 20px;
`;
const AgeStateYears = styled.div`
  margin-top: 50px;
  display: flex;
  justify-content: center;
  align-items: center;
`;

const AgeStateInputBox = styled.div`
  margin-right: 15px;
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
const ChartBox = styled.div``;

const CustomLabel = styled.label`
  color: #7e7d7d;
  &:hover {
    color: #b8a07e;
    cursor: pointer;
  }
`;

function AgeState() {
  const { isLoading, data } = useQuery(["key3"], () => apiTestAgeState());
  const [index, setIndex] = useState(2021);
  const options = {
    scales: {
      x: {
        stacked: true,
        grid: {
          borderColor: "black",
        },
      },
      y: {
        stacked: true,
        grid: {
          borderColor: "black",
        },
      },
    },
    plugins: {
      title: {
        display: false,
        text: "나이대별 처리현황",
      },

      legend: {
        display: true,
        position: "right",
      },
    },

    maintainAspectRatio: false,
  };

  // const dogTotal = data && data.data[index]["개Total"];
  const labels = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13];
  const dogChartData = {
    labels: labels,
    datasets: [
      {
        label: "입양",
        data: data && labels.map((d) => data.data[index]["개"][d][0].count),
        backgroundColor: "rgba(255, 102, 99, 0.6)",
      },
      {
        label: "기부",
        data: data && labels.map((d) => data.data[index]["개"][d][1].count),
        backgroundColor: "rgba(254, 177, 68, 0.6)",
      },
      {
        label: "방사",
        data: data && labels.map((d) => data.data[index]["개"][d][2].count),
        backgroundColor: "rgba(253, 253, 151, 0.6)",
      },
      {
        label: "안락사",
        data: data && labels.map((d) => data.data[index]["개"][d][3].count),
        backgroundColor: "rgba(158, 224, 158, 0.6)",
      },
      {
        label: "자연사",
        data: data && labels.map((d) => data.data[index]["개"][d][4].count),
        backgroundColor: "rgba(208, 208, 254, 0.6)",
      },
      {
        label: "보호중",
        data: data && labels.map((d) => data.data[index]["개"][d][5].count),
        backgroundColor: "rgba(158, 193, 207, 0.6)",
      },
      {
        label: "주인에게 반환",
        data: data && labels.map((d) => data.data[index]["개"][d][6].count),
        backgroundColor: "rgba(204, 153, 201, 0.6)",
      },
    ],
  };

  const catChartData = {
    labels: labels,
    datasets: [
      {
        label: "입양",
        data: data && labels.map((d) => data.data[index]["고양이"][d][0].count),
        backgroundColor: "rgba(255, 102, 99, 0.6)",
      },
      {
        label: "기부",
        data: data && labels.map((d) => data.data[index]["고양이"][d][1].count),
        backgroundColor: "rgba(254, 177, 68, 0.6)",
      },
      {
        label: "방사",
        data: data && labels.map((d) => data.data[index]["고양이"][d][2].count),
        backgroundColor: "rgba(253, 253, 151, 0.6)",
      },
      {
        label: "안락사",
        data: data && labels.map((d) => data.data[index]["고양이"][d][3].count),
        backgroundColor: "rgba(158, 224, 158, 0.6)",
      },
      {
        label: "자연사",
        data: data && labels.map((d) => data.data[index]["고양이"][d][4].count),
        backgroundColor: "rgba(208, 208, 254, 0.6)",
      },
      {
        label: "보호중",
        data: data && labels.map((d) => data.data[index]["고양이"][d][5].count),
        backgroundColor: "rgba(158, 193, 207, 0.6)",
      },
      {
        label: "주인에게 반환",
        data: data && labels.map((d) => data.data[index]["고양이"][d][6].count),
        backgroundColor: "rgba(204, 153, 201, 0.6)",
      },
    ],
  };
  return (
    <>
      <AgeStateMain>
        <AgeStateTitle>나이대 별 처리 현황(마릿수) - {index}년</AgeStateTitle>
        <Container>
          <Row>
            <Col xs={12}>
              <ChartBox>
                <AgeStateYears>
                  <AgeStateInputBox>
                    <CustomLabel htmlFor="2017c" onClick={() => setIndex(2017)}>
                      2017
                    </CustomLabel>
                  </AgeStateInputBox>
                  <AgeStateInputBox>
                    <CustomLabel htmlFor="2018c" onClick={() => setIndex(2018)}>
                      2018
                    </CustomLabel>
                  </AgeStateInputBox>
                  <AgeStateInputBox>
                    <CustomLabel htmlFor="2019c" onClick={() => setIndex(2019)}>
                      2019
                    </CustomLabel>
                  </AgeStateInputBox>
                  <AgeStateInputBox>
                    <CustomLabel htmlFor="2020c" onClick={() => setIndex(2020)}>
                      2020
                    </CustomLabel>
                  </AgeStateInputBox>
                  <AgeStateInputBox>
                    <CustomLabel htmlFor="2021c" onClick={() => setIndex(2021)}>
                      2021
                    </CustomLabel>
                  </AgeStateInputBox>
                </AgeStateYears>
                {isLoading ? (
                  <div>Loading...</div>
                ) : (
                  <div>
                    <AgeStateSubTitle>강아지</AgeStateSubTitle>
                    <div>
                      <Bar
                        data={dogChartData}
                        width={50}
                        height={350}
                        options={options}
                      />
                    </div>
                    <AgeStateSubTitle>고양이</AgeStateSubTitle>
                    <div>
                      <Bar
                        data={catChartData}
                        width={50}
                        height={350}
                        options={options}
                      />
                    </div>
                  </div>
                )}
              </ChartBox>
            </Col>
            <Col xs={12}>
              <ChartMessage>
                유기동물 중 <SpanBold>0세~3세</SpanBold>의 비중이 가장 많은 것을
                알 수 있습니다. <br />
                특히, 관리가 많이 필요한 0세 유기동물들의 많은 사망(자연사 +
                안락사) 수 또한 확인 할 수 있습니다.
                <br />
                <br /> 왜 어린개체의 수가 이토록 많은 것일까요?? <br />
                <br />
                이는
                <SpanBold> 중성화 여부</SpanBold>와 관련 있습니다.
              </ChartMessage>
            </Col>
          </Row>
        </Container>
      </AgeStateMain>
    </>
  );
}

export default AgeState;
