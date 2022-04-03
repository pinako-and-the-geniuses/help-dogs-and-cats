import React from "react";
import { Link } from "react-router-dom";
import st from "./styles/CardItem.module.scss";

function CardItem(props) {
  return (
    <>
      <li className={st.cards__item}>
        <Link className={st.cards__item__link} to={props.path}>
          <figure
            className={st.cards__item__pic_wrap}
            data-category={props.label}
          >
            <img className={st.cards__item__img} alt="..." src={props.src} />
          </figure>
        </Link>
      </li>
    </>
  );
}

export default CardItem;
