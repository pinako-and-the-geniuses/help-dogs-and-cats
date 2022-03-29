import ProfileCategory from "./ProfileCategory";
import ProfileCommunity from "./ProfileCommunity";
import ProfileVolunteer from "./ProfileVolunteer";
import ProfileAdoption from "./ProfileAdoption";
import st from "../styles/profile.module.scss";
import cn from "classnames";
import { useEffect, useState } from "react";

import axios from "axios";
import { URL } from "public/config";
import { useNavigate } from "react-router-dom";

export default function ProfileList(props) {
  const [category, setCategory] = useState("community");
  const navigator = useNavigate();

  const onNowCategory = () => {
    if (category === "communities") {
      return (
        <ProfileCommunity
          category={category}
          seq={props.seq}
          isLogin={props.isLogin}
        />
      );
    } else if (category === "volunteers") {
      return (
        <ProfileVolunteer
          category={category}
          seq={props.seq}
          isLogin={props.isLogin}
        />
      );
    } else {
      return (
        <ProfileAdoption
          category={category}
          seq={props.seq}
          isLogin={props.isLogin}
        />
      );
    }
  };
  return (
    <>
      <ProfileCategory category={category} setCategory={setCategory} />
      {onNowCategory()}
    </>
  );
}
