export default function NickName({
  URL,
  nickName,
  setNickName,
  setIsNickName,
  isNickName,
  inputClass,
}) {
  const onNickNameHandler = (e) => {
    const nickName = e.currentTarget.value;
    setNickName(nickName);

    if (nickName.length < 2 || nickName.length > 8) {
      setIsNickName(false);
    } else {
      setIsNickName(true);
    }
  };

  const isEnteredNickNameValid = () => {
    if (nickName) return isNickName;
  };

  return (
    <>
      <div>
        <label htmlFor="nickname">닉네임</label>
        <input
          id="nickname"
          name="nickname"
          type="text"
          placeholder="닉네임"
          value={nickName}
          onChange={onNickNameHandler}
          className={`form-control ${inputClass(isEnteredNickNameValid())}`}
        />
      </div>
    </>
  );
}
