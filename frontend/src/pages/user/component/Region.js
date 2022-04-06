import Area from "../../volunteer/areaData";

export default function Region({ URL, region, setRegion }) {
  const onRegionHandler = (e) => {
    const RegionCurrent = e.target.value;
    setRegion(RegionCurrent);
  };

  return (
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
          {Area.map((area) => (
            <option value={area.value} key={area.value}>
              {area.name}
            </option>
          ))}
        </select>
      </div>
    </div>
  );
}
