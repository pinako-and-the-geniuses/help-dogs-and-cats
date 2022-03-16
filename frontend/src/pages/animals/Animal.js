import AnimalDetails from "./AnimalDetails";
import AnimalDetails2 from "./AnimalDetails2";
import './styles/Animal.scss';

export default function Animal() {
  return (
    <div className="row">
        <div className="left">
            <AnimalDetails />
        </div>
        <div className="right">
            <AnimalDetails2 />
        </div>
    </div>
  );
}
