import st from "pages/user/styles/profile.module.scss";

export default function SmallPaging({ page, setPage, totalPageNumber }) {
  const onPrevious = () => {
    if (page === 1) {
      return (
        <button href="#" disabled>
          Previous
        </button>
      );
    } else if (page !== 1)
      return (
        <button href="#" onClick={() => setPage(page - 1)}>
          Previous
        </button>
      );
  };

  const onNext = () => {
    if (totalPageNumber !== page) {
      return (
        <button href="#" onClick={() => setPage(page + 1)}>
          Next
        </button>
      );
    } else {
      return (
        <button href="#" disabled>
          Next
        </button>
      );
    }
  };
  return (
    <div name="페이저" className={st.pager}>
      <li>{onPrevious()}</li>
      <li>{onNext()}</li>
    </div>
  );
}
