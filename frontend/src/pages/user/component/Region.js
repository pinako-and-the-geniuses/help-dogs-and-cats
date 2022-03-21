export default function Region({ URL, region, setRegion }) {
  const onRegionHandler = (e) => {
    const RegionCurrent = e.target.value;
    setRegion(RegionCurrent);
  };

  return (
    <>
      <div>
        <label htmlFor="region">활동 지역</label>
        <div className="input-group mb-3">
          <select
            className="form-select"
            id="region"
            aria-label="Example select with button addon"
            value={region}
            onChange={onRegionHandler}
          >
            <option value="전체">전체</option>
            <option value="서울">서울</option>
            <option value="광주">광주</option>
            <option value="부산">부산</option>
            <option value="울산">울산</option>
            <option value="경주">경주</option>
          </select>
        </div>
      </div>
    </>
  );
}
