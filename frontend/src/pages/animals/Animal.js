import animal from "./styles/Animal.module.scss";
import cn from 'classnames'
import Box from "../../components/animals/Box";
export default function Animal() {
  return (
    <div className={animal.animalcontent}>
      <header>
        <h2>유기동물 조회</h2>
      </header>
      <article className={animal.topContent}>
        <div className={animal.box}>
          <span className={animal.date}>
            <input type="date" />
          </span>
          <select className={animal.textBox} aria-label="시도">
            <option selected>시도</option>
            <option value="1">One</option>
            <option value="2">Two</option>
            <option value="3">Three</option>
          </select>
          <select className={animal.textBox} aria-label="시군구">
            <option selected>시군구</option>
            <option value="1">One</option>
            <option value="2">Two</option>
            <option value="3">Three</option>
          </select>
          <select className={animal.textBox} aria-label="보호센터">
            <option selected>보호센터</option>
            <option value="1">One</option>
            <option value="2">Two</option>
            <option value="3">Three</option>
          </select>
          <button type="button" className={cn(animal.btn, "btn btn")}>
            조회
          </button>
          <div className={animal.box2}>
            <select className={animal.textBox} aria-label="축종">
              <option selected>축종</option>
              <option value="1">One</option>
              <option value="2">Two</option>
              <option value="3">Three</option>
            </select>
            <div className="form-check form-check-inline mx-4">
              <input
                className="form-check-input"
                type="radio"
                name="inlineRadioOptions"
                id="inlineRadio1"
                value="option1"
              />
              <label className="form-check-label" for="inlineRadio1">
                남
              </label>
            </div>
            <div className="form-check form-check-inline">
              <input
                className="form-check-input"
                type="radio"
                name="inlineRadioOptions"
                id="inlineRadio2"
                value="option2"
              />
              <label className="form-check-label" for="inlineRadio2">
                여
              </label>
            </div>
          </div>
        </div>
      </article>
      <article className={animal.bottomContent}>
        {/* <div className="container">
          <div className="row">
            <div className="col"><Box></Box></div>
            <div className="col"><Box></Box></div>
            <div className="col"><Box></Box></div>
          </div>
        </div> */}
        <Box></Box>
      </article>
    </div>
  );
}
