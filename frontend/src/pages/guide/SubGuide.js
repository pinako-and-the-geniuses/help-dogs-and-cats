import styled from 'styled-components';

const Container = styled.div`
    padding: 10px 20px;
    margin: 0;
`;

const Box = styled.div`
    padding-bottom: 10px;
    margin-top: 20px;
`;

const Title = styled.p `
    font-size: 20px;
    font-weight: bold;
    margin: 0 0 5px 15px;
`;

const Line = styled.hr`
    color: silver;
    margin: 10px 0;
    width: 90%;
`;

function SubGuide(){
    return(
        <Container>
            <Title>입양을 원하신다면 보호소에 연락해 담당자와 상담해주세요</Title>
            <ul>
                <li>보호소에서 전화를 받지 않는다면 관할 시군구청 유기동물담당부서로 연락해 안내받을 수 있습니다</li>
            </ul>
            <Line />

            <Box>
                <Title>입양 전 고려해주세요</Title>
                <ol>
                    <li>반려동물을 맞이할 환경과 마음의 준비가 되어있으신가요?</li>
                    <li>개와고양이는10~15년 이상 삽니다.<br/>
                        결혼, 임신, 유학, 이사 등으로 가정환경이 바뀌어도 한번 인연을 맺은 동물은 끝까지 책임지고 보살피겠다는 결심이 있으신가요?</li>
                    <li>입양에 대해서 모든 가족 구성원들의 동의가 이루어졌나요?</li>
                    <li>반려동물은 세심한 관리가 필요합니다. 내 동물을 위해 열심히 공부할 각오가 되어 있으신가요?</li>
                    <li>아플 때 적절한 치료를 해주며, 중성화수술을 실천 하실 건가요?</li>
                    <li>입양으로 인한 경제적 부담을 짊어질 의사와 능력이 충분하신가요?</li>
                </ol>
            </Box>       
            <Line />
            <Box>
                <Title>입양시 일부 경부가 청구될 수 있습니다</Title>
                <ol>
                    <li>시 군 구청에서 보호하고 있는 유기동물 중 공고한 지 10일이 지나도 주인이 나타나지 않는 경우 일반인에게 분양할 수 있습니다.</li>
                    <li>입양 보호 시설에 미리 전화로 문의하고 담당자의 안내에 따라 방문 일시 등을 예약합니다.</li>
                    <li>입양 시 신분증 복사본 2장과 필요한 반려동물 물품을 준비하고 보호시설을 방문해 입양계약서를 작성해야 합니다.</li>
                    <li>입양 보호시설에는 신청자 본인이 직접 방문해야 합니다.</li>
                    <li>미성년자에게는 반려동물을 분양하지 않습니다.<br/>분양을 원하는 미성년자는 부모님의 허락을 얻어 반드시 부모님과 함께 방문해야 합니다.</li>
                </ol>
            </Box>     
        </Container>
    )
}

export default SubGuide;