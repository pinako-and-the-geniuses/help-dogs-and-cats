import styled from "styled-components";

function Paging({ total, limit, page, setPage }) {
    const numPages = Math.ceil(total / limit);
    console.log('총페이지수', numPages);
    // //numpages = 총 페이지 수
    // //한번에 보여줄 페이지 수........10개.....뭐...뭐요...아 짱나넼ㅋ
    // //numPages를...한 페이지에 보여줄 번호 수로 나눔
    // const currentPosts = Math.ceil(numPages/10);
    // console.log('총페이지수....가.......이만큼..',currentPosts);
    // console.log(Math.ceil(numPages/currentPosts));
  

    //필요한 것: 
    //pageRangeDisplayed
    //만 있으면 끝이라구용...~!

    //10은 postPerPage (props로 받아오기 수정~!)
    const indexOfLast = page * 10;
    const indexOfFirst = indexOfLast - 10;
    // function currentPosts(tmp){
    //   let currentPosts = 0;
    //   currentPosts = tmp.slice(indexOfFirst, indexOfLast);
    //   return currentPosts;
    // }
    // posts = {currentPosts(posts)}

    console.log(indexOfLast);
    console.log(indexOfFirst);
    //postPerPage, totalPosts, paginate
    return (
      <>
        <Nav>
          <Button onClick={() => setPage(page - 1)} disabled={page === 1}>
            &lt;
          </Button>
          {/* {Array(numPages.slice(indexOfFirst, indexOfLast)) */}
          {Array(numPages)
            .fill()
            .map((_, i) => (
              <Button
                key={i + 1}
                onClick={() => setPage(i + 1)}
                aria-current={page === i + 1 ? "page" : null}
              >
                {i + 1}
              </Button>
            ))}
          <Button onClick={() => setPage(page + 1)} disabled={page === numPages}>
            &gt;
          </Button>
        </Nav>
      </>
    );
  }

  const Nav = styled.nav`
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 4px;
  margin: 16px;
`;

const Button = styled.button`
  border: 1px solid #b59d7c;
  border-radius: 8px;
  padding: 5px 10px;
  margin: 0;
  background: white;
  color: #b59d7c;
  font-size: 1rem;

  &:hover {
    background: #b59d7c;
    color: white;
    cursor: pointer;
    transform: translateY(-2px);
  }

  &[disabled] {
    background: lightgrey;
    cursor: revert;
    transform: revert;
  }

  &[aria-current] {
    background: #b59d7c;
    color: white;
    font-weight: bold;
    cursor: revert;
    transform: revert;
  }
`;

  export default Paging;