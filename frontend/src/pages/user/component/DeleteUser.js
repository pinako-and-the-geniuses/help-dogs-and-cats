export default function DeleteUser() {
  const onDeleteUser = () => {
    console.log("탈퇴한다.");
  };
  return (
    <div className="deleteuser">
      <button id="delete" onClick={onDeleteUser}>
        회원탈퇴
      </button>
    </div>
  );
}
