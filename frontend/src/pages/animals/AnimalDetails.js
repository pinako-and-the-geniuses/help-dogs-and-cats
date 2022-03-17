import { Card, CardImg, CardBody, Badge, CardSubtitle } from "reactstrap";
import './styles/AnimalDetails.scss';
export default function AnimalDetails() {
  return (
    <div>
      <h2 className="name">
        <b>보호중 동물</b>
      </h2>

      <div>
        <Card>
          <CardImg
            alt="Card image cap"
            src="https://picsum.photos/256/186"
            top
            width="100%"
          />
          <CardBody>
            
              <Badge color="success" pill className="mb-3">
                공고중
              </Badge>
            <CardSubtitle tag="h5">
              <b>공고번호</b>
            </CardSubtitle>
            <br />
            <CardSubtitle tag="h5">
              <b>접수일시</b>
            </CardSubtitle>
            <br />
            <CardSubtitle tag="h5">
              <b>발견장소</b>
            </CardSubtitle>
            <br />
          </CardBody>
        </Card>
      </div>
    </div>
  );
}
