import styled from "styled-components";
import { Bar } from "react-chartjs-2";
import Chart from "chart.js/auto";
import { useQuery } from "react-query";
import { apiTestAgeState } from "./api";
import { useState } from "react";

const AgeStateMain = styled.div`
  margin-top: 70px;
`;

const AgeStateTitle = styled.div`
  font-size: 30px;
`;
const AgeStateSubTitle = styled.div`
  margin-top: 30px;
  margin-bottom: 20px;
  font-size: 20px;
`;
const AgeStateYears = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
`;

const AgeStateInputBox = styled.div`
  margin-right: 15px;
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
        backgroundColor: "#ff6663",
      },
      {
        label: "기부",
        data: data && labels.map((d) => data.data[index]["개"][d][1].count),
        backgroundColor: "#feb144",
      },
      {
        label: "방사",
        data: data && labels.map((d) => data.data[index]["개"][d][2].count),
        backgroundColor: "#fdfd97",
      },
      {
        label: "안락사",
        data: data && labels.map((d) => data.data[index]["개"][d][3].count),
        backgroundColor: "#9ee09e",
      },
      {
        label: "자연사",
        data: data && labels.map((d) => data.data[index]["개"][d][4].count),
        backgroundColor: "#d0d0fe",
      },
      {
        label: "보호중",
        data: data && labels.map((d) => data.data[index]["개"][d][5].count),
        backgroundColor: "#9ec1cf",
      },
      {
        label: "주인에게 반환",
        data: data && labels.map((d) => data.data[index]["개"][d][6].count),
        backgroundColor: "#cc99c9",
      },
    ],
  };

  const catChartData = {
    labels: labels,
    datasets: [
      {
        label: "입양",
        data: data && labels.map((d) => data.data[index]["고양이"][d][0].count),
        backgroundColor: "#ff6663",
      },
      {
        label: "기부",
        data: data && labels.map((d) => data.data[index]["고양이"][d][1].count),
        backgroundColor: "#feb144",
      },
      {
        label: "방사",
        data: data && labels.map((d) => data.data[index]["고양이"][d][2].count),
        backgroundColor: "#fdfd97",
      },
      {
        label: "안락사",
        data: data && labels.map((d) => data.data[index]["고양이"][d][3].count),
        backgroundColor: "#9ee09e",
      },
      {
        label: "자연사",
        data: data && labels.map((d) => data.data[index]["고양이"][d][4].count),
        backgroundColor: "#d0d0fe",
      },
      {
        label: "보호중",
        data: data && labels.map((d) => data.data[index]["고양이"][d][5].count),
        backgroundColor: "#9ec1cf",
      },
      {
        label: "주인에게 반환",
        data: data && labels.map((d) => data.data[index]["고양이"][d][6].count),
        backgroundColor: "#cc99c9",
      },
    ],
  };
  return (
    <>
      <AgeStateMain>
        <AgeStateTitle>나이대 별 처리 현황(마릿수)</AgeStateTitle>
        <AgeStateYears>
          <AgeStateInputBox>
            <input type="radio" id="2017c" name="c" />
            <label htmlFor="2017c" onClick={() => setIndex(2017)}>
              2017
            </label>
          </AgeStateInputBox>
          <AgeStateInputBox>
            <input type="radio" id="2018c" name="c" />
            <label htmlFor="2018c" onClick={() => setIndex(2018)}>
              2018
            </label>
          </AgeStateInputBox>
          <AgeStateInputBox>
            <input type="radio" id="2019c" name="c" />
            <label htmlFor="2019c" onClick={() => setIndex(2019)}>
              2019
            </label>
          </AgeStateInputBox>
          <AgeStateInputBox>
            <input type="radio" id="2020c" name="c" />
            <label htmlFor="2020c" onClick={() => setIndex(2020)}>
              2020
            </label>
          </AgeStateInputBox>
          <AgeStateInputBox>
            <input type="radio" id="2021c" name="c" />
            <label htmlFor="2021c" onClick={() => setIndex(2021)}>
              2021
            </label>
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
                height={400}
                options={options}
              />
            </div>
            <AgeStateSubTitle>고양이</AgeStateSubTitle>
            <div>
              <Bar
                data={catChartData}
                width={50}
                height={400}
                options={options}
              />
            </div>
          </div>
        )}
      </AgeStateMain>
    </>
  );
}

export default AgeState;
