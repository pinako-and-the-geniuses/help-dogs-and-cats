import {
  Card,
  CardImg,
  CardBody,
  Badge,
  CardSubtitle,
} from "reactstrap";
import "./styles/AnimalDetails.scss";
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
            
            <Badge color="success" pill>
              공고중
            </Badge>
            <CardSubtitle tag="h4">
              <b>공고번호</b>
            </CardSubtitle>
            <CardSubtitle tag="h4">
              <b>접수일시</b>
            </CardSubtitle>
            <CardSubtitle tag="h4">
              <b>발견장소</b>
            </CardSubtitle>
            {/* <CardText>
              This is a wider card with supporting text below as a natural
              lead-in to additional content. This content is a little bit
              longer.
            </CardText> */}
          </CardBody>
        </Card>
      </div>
    </div>
  );
}
