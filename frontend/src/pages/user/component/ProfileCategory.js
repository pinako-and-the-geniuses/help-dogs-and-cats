import st from "../styles/profile.module.scss";

export default function ProfileCategory({ category, setCategory }) {
  return (
    <div>
      <div name="카테고리">
        <ul className={st.category}>
          <li
            className={category === "volunteers" ? st.now : st.notnow}
            onClick={() => setCategory("volunteers")}
          >
            봉사활동
          </li>
          <li
            className={category === "adopts" ? st.now : st.notnow}
            onClick={() => setCategory("adopts")}
          >
            입양활동
          </li>
          <li
            className={category === "communities" ? st.now : st.notnow}
            onClick={() => setCategory("communities")}
          >
            커뮤니티 활동
          </li>
        </ul>
      </div>
    </div>
  );
}
