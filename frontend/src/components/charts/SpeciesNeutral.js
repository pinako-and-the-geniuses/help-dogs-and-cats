import styled from "styled-components";
import { Bar } from "react-chartjs-2";
import Chart from "chart.js/auto";
import { useQuery } from "react-query";
import { apiTestSpeciesNeutral } from "./api";
import { useState } from "react";

const SpeciesNeutralMain = styled.div`
  margin-top: 70px;
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
  font-size: 30px;
`;
const SpeciesNeutralSubTitle = styled.div`
  margin-right: 30px;
  margin-top: 30px;
  margin-bottom: 20px;
  font-size: 20px;
`;
const SpeciesNeutralYears = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
`;

const SpeciesNeutralInputBox = styled.div`
  margin-right: 15px;
`;

function SpeciesNeutral() {
  const { isLoading, data } = useQuery(["key4"], () => apiTestSpeciesNeutral());
  const [index, setIndex] = useState(2021);

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
        label: "개",
        data:
          data &&
          data.data[index]["개"].map((res) =>
            ((res.count / dogTotal) * 100).toFixed(1)
          ),
        backgroundColor: ["#ff6663", "#fdfd97", "#9ee09e"],
      },
    ],
  };
  const catTotal = data && data.data[index]["고양이Total"];
  data && data.data[index]["고양이"].map((res) => res.neutral);
  const catChartData = {
    labels: ["안 함", "모름", "함"],
    datasets: [
      {
        label: "고양이",
        data:
          data &&
          data.data[index]["고양이"].map((res) =>
            ((res.count / catTotal) * 100).toFixed(1)
          ),
        backgroundColor: ["#ff6663", "#fdfd97", "#9ee09e"],
      },
    ],
  };
  return (
    <>
      <SpeciesNeutralMain>
        <SpeciesNeutralTitle>중성화 여부(%)</SpeciesNeutralTitle>
        <SpeciesNeutralYears>
          <SpeciesNeutralInputBox>
            <input type="radio" id="2017d" name="d" />
            <label htmlFor="2017d" onClick={() => setIndex(2017)}>
              2017
            </label>
          </SpeciesNeutralInputBox>
          <SpeciesNeutralInputBox>
            <input type="radio" id="2018d" name="d" />
            <label htmlFor="2018d" onClick={() => setIndex(2018)}>
              2018
            </label>
          </SpeciesNeutralInputBox>
          <SpeciesNeutralInputBox>
            <input type="radio" id="2019d" name="d" />
            <label htmlFor="2019d" onClick={() => setIndex(2019)}>
              2019
            </label>
          </SpeciesNeutralInputBox>
          <SpeciesNeutralInputBox>
            <input type="radio" id="2020d" name="d" />
            <label htmlFor="2020d" onClick={() => setIndex(2020)}>
              2020
            </label>
          </SpeciesNeutralInputBox>
          <SpeciesNeutralInputBox>
            <input type="radio" id="2021d" name="d" />
            <label htmlFor="2021d" onClick={() => setIndex(2021)}>
              2021
            </label>
          </SpeciesNeutralInputBox>
        </SpeciesNeutralYears>
        {isLoading ? (
          <div>Loading...</div>
        ) : (
          <SpeciesNeutralChartBox>
            <SpeciesNeutralSubTitle>강아지</SpeciesNeutralSubTitle>
            <SpeciesNeutralChart>
              <Bar
                data={dogChartData}
                width={600}
                height={200}
                options={options}
              />
            </SpeciesNeutralChart>
            <SpeciesNeutralSubTitle>고양이</SpeciesNeutralSubTitle>
            <SpeciesNeutralChart>
              <Bar
                data={catChartData}
                width={600}
                height={200}
                options={options}
              />
            </SpeciesNeutralChart>
          </SpeciesNeutralChartBox>
        )}
      </SpeciesNeutralMain>
    </>
  );
}

export default SpeciesNeutral;
