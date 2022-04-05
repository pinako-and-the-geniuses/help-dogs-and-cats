import st from "./styles/box.module.scss";
import cn from "classnames";
import { animalGetAction } from "actions/AnimalAction";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux";
import loading from "public/img/loading.gif";

export default function AnimalBox(props) {
  // const listNum = 12;
  const [list, setList] = useState("");
  const navigate = useNavigate();
  const dispatch = useDispatch();

  useEffect(() => {
    setList(props.list);
    console.log("animalBox - list", list); ////////////////////////
  }, [props.list]);

  const onGoDetail = (index) => {
    dispatch(animalGetAction(props.list[index]));
    navigate(`/animals/detail`);
  };

  const onReturn = () => {
    if (list.length >= 1) {
      return list.map((item, index) => {
        const data = item.children;
        return (
          <div className="col" key={data[0].value}>
            <div className={cn(`${st.card}`)}>
              <img
                src={data[11].value}
                className={st.cardImgTop}
                alt="사진없음"
              />
              <div className={st.cardBody}>
                <div className={st.processState}>{data[12].value}</div>
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
                  <p className="mt-1">{data[16].value}</p>
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
                    className={st.button}
                    onClick={() => onGoDetail(index)}
                  >
                    자세히 보기
                  </button>
                </div>
              </div>
            </div>
          </div>
        );
      });
    } else if (list === [] || list.length === 0) {
      return (
        <>
          <div className={st.loadingImg}>
            <h5> 데이터가 없습니다.</h5>
          </div>
        </>
      );
    } else {
      return (
        <>
          <div className={st.loadingImg}>
            <h5> 공공데이터 포털과 연결이 불안정합니다.</h5>
            <h2>
              <img src={loading} alt="로딩중" />
            </h2>
          </div>
        </>
      );
    }
  };

  return <>{onReturn()}</>;
}
