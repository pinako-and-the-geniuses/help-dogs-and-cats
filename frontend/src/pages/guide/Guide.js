import styled from "styled-components";

const GuideMain = styled.div`
  margin: auto;
  width: 75%;
  /* background-color: #b59d7c; */
  background-color: #f8eddd;
  padding-top: 50px;
  padding-bottom: 50px;
  border-radius: 5px;
`;

const GuideBanner = styled.div`
  position: relative;
`;
const GuideBackground = styled.div`
  margin: auto;
  background: url("images/guide.jpg") repeat scroll center center/cover;
  height: 700px;
  width: 90%;
  filter: brightness(60%);
  border-radius: 5px;
`;

const GuideBannerText = styled.div`
  position: absolute;
  margin-left: 10%;
  font-weight: bold;
  font-size: 20px;
  color: white;
  top: ${(props) => props.top};
`;

const GuideBannerTop = styled.div`
  font-size: 25px;
`;

const GuideBannerSpan = styled.span`
  text-align: center;
  color: ${(props) => props.color};
  font-size: ${(props) => props.size};
`;

const GuideTitle = styled.div`
  font-size: 35px;
  margin: auto;
  text-align: center;
  margin-bottom: 30px;
`;

const GuideInfoBox = styled.div`
  margin-left: 5%;
  margin-top: 30px;
  margin-bottom: 30px;
`;
const GuideInfo = styled.div`
  font-size: 30px;
`;

const GuideInfoList = styled.li`
  margin-top: 30px;
`;

const GuideInfoDetail = styled.div`
  font-size: 20px;
`;

const GuideMessageBox = styled.div``;
const GuideMessage = styled.div`
  margin: auto;
  margin-top: 30px;
  text-align: center;
  width: 70%;
  font-size: 17px;
`;
const GuideMessageInfo = styled.div`
  margin: auto;
  margin-top: 30px;
  margin-bottom: 30px;
  text-align: center;
  width: 70%;
  font-size: 30px;
`;

const SeparateLine = styled.hr``;

function Guide() {
  return (
    <GuideMain>
      <GuideTitle>유기 동물 입양 가이드</GuideTitle>
      <GuideBanner>
        <GuideBackground></GuideBackground>
        <GuideBannerText top="10%">
          <GuideBannerTop>
            <GuideBannerSpan color="#b8a07e">동물</GuideBannerSpan>을 입양한
            당신이
            <GuideBannerSpan color="skyblue">자랑</GuideBannerSpan>스럽습니다.
          </GuideBannerTop>
        </GuideBannerText>
        <GuideBannerText top="70%">
          <GuideBannerSpan color="white" size="35px">
            도와주개냥
          </GuideBannerSpan>
          은 <br />
          <GuideBannerSpan color="white" size="30px">
            유기동물입양
          </GuideBannerSpan>
          과{" "}
          <GuideBannerSpan color="white" size="30px">
            봉사활동
          </GuideBannerSpan>
          의 활성화를 위한{" "}
          <GuideBannerSpan color="white" size="30px">
            문화공간
          </GuideBannerSpan>
          입니다
        </GuideBannerText>
      </GuideBanner>
      <GuideMessageBox>
        <GuideMessage>
          개나 고양이를 키우고 싶다면 유기동물 보호시설에서 보호하고 있는
          유기동물을 입양하는게 어떨까요?
          <br /> 원하는 동물을 새 식구로 맞이하는 것은 물론 한 생명을 구했다는
          자부심으로 가슴이 뿌듯해집니다.
        </GuideMessage>
        <GuideMessageInfo>
          도와주개냥을 통해 유기동물 입양과 봉사활동을 경험해보세요!
        </GuideMessageInfo>
      </GuideMessageBox>
      <SeparateLine></SeparateLine>
      <GuideInfoBox>
        <GuideInfo>
          입양을 하기 전 진지하게 다음 사항들을 고려해주세요!
        </GuideInfo>
        <GuideInfoDetail as="p">
          <ol>
            <GuideInfoList>
              반려동물을 맞이할 환경과 마음의 준비가 되어있으신가요?
            </GuideInfoList>
            <GuideInfoList>
              개와고양이는10~15년 이상 삽니다. <br />
              결혼, 임신, 유학, 이사 등으로 가정환경이 바뀌어도 한번 인연을 맺은
              동물은 끝까지 책임지고 보살피겠다는 결심이 있으신가요?
            </GuideInfoList>
            <GuideInfoList>
              입양에 대해서 모든 가족 구성원들의 동의가 이루어졌나요?
            </GuideInfoList>
            <GuideInfoList>
              반려동물은 세심한 관리가 필요합니다. 내 동물을 위해 열심히 공부할
              각오가 되어 있으신가요?
            </GuideInfoList>
            <GuideInfoList>
              아플 때 적절한 치료를 해주며, 중성화수술을 실천 하실 건가요?
            </GuideInfoList>
            <GuideInfoList>
              입양으로 인한 경제적 부담을 짊어질 의사와 능력이 충분하신가요?
            </GuideInfoList>
          </ol>
        </GuideInfoDetail>
      </GuideInfoBox>
      <SeparateLine></SeparateLine>
      <GuideInfoBox>
        <GuideInfo>입양 시 일부 경비가 청구될 수 있습니다.</GuideInfo>
        <GuideInfoDetail>
          <ol>
            <GuideInfoList>
              시 군 구청에서 보호하고 있는 유기동물 중 공고한 지 10일이 지나도
              주인이 나타나지 않는 경우 일반인에게 분양할 수 있습니다.
            </GuideInfoList>
            <GuideInfoList>
              입양 보호 시설에 미리 전화로 문의하고 담당자의 안내에 따라 방문
              일시 등을 예약합니다.
            </GuideInfoList>
            <GuideInfoList>
              입양 시 신분증 복사본 2장과 필요한 반려동물 물품을 준비하고
              보호시설을 방문해 입양계약서를 작성해야 합니다.
            </GuideInfoList>
            <GuideInfoList>
              입양 보호시설에는 신청자 본인이 직접 방문해야 합니다.
            </GuideInfoList>
            <GuideInfoList>
              미성년자에게는 반려동물을 분양하지 않습니다.
              <br /> 분양을 원하는 미성년자는 부모님의 허락을 얻어 반드시
              부모님과 함께 방문해야 합니다.
            </GuideInfoList>
          </ol>
        </GuideInfoDetail>
      </GuideInfoBox>
    </GuideMain>
  );
}

export default Guide;
