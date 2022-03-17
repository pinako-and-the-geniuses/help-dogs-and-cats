import Map from '../../components/Map';
import './styles/ShelterDetail.scss';

function ShelterDetail(){

    return(
        <div className='main-container'>
            <header>
                <h2>동물 보호 센터</h2>
            </header>

            <section className="container">
                <div className='row'>
                    <div className=" col-6 main-info">
                        <div className="text-box">
                            <p className="title">보호센터명</p>
                            <p>멀티캠퍼스</p>
                        </div>
                        <div className="text-box">
                            <p className="title">전화번호</p>
                            <p>02-000-0000</p>
                        </div>
                        <div className="text-box">
                            <p className="title">주소</p>
                            <p>서울특별시 강남구 테헤란로 121</p>
                        </div>
                        <div className="text-box">
                            <p className="title">관리기관명</p>
                            <p>삼성멀티캠퍼스 (법인)</p>
                        </div>
                        <div className="text-box">
                            <p className="title">운영시간</p>
                            <p>9:00 ~ 18:00</p>
                        </div>
                        <div className="text-box">
                            <p className="title">휴무일</p>
                            <p></p>
                        </div>
                        <div className="text-box">
                            <p className="title">구조대상동물</p>
                            <p>개+고양이+기타</p>
                        </div>
                    </div>

                    <div className="col-6 map">
                        <Map />
                    </div>
                </div>
            </section>
        </div>
    )
}

export default ShelterDetail;