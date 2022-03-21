import {
  Table,
  Button,
  Modal,
  ModalHeader,
  ModalBody,
  ModalFooter,
} from "reactstrap";
import Map from "../../components/Map";
import './styles/AnimalDetails.scss';
export default function AnimalDetails2() {
  return (
    <div>
      <Table striped bordered hover size="sm" className="one">
        <thead>
          <tr>
            <th colSpan="4">동물의 정보</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>
              <b>품종</b>
            </td>
            <td colSpan={3}>Jacob</td>
          </tr>
          <tr>
            <td>
              <b>kg</b>
            </td>
            <td colSpan="3">Mark</td>
          </tr>
          <tr>
            <td>
              <b>성별</b>
            </td>
            <td colSpan={3}>Jacob</td>
          </tr>
          <tr>
            <td>
              <b>중성화</b>
            </td>
            <td colSpan={3}>Jacob</td>
          </tr>
          <tr>
            <td>
              <b>색상</b>
            </td>
            <td colSpan={3}>Jacob</td>
          </tr>
          <tr>
            <td>
              <b>특징</b>
            </td>
            <td colSpan={3}>Jacob</td>
          </tr>
        </tbody>
      </Table>
      <Table striped bordered hover size="sm" className="two">
        <thead>
          <tr>
            <th colSpan="4">보호 정보</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>
              <b>관할기관</b>
            </td>
            <td colSpan={3}>Jacob</td>
          </tr>
          <tr>
            <td>
              <b>보호센터</b>
            </td>
            <td colSpan="3">Mark</td>
          </tr>
          <tr>
            <td>
              <b>보호소 주소</b>
            </td>
            <td colSpan={3}>Jacob</td>
          </tr>
          <tr>
            <td>
              <b>연락처</b>
            </td>
            <td colSpan={3}>Jacob</td>
          </tr>
        </tbody>
      </Table>
      <div>
        <Button
          color="secondary"
          onClick={function noRefCheck() {}}
          style={{ position: "absolute", right: 0, marginRight: "230px" }}
        >
          입양가이드
        </Button>
        <Modal toggle={function noRefCheck() {}}>
          <ModalHeader toggle={function noRefCheck() {}}>
            Modal title
          </ModalHeader>
          <ModalBody>
            Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do
            eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim
            ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut
            aliquip ex ea commodo consequat. Duis aute irure dolor in
            reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla
            pariatur. Excepteur sint occaecat cupidatat non proident, sunt in
            culpa qui officia deserunt mollit anim id est laborum.
          </ModalBody>
          <ModalFooter>
            <Button color="primary" onClick={function noRefCheck() {}}>
              Do Something
            </Button>{" "}
            <Button onClick={function noRefCheck() {}}>Cancel</Button>
          </ModalFooter>
        </Modal>
      </div>
      <div className="map">
        <Map></Map>
      </div>
    </div>
  );
}
