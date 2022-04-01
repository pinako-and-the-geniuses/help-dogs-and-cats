import animaldetails from "./styles/AnimalDetails.module.scss";
import Map from "components/Map";
import cn from "classnames";
import { useSelector } from "react-redux";
import { useParams } from "react-router-dom";

export default function AnimalDetails() {
  const animalSeq = useParams();
  const info = useSelector((state) => state.animalInfo.info);
  const data = info.children;
  console.log("info", data);

  return (
    <body className={animaldetails.animaldetailbody}>
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
                src={data[1].value}
                className="card-img-top"
                alt="사진 없음"
              />
              <div className="card-body">
                <span className="badge rounded-pill bg-secondary">
                  Secondary
                </span>
                <div className={animaldetails.cardText}>
                  <p className={animaldetails.title}>공고번호</p>
                  <p>{data[0].value}</p>
                </div>
                <div className={animaldetails.cardText}>
                  <p className={animaldetails.title}>접수일시</p>
                  <p>
                    {data[2].value.slice(0, 4)}-{data[2].value.slice(4, 6)}-
                    {data[2].value.slice(6)}
                  </p>
                </div>
                <div className={animaldetails.cardText}>
                  <p className={animaldetails.title}>발견장소</p>
                  <p>{data[3].value}</p>
                </div>
                <button
                  type="button"
                  className="btn btn-secondary"
                  data-bs-toggle="modal"
                  data-bs-target="#exampleModal"
                >
                  입양가이드
                </button>
                <div
                  className="modal fade"
                  id="exampleModal"
                  tabindex="-1"
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
                        안뇽하세요
                        <br />
                        <br />
                        <br />
                        <br />
                        <br />
                        <br />
                        <br />
                        <br />
                        <br />
                        <br />
                        <br />
                        <br />
                        <br />
                        <br />
                        <br />
                        <br />
                        <br />
                        <br />
                        <br />
                        <br />
                        <br />
                        <br />
                        <br />
                        <br />
                        <br />
                        <br />
                        <br />
                        <br />
                        <br />
                        <br />
                        <br />
                        <br />
                        <br />
                        <br />
                        <br />
                      </div>
                      <div className="modal-footer">
                        <button
                          type="button"
                          className="btn btn-secondary"
                          data-bs-dismiss="modal"
                        >
                          후원하기
                        </button>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <div className="tablebox mx-5">
              <table className="table table-hover">
                <thead>
                  <tr>
                    <th className={animaldetails.head} scope="col" colspan="4">
                      동물의 정보
                    </th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <th scope="row">품종</th>
                    <td colspan="3">{data[4].value}</td>
                  </tr>
                  <tr>
                    <th scope="row">연생</th>
                    <td colspan="3">{data[6].value}</td>
                  </tr>
                  <tr>
                    <th scope="row">kg</th>
                    <td colspan="3">{data[7].value}</td>
                  </tr>
                  <tr>
                    <th scope="row">성별</th>
                    <td colspan="3">{data[13].value}</td>
                  </tr>
                  <tr>
                    <th scope="row">중성화여부</th>
                    <td colspan="3">{data[14].value}</td>
                  </tr>
                  <tr>
                    <th scope="row">색상</th>
                    <td colspan="3">{data[5].value}</td>
                  </tr>
                  <tr>
                    <th scope="row">특징</th>
                    <td colspan="3">{data[15].value}</td>
                  </tr>
                </tbody>
              </table>

              <table className="table table-hover">
                <thead>
                  <tr>
                    <th className={animaldetails.head} scope="col" colspan="4">
                      보호정보
                    </th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <th scope="row">관할기관</th>
                    <td colspan="3">{data[19].value}</td>
                  </tr>
                  <tr>
                    <th scope="row">보호센터</th>
                    <td colspan="3">{data[16].value}</td>
                  </tr>
                  <tr>
                    <th scope="row">보호소 주소</th>
                    <td colspan="3">{data[18].value}</td>
                  </tr>
                  <tr>
                    <th scope="row">연락처</th>
                    <td colspan="3">{data[21].value}</td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </section>
        <div className={animaldetails.bottomContent}>
          <Map></Map>
        </div>
      </main>
    </body>
  );
}
