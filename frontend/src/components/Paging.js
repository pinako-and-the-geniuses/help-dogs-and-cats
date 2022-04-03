import styled from "styled-components";

function Paging({ total, limit, page, setPage }) {
    const numPages = Math.ceil(total / limit);
  
    return (
      <>
        <Nav>
          <Button onClick={() => setPage(page - 1)} disabled={page === 1}>
            &lt;
          </Button>
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