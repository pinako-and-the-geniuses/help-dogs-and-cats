import st from "./styles/box.module.scss";
import cn from "classnames";
import { animalGetAction } from "actions/AnimalAction";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux";
import loading from "public/img/loading.gif";

export default function AnimalBox(props) {
  const [list, setList] = useState("");
  const navigate = useNavigate();
  const dispatch = useDispatch();

  useEffect(() => {
    setList(props.list);
  }, [props.list]);

  const onGoDetail = (item) => {
    dispatch(animalGetAction(item));
    navigate(`/animals/detail`);
  };

  const onReturn = () => {
    if (list.length >= 1) {
      return list.map((item, index) => {
        return (
          <div className="col" key={index}>
            <div className={cn(`${st.card}`)}>
              <img
                src={item.popfileImagePath}
                className={st.cardImgTop}
                alt="사진없음"
              />
              <div className={st.cardBody}>
                {props.postState === "notice" ? (
                  <div className={st.processState}>공고중</div>
                ) : (
                  <div className={st.processState}>{item.processState}</div>
                )}
                <div className={cn(`${st.cardText}`, "card-text")}>
                  <span className={st.title}>공고번호</span>
                  <span>{item.noticeNo}</span>
                </div>
                <div className={cn(`${st.cardText}`, "card-text")}>
                  <span className={st.title}>접수일시</span>
                  <span>{item.happenDate}</span>
                </div>
                <div className={cn(`${st.cardText}`, "card-text")}>
                  <span className={st.title}>관할기관</span>
                  <p className="mt-1">{item.organizationName}</p>
                </div>
                <div className={cn(`${st.cardText}`, "card-text")}>
                  <div>
                    <span className={st.title}>발견장소</span>
                  </div>
                  <div>
                    <p>{item.happenPlace}</p>
                  </div>
                </div>
                <div className={st.detilaBtn}>
                  <button
                    className={st.button}
                    onClick={() => onGoDetail(item)}
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
