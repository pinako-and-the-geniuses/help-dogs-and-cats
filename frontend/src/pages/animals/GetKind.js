import animal from "./styles/Animal.module.scss";

// [축종 조회]
export default function GetKind({ setKind }) {
  return (
    <select
      defaultValue="0"
      className={animal.textBox}
      aria-label="축종"
      onChange={(e) => setKind(e.target.value)}
    >
      <option value="">축종</option>
      <option value="417000">강아지</option>
      <option value="422400">고양이</option>
      <option value="429900">기타</option>
    </select>
  );
}
