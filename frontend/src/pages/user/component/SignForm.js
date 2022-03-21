// import DeleteUser from "./DeleteUser";
// import Email from "./Email";
// import Password from "./Password";
// import NickName from "./NickName";
// import Phone from "./Phone";
// import Region from "./Region";
// import st from "../styles/userform.module.scss";

// export default function SignForm(props) {

//   return (
//     <form className={`${st.userinfoForm} ${st.form}`}>
//       <h2>{props.pagename}</h2>
//       <Email
//         URL={URL}
//         email={props.email}
//         setEmail={props.set}
//         isEmail={props.isEmail}
//         setIsEmail={props.setIsEmail}
//         inputClass={inputClass}
//       ></Email>
//       <Password
//         URL={URL}
//         isPwd={props.isPwd}
//         setIsPwd={props.setIsPwd}
//         isPwdConfirm={props.isPwdConfirm}
//         setIsPwdConfirm={props.setIsPwdConfirm}
//         inputClass={inputClass}
//       ></Password>
//       <NickName
//         URL={URL}
//         isNickName={props.isNickName}
//         setIsNickName={props.setIsNickName}
//         inputClass={inputClass}
//       />
//       <Phone
//         URL={URL}
//         isPhone={props.isPhone}
//         setIsPhone={props.setIsPhone}
//         inputClass={inputClass}
//       />
//       <Region
//         URL={URL}
//         isRegion={props.isRegion}
//         setIsRegion={props.setIsRegion}
//       />

//       <div className="form-check">
//         <label className="form-check-label" htmlFor="policy">
//           이용약관에 동의합니다.
//         </label>
//         <input
//           className="form-check-input"
//           type="checkbox"
//           style={{ width: "auto", marginRight: "5px" }}
//           onChange={props.onIsChekedHandler}
//         />
//       </div>

//       <div>
//         <button
//           className={st.longButton}
//           disabled={!props.isPassword}
//           onClick={props.onSubmit}
//         >
//           {props.pagename}
//         </button>
//       </div>
//       <DeleteUser />
//     </form>
//   );
// }
