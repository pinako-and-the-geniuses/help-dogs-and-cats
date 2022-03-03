![waving](https://capsule-render.vercel.app/api?type=waving&height=200&text=🎉BigData_A302팀!🎉&fontAlign=50&fontAlignY=40&color=gradient)

# 서비스 - 도와주개냥🐾

## 목차

- [팀 소개](#팀-소개)
- [서비스 기획의도](#서비스-기획의도)
- [서비스 주요기능](#서비스-주요기능)
- [참고 데이터](#참고-데이터)

## 팀 소개
| 구분 | 이름   | 개발 파트 |
| ---- | ------ | --------- |
| 팀장 | 안영진 | 미정      |
| 팀원 | 김경석 | 미정      |
| 팀원 | 김중우 | 미정      |
| 팀원 | 송지호 | 미정      |
| 팀원 | 임건호 | 미정      |
| 팀원 | 장효정 | 미정      |

## 서비스 기획의도

매년 증가하는 유기동물 발생 수 [지난해(2021) 유기동물 13만 마리… 21%는 안락사](https://futurechosun.com/archives/55827)<br/>
유기동물 조회및 정보 열람의 편의성을 높이며,<br/>
쉽고 간편하게 여러 사람들과 함께 유기동물 관리(봉사, 순찰)에 참여할 수 있는 플랫폼을 개발

## 서비스 주요기능


#### 유기동물 관련 데이터 제공 (동물 조회 / 데이터 시각화)
  - 품종, 성별, 색상 등 다양한 조건들로 보호중인 유기동물 조회 가능
  - 2007년부터의 유기동물 관련 데이터를 분석해, 시각적인 정보로 제공

#### 보호소 관련 데이터 제공
  - 지역별 혹은 현재 위치에서 가까운 보호소 정보 제공

#### 봉사 신청
  - 유기동물 발생이 잦은 지역 또는 장소 정보 제공 -> 서비스 사용자들에게 소정의 혜택을 제공하며 해당지역 자발적 봉사참여 권유
  - 근처 or 특정 보호소 봉사

#### 프로필 꾸미기
  - 봉사 or 입양 등 서비스내에서 제공하는 다양한 활동을 통해 꾸며진 프로필을 다른 사용자에게 드러내며 봉사의식 함양 및 성취감 증대

#### 커뮤니티 기능
  - 봉사 인원 구하기, 주변 유기동물 신고 등 다양한 커뮤니티 활동 가능

#### 추가될 수 있는 기능

- 구조 동물 관련 데이터 분석 정보 제공
- 애견 관련 서비스 정보 제공(병원, 약국, 카페 등등)
- 분석 자료를 통해서 유기 확률이 높은 동물

<hr>

### 참고 데이터
- 공공데이터 포탈에서 제공하는 농림축산식품부 농림축산검역본부_동물보호관리시스템 유기동물 정보 조회 Open Api를 이용하였습니다.
- 공공데이터 포털에 없는 지역의 데이터는 각 지방자치단체 홈페이지의 데이터를 이용하였습니다.


# 저희 Team의 컨벤션은 다음과 같습니다.

## 모든 git 행동의 첫 번째 행동은 Pull하기

### Git 커밋 컨벤션

> git commit -m "#jira-id Feat, Fix, Style 등등: 한글~ ”

1. 기능을 추가한 경우

```bash
# 따옴표 안쪽만 복사하기~~~~
git commit -m "#jira-id Feat: 로그인 API 구현"
```

2. 버그를 수정한 경우

```bash
# 따옴표 안쪽만 복사하기~~~~
git commit -m "#jira-id Fix: 봉사 신청 버그 수정"
```

3. 코드 스타일만 변경된 경우 (성능과 무관)

```bash
# 따옴표 안쪽만 복사하기~~~~
git commit -m "#jira-id Style: 메인 페이지 메뉴 스타일 변경"
```

4. 빌드와 관련되거나 잡다한 것들

```bash
# 따옴표 안쪽만 복사하기~~~~
git commit -m "#jira-id Chore: 이슈 템플릿 추가"
git commit -m "#jira-id Chore: 젠킨스..??..??"
```

5. 문서 작업

```bash
# 따옴표 안쪽만 복사하기~~~~
git commit -m "#jira-id Docs: 리드미 작성"
```

6. 코드를 리팩토링한 경우

```bash
# 따옴표 안쪽만 복사하기~~~~
git commit -m "#jira-id Refac: 공지사항 API 호출 콛" 
```

### 🌳 Branch 컨벤션

- 참고
  ![Untitled](/uploads/f9f5ddd46f5f49b8e10540ac442387dd/Untitled.png)

#### “branch-name 양식”


### <span style="color:red">`develop`</span>(<span style="color:red">`front` </span>or <span style="color:red">`back`</span>)/<span style="color:red">`유형`</span>-<span style="color:red">`기능 요약`</span>/<span style="color:red">`jira 이슈번호`</span>

예시

```bash
$ git checkout -b 'branch-name'

$ git checkout -b (front or back)/Add-Login-API/#3EWIAOVAIE

$ git checkout -b develop:(front or back)/유형-기능요약/jira이슈번호
	          |-----| 는 파고싶은 브랜치 이름

$ git branch checkout -b front/feature-login-menu-component/#ASDWQ123 
		         |------------------------------------------| (브랜치 이름)
$ git push 라고 치면 어떻게 쳐야하는지 알아서 알려줌

$ git push origin front-feature/#ASDWQ123-login-menu-component 
		  |------------------------------------------| (브랜치 이름)

/////////////////////////////////////////////////////////////////////////////////
[보낼때]
$ git add .

$ git commit -m "#S06P12A202-80 Feat: Component 생성"

$ git push origin develop:front/feature-nav-component/#S06P12A202-56

$ git push origin develop:front/fest-home-funding-list/#S06P12A202-82

[받을때]
$ git pull origin develop
```


### Branch name 실수했을 때

local에서는

```bash
$ git branch -m back/future/#XXX-XX-XXX back/feature/#XXX-XX-XXX
	        |---------------------| |----------------------|
		      틀린 브랜치 이름          바꿀 브랜치 이름
```

이미 remote에 올렸다면???

일단 remote에서는 그냥 브랜치 삭제한다

local에서 브랜치 이름 바꾼다음에

```bash
$ git push origin HEAD
```

해준다

## Jira

- Story는 서술형 작성([사용자]는 [무엇]을/으로 [액션]할 수 있다)
- Labels는 대문자로 작성
- Labels에 업무 직무는 FRONTEND 또는 BACKEND로 작성
- Labels에 업무 형태는 Git 커밋 메세지와 동일(EX. FEAT, FIX, DOCS 등)
