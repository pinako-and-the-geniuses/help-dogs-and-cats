import "./styles/Box.scss";
import CardItem from "pages/animals/CardItem";
import { useEffect, useState } from "react";

export default function AnimalBox(props) {
  const listNum = 12;
  const [list, setList] = useState("");

  useEffect(() => {
    setList(props.list);
    console.log(props.list);
  }, [props.list]);

  const rendering = () => {
    if (list) {
      list.map((item) => {
        const data = item.children;
        // console.log("map들어옴", data);
        return (
          <div
            key={item.desertionNo}
            className="row row-cols-1 row-cols-md-4 g-4"
          >
            <div key={item.desertionNo}>나다나다</div>
            <div className="col">
              <div className="card h-100">
                {/* <img
              src="https://mdbootstrap.com/img/Photos/Others/images/43.webp"
              className="card-img-top"
              alt="..."
              path='/animals/animaldetails'
            /> */}
                <CardItem
                  src="https://mdbootstrap.com/img/Photos/Others/images/43.webp"
                  path="/animals/animaldetails"
                />
                <div className="card-body">
                  <div className="card-text">
                    <span className="title">공고번호</span>
                    <p>{data[0].value}</p>
                  </div>
                  <div className="card-text">
                    <span className="title">접수일시</span>
                    <p>{data[3].value}</p>
                  </div>
                  <div className="card-text">
                    <span className="title">관할기관</span>
                    <p>{item.children[16].value}</p>
                  </div>
                  <div className="card-text">
                    <span className="title">발견장소</span>
                    <p>멀티캠퍼스</p>
                  </div>
                  <a
                    href="http://localhost:3000/animals/animaldetails"
                    className="btn btn-primary"
                  >
                    자세히 보기
                  </a>
                </div>
              </div>
            </div>
          </div>
        );
      });
    } else {
      return (
        <div>
          <h2>"데이터 불러오는 데 실패했습니다."</h2>
        </div>
      );
    }
  };
  return (
    <>
      {list ? (
        list.map((item) => {
          const data = item.children;
          // console.log("map들어옴", data);
          return (
            <div className="col">
              <div className="card h-100">
                {/* <img
                src="https://mdbootstrap.com/img/Photos/Others/images/43.webp"
                className="card-img-top"
                alt="..."
                path='/animals/animaldetails'
              /> */}
                <CardItem
                  src="https://mdbootstrap.com/img/Photos/Others/images/43.webp"
                  path="/animals/animaldetails"
                />
                <div className="card-body">
                  <div className="card-text">
                    <span className="title">공고번호</span>
                    <p>{data[0].value}</p>
                  </div>
                  <div className="card-text">
                    <span className="title">접수일시</span>
                    <p>
                      {data[2].value.slice(0, 4)}-{data[2].value.slice(4, 6)}-
                      {data[2].value.slice(6)}
                    </p>
                  </div>
                  <div className="card-text">
                    <span className="title">관할기관</span>
                    <p>{data[16].value}</p>
                  </div>
                  <div className="card-text">
                    <span className="title">발견장소</span>

                    <p>{data[3].value}</p>
                  </div>
                  <a
                    href={`http://localhost:3000/animals/detail/${data[0].value}`}
                    className="btn btn-primary"
                  >
                    자세히 보기
                  </a>
                </div>
              </div>
            </div>
          );
        })
      ) : (
        <div>
          <h2>"데이터 불러오는 데 실패했습니다."</h2>
        </div>
      )}
    </>
  );
}
