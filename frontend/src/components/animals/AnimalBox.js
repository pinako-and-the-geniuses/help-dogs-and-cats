import st from "./styles/box.module.scss";
import cn from "classnames";
import { animalGetAction } from "actions/AnimalAction";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux";

export default function AnimalBox(props) {
  const listNum = 12;
  const [list, setList] = useState("");
  const navigate = useNavigate();
  const dispatch = useDispatch();

  useEffect(() => {
    setList(props.list);
  }, [props.list]);

  const onGoDetail = (index) => {
    dispatch(animalGetAction(props.list[index]));
    navigate(`/animals/detail`);
  };

  return (
    <>
      {list ? (
        list.map((item, index) => {
          const data = item.children;
          return (
            <div className="col" key={data[0].value}>
              <div className={cn(`${st.card}`, "card h-100")}>
                <img
                  src={data[1].value}
                  className={st.cardImgTop}
                  alt="사진없음"
                  onClick={() => onGoDetail(index)}
                />

                {/* <CardItem
                  src={data[1].value}
                  path={`/animals/detail/${data[0].value}`}
                /> */}
                <div className="card-body">
                  <div className={cn(`${st.cardText}`, "card-text")}>
                    <span className={st.title}>공고번호</span>
                    <span>{data[0].value}</span>
                  </div>
                  <div className={cn(`${st.cardText}`, "card-text")}>
                    <span className={st.title}>접수일시</span>
                    <span>
                      {data[2].value.slice(0, 4)}-{data[2].value.slice(4, 6)}-
                      {data[2].value.slice(6)}
                    </span>
                  </div>
                  <div className={cn(`${st.cardText}`, "card-text")}>
                    <span className={st.title}>관할기관</span>
                    <p className="mt-1 ms-5">{data[16].value}</p>
                  </div>
                  <div className={cn(`${st.cardText}`, "card-text")}>
                    <div>
                      <span className={st.title}>발견장소</span>
                    </div>
                    <div>
                      <p>{data[3].value}</p>
                    </div>
                  </div>
                  <div className={st.detilaBtn}>
                    <button
                      // href={`/animals/detail/${data[0].value}`}
                      className="btn btn-primary"
                    >
                      자세히 보기
                    </button>
                  </div>
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
