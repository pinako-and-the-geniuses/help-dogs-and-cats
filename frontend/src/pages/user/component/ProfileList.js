import ProfileCategory from "./ProfileCategory";
import ProfileCommunity from "./ProfileCommunity";
import ProfileVolunteer from "./ProfileVolunteer";
import ProfileAdoption from "./ProfileAdoption";

import { useState } from "react";

export default function ProfileList(props) {
  const [category, setCategory] = useState("volunteers");

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
