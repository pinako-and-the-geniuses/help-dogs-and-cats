import animaldetails from "./styles/AnimalDetails.module.scss";
import cn from "classnames";
import { useSelector } from "react-redux";
import { useEffect, useState } from "react";
import { SubGuide } from "pages/guide";

export default function AnimalDetails() {
  const info = useSelector((state) => state.animalInfo.info);
  const [data, setData] = useState("");

  useEffect(() => {
    setData(info);
  }, []);

  return (
    <>
      {data ? (
        <div className={animaldetails.animaldetailbody}>
          <main className={animaldetails.animaldetailmain}>
            <header>
              <h2>동물 상세조회</h2>
            </header>
            <section className={animaldetails.topContent}>
              <div className={animaldetails.mainInfo}>
                <div
                  className={cn(animaldetails.detailscard, "h-50", "mx-5")}
                  style={{ width: "23rem" }}
                >
                  <img
                    src={data.popfileImagePath}
                    className="card-img-top"
                    alt="사진 없음"
                  />
                  <div className="card-body">
                    <div className={animaldetails.processState}>
                      {data.processState ? data.processState : <p></p>}
                    </div>
                    <div className={animaldetails.cardText}>
                      <p className={animaldetails.title}>공고번호</p>
                      {data.noticeNo ? <p>{data.noticeNo}</p> : <p></p>}
                    </div>
                    <div className={animaldetails.cardText}>
                      <p className={animaldetails.title}>접수일시</p>
                      {data.happenDate ? <p>{data.happenDate}</p> : <p></p>}
                    </div>
                    <div className={animaldetails.cardText}>
                      <p className={animaldetails.title}>발견장소</p>
                      {data.happenPlace ? <p>{data.happenPlace}</p> : <p></p>}
                    </div>
                    <button
                      type="button"
                      className={animaldetails.button}
                      data-bs-toggle="modal"
                      data-bs-target="#exampleModal"
                    >
                      입양가이드
                    </button>
                    <div
                      className="modal fade"
                      id="exampleModal"
                      tabIndex="-1"
                      aria-labelledby="exampleModalLabel"
                      aria-hidden="true"
                    >
                      <div className="modal-dialog modal-xl modal-dialog-scrollable">
                        <div className="modal-content">
                          <div className="modal-header">
                            <h5 className="modal-title" id="exampleModalLabel">
                              입양 가이드
                            </h5>
                            <button
                              type="button"
                              className="btn-close"
                              data-bs-dismiss="modal"
                              aria-label="Close"
                            ></button>
                          </div>
                          <div className="modal-body">
                            <SubGuide />
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>

                <div className="tablebox mx-5">
                  <table className="table">
                    <thead>
                      <tr>
                        <th
                          className={animaldetails.head}
                          scope="col"
                          colSpan="4"
                        >
                          동물의 정보
                        </th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr>
                        <th scope="row" width="30%">
                          품종
                        </th>
                        {data.breedOfAnimal ? (
                          <td colSpan="3">{data.breedOfAnimal}</td>
                        ) : (
                          <td colSpan="3"></td>
                        )}
                      </tr>
                      <tr>
                        <th scope="row">연생</th>
                        {data.age ? (
                          <td colSpan="3">{data.age}</td>
                        ) : (
                          <td colSpan="3"></td>
                        )}
                      </tr>
                      <tr>
                        <th scope="row">kg</th>
                        {data.weight ? (
                          <td colSpan="3">{data.weight}</td>
                        ) : (
                          <td colSpan="3"></td>
                        )}
                      </tr>
                      <tr>
                        <th scope="row">성별</th>
                        {data.sex ? (
                          <td colSpan="3">{data.sex}</td>
                        ) : (
                          <td colSpan="3"></td>
                        )}
                      </tr>
                      <tr>
                        <th scope="row">중성화여부</th>
                        {data.neuterYn ? (
                          <td colSpan="3">{data.neuterYn}</td>
                        ) : (
                          <td colSpan="3"></td>
                        )}
                      </tr>
                      <tr>
                        <th scope="row">색상</th>
                        {data.color ? (
                          <td colSpan="3">{data.color}</td>
                        ) : (
                          <td colSpan="3"></td>
                        )}
                      </tr>
                      <tr>
                        <th scope="row">특징</th>
                        {data.specialMark ? (
                          <td colSpan="3">{data.specialMark}</td>
                        ) : (
                          <td colSpan="3"></td>
                        )}
                      </tr>
                    </tbody>
                  </table>

                  <table className="table">
                    <thead>
                      <tr>
                        <th
                          className={animaldetails.head}
                          scope="col"
                          colSpan="4"
                        >
                          보호정보
                        </th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr>
                        <th scope="row" width="30%">
                          관할기관
                        </th>
                        {data.organizationName ? (
                          <td colSpan="3">{data.organizationName}</td>
                        ) : (
                          <td colSpan="3"></td>
                        )}
                      </tr>
                      <tr>
                        <th scope="row">보호센터</th>
                        {data.shelterName ? (
                          <td colSpan="3">{data.shelterName}</td>
                        ) : (
                          <td colSpan="3"></td>
                        )}
                      </tr>

                      <tr>
                        <th scope="row">보호소 주소</th>
                        {data.shleterAddress ? (
                          <td colSpan="3">{data.shleterAddress}</td>
                        ) : (
                          <td colSpan="3"></td>
                        )}
                      </tr>
                      <tr>
                        <th scope="row">연락처</th>
                        {data.shelterTel ? (
                          <td colSpan="3">{data.shelterTel}</td>
                        ) : (
                          <td colSpan="3"></td>
                        )}
                      </tr>
                    </tbody>
                  </table>
                </div>
              </div>
            </section>
            <div className={animaldetails.gotoList}>
              <a
                type="button"
                className={animaldetails.button}
                href="/animals/list"
              >
                목록으로
              </a>
            </div>
          </main>
        </div>
      ) : (
        "데이터가 없습니다."
      )}
    </>
  );
}
