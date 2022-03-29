import React, { useState } from "react";
import Pagination from "react-js-pagination";
const Paging = ({currentPageNumber, totalcount, setPage}) => {
  // const [page, setPage] = useState(1);
  // const handlePageChange = (page) => {
  //   setPage(page);
  //   // console.log(page);
  // };
  return (
    // <Pagination
    //   activePage={page}
    //   itemsCountPerPage={10}
    //   totalItemsCount={450}
    //   pageRangeDisplayed={5}
    //   prevPageText={"‹"}
    //   nextPageText={"›"}
    //   onChange={handlePageChange}
    // />
    <Pagination
      activePage={currentPageNumber}
      itemsCountPerPage={10}
      totalItemsCount={totalcount}
      pageRangeDisplayed={5}
      prevPageText={"‹"}
      nextPageText={"›"}
      onChange={setPage}
    />
  );
};
export default Paging;
