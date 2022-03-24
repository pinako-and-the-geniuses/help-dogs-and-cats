import "./styles/Box.scss";
export default function Box() {
  const arr = [6];
  const rendering = () => {
    const result = [];
    for (let i = 0; i < arr[0]; i++) {
      result.push(
        <div className="col">
          <div className="card h-100">
            <img
              src="https://mdbootstrap.com/img/Photos/Others/images/43.webp"
              className="card-img-top"
              alt="..."
            />
            <div className="card-body">
              <div className="card-text">
                <span className="title">공고번호</span>
                <p>0000000000</p>
              </div>
              <div className="card-text">
                <span className="title">접수일시</span>
                <p>2022-03-22</p>
              </div>
              <div className="card-text">
                <span className="title">관할기관</span>
                <p>멀티캠퍼스</p>
              </div>
              <div className="card-text">
                <span className="title">발견장소</span>
                <p>멀티캠퍼스</p>
              </div>
              <a href="http://localhost:3000/animals/animaldetails" className="btn btn-primary">
                자세히 보기
              </a>
            </div>
          </div>
        </div>
      );
    }
    return result;
  };
  return (
    <div className="row row-cols-1 row-cols-md-4 g-4">
      {rendering()}
      {/* <div className="col">
        <div className="card h-100">
          <img
            src="https://mdbootstrap.com/img/Photos/Others/images/43.webp"
            className="card-img-top"
            alt="..."
          />
          <div className="card-body">
            <div className="card-text">
              <span className="title">공고번호</span>
              <p>0000000000</p>
            </div>
            <div className="card-text">
              <span className="title">접수일시</span>
              <p>2022-03-22</p>
            </div>
            <div className="card-text">
              <span className="title">관할기관</span>
              <p>멀티캠퍼스</p>
            </div>
            <div className="card-text">
              <span className="title">발견장소</span>
              <p>멀티캠퍼스</p>
            </div>
            <a href="#!" className="btn btn-primary">
              자세히 보기
            </a>
          </div>
        </div>
      </div>

      <div className="col">
        <div className="card h-100">
          <img
            src="https://mdbootstrap.com/img/Photos/Others/images/43.webp"
            className="card-img-top"
            alt="..."
          />
          <div className="card-body">
            <div className="card-text">
              <span className="title">공고번호</span>
              <p>0000000000</p>
            </div>
            <div className="card-text">
              <span className="title">접수일시</span>
              <p>2022-03-22</p>
            </div>
            <div className="card-text">
              <span className="title">관할기관</span>
              <p>멀티캠퍼스</p>
            </div>
            <div className="card-text">
              <span className="title">발견장소</span>
              <p>멀티캠퍼스</p>
            </div>
            <a href="#!" className="btn btn-primary">
              자세히 보기
            </a>
          </div>
        </div>
      </div>
      <div className="col">
        <div className="card h-100">
          <img
            src="https://mdbootstrap.com/img/Photos/Others/images/43.webp"
            className="card-img-top"
            alt="..."
          />
          <div className="card-body">
            <div className="card-text">
              <span className="title">공고번호</span>
              <p>0000000000</p>
            </div>
            <div className="card-text">
              <span className="title">접수일시</span>
              <p>2022-03-22</p>
            </div>
            <div className="card-text">
              <span className="title">관할기관</span>
              <p>멀티캠퍼스</p>
            </div>
            <div className="card-text">
              <span className="title">발견장소</span>
              <p>멀티캠퍼스</p>
            </div>
            <a href="#!" className="btn btn-primary">
              자세히 보기
            </a>
          </div>
        </div>
      </div>
      <div className="col">
        <div className="card h-100">
          <img
            src="https://mdbootstrap.com/img/Photos/Others/images/43.webp"
            className="card-img-top"
            alt="..."
          />
          <div className="card-body">
            <div className="card-text">
              <span className="title">공고번호</span>
              <p>0000000000</p>
            </div>
            <div className="card-text">
              <span className="title">접수일시</span>
              <p>2022-03-22</p>
            </div>
            <div className="card-text">
              <span className="title">관할기관</span>
              <p>멀티캠퍼스</p>
            </div>
            <div className="card-text">
              <span className="title">발견장소</span>
              <p>멀티캠퍼스</p>
            </div>
            <a href="#!" className="btn btn-primary">
              자세히 보기
            </a>
          </div>
        </div>
      </div> */}
    </div>
  );
}
