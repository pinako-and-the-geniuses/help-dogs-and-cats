export default function Phone({
  URL,
  phone,
  setPhone,
  setIsPhone,
  isPhone,
  inputClass,
}) {
  const onPhoneHandler = (e) => {
    const phoneRegex = /^\d{3}-\d{3,4}-\d{4}$/;
    const phoneCurrent = e.target.value;
    setPhone(phoneCurrent);

    if (!phoneRegex.test(phoneCurrent)) {
      setIsPhone(false);
    } else {
      setIsPhone(true);
    }
  };

  const isEnteredPhoneValid = () => {
    if (phone) return isPhone;
  };

  return (
    <>
      <div>
        <label htmlFor="phone">휴대폰 번호</label>
        <input
          id="phone"
          name="phone"
          type="tel"
          pattern="[0-9]{3}-[0-9]{4}-[0-9]{4}"
          placeholder="010-1234-5678( - 포함 입력)"
          value={phone}
          onChange={onPhoneHandler}
          className={`form-control ${inputClass(isEnteredPhoneValid())}`}
          required
        />
      </div>
    </>
  );
}
