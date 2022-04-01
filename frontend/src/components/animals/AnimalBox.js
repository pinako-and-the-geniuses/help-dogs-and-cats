import "./styles/Box.scss";
import CardItem from "pages/animals/CardItem";
import { useEffect, useState } from "react";

export default function AnimalBox(props) {
  const listNum = 12;
  const [list, setList] = useState("");

  useEffect(() => {
    setList(props.list);
  }, [props.list]);

  return (
    <>
      {list ? (
        list.map((item) => {
          const data = item.children;
          return (
            <div className="col" key={data[0].value}>
              <div className="card h-100">
                {/* <img
                src="https://mdbootstrap.com/img/Photos/Others/images/43.webp"
                className="card-img-top"
                alt="..."
                path='/animals/animaldetails'
              /> */}
                <CardItem
                  src={data[1].value}
                  path={`/animals/detail/${data[0].value}`}
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
                    href={`/animals/detail/${data[0].value}`}
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
        <div>{/* <img src="/public/img/loading.gif" alt="로딩중" /> */}</div>
      )}
    </>
  );
}
