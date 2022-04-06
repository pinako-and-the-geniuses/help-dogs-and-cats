import { Col, Container, Row } from "react-bootstrap";
import styled from "styled-components";

const FooterMain = styled.footer`
  width: 100%;
  background-color: lightgray;
  display: flex;
  align-items: center;
  justify-content: space-evenly;
  flex-wrap: nowrap;
  bottom: 0px;
  position: relative;
`;

const FooterContentForm = styled.div`
  margin: 20px 0px;
`;

const FooterContentTitle = styled.span`
  display: block;
  font-weight: bold;
  font-size: 15px;
`;

const FooterContent = styled.span`
  display: block;
`;

const FooterLogo = styled.img`
  width: 60px;
  height: 60px;
  margin-bottom: 10px;
  border-radius: 5px;
`;

const styles = {
  col: {
    paddingLeft: 30,
    paddingRight: 30,
  },
  container: {
    marginLeft: 0,
    marginRight: 0,
  },
  font: {
    fontSize: 13,
  },
};

function Footer() {
  return (
    <FooterMain>
      <Container>
        <Row>
          <Col style={styles.col} xs={4}>
            <FooterContentForm>
              <FooterContentTitle>고객센터</FooterContentTitle>
              <FooterContent>1588-1234</FooterContent>
              <br />
              <FooterContentTitle>상담 가능 시간</FooterContentTitle>
              <FooterContent>
                평일 오전 9시 ~ 오후 6시 <br />
                (주말, 공휴일 제외)
              </FooterContent>
            </FooterContentForm>
          </Col>
          <Col style={styles.col} xs={4}>
            <FooterContentForm>
              <FooterContentTitle>관리자 (24시간 접수 가능)</FooterContentTitle>
              <FooterContent>a302helpdogscats@gmail.com</FooterContent>
              <br />
              <FooterContentTitle>
                도와주개냥은 유기동물입양과 봉사활동의 활성화를 위한
                문화공간입니다.
              </FooterContentTitle>
            </FooterContentForm>
          </Col>
          <Col style={styles.col} xs={4}>
            <FooterContentForm>
              <FooterLogo src="/favicon.png" alt="안영진" />
              <FooterContentTitle>도와주개냥(주)</FooterContentTitle>
              <FooterContentTitle>
                서울시 강남구 테헤란로 212
              </FooterContentTitle>
              <FooterContent style={styles.font}>
                <br />© 2022 서울3반2조, Inc. All Rights Reserved
              </FooterContent>
            </FooterContentForm>
          </Col>
        </Row>
      </Container>
    </FooterMain>
  );
}

export default Footer;
