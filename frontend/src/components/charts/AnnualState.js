import styled from "styled-components";
import { Pie } from "react-chartjs-2";
import Chart from "chart.js/auto";
import { useQuery } from "react-query";
import { apiTestAnnualState } from "./api";
import { useState } from "react";
import SpeciesNeutral from "./SpeciesNeutral";

const AnnualStateMain = styled.div`
  margin-top: 70px;
`;

const AnnualStateTitle = styled.div`
  font-size: 30px;
`;
const AnnualStateSubTitle = styled.div`
  text-align: center;
  margin-top: 30px;
  margin-bottom: 20px;
  font-size: 20px;
`;
const AnnualStateYears = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
`;

const AnnualStateInputBox = styled.div`
  margin-right: 15px;
`;

function AnnualState() {
  const { isLoading, data } = useQuery(["key2"], () => apiTestAnnualState());
  const [index, setIndex] = useState(2021);

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

  // const dogTotal = data && data.data[index]["개Total"];
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
        data: data && data.data[index]["개"].map((res) => res.count),

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
  // const catTotal = data && data.data[index]["고양이Total"];
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
        data: data && data.data[index]["고양이"].map((res) => res.count),
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
      <AnnualStateMain>
        <AnnualStateTitle>
          유기동물 연간 처리 현황(%) - {index}년
        </AnnualStateTitle>
        <AnnualStateYears>
          <AnnualStateInputBox>
            <input type="radio" id="2017b" name="b" />
            <label htmlFor="2017b" onClick={() => setIndex(2017)}>
              2017
            </label>
          </AnnualStateInputBox>
          <AnnualStateInputBox>
            <input type="radio" id="2018b" name="b" />
            <label htmlFor="2018b" onClick={() => setIndex(2018)}>
              2018
            </label>
          </AnnualStateInputBox>
          <AnnualStateInputBox>
            <input type="radio" id="2019b" name="b" />
            <label htmlFor="2019b" onClick={() => setIndex(2019)}>
              2019
            </label>
          </AnnualStateInputBox>
          <AnnualStateInputBox>
            <input type="radio" id="2020b" name="b" />
            <label htmlFor="2020b" onClick={() => setIndex(2020)}>
              2020
            </label>
          </AnnualStateInputBox>
          <AnnualStateInputBox>
            <input type="radio" id="2021b" name="b" display />
            <label htmlFor="2021b" onClick={() => setIndex(2021)}>
              2021
            </label>
          </AnnualStateInputBox>
        </AnnualStateYears>
        {isLoading ? (
          <div>Loading...</div>
        ) : (
          <div>
            <AnnualStateSubTitle>강아지</AnnualStateSubTitle>
            <div>
              <Pie
                data={dogChartData}
                width={50}
                height={400}
                options={options}
              />
            </div>
            <AnnualStateSubTitle>고양이</AnnualStateSubTitle>
            <div>
              <Pie
                data={catChartData}
                width={50}
                height={400}
                options={options}
              />
            </div>
          </div>
        )}
      </AnnualStateMain>
    </>
  );
}

export default AnnualState;
