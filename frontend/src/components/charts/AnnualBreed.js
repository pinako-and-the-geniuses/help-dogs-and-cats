import styled from "styled-components";
import { Bar } from "react-chartjs-2";
import Chart from "chart.js/auto";
import { useQuery } from "react-query";
import { apiTestAnnualBreed } from "./api";
import { useState } from "react";

const AnnualBreedMain = styled.div`
  margin-top: 70px;
`;

const AnnualBreedTitle = styled.div`
  font-size: 30px;
`;
const AnnualBreedSubTitle = styled.div`
  margin-top: 30px;
  margin-bottom: 20px;
  font-size: 20px;
`;
const AnnualBreedYears = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
`;

const AnnualBreedInputBox = styled.div`
  margin-right: 15px;
`;

function AnnualBreed() {
  const { isLoading, data } = useQuery(["key1"], () => apiTestAnnualBreed());
  const [index, setIndex] = useState(2021);

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
        data:
          data &&
          data.data[index]["개"].map((res) =>
            ((res.count / dogTotal) * 100).toFixed(1)
          ),
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
  const catTotal = data && data.data[index]["고양이Total"];
  const catLabels = data && data.data[index]["고양이"].map((res) => res.breed);
  const catChartData = {
    labels: catLabels,
    datasets: [
      {
        data:
          data &&
          data.data[index]["고양이"].map((res) =>
            ((res.count / catTotal) * 100).toFixed(1)
          ),
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
      <AnnualBreedMain>
        <AnnualBreedTitle>연간 품종 마리수</AnnualBreedTitle>
        <AnnualBreedYears>
          <AnnualBreedInputBox>
            <input type="radio" id="2017a" name="a" />
            <label htmlFor="2017a" onClick={() => setIndex(2017)}>
              2017
            </label>
          </AnnualBreedInputBox>
          <AnnualBreedInputBox>
            <input type="radio" id="2018a" name="a" />
            <label htmlFor="2018a" onClick={() => setIndex(2018)}>
              2018
            </label>
          </AnnualBreedInputBox>
          <AnnualBreedInputBox>
            <input type="radio" id="2019a" name="a" />
            <label htmlFor="2019a" onClick={() => setIndex(2019)}>
              2019
            </label>
          </AnnualBreedInputBox>
          <AnnualBreedInputBox>
            <input type="radio" id="2020a" name="a" />
            <label htmlFor="2020a" onClick={() => setIndex(2020)}>
              2020
            </label>
          </AnnualBreedInputBox>
          <AnnualBreedInputBox>
            <input type="radio" id="2021a" name="a" />
            <label htmlFor="2021a" onClick={() => setIndex(2021)}>
              2021
            </label>
          </AnnualBreedInputBox>
        </AnnualBreedYears>
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
              />
            </div>
            <AnnualBreedSubTitle>고양이</AnnualBreedSubTitle>
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
      </AnnualBreedMain>
    </>
  );
}

export default AnnualBreed;
