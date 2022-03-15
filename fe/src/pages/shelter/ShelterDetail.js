import Map from "./Map";
import './styles/ShelterDetail.scss';

function ShelterDetail(){

    return(
        <body>
            <h2>동물 보호 센터</h2>

            <div id="content-box">
                <div className="detail-box">
                    <p className="title">보호센터명</p>
                    <p className="content">믿음소망사랑</p>
                </div>
                <div className="detail-box">
                    <p className="title">전화번호</p>
                    <p className="content">032-000-0000</p>
                </div>
                <div className="detail-box">
                    <p className="title">주소</p>
                    <p className="content">사랑구 행복동</p>
                </div>

                <Map className="map"></Map>            

            </div>
        </body>
    )
}

export default ShelterDetail;