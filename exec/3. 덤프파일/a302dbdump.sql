-- MariaDB dump 10.19  Distrib 10.7.3-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: a302db
-- ------------------------------------------------------
-- Server version	10.7.3-MariaDB-1:10.7.3+maria~focal

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Sequence structure for `hibernate_sequence`
--

DROP SEQUENCE IF EXISTS `hibernate_sequence`;
CREATE SEQUENCE `hibernate_sequence` start with 1 minvalue 1 maxvalue 9223372036854775806 increment by 1 cache 1000 nocycle ENGINE=InnoDB;
SELECT SETVAL(`hibernate_sequence`, 1, 0);

--
-- Table structure for table `tb_adopt_auth`
--

DROP TABLE IF EXISTS `tb_adopt_auth`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_adopt_auth` (
  `adopt_seq` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `last_modified_by` varchar(255) NOT NULL,
  `last_modified_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `content` text NOT NULL,
  `status` varchar(255) NOT NULL,
  `title` varchar(255) NOT NULL,
  `member_seq` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`adopt_seq`),
  KEY `FKtite0rshttts6laqd5d289ony` (`member_seq`),
  CONSTRAINT `FKtite0rshttts6laqd5d289ony` FOREIGN KEY (`member_seq`) REFERENCES `tb_member` (`member_seq`)
) ENGINE=InnoDB AUTO_INCREMENT=228 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_adopt_auth`
--

LOCK TABLES `tb_adopt_auth` WRITE;
/*!40000 ALTER TABLE `tb_adopt_auth` DISABLE KEYS */;
INSERT INTO `tb_adopt_auth` VALUES
(163,'f2cc4b67-3c3b-4289-a4c6-d30cbca0b877','2022-03-22 17:36:53','f57f9728-9d4a-4f91-9aff-dc2fbd788e0c','2022-04-06 02:44:25','<p>얼마 전에 입양을 했어요.</p><p>귀겨운 녀석입니다!! ㅎㅎ</p><p>첫 입양이라서 준비할 게 많았네요.</p><p>앞으로 잘 지내보겠습니다!!</p><p><br></p><p><img src=\"https://upload.wikimedia.org/wikipedia/commons/f/fa/Puppy.JPG\" width=\"212\" style=\"\"></p>','REQUEST','얼마전에 입양을 했습니다. 하하!!',3019),
(172,'9d6afb75-ff5b-4e0b-8228-03a58e4bb072','2022-04-06 05:59:43','98a4a6e1-e9d1-413b-a0b9-b690714fb735','2022-04-06 07:27:31','<p><img src=\"http://j6a302.p.ssafy.io:8080/static/files/images/board/9cd4679f-2f94-49ae-a524-12da234233fd.jpg\" width=\"232\" style=\"\"></p><p>입양 인증합니당~~</p>','DONE','입양 인증합니다.~!!',3026),
(217,'f6620129-c087-4953-87b1-302430dd6989','2022-04-07 05:26:12','d56413b2-7808-4a90-98b3-9b95ce2862e7','2022-04-07 05:26:40','<p><img src=\"http://j6a302.p.ssafy.io:8080/static/files/images/board/03dfe694-331d-4b7c-a324-cad134fc4e9e.jpg\" width=\"314\" style=\"\"></p><p>울 고양이 흑미에요!!!</p><p>너무 귀여워 죽겠어요 헤헤헤</p>','DONE','유기동물 입양 인증합니다.~!!',3029),
(226,'ab965980-ab5f-44c3-99d4-e9b139546c7c','2022-04-07 16:16:03','f7335c8c-ada4-4cae-870c-b0663f25073e','2022-04-07 16:16:13','<p><img src=\"http://j6a302.p.ssafy.io:8080/static/files/images/board/15e8e3cf-5acf-4a0a-8a0c-99723b51f1c9.jpg\" width=\"239\" style=\"\"></p><p>귀엽죠??</p>','DONE','입양 했씁니다!!!',3028),
(227,'5be2bd21-177c-4414-87ab-497ae5e85931','2022-04-07 16:25:21','5be2bd21-177c-4414-87ab-497ae5e85931','2022-04-07 16:25:21','<p><img src=\"http://j6a302.p.ssafy.io:8080/static/files/images/board/f18e4f2b-25fb-42ba-9ca4-74c9b1613b78.jpg\" width=\"143\" style=\"\"> 지금은 편안하게 이불덮고 잘 지내는 ㅋㅋㅋ저희강아지입니다.</p>','REQUEST','3월에 말티즈 유기견 데려왔어요~!!',677);
/*!40000 ALTER TABLE `tb_adopt_auth` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_badge`
--

DROP TABLE IF EXISTS `tb_badge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_badge` (
  `badge_seq` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `content` varchar(255) NOT NULL,
  `how_to_get` text NOT NULL,
  `name` varchar(255) NOT NULL,
  `image_filename` text NOT NULL,
  PRIMARY KEY (`badge_seq`),
  UNIQUE KEY `UK9tbyrn02b9w0lrspi0vduxvkx` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=482 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_badge`
--

LOCK TABLES `tb_badge` WRITE;
/*!40000 ALTER TABLE `tb_badge` DISABLE KEYS */;
INSERT INTO `tb_badge` VALUES
(15,'d5f7d63d-a1d0-4158-a265-7ab3e7648732','2022-03-25 05:01:46','도와주개냥에 가입하신 모든분께 드리는 뱃지에요! 앞으로 좋은 활동 부탁드린다냥~','최초 가입 시 뱃지 제공','가입 환영 뱃지','welcome-badge.png'),
(16,'a4898267-adc6-41f9-af45-b1c2fdb36347','2022-03-25 05:01:46','사람들과 봉사 이야기로 자주 소통하는 회원이 받는 뱃지에요!','커뮤니티 글 10개 이상 작성 시 뱃지 획득','소통하는 활동가','communication-activist.png'),
(17,'53e3dca3-76f3-4407-9d9b-9b2651d2d270','2022-03-25 05:01:46','유기동물을 입양한 당신! 이제 정말 행복이 가득할거에요!','유기동물 입양 시 뱃지 획득','행복의 시작','start-of-happiness.png'),
(18,'f1076c95-6f3d-4ac7-8a48-131a47320025','2022-03-25 05:01:46','입양이 벌써 두번째시라구요?! 행복은 제곱이 될거에요!!','유기동물 입양 2회시 뱃지 획득','행복은 제곱','happiness-is-square.png'),
(19,'16114a8d-f6d9-4689-8162-e4266b2ce073','2022-03-25 05:01:46','주위를 세심하게 관찰해 유기동물을 발견한 당신에게 드리는 상이에요!','유기동물발견 신고 5회 이상 시 뱃지 획득 ','세심한 관찰꾼','a-careful-observer.png'),
(20,'3788f222-f85f-4b18-8fc5-184e55b2d7e1','2022-03-25 05:01:46','첫 봉사활동을 완료한 당신! 유기동물을 위해 힘내줘서 고마워요!','봉사활동 1회 참여 시(인증) 뱃지 획득','용기있는 발 딛음','brave-step.png'),
(21,'19d4686d-e280-4b85-a458-2358f44f50f7','2022-03-25 05:01:46','봉사 모집왕에게 드리는 뱃지에요!','봉사 모집 5회 달성','나는 봉사 모집왕','volunteer-recruitment-king.png'),
(22,'739896b3-547b-40d7-ade7-f194efceb411','2022-03-25 05:01:46','봉사 모집왕에게 드리는 뱃지에요! ','봉사 모집 15회 달성','나는 봉사 모집왕2','volunteer-recruitment-king2.png'),
(23,'ec99b7d5-1e56-4653-990c-c0e9725b1bec','2022-03-25 05:01:46','봉사 참여왕에게 드리는 뱃지에요!','봉사 참여 5회 달성','나는 봉사 참여왕','volunteer-participation-king.png'),
(24,'a98f185b-091c-4ed4-9f6b-08f8e9a586ed','2022-03-25 05:01:46','봉사 참여왕에게 드리는 뱃지에요!','봉사 참여 15회 달성','나는 봉사 참여왕2','volunteer-participation-king2.png');
/*!40000 ALTER TABLE `tb_badge` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_community`
--

DROP TABLE IF EXISTS `tb_community`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_community` (
  `community_seq` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `last_modified_by` varchar(255) NOT NULL,
  `last_modified_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `category` varchar(255) NOT NULL,
  `content` text NOT NULL,
  `is_deleted` tinyint(4) NOT NULL,
  `title` varchar(255) NOT NULL,
  `view_count` bigint(20) unsigned NOT NULL,
  `member_seq` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`community_seq`),
  KEY `FKowocrgone149wy0l8lkxynj33` (`member_seq`),
  CONSTRAINT `FKowocrgone149wy0l8lkxynj33` FOREIGN KEY (`member_seq`) REFERENCES `tb_member` (`member_seq`)
) ENGINE=InnoDB AUTO_INCREMENT=947 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_community`
--

LOCK TABLES `tb_community` WRITE;
/*!40000 ALTER TABLE `tb_community` DISABLE KEYS */;
INSERT INTO `tb_community` VALUES
(684,'26828fc0-4a9c-46d8-bc77-95fb6ca00077','2022-03-24 17:10:05','5be1b6ef-f82f-4831-b264-ac0edf39dca6','2022-04-05 21:27:51','REPORT','<p>인천대공원에 자주 가는 편인데 요 며칠 내내 한 강아지가 돌아다니고 있더라구요.</p><p><br></p><p>밥도 제대로 먹지 못한 것 같았어요..</p><p><br></p><p>이런 경우에 어떻게 하는 게 좋을까요?</p>',0,'인천대공원에서 며칠 내내 보이는 강아지가 있어요 ㅠㅠ',21,3017),
(685,'0a229734-885d-4aca-a865-7c3d85b393f2','2022-03-24 17:52:53','c8c6d814-f42b-4976-ab97-e6829ae0aef2','2022-04-06 00:07:04','REVIEW','<p>얼마전에 강아지를입양했어요!!</p><p>아주 귀여운 녀석입니다!!! 하하하하하하하하하하하하</p><p>첫 반려 강아지라 떨리는데 잘 살아보겠습니다</p><p><br></p><p><img src=\"https://upload.wikimedia.org/wikipedia/commons/f/fa/Puppy.JPG\" width=\"365\" style=\"cursor: nesw-resize;\"></p>',0,'유기견을 입양했어요!!',15,3019),
(686,'a6458dba-e845-463b-8550-f2377c005e50','2022-03-26 19:49:45','469a2aa3-f10a-4bbc-8368-e3bbd80a7f4c','2022-04-05 19:50:03','GENERAL','<p>asfe</p>',1,'fe',1,554),
(687,'def4451c-d145-4a66-9323-bd1fe66d6dd5','2022-03-26 19:59:56','4f5a6408-0013-4fc1-961f-08448c8832ee','2022-04-07 07:43:22','REPORT','<p>강아지가 혼자 매일 돌아다니는데 ㅠㅠ </p><p>누가 잃어버린거 같지는 않구.. </p><p>신고 해야되나요?</p>',0,'집 근처에 강아지',28,677),
(688,'0de647a9-fcbe-44ce-83e0-802d89ea8d31','2022-03-27 20:01:56','d28c79fa-57bc-445e-aa7e-8503037f68ff','2022-04-06 20:07:57','GENERAL','<p>저희 집 집사 꼬물이에요~~ㅎㅎ</p><p><img src=\"http://j6a302.p.ssafy.io:8080/static/files/images/board/f1506881-2ad6-4aa1-8ca6-fe22143708c7.jpg\" width=\"297\" style=\"\"></p><p><br></p><p>https://www.youtube.com/watch?v=MwUtlcGeSpg</p><p>이 영상을 따라했었는데 꼬물이도 엄청 좋아하구 키우기도 되게 편하더라구요 </p><p>다들 참고하셔요 ㅎㅎ</p>',0,'고양이 키울때 꿀 팁',6,3027),
(689,'a14a34d5-18ba-4348-8ed0-c3f740eeaca8','2022-03-28 20:18:33','4ba8025c-da5c-4fb9-8d24-e23b8dce0400','2022-04-06 20:07:59','GENERAL','<h4>커피 한 잔 시켜놓구 멍멍이들이랑 놀아주는데 너무 좋네요 ㅎㅎ</h4><p><br></p><p><img src=\"http://j6a302.p.ssafy.io:8080/static/files/images/board/cb4bf1aa-26af-4894-a767-a726250bcb4d.jpg\" width=\"726\" style=\"\"></p><p><br></p><p>우리 다른 집사님들도 주변에 애견카페 자주 가시나요???</p>',0,'근처에 애견카페가 생겼어요!!',7,3028),
(690,'0b84ca49-267f-4228-94bc-4b2fb4cd2cd6','2022-03-28 20:18:59','de941a0f-3e0f-4cb7-b482-1514285239e8','2022-04-05 20:19:03','GENERAL','<p>sef</p>',1,'fea',1,554),
(691,'9d8bc692-cc53-4bc1-8845-3bae63deaab7','2022-03-28 20:21:38','17248997-8cbf-4b0d-afaf-0409df8857ea','2022-04-05 20:21:42','GENERAL','<p>fas</p>',1,'afsfesef',1,554),
(692,'7986a916-e0e2-4c80-a2bc-03cab56a1430','2022-03-29 20:24:04','dc6deb5d-6d4f-43b7-90a4-74a809132a2c','2022-04-07 16:22:05','REVIEW','<h4>어제 동물보호 센터 갔다가 고심 끝에 입양하기로 했습니다 ㅠㅡㅠ</h4><h4><br></h4><h4>입양가이드 꼼꼼히 읽어보구 내가 해당되는지 여러번 생각하구 입양했습니다 ㅎㅎ!!</h4><h4><br></h4><h4><img src=\"http://j6a302.p.ssafy.io:8080/static/files/images/board/da738ab6-a5ae-4090-a9a4-dfe3040269cf.jpg\" width=\"662\" style=\"cursor: nesw-resize;\"></h4><h4><br></h4><h4>우리 흑미 귀엽쬬??</h4><h4><br></h4><h4>보호소에서는 완전 상태가 별로였는데</h4><h4><br></h4><h4>입양하자마자 병원가서 예방접종하고 집에와서 씼겼더니 이렇게 이쁘네요 ㅎㅎ</h4><h4><br></h4><h4>열심히 키워보겠습니다.</h4>',0,'냥이 입양후기@@',32,3029),
(693,'aed19f60-fc0e-4bb2-9bad-ab8e7d5ff9d4','2022-03-31 20:24:58','49061cae-3131-4c65-9d27-27eea9c4c729','2022-04-06 05:02:09','REVIEW','<p>test</p>',1,'test123',55,554),
(694,'cf52ada3-1028-447b-85fc-2e8056e7691a','2022-04-01 20:30:59','3e9b32ca-8318-456c-bc53-ae1a5768aabc','2022-04-07 16:04:15','REVIEW','<p>봉사가 처음이라서 걱정했는데 안내를 잘 해주셔서 힘들었지만 재밌었어요 ㅎㅎ</p><p><img src=\"http://j6a302.p.ssafy.io:8080/static/files/images/board/8ba131fe-4fac-4895-98e0-064893213e1a.jpg\" width=\"630\" style=\"\"></p><p>초반에 사료나르기에서 체력을 다 썻지 뭐에요 ㅋㅋㅋㅋ</p><p><br></p><p>보호소 청소하고~~</p><p><br></p><p><img src=\"http://j6a302.p.ssafy.io:8080/static/files/images/board/bf754cc9-005f-4559-9cbb-6f58d317d1d3.jpg\" width=\"625\" style=\"\"></p><p>아이들 목욕하고 미용시켜주기~~</p><p><br></p><p>정말 좋은 시간이었습니다!! 다들 보호소 봉사 얼른 하세요~!</p>',0,'행복센터 봉사후기',57,3030),
(695,'c6c8f787-0d3d-4f38-b077-680f298da244','2022-04-01 20:40:08','a89d4813-5b5e-4924-ae69-6234a19df17f','2022-04-07 04:59:30','GENERAL','<h3>저만 좋나요?</h3><h3><br></h3><h3>이거 왜 자꾸 맡게 되죠?</h3><h3><br></h3><h3>아니 하루에 10번 이상은 맡게되는거 같아요</h3><h3><br></h3><h3>저만 그런가요?</h3><h3><br></h3><p><img src=\"http://j6a302.p.ssafy.io:8080/static/files/images/board/8fd3fe91-7c01-4c32-91ec-5c0759c3361e.jpg\" width=\"434\" style=\"cursor: nesw-resize;\"></p>',0,'발바닥 꼬순내',15,3031),
(696,'bf96f831-a5b5-4604-b0ef-806d2e3b1ee8','2022-04-01 20:46:29','48e96542-e8bd-47b4-ac74-cf3afb0f2004','2022-04-07 07:03:21','REPORT','<p>광주역 근처에서 어슬렁거리는 강아지 무리가 있던디요..</p><p>이넘들 유기된 애들이 모여서 다니는 것 같은데..</p><p>곧 날씨도 추워지는디 어디서 데려다 살 수는 없는교?</p>',0,'광주역 근처에서 어슬렁거리는 강아지 무리가 있던디',31,3033),
(697,'9cf5e8cb-1f2a-4f01-80ba-42bfbec26df1','2022-04-02 20:51:18','d4169930-dab3-4faf-9ea0-81958cb9ca34','2022-04-07 05:12:02','GENERAL','<p>우리 집 애가 원래 먹는 사료가 여러 종류인디.. 건강을 위해서 밥을 돌아가면서 주고 있는데 자꾸 편식하는데 이거 어떡하는교?</p>',0,'우리 집 애가 요즘 밥을 편식하는데 무슨 방법 없는교',14,3033),
(698,'5f685112-46f2-437c-a590-028760c239a4','2022-04-03 20:53:54','d12f41a4-f343-4ba1-b3cb-c7ae5989ef15','2022-04-07 07:16:01','GENERAL','<p>가이드가 있는데.. 입양이 되는지 궁금해요.</p>',0,'여기 사이트에서 입양도 가능한가요?',41,3034),
(699,'edcc8ddb-607c-43f9-a142-3160ce165450','2022-04-04 20:59:09','8dcf215d-e161-46f3-9117-ca042798306a','2022-04-07 07:06:14','NOTICE','<p>커뮤니티 글은 선택한 <strong>카테고리에 적합한</strong> 글이어야 합니다.</p><p>카테고리와 관련 있는 주제 하에 자유롭게 글 작성 하시면 됩니다.</p><p><br></p><p><strong>커뮤니티 게시 글 위반 사항</strong></p><p><strong>1.욕설 및 성희롱 </strong></p><p><strong>2.판매글</strong></p><p><strong>3.다른 사람의 글을 복사해서 올린 경우</strong></p><p><br></p><p><br></p><p><br></p><p><br></p><p><br></p><p>유기동물 입양, 봉사활동 커뮤니티 공간 도와주개냥.</p>',0,'도와주개냥 커뮤니티 글 작성 가이드 필독!!',46,2110),
(714,'2a2c6cea-77e6-4ae3-a159-34c95754f06c','2022-04-05 23:54:07','7c616ae2-4ce8-41a9-9a57-f58b1124b0d4','2022-04-07 16:03:59','NOTICE','<p>안녕하세요 도와주개냥 관리자입니다.</p><p><br></p><p><br></p><p>이번 달 봉사 참여왕인 싸피조아님</p><p><img src=\"http://j6a302.p.ssafy.io:8080/static/files/images/board/0baeed64-a4f5-4bff-869a-aac1eaf298c5.png\"></p><p>도와주개냥 글자와 캐릭터가 그려진 머그 컵 보내드리겠습니다.</p><p><br></p><p><br></p><p><br></p><p>이번 달 유기동물 입양 후기 아주 재밌게 올려주신 \"불멸의이순신\" 님께는</p><p><br></p><p><img src=\"http://j6a302.p.ssafy.io:8080/static/files/images/board/3b34ea4e-28e9-408d-b287-d97ae6352d5d.png\"><img src=\"http://j6a302.p.ssafy.io:8080/static/files/images/board/678e9543-227f-47b8-8717-ee16e6494999.png\"></p><p>도와주개냥 글자와 로고가 프린팅 된 맨투맨 보내드리겠습니다.</p><p><br></p><p><br></p><p>유기동물 입양, 봉사활동 커뮤니티 공간 도와주개냥.</p>',0,'도와주개냥 굿즈 이벤트!',68,2110),
(857,'0789dc9c-4b1a-4d64-b9df-77ed1ebf1ef0','2022-04-06 20:08:55','d38a2698-d0f0-401f-ad23-8f954af5f220','2022-04-06 22:11:55','REVIEW','<h4><br></h4><h4>안녕하세요~~</h4><h4><br></h4><h4>고양이 카페에서 힐링하고 싶은데 어디에 있는지 아시는 분~~~!!</h4><h4><br></h4><h4>여기 처음 이사와서 잘 모르겠습니다.</h4><h4><br></h4><h4>노원구쪽에 살아요</h4><h4><br></h4><h4>많은 댓글 부탁드려요!!!!!</h4><h2><br></h2><h2><br></h2><h2><br></h2>',1,'근처에 고양이 카페도 있나요?',18,111),
(872,'bf5157c7-c127-4227-bc66-0da745e14ecc','2022-04-06 21:38:16','cd815eaa-bf15-4527-bdc6-237c8c8662e2','2022-04-07 07:56:16','GENERAL','<p><img src=\"http://j6a302.p.ssafy.io:8080/static/files/images/board/b8d582ab-664c-4104-82c1-640e8f33116e.png\"></p>',1,'안녕하세요',11,111),
(887,'4d5c0263-59a5-41ad-8db5-c3be0be41989','2022-04-06 22:24:01','c0974030-b3af-4c96-8f01-feb60f698984','2022-04-07 07:46:56','GENERAL','<h4>안녕하세요</h4><h4><br></h4><h4>노원구에 새로 이사 온 사람입니다.</h4><h4><br></h4><h4>고양이를 좋아해서 고양이 카페를 가고 싶은데 아시는 분 있나요?</h4><h4><br></h4><h4>아시는 분 있다면 댓글이나 오픈 카톡으로 알려 주실 수 있나요?</h4><h4><br></h4><h4>그리고 같이 보러 가실 분도 환영입니다.</h4><h4><br></h4><h4>^^</h4>',0,'고양이 카페 추천해 주실 분 계신가요?',18,554),
(944,'1819b750-f64e-410e-ae7d-4885c8d45b93','2022-04-07 07:42:39','6b90e5af-6f60-49e4-b4c1-9501cf06f92f','2022-04-07 16:26:26','GENERAL','<h3><strong>강아지 발톱 자르는 법!!!</strong></h3><p><br></p><p><img src=\"http://j6a302.p.ssafy.io:8080/static/files/images/board/1b9a3aea-983e-4e41-a40f-2b406dc27107.png\" width=\"448\" style=\"\"></p><p><br></p><p><span style=\"color: rgb(44, 47, 52);\">강아지 발톱은 사람 발톱과 다르다. 발톱의 꽤 끝부분까지 혈관이 연장되어 있다. 따라서, 사람 발톱을 자르듯이 살에 가깝게 발톱을 자르면 안 된다.</span></p><p><br></p><p><br></p><p><span style=\"color: rgb(44, 47, 52);\">위 그림처럼 혈관이 닿지 않는 발톱 끝부분을 45도 각도로 잘라주자.</span></p><p><br></p><p><br></p><p><br></p><p><span style=\"color: rgb(44, 47, 52);\">출처: https://mypetlife.co.kr/65381/</span></p><p><br></p>',0,'강아지 발톱 자를 때!!',6,677),
(945,'89c36921-55f4-49ed-9361-85d8e3a67748','2022-04-07 07:43:40','81b99b5a-22e1-4c9a-ba1a-05555d0baa4a','2022-04-07 08:04:59','GENERAL','<p>ㅎㅎㅎㅎㅎ</p>',1,'금방 지울게요',22,1476),
(946,'399d1c0b-ab7d-4eea-b81b-3c86a38c9bb2','2022-04-07 07:46:03','69fdff2a-eb24-40ac-bdce-d6a2f31fe72d','2022-04-07 08:29:39','GENERAL','<p>제가 강아지 치석때문에 진짜 고생많이 했는데 </p><p>이치약 진짜 도움 됐습니다!!</p><p><br></p><p><br></p><p><img src=\"http://j6a302.p.ssafy.io:8080/static/files/images/board/f0975386-37c6-4529-bae0-cd16d23dfa78.png\" width=\"400\" style=\"\"></p><p><br></p><p>맛도 좋은지 우리강아지는 가만히 잘있더라고요~!!</p><p>치약 강추입니다.</p>',0,'강아지 치석 제거 집에서 하는 법',3,111);
/*!40000 ALTER TABLE `tb_community` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_community_comment`
--

DROP TABLE IF EXISTS `tb_community_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_community_comment` (
  `comment_seq` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `last_modified_by` varchar(255) NOT NULL,
  `last_modified_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `content` text NOT NULL,
  `is_deleted` tinyint(4) NOT NULL,
  `community_seq` bigint(20) unsigned NOT NULL,
  `member_seq` bigint(20) unsigned NOT NULL,
  `parent_seq` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`comment_seq`),
  KEY `FKpfubkjjl7npt5u312qmyy6733` (`community_seq`),
  KEY `FK6nvhytqivl4xnydlmmlcvftm0` (`member_seq`),
  KEY `FKafn8jety6nwh65o71uix4wr7r` (`parent_seq`),
  CONSTRAINT `FK6nvhytqivl4xnydlmmlcvftm0` FOREIGN KEY (`member_seq`) REFERENCES `tb_member` (`member_seq`),
  CONSTRAINT `FKafn8jety6nwh65o71uix4wr7r` FOREIGN KEY (`parent_seq`) REFERENCES `tb_community_comment` (`comment_seq`),
  CONSTRAINT `FKpfubkjjl7npt5u312qmyy6733` FOREIGN KEY (`community_seq`) REFERENCES `tb_community` (`community_seq`)
) ENGINE=InnoDB AUTO_INCREMENT=578 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_community_comment`
--

LOCK TABLES `tb_community_comment` WRITE;
/*!40000 ALTER TABLE `tb_community_comment` DISABLE KEYS */;
INSERT INTO `tb_community_comment` VALUES
(450,'251cd24a-51bb-447f-a1f1-60496b05788b','2022-03-28 17:20:02','251cd24a-51bb-447f-a1f1-60496b05788b','2022-04-05 17:20:02','그럴 경우에는 근처 동물 보호센터에 연락해보면 어떨까요?',0,684,3018,NULL),
(451,'e713708e-4df2-42d2-bdaf-28374ce0ab11','2022-03-31 19:32:59','e713708e-4df2-42d2-bdaf-28374ce0ab11','2022-04-05 19:32:59','꺄아ㅏㅏㅏㅏ 너무 귀여워요 ㅠㅠㅎㅎ\n',0,685,1252,NULL),
(452,'220d342e-ca8b-48cb-a25d-7d3bf27b46c0','2022-04-01 19:50:50','220d342e-ca8b-48cb-a25d-7d3bf27b46c0','2022-04-05 19:50:50','저도 유기동물인거 같으면 바로 근처 센터에 연락했어요\n',0,684,3026,NULL),
(453,'b40e89cf-2f60-439b-9176-39c518dcd361','2022-04-01 20:18:55','b40e89cf-2f60-439b-9176-39c518dcd361','2022-04-05 20:18:55','ㅎㅎ 고양이 귀엽네요\n',0,688,3028,NULL),
(454,'8a7c3cb6-9be1-455b-bc17-2128ddca61ec','2022-04-01 20:32:01','8a7c3cb6-9be1-455b-bc17-2128ddca61ec','2022-04-05 20:32:01','오구오구 흑미야 잘커랏~~\n',0,692,3030,NULL),
(455,'31e661b4-9900-464d-82f6-5dd564415ea8','2022-04-03 20:32:47','31e661b4-9900-464d-82f6-5dd564415ea8','2022-04-05 20:32:47','저도 2주에 한 번 꼴로 가는 것 같아요 ㅎㅎ 저희 카페는 아이들 관리가 잘 되어있어서 좋더라구요',0,689,3030,NULL),
(456,'7a91ee73-8c8e-421d-98dc-44c4dbaf2f1a','2022-04-04 21:24:02','7a91ee73-8c8e-421d-98dc-44c4dbaf2f1a','2022-04-05 21:24:02','흑미야 이순신님 밑에서 잘 자라야 한다~!~!~!',0,692,3017,NULL),
(457,'efdd0241-f9e7-470f-b975-5f35f70c96f7','2022-04-05 21:27:09','efdd0241-f9e7-470f-b975-5f35f70c96f7','2022-04-05 21:27:09','이제 곧 날씨 따뜻해지지 않나요?\n',0,696,1196,NULL),
(458,'c5ca12e2-f2b5-479c-9b3d-9c2fb0e73d2e','2022-04-05 21:27:18','c5ca12e2-f2b5-479c-9b3d-9c2fb0e73d2e','2022-04-05 21:27:18','아.. 인정.. ㅎㅎ\n',0,695,1196,NULL),
(477,'2dbdc487-7658-421a-b291-6a83eb0e5f05','2022-04-06 07:14:11','73496c0f-5aa1-4328-a729-c651ce1a7809','2022-04-06 07:14:20','수고하셨어요 ㅎㅎ\n',1,694,3026,NULL),
(478,'af30f0a5-706a-46f7-9f9a-a9961961909a','2022-04-06 07:14:26','32ffe66f-c267-4bc8-bda0-138a0d173e33','2022-04-06 07:14:28','수고하셨어요 ㅎㅎ\n',1,694,3026,NULL),
(479,'49f7c08f-232e-4319-87f0-3425951e6dff','2022-04-06 07:14:45','49f7c08f-232e-4319-87f0-3425951e6dff','2022-04-06 07:14:45','수고하셨어요 ㅎㅎ\n',0,694,3026,NULL),
(535,'da0a71c1-deb1-4e4a-810a-7b811e9a50a0','2022-04-06 20:10:43','da0a71c1-deb1-4e4a-810a-7b811e9a50a0','2022-04-06 20:10:43','저 알고 있어요 건대 쪽에 큰 고양이 카페가 있고 거기 고양이가 종류별로 많아서 힐링되요~~  카톡으로 위치 알려드리겠습니다.',0,857,554,NULL),
(542,'b11c0283-1da7-4e18-97aa-09c88aae7aee','2022-04-06 21:45:52','b11c0283-1da7-4e18-97aa-09c88aae7aee','2022-04-06 21:45:52','감사합니다~~~',0,857,111,NULL),
(543,'b167c9bd-cba1-4e42-af0f-26dea37539b2','2022-04-06 21:54:49','b167c9bd-cba1-4e42-af0f-26dea37539b2','2022-04-06 21:54:49','섞어서 한 번 줘보세요',0,697,3032,NULL),
(562,'550920ca-c91c-4eb9-8040-117cc97c9f08','2022-04-07 02:47:42','550920ca-c91c-4eb9-8040-117cc97c9f08','2022-04-07 02:47:42','헉 저 노원구 근처살아요!!!\n',0,887,1196,NULL),
(563,'ad39f420-7c79-4807-bf8f-83555e0f24fb','2022-04-07 05:10:22','ad39f420-7c79-4807-bf8f-83555e0f24fb','2022-04-07 05:10:22','보호소에 직접 연락해서 문의하셔야 합니다~',0,698,1476,NULL),
(576,'a1618b59-53bf-4f38-a40a-af2b68dc975e','2022-04-07 07:07:46','1b29ac4d-0518-4afb-8a11-aec1bc7fb3f8','2022-04-07 07:07:48','댓글이욤\n',1,698,1476,NULL),
(577,'386f7c40-1992-4369-a6cb-a34d6244d2e5','2022-04-07 16:22:01','386f7c40-1992-4369-a6cb-a34d6244d2e5','2022-04-07 16:22:01','우왕 진짜 기여워요ㅠㅠ ',0,692,554,NULL);
/*!40000 ALTER TABLE `tb_community_comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_community_like`
--

DROP TABLE IF EXISTS `tb_community_like`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_community_like` (
  `community_seq` bigint(20) unsigned NOT NULL,
  `member_seq` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`community_seq`,`member_seq`),
  KEY `FK2lx8dmb32kiq9536p2nm13oes` (`member_seq`),
  CONSTRAINT `FK2lx8dmb32kiq9536p2nm13oes` FOREIGN KEY (`member_seq`) REFERENCES `tb_member` (`member_seq`),
  CONSTRAINT `FKk6aw67rh0h348mu3q36xie1na` FOREIGN KEY (`community_seq`) REFERENCES `tb_community` (`community_seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_community_like`
--

LOCK TABLES `tb_community_like` WRITE;
/*!40000 ALTER TABLE `tb_community_like` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_community_like` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_email_auth`
--

DROP TABLE IF EXISTS `tb_email_auth`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_email_auth` (
  `auth_key` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `created_by` varchar(255) NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`auth_key`,`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_email_auth`
--

LOCK TABLES `tb_email_auth` WRITE;
/*!40000 ALTER TABLE `tb_email_auth` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_email_auth` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_member`
--

DROP TABLE IF EXISTS `tb_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_member` (
  `member_seq` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `last_modified_by` varchar(255) NOT NULL,
  `last_modified_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `email` varchar(255) NOT NULL,
  `is_deleted` tinyint(4) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(255) NOT NULL,
  PRIMARY KEY (`member_seq`),
  UNIQUE KEY `UK2corc7ojj2bob4ix9ny5gr826` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=4001 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_member`
--

LOCK TABLES `tb_member` WRITE;
/*!40000 ALTER TABLE `tb_member` DISABLE KEYS */;
INSERT INTO `tb_member` VALUES
(1,'549390d7-7cec-4257-9e7c-f658cd6542c7','2022-03-22 17:50:27','bd9ceaf3-00e2-44a6-9765-ff99bb768109','2022-04-02 08:22:10','test1@test.com',0,'$2a$10$BXN0c9zhqr.iA8bgH8Hy9u8NIBIxVnbY.vtXXr1knT35dOfw6GpkG','MEMBER'),
(110,'714adb7f-933d-499e-a34b-d34154e41d09','2022-03-23 17:08:03','714adb7f-933d-499e-a34b-d34154e41d09','2022-03-23 17:08:03','test@test.com',0,'$2a$10$CVtC/XcnWMdCZiVOIQ6AX.MvvAsSggdV6ay9tKt1hUM6XKFwKBHPW','MEMBER'),
(111,'2266b2df-b132-4a27-b012-7ca7c24329e0','2022-03-23 17:12:54','b0b72485-2032-4d3a-8d9e-929d58361847','2022-04-07 07:43:20','name1@ssafy.com',0,'$2a$10$mkapYt0PWatWQ2LhQEZBTeXk0zbvrtC3/XfFi.KgnQqjPcBbB0t8C','MEMBER'),
(179,'c7bcadb7-bfc2-4825-8993-7ff8e8dd3b18','2022-03-24 23:13:30','c7bcadb7-bfc2-4825-8993-7ff8e8dd3b18','2022-03-24 23:13:30','admin1@test.com',0,'$2a$10$F8R6p7.9p5J1h87WKdClD.p24vRRdBpnKE2gY6wfW5XM.a3ZH6Eh.','ADMIN'),
(554,'be73d8cd-4897-40cc-bb2a-7db2efbcdaf3','2022-03-26 05:29:51','a2cd2efc-8bd8-48b1-a0e2-e3afb41128ee','2022-04-07 06:05:23','name2@ssafy.com',0,'$2a$10$iW5Qacr6JlHhrjoasjfkie7mDKILSSaUiuANKE4K7o7lzd/aNe3Cu','MEMBER'),
(635,'fcb45931-7289-4b81-837f-881fdb622d72','2022-03-27 15:41:41','fcb45931-7289-4b81-837f-881fdb622d72','2022-03-27 15:41:41','name4@test.com',0,'$2a$10$gtt7AtmRotCijkSxjMC8Yu6mlfPil9y9TjMLd80tMcR38ZF91VBY6','MEMBER'),
(636,'e85e0ec6-9309-408f-9a8c-ce6a358b271e','2022-03-27 15:43:04','e85e0ec6-9309-408f-9a8c-ce6a358b271e','2022-03-27 15:43:04','name3@good.com',0,'$2a$10$NY3Q6CJLNwgWZFlU2kztMO6WHPkAcb5sOE1sfeV6/SWHPGaqwkBaW','MEMBER'),
(637,'cc38f3ea-8ad6-43b2-93e6-db9011c3f6d9','2022-03-27 16:01:00','ec5d8331-83a1-4558-813d-8bc79e2ef1b4','2022-03-27 16:08:29','test4@ssafy.com',0,'$2a$10$f6vRD8Kk52Bh12cNTgZRgu2dlK8pwZyYkTaV9Wh8jBGoQC6JQy0L6','MEMBER'),
(677,'cecbc1bc-1637-4aa8-ac96-4e8fa641e1a3','2022-03-27 19:42:03','193bf3f5-059d-4ed5-a391-34ab0bb68e57','2022-04-05 20:14:26','ase0574@naver.com',0,'$2a$10$STu4qqt1jOzaasgJObR/GuvJdMcyEbBpYWBpv7YdEw8/soVOHnA.q','MEMBER'),
(936,'c6fe6631-d4b3-4882-8c97-a4ec887f3de0','2022-03-28 07:05:13','621faa65-39e1-428f-9322-fa020e079133','2022-04-07 06:08:14','name3@ssafy.com',0,'$2a$10$4xcRzmzSNcOzCKUPLyogsu39nhW/gLH/PY0CkxvIGgkNb.FvpJzru','MEMBER'),
(1195,'8e3fe00f-cdec-4479-9b0c-c1d636ef6905','2022-03-28 22:26:08','8e3fe00f-cdec-4479-9b0c-c1d636ef6905','2022-03-28 22:26:08','kim1234@test.com',0,'$2a$10$jYFj..z4qwbkDDhmZlP36ehgRGdOmNO.la2hiX3uFfM6nCCi/VJa6','MEMBER'),
(1196,'3a31ad9d-77d6-45f6-8a52-692b214f7052','2022-03-29 16:30:06','82ee6b99-2523-4dbd-a702-8aea24ef6428','2022-04-05 19:29:53','fufru@naver.com',0,'$2a$10$Dav/oqz7ralToVJGcCMXue6c1CgWFY.PZPbQX0ELqecVStd9lNnmK','MEMBER'),
(1252,'0d6105b3-22a3-4eb4-9d79-e8ee662a0753','2022-03-29 19:51:08','63559154-1a25-4768-b7ae-a5d70a815c9a','2022-04-05 17:24:14','hello@naver.com',0,'$2a$10$UgodL7qKdk73JoRallyTVeW9aURrIEhtsKLdZHZe2SBpWzjR9oPQe','MEMBER'),
(1253,'5fb48fb7-7016-44b6-a928-14099ff3a6f6','2022-03-29 21:39:23','5fb48fb7-7016-44b6-a928-14099ff3a6f6','2022-03-29 21:39:23','admin2@test.com',0,'$2a$10$GgQakgpLrKj83JFcO3EoWe6ic6rwxez1fB03MpaxWD/x3SYNBuFka','ADMIN'),
(1364,'4c7e0990-5ef5-4d62-907c-9a9fb14d3389','2022-03-30 00:32:57','4c7e0990-5ef5-4d62-907c-9a9fb14d3389','2022-03-30 00:32:57','ghlim909@naver.com',0,'$2a$10$9Obr5Wm.svrO4qih8B08q.sB765wH0KAjOehU0h3TXJ/ZdPKBY0TG','MEMBER'),
(1476,'e9e44de9-001c-4524-9d6c-78046f195290','2022-03-30 08:07:50','bc99d278-03d3-4bb6-b50d-239373933830','2022-04-04 23:24:02','jiho@test.com',0,'$2a$10$PfJRQBKb6rqPyAkbhS1sjOQgwfextKf8iklNpxc/Y5v62iblXfibC','MEMBER'),
(1477,'9289c722-6a9b-4449-916f-a8c9e0642e0c','2022-03-30 08:49:42','9289c722-6a9b-4449-916f-a8c9e0642e0c','2022-03-30 08:49:42','seok@test.com',0,'$2a$10$0kICihTfd.NGsUOAXiWSQuepahITFlWKfxsiZt06sS4xR0ZpwB9Ru','MEMBER'),
(2110,'016d6297-ee1b-47c5-bf24-5daab373732a','2022-04-03 05:03:35','016d6297-ee1b-47c5-bf24-5daab373732a','2022-04-03 05:03:35','a302_admin@ssafy.com',0,'$2a$10$2psn2fSDfsphE28fMS5DpuY/UX12GbTyMG.0ThJpzjHbm4f.uPV5G','ADMIN'),
(2111,'6861ce6b-1fcf-4480-b633-f6bc9c7088f6','2022-04-03 05:11:31','6861ce6b-1fcf-4480-b633-f6bc9c7088f6','2022-04-03 05:11:31','a302_member@ssafy.com',0,'$2a$10$YFb0qEdPlYkTbJBJTzzCUegjwaW6aGfq8qe67LHogPQAZKBO0YSwC','MEMBER'),
(2112,'d9990740-6cb6-4b04-8c82-d1070dcc16ed','2022-04-03 15:10:52','d9990740-6cb6-4b04-8c82-d1070dcc16ed','2022-04-03 15:10:52','0xe82de@ssafy.com',0,'$2a$10$zZBnOsJFKgKTchwkado53utDKG5BgrfGbe8HyyyGf6XLpYTCIpSKW','MEMBER'),
(2778,'79403b60-d2e7-4fbb-8745-f43e4f960515','2022-04-04 20:40:07','b0899d40-6676-4476-a61b-155dbd496f8b','2022-04-04 20:59:21','test2@test.com',1,'$2a$10$5NkUodwcRXq7w0TbtTH6yeqW10X3MD4lFWnl7hBmczH0pnMgaoU/O','MEMBER'),
(2954,'896537e9-3742-4b70-9739-16e38e6b2726','2022-04-05 04:34:58','896537e9-3742-4b70-9739-16e38e6b2726','2022-04-05 04:34:58','test99@test.com',0,'$2a$10$dVhQhaXuRSVj7ZCKlkE2t.4rjETqTo3d3z0rxkmTtq00Y2.NEIJn.','MEMBER'),
(2955,'37d38173-fa0c-4ab7-91ea-08951d5a4ab1','2022-04-05 06:52:19','37d38173-fa0c-4ab7-91ea-08951d5a4ab1','2022-04-05 06:52:19','pinako@naver.com',0,'$2a$10$h7DBols2kiwIsLf1F2QujOo8a2yY1LOLYuP1I0iWmJiFSk9LeJVHO','MEMBER'),
(2956,'7162bb7f-4036-4559-8a80-e5da5238b3db','2022-04-05 16:37:00','7162bb7f-4036-4559-8a80-e5da5238b3db','2022-04-05 16:37:00','mung@ssafy.com',0,'$2a$10$UfuY63pF36NJbt6Yl9.E/uXJsmR3IiHym8uUaZ6LEmJo86Sv7lLQG','MEMBER'),
(2957,'ee530fa3-7bf9-402e-af1a-44a22ad27d8c','2022-04-05 16:37:19','ee530fa3-7bf9-402e-af1a-44a22ad27d8c','2022-04-05 16:37:19','kjw@naver.com',0,'$2a$10$VIh7eOgDep3wI2w4mk7wQeKLbJYYYBQq28HiEKwwp9g0USKsjOWvW','MEMBER'),
(3017,'5aa737fe-ed1f-4a45-ad34-f4ca25563525','2022-03-01 17:06:11','5aa737fe-ed1f-4a45-ad34-f4ca25563525','2022-04-05 17:06:11','seok@ssafy.com',0,'$2a$10$vFNzb/fiQPcNZA3iDdvdr.7NsjMOoqoDZnB4.7VFaEH28wT1DF6Ge','MEMBER'),
(3018,'8645d6d3-e623-4727-ae87-d35903e7f4c1','2022-03-02 17:19:34','8645d6d3-e623-4727-ae87-d35903e7f4c1','2022-04-05 17:19:34','dog1004@ssafy.com',0,'$2a$10$3sq0XKDWzyeSHxlDYg/TNea8xbSmY5F7EGrfAF7.8sHXQf/9SW8UO','MEMBER'),
(3019,'78318c64-516b-477c-a9bc-361df3227cf2','2022-03-03 17:33:23','7bb4eac3-ccce-4b31-af26-b92274ff8cad','2022-04-05 17:38:14','jesus@ssafy.com',0,'$2a$10$wzeKx6SxnaFLjKzMrp6Of.wW66uW8uon1j6XQJr9Ya8zxp1JJ70TS','MEMBER'),
(3020,'df4a9a99-1d53-4cb7-af7c-f401c752e3a0','2022-03-04 17:38:28','df4a9a99-1d53-4cb7-af7c-f401c752e3a0','2022-04-05 17:38:28','doglove@gmail.com',0,'$2a$10$1hU7jqFTSu1Gt9ZAvV..EOhfpgSxGMVnyY8wDsKoX6M4ON5HviEye','MEMBER'),
(3021,'3b5c322d-42da-4b67-aa41-e03383296f0c','2022-03-05 17:59:17','3b5c322d-42da-4b67-aa41-e03383296f0c','2022-04-05 17:59:17','catmom@naver.com',0,'$2a$10$a2xlUPFwL0IO5mNuIigh8uyZiuT2ss9KKR78AA5uVoVAYqilD7n6u','MEMBER'),
(3022,'65d10d59-1693-4de4-a09d-a42905fcb2a1','2022-03-05 19:11:48','65d10d59-1693-4de4-a09d-a42905fcb2a1','2022-04-05 19:11:48','allall@naver.com',0,'$2a$10$mChNBSnkGiQka0trS4ZNcO4P8jc6lCFyIID1T1BRVmEKfQeVdjjVu','MEMBER'),
(3023,'8a377ec4-474f-473c-8976-2de9bb14a7c7','2022-03-05 19:18:25','0f57dd98-d714-437e-931a-d8728d654f24','2022-04-05 19:27:29','corgi@naver.com',0,'$2a$10$4ogcFHVjTAanAbmUSnF50.kFFkevYANTfWwfa3Zw8dpPGd/Ij.Nre','MEMBER'),
(3024,'cad6e303-bf49-467a-90de-316a32b3a704','2022-03-05 19:23:50','cad6e303-bf49-467a-90de-316a32b3a704','2022-04-05 19:23:50','ase0574@ssafy.com',0,'$2a$10$pGLf6N0kcRu8iCqux7lu6OSWo3guqKhBScZBKVGkBmUp8F6HL2dN2','MEMBER'),
(3025,'67e6e6c0-3632-4122-b405-183d9005b597','2022-03-06 19:27:34','67e6e6c0-3632-4122-b405-183d9005b597','2022-04-05 19:27:34','ase0574@test.com',0,'$2a$10$Y12oJkDtsW./5a0rvYs8qOFqwZlFpxNqMti8F3Emeakx8TG2waQ3S','MEMBER'),
(3026,'919308e7-ba3f-452a-a05d-45f40797d3a2','2022-03-07 19:35:56','623f02f8-2018-43d4-9e3f-167b06c2ff14','2022-04-06 05:58:34','ssaffa@naver.com',0,'$2a$10$mgC.IXomgXOALX2eUCBw2eRVgy33XV/rYSVASo4PG8W6MK.jV5WLC','MEMBER'),
(3027,'243e6df4-2dde-465f-9621-87038b13efb8','2022-03-08 19:53:25','243e6df4-2dde-465f-9621-87038b13efb8','2022-04-05 19:53:25','timegame@naver.com',0,'$2a$10$aISNpVVsb564UVDQqdYlAu3sWgz7b3LPdhdDJLTM4skd983idyB82','MEMBER'),
(3028,'ce666d27-7e93-480e-96c0-c33b27a5e8fa','2022-03-09 20:11:52','fe4c5fa6-bd75-4e0d-b8ff-448c26e9fbbf','2022-04-05 20:19:39','kokyu@naver.com',0,'$2a$10$3iS0ysXjRkDp0OAc.dq3dO3uNcm52N.xjZU.VFm/0QihefZJDsFp.','MEMBER'),
(3029,'2852f73b-4a02-4065-a384-b2e55608a845','2022-03-09 20:12:37','f86bb75c-8f39-43ac-ad26-8802ba5f47ab','2022-04-07 05:32:19','game@naver.com',0,'$2a$10$xzTsutyMs4CrDxHZi/k4muFYIFvxn6/lMbPKn4MP8JYgu4hKDiaTu','MEMBER'),
(3030,'1e95800c-cec1-46de-b3bc-360893dd5208','2022-03-09 20:13:07','0191c3ba-6129-4d20-b4c6-4e8d4dbf2c8f','2022-04-05 20:33:14','flower@naver.com',0,'$2a$10$BFVDGhmnJqnmvhKkVgnLzeHq.p2ajY3xnUb4ZN.TiFtauwDPfuH9W','MEMBER'),
(3031,'29df9064-40c6-47e4-8be0-e5146acd64c6','2022-03-10 20:16:05','4f002f83-d476-4673-9822-3f95c4099939','2022-04-05 20:40:40','babo@gmail.com',0,'$2a$10$efG9BAlz85NMqkgwH335l.thvFeR5mCBD0tDHni59YNbuNyFPiNQq','MEMBER'),
(3032,'8c8001eb-6949-42a3-a29e-f9e92d72bf5c','2022-03-10 20:39:33','b31a6338-cde2-40ce-aba0-9c7bcc56f252','2022-04-06 21:55:42','superman@ssafy.com',0,'$2a$10$9Drj6df7N1p67i.FBzpxc.zQk/cxNWuFds8IYm2eyrrNZazUVoXI.','MEMBER'),
(3033,'d591717d-60d8-412a-9a63-88e5e947c67a','2022-03-10 20:44:54','d591717d-60d8-412a-9a63-88e5e947c67a','2022-04-05 20:44:54','dogcat@ssafy.com',0,'$2a$10$zUFy0xuu4qeJz5LaeDboy.xNIqlQBaDqNWmTKjwtbisUv0FEkjzva','MEMBER'),
(3034,'4a75836e-497c-4e55-9c4f-2d3af3ef2d20','2022-03-11 20:53:02','4a75836e-497c-4e55-9c4f-2d3af3ef2d20','2022-04-05 20:53:02','hahaha@ssafy.com',0,'$2a$10$iER/sJQPANglq5uPOGYK8e0fFnl3kogBqF530TKQ6jV43vJyEtOpy','MEMBER'),
(3822,'2e918456-098b-4e00-8776-25010cfaeefa','2022-04-07 00:11:36','2e918456-098b-4e00-8776-25010cfaeefa','2022-04-07 00:11:36','ss@ss.sss',0,'$2a$10$pN42v6knWy2AgQT.KDBY9OIkxvOk2VDgFiQCWvAeqTL00oAkyVGvW','MEMBER'),
(4000,'b68854d9-afa2-4845-891e-6ebaf4810e44','2022-04-07 16:25:06','b68854d9-afa2-4845-891e-6ebaf4810e44','2022-04-07 16:25:06','ghlim2103@naver.com',0,'$2a$10$9DWloRwjC0AyoXJwV9dOQOSYwQHzjcHFYvCEHHsx7qul56O5Z72q.','MEMBER');
/*!40000 ALTER TABLE `tb_member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_member_badge`
--

DROP TABLE IF EXISTS `tb_member_badge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_member_badge` (
  `badge_seq` bigint(20) unsigned NOT NULL,
  `member_seq` bigint(20) unsigned NOT NULL,
  `created_by` varchar(255) NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`badge_seq`,`member_seq`),
  KEY `FKfirbh10w2vg7jvfxbqph5fang` (`member_seq`),
  CONSTRAINT `FKfirbh10w2vg7jvfxbqph5fang` FOREIGN KEY (`member_seq`) REFERENCES `tb_member` (`member_seq`),
  CONSTRAINT `FKthbwukrmdf65u94bk1pc7tduj` FOREIGN KEY (`badge_seq`) REFERENCES `tb_badge` (`badge_seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_member_badge`
--

LOCK TABLES `tb_member_badge` WRITE;
/*!40000 ALTER TABLE `tb_member_badge` DISABLE KEYS */;
INSERT INTO `tb_member_badge` VALUES
(15,3017,'a2b586a1-7f24-49b6-8fb8-6eadec5272c4','2022-04-05 17:06:12'),
(15,3018,'640e39f9-a30a-470b-9bed-5d9a2da841e7','2022-04-05 17:19:34'),
(15,3019,'00731af8-34b6-4924-851d-79d0fbc64823','2022-04-05 17:33:23'),
(15,3020,'30886644-3d53-4f53-97d0-8ffa81b67e94','2022-04-05 17:38:28'),
(15,3021,'051b8948-dd7c-45e4-94de-7011ec46f267','2022-04-05 17:59:17'),
(15,3022,'024f472e-fb36-41a1-8e40-4e86be54e8f0','2022-04-05 19:11:48'),
(15,3023,'d5ac3816-f5d7-4a8c-939e-6bda63e5724c','2022-04-05 19:18:25'),
(15,3024,'622e8223-d0f6-4d9f-8a70-42f6088894e0','2022-04-05 19:23:50'),
(15,3025,'bb98ac98-de18-4c6f-81c1-09aa4761505b','2022-04-05 19:27:34'),
(15,3026,'49a49f9e-155b-45a0-9803-63819dcf31f5','2022-04-05 19:35:56'),
(15,3027,'bab51e23-cf4e-45fe-b5e6-c09baf40aaea','2022-04-05 19:53:25'),
(15,3028,'b2b1798c-a839-4981-b97e-4cb2899fc868','2022-04-05 20:11:52'),
(15,3029,'87f1eca9-d987-4c4c-88d9-fd454ef27509','2022-04-05 20:12:37'),
(15,3030,'ef3e8219-6cb2-4626-80d2-405dfce50dec','2022-04-05 20:13:07'),
(15,3031,'dd1ca680-6e35-4109-86c6-efb33156b86f','2022-04-05 20:16:05'),
(15,3032,'07943745-baa7-4bed-aaf7-dc914fde9b93','2022-04-05 20:39:33'),
(15,3033,'089bceea-0b63-4cc3-b552-9e922dfd77f0','2022-04-05 20:44:54'),
(15,3034,'fa814aa8-b259-489d-9d6a-0fe390f93c2b','2022-04-05 20:53:02'),
(15,3822,'b177acbd-d85f-4a61-9e3e-4e5ec19b38da','2022-04-07 00:11:36'),
(15,4000,'123d6dbf-d211-4bfc-aae8-fc16c14ba093','2022-04-07 16:25:06'),
(17,554,'599bd128-3bf7-4cbe-be2a-ab707d4f43ec','2022-04-05 23:57:46'),
(17,3019,'8ae1eaa0-fabd-44c6-9f01-d80836175396','2022-04-05 17:46:58'),
(17,3026,'12a08beb-05f7-4611-9dbd-f0d20895855d','2022-04-06 06:40:58'),
(17,3028,'9d7710c0-6923-4916-afba-e8e7397ea9e1','2022-04-07 16:16:13'),
(17,3029,'4a77c3f5-3fe7-4f35-98db-d47389034a50','2022-04-07 05:26:40'),
(18,554,'3ff180f1-d6fd-4a1a-b524-3b30431e73bc','2022-04-06 00:05:18'),
(20,1196,'a0eb0abb-5421-4bda-91f2-fc567ce8fdbb','2022-04-06 06:45:31'),
(20,3017,'18bbd28e-f05e-4dd4-b156-ac6e403c5c61','2022-04-06 06:45:31'),
(20,3022,'0140994b-eb93-4b21-8e50-23cd616453f1','2022-04-06 06:45:31'),
(20,3026,'432fdeed-76fd-481b-a49c-c6567a59e798','2022-04-06 06:49:46');
/*!40000 ALTER TABLE `tb_member_badge` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_member_detail`
--

DROP TABLE IF EXISTS `tb_member_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_member_detail` (
  `member_seq` bigint(20) unsigned NOT NULL,
  `activity_area` varchar(255) NOT NULL,
  `exp` int(10) unsigned NOT NULL,
  `nickname` varchar(255) NOT NULL,
  `tel` varchar(13) NOT NULL,
  `profile_image_filename` text DEFAULT NULL,
  PRIMARY KEY (`member_seq`),
  UNIQUE KEY `UKfdv4op5ahtrncsmvba4pic74t` (`nickname`),
  UNIQUE KEY `UKfcl0efndyy3urmogfr709gknq` (`tel`),
  CONSTRAINT `FKj1nohw6a7qkxkek8hrqcmqifk` FOREIGN KEY (`member_seq`) REFERENCES `tb_member` (`member_seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_member_detail`
--

LOCK TABLES `tb_member_detail` WRITE;
/*!40000 ALTER TABLE `tb_member_detail` DISABLE KEYS */;
INSERT INTO `tb_member_detail` VALUES
(1,'서울시 강남구',5,'good1','010-1234-5678','309b4c82-9048-4981-91b7-f76f535bd14f.jpg'),
(110,'서울',0,'test','010-0000-0001',NULL),
(111,'부산',100,'디비디비딥','010-0000-0002','a845b31b-b2cd-4b00-9a72-81cc9279e41f.jpg'),
(179,'전체',5,'관리자1','010-1122-3344',NULL),
(554,'광주',3755,'갱스터지오','010-0000-0010','88efbc1b-e11c-4613-9da7-5aade84171b0.jpg'),
(635,'서울시 강남구',0,'test1','010-1234-5617',NULL),
(636,'광주',0,'name3','010-0000-0008',NULL),
(637,'울산',0,'회원수다','010-0000-0012',NULL),
(677,'경기',10,'호랑이강아지','010-0000-0009','563a48ce-1c20-46d8-9d30-7e0dbbe8fa91.jpg'),
(936,'울산',0,'고양이와 아들','010-0000-0003','cd699e78-7d88-4bfd-92d2-8cac2caa5c46.jpg'),
(1195,'서울',0,'kkk1234','010-1111-2211',NULL),
(1196,'서울',170,'냥냥펀치','010-7336-4954','7135998b-e492-439f-9c04-e14640f511b1.png'),
(1252,'광주',5,'봉사조아','010-9224-3893','a6390ac2-a170-4df3-b1ad-3b497ccfbf0f.png'),
(1253,'서울시 강남구',0,'adm12','010-1234-3311',NULL),
(1364,'서울',0,'임건호임','010-1234-7164',NULL),
(1476,'서울',5,'w지호w','010-0011-0088','5ef7ee9d-5b2c-4fe0-bb72-0d1520edc572.jpg'),
(1477,'서울시 강남구',120,'seok1','010-1234-1414',NULL),
(2110,'서울',0,'admin','010-9999-9999',NULL),
(2111,'서울',0,'member','010-1111-1111',NULL),
(2112,'서울',0,'0xe82de','010-1122-2211',NULL),
(2778,'경주',0,'test2','010-1111-1112',NULL),
(2954,'서울',5,'test123','010-0000-0000',NULL),
(2955,'울산',0,'피나코에요','010-9224-3892',NULL),
(2956,'서울',0,'멈머멍멍','010-8765-4321',NULL),
(2957,'서울',0,'서울전역순찰봉사','010-1234-1234',NULL),
(3017,'서울',165,'도와준다냥','010-0001-0001',NULL),
(3018,'서울',5,'개처언사','010-0001-0002',NULL),
(3019,'전체',200,'오마이갓','010-0001-0003','302a9da3-1769-48e7-a234-7981e5bac713.jpeg'),
(3020,'부산',5,'꼬미엄마','010-3838-2929',NULL),
(3021,'전체',5,'캣맘파워','010-2848-2367',NULL),
(3022,'경주',165,'햄볶아요','010-2922-1945',NULL),
(3023,'서울',5,'코기귀여어','010-2922-1943',NULL),
(3024,'광주',0,'강지오멍','010-0000-1122',NULL),
(3025,'부산',0,'고양이냥','010-9992-1231',NULL),
(3026,'서울',625,'싸피조아','010-8372-3948','63abf371-71a6-4f39-bce9-369eac58e35e.png'),
(3027,'부산',5,'냥아치라능','010-2947-3956',NULL),
(3028,'부산',100,'이노스케','010-9838-2822','4f78c290-503a-459c-bbe5-7a137d30db6f.jpg'),
(3029,'전체',100,'불멸의이순신','010-7777-5994','db601e31-afe4-4cad-95b7-740c39b76b3e.jpg'),
(3030,'서울',0,'카리나 로켓펀처','010-9224-3814','6e44ee1e-bffc-47e0-ae4a-76f89ce95456.jpg'),
(3031,'광주',0,'발바닥꼬순내','010-9246-3892','e82222dd-1a10-4bf7-93a0-a7d5e2338a68.png'),
(3032,'부산',5,'날아라슈퍼맨','010-0001-0004','461f8f7e-4a26-4f60-8c64-6d248d0b7b1f.jpeg'),
(3033,'광주',0,'도그캣도그캣','010-0001-0005',NULL),
(3034,'서울',0,'개강아지','010-0001-0006',NULL),
(3822,'경북',0,'sssssss','010-4567-1596',NULL),
(4000,'인천',0,'빵진조아','010-4321-7164',NULL);
/*!40000 ALTER TABLE `tb_member_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_support_history`
--

DROP TABLE IF EXISTS `tb_support_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_support_history` (
  `support_history_seq` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `amount` int(10) unsigned NOT NULL,
  `email` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `member_seq` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`support_history_seq`),
  KEY `FKdj504pssvrsvir0bqtbewu5es` (`member_seq`),
  CONSTRAINT `FKdj504pssvrsvir0bqtbewu5es` FOREIGN KEY (`member_seq`) REFERENCES `tb_member` (`member_seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_support_history`
--

LOCK TABLES `tb_support_history` WRITE;
/*!40000 ALTER TABLE `tb_support_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_support_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_volunteer`
--

DROP TABLE IF EXISTS `tb_volunteer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_volunteer` (
  `volunteer_seq` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `last_modified_by` varchar(255) NOT NULL,
  `last_modified_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `activity_area` varchar(255) NOT NULL,
  `content` text NOT NULL,
  `is_deleted` bit(1) NOT NULL,
  `max_participant_count` int(10) unsigned NOT NULL,
  `min_participant_count` int(10) unsigned NOT NULL,
  `status` varchar(255) NOT NULL,
  `title` varchar(255) NOT NULL,
  `view_count` bigint(20) unsigned NOT NULL,
  `member_seq` bigint(20) unsigned NOT NULL,
  `auth_time` varchar(255) NOT NULL,
  `contact` varchar(255) NOT NULL,
  `end_date` date NOT NULL,
  PRIMARY KEY (`volunteer_seq`),
  KEY `FKcx3mlu3b53fav1owh1kd6etp4` (`member_seq`),
  CONSTRAINT `FKcx3mlu3b53fav1owh1kd6etp4` FOREIGN KEY (`member_seq`) REFERENCES `tb_member` (`member_seq`)
) ENGINE=InnoDB AUTO_INCREMENT=745 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_volunteer`
--

LOCK TABLES `tb_volunteer` WRITE;
/*!40000 ALTER TABLE `tb_volunteer` DISABLE KEYS */;
INSERT INTO `tb_volunteer` VALUES
(512,'1ef93fd1-5a11-4a1c-b685-e7f3ad973d54','2022-03-27 17:08:18','591a621a-d859-4c2c-a176-e93c64a6e574','2022-03-27 17:08:30','전체','<p>바로 지울게요 잠시</p>','',3,3,'RECRUITING','바로 지울게요 잠시',185,1476,'0','https://open.kakao.com/o/gROTPK9d','2022-04-07'),
(513,'6afc5238-d974-4902-8a89-7d89455370ff','2022-03-27 17:18:42','848ab97f-0b8b-48f0-b114-f5f4cde237da','2022-04-07 07:53:34','서울','<p>안녕하세요. 서울시 야생동물센터에서 봉사하실 분들을 구합니다!</p><p><br></p><p>4월 23일 토요일부터 시작해서 매주 토요일에 4시간 정도 진행됩니다!</p><p>총 4주 동안 진행이 되구요!</p><p><br></p><p>봉사활동 내용은 야생 동물 보호소 청소와 동물 관리를 도와드리는 겁니다!!</p><p>관심 있으신 분은 제 연락처로 문자를 보내주세요!!</p><p><br></p><p>- 활동 장소 : 서울시 야생동물센터</p><p>- 활동 기간 : 2022.04.23 ~ 2022.05.07</p>','\0',6,3,'ONGOING','서울시 야생동물센터에서 봉사하실 분들을 구해요!',1983,3017,'16','010-0000-0000','2022-04-13'),
(514,'5350fb93-4cf3-4ae6-a366-de190d9888ab','2022-03-28 17:22:15','28da7b67-87a1-43b1-8841-d97057d59aab','2022-04-07 02:34:14','경기','<p>활동 장소 : 경기도 도우미견 나눔센터</p><p><br></p><p>활동 기간 : 4/16~17 (토,일 이틀간)&nbsp;오전 11시 ~ 오후 4시</p><p><br></p><p>봉사 내용: 보호소 내부 청소, 배변패드 교체, 유기견/묘 사료 분배 등의 활동을 하게됩니다.</p><p><br></p><p><img src=\"http://j6a302.p.ssafy.io:8080/static/files/images/board/37c4be5e-a3e3-444b-aceb-b4db967bdddb.jpg\" width=\"404\" style=\"cursor: nesw-resize;\"></p>','\0',6,3,'ONGOING','경기도 도우미견 나눔센터 봉사 모집합니다!',586,1196,'10','https://open.kakao.com/o/gROTPK9d','2022-04-13'),
(515,'c4e1f462-b08d-42db-bb4a-2bfe1fafe47d','2022-03-31 17:22:37','2df4b671-c5a6-45a4-adf6-4f7715cda74b','2022-04-05 21:30:30','전체','<p>입양센터에서 봉사활동 하실 분들을 구합니다 ㅎㅎ</p><p><br></p><p>활동 기간은 4월 22일부터 5월 13일까지 입니다.</p><p>매주 금요일 저녁에 모여서 진행되구요!!</p><p><br></p><p>입양센터에서 입양을 기다리는 아이들이 있는데 아이들이 잘 지낼 수 있도록 청소 등을 도와드리는 활동입니다.</p><p>관심 있으신 분은 오픈카톡방으로 연락주세요!!</p><p><br></p><p>- 활동 장소 : 발라당 입양센터</p><p>- 활동 기간 : 2022.04.22 ~ 2022.05.13</p>','\0',4,3,'ONGOING','입양센터에서 봉사활동 하실 분 구합니다 ㅎㅎ',909,3018,'0','https://open.kakao.com/o/gROTPK9d','2022-05-15'),
(516,'402bc53c-4f1f-4014-86da-54941de8b47a','2022-03-31 17:22:44','9bcede0f-92e9-4ace-b36b-9ecce7ac267a','2022-04-05 17:23:01','전체','<p>ㅀㅇㅀㅀㅇㅀㅇㄹ</p>','',3,3,'RECRUITING','ㅇㄹㄹㄹ',248,1476,'0','','2022-04-05'),
(517,'f96bb710-4b35-4bcc-aee1-ee64caeaae6b','2022-04-01 17:23:35','28941cf4-675e-4958-aefc-766658498819','2022-04-05 17:23:42','전체','<p>ㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹ</p>','',3,3,'RECRUITING','ㄹㄹ',184,1476,'0','','2022-04-06'),
(518,'a1d59b41-7d02-4012-bfd2-4e6c002dbfdf','2022-04-01 17:36:50','01c9fbf8-1305-4417-8d0f-7ef4e13eebf3','2022-04-07 02:41:01','전남','<p>광주 유기견 쉼터 햇빛 에서 주말 봉사자 모집합니다.</p><p><br></p><p>오셔서 유기견 관리, 간식 분배, 쉼터 청소등의 활동 하시게 될겁니다.</p><p><br></p><p>궁금하신 내용은 위의 오픈채팅으로 연락 주세요~~</p><p><br></p><p><img src=\"http://j6a302.p.ssafy.io:8080/static/files/images/board/9796d2bf-7c08-4fae-a04b-096af9f2d3c1.jpg\"></p>','\0',5,3,'RECRUITING','광주 유기견 쉼터 주말 봉사자 모집합니다',622,1252,'4','https://open.kakao.com/o/gROTPK9d','2022-04-11'),
(519,'b59d248f-fe0c-42a4-a3db-a4cc3ed29dde','2022-04-01 17:47:18','5f403c39-9891-4148-a2c4-691fc346cecd','2022-04-07 02:40:59','경남','<p>안녕하세요! 경남 자원봉사 센터에서 유기견, 유기묘 이불 만들기 봉사자를 모집하고 있습니다.</p><p><br></p><p>헌 이불을 활용하여 유기동물을 위한 이불을 만들고 유기동물 보호소에 전달하려 합니다.</p><p><br></p><p>60cm x 50cm 크기로 이불을 자르신 후 봉사센터에 직접 방문하셔서 전달하시거나 직접 오셔서 제작도 가능합니다.</p><p><br></p><p><br></p><p>이불 3개당 봉사시간 2시간(1일 최대 2시간) 인정을 해드립니다.</p><p><br></p><p><br></p><p>봉사 기간은 4/1 ~ 4/8 까지 입니다.</p>','\0',10,3,'RECRUITING','유기견, 유기묘 이불 만들기 봉사 모집합니다~~',1604,3020,'2','055-111-1111','2022-04-08'),
(520,'fefee5e6-86c1-487c-b21d-65180de9fa6e','2022-04-01 17:57:38','74c5e317-1732-43a7-b970-1c3a2999dc3f','2022-04-05 17:58:28','전체','<p>안녕하세요!! 유기묘 봉사 동호회 캣맘 파워입니다~!!</p><p><br></p><p><br></p><p>4월 23일날 진행하는 정기 봉사활동 참여 인원을 모집합니다.</p><p><br></p><p><br></p><p>시간은 오전10시 부터 오후 4시까지 진행될 예정입니다.</p><p><br></p><p><br></p><p>궁금하신 점은 위의 오픈채팅으로 문의 주시면 됩니다. </p><p><br></p><p><br></p><p>저희 캣맘 파워를 통해 유기묘들에게 사랑을 전달하세요~</p><p><br></p><p><br></p><p><img src=\"http://j6a302.p.ssafy.io:8080/static/files/images/board/f7f30541-5409-40c3-89f8-d6a521963ea3.jpg\" width=\"592\" style=\"cursor: nesw-resize;\"></p><p><br></p><p><br></p><p><br></p>','',20,3,'RECRUITING','유기묘 봉사 동호회 캣맘 파워 봉사활동 참여인원 모집 합니다.',543,3020,'0','https://open.kakao.com/o/gROTPK9d','2022-04-20'),
(521,'b4bad981-f7cb-4651-b455-f80c4126b64f','2022-04-02 18:00:45','59020b8f-870f-46c7-8e20-b4540af4da4a','2022-04-06 07:21:45','전체','<h2>안녕하세요!! 유기묘 봉사 동호회 캣맘 파워입니다~!!</h2><h2><br></h2><h2><br></h2><h2>4월 23일날 진행하는 정기 봉사활동 참여 인원을 모집합니다.</h2><h2><br></h2><h2><br></h2><h2>시간은 오전10시 부터 오후 4시까지 진행될 예정입니다.</h2><h2><br></h2><h2><br></h2><h2>궁금하신 점은 위의 오픈채팅으로 문의 주시면 됩니다.</h2><h2><br></h2><h2><br></h2><h2>저희 캣맘 파워를 통해 유기묘들에게 사랑을 전달하세요~</h2><p><br></p><p><img src=\"http://j6a302.p.ssafy.io:8080/static/files/images/board/69eb88f8-de5f-4876-b87e-88c711fd5526.jpg\" width=\"753\" style=\"cursor: nesw-resize;\"></p><p><br></p>','\0',20,3,'RECRUITING','유기묘 봉사 동호회 캣맘파워에서 봉사 참여인원을 모집합니다~',352,3021,'0','https://open.kakao.com/o/gROTPK9d','2022-04-23'),
(522,'b8d3b09e-2d55-4ea5-a927-2ae4da7a422b','2022-04-02 19:17:11','96fdf2c2-af5a-4872-bb84-de10e7a9fa55','2022-04-07 02:49:48','경북','<h3>4월 15일 (금요일) 오후 2시부터 6시까지 경주 행복센터 유기동물 봉사활동에 참여할 인원을 모집합니다.</h3><h3><br></h3><p><br></p><p>준비물 : 작업복, 마스크 </p><p><br></p><p><br></p><p>연락처에 적힌 번호로 문의사항 문자 남겨주시면 빠르게 답변드리겠습니다.</p><p><br></p><p><br></p><p><img src=\"http://j6a302.p.ssafy.io:8080/static/files/images/board/b6044c00-ff28-47c2-a62e-1a51f6ab7279.jpg\" width=\"426\" style=\"cursor: nesw-resize;\"></p>','\0',4,3,'RECRUITING','경주 행복센터 유기동물 봉사 모집',360,3022,'0','055-222-2222','2022-04-14'),
(523,'99ffefec-075e-49ce-bac7-9b8334081fd8','2022-04-02 19:23:42','be750747-533e-4272-a0b1-1b321b8658e6','2022-04-06 22:21:17','서울','<h3>4월 9일 한강 공원에서 유기동물 집 만들기 캠페인이 시작됩니다.</h3><p><br></p><p>한강 공원 부스내에서 2인 1조로 집을 만드는 활동에 많은 참여 바랍니다.</p><p><br></p><p>기타 사항 :</p><ol><li>기본적인 연장과 나무는 제공됩니다.</li><li>혼자 집 만들기도 가능합니다.</li><li>행사에서 만들어진 집은 유기동물 보호소에 전달 됩니다.</li><li>봉사 시간 인정 됩니다.(2시간)</li></ol>','\0',10,3,'RECRUITING','[한강공원] 유기동물 집 만들어주기 봉사 활동 모집합니다',763,3023,'2','https://open.kakao.com/o/gROTPK9d','2022-04-08'),
(524,'480c22e6-5b3d-4a13-b56b-8c3dd6c5ad42','2022-04-02 19:30:31','1076b234-cb13-41ab-ba86-e2f7812f94de','2022-04-05 19:47:40','전체','<p>왜 삭제가 바로 안되는것인ㅈㅣ?</p>','',3,3,'ONGOING','지나갑니다.. 삭제 테스트입니다..',34264,1476,'0','','2022-04-07'),
(525,'6189f2a7-6c90-4fd4-b5f3-d918564d40d8','2022-04-02 19:39:01','d0df886f-0c2b-425a-9600-78f5ece564e7','2022-04-07 16:27:34','서울','<p>4월13일에 양주 유기견 보호소 같이가서 봉사활동 하려고 합니다.</p><p>혼자 보다는 여럿이 좋을 것 같아서요 같이 가실 분 모집합니다!!</p><p>3시간 정도 봉사할 계획입니다.</p>','\0',4,3,'ONGOING','서울에서 보호소 봉사활동 같이 가실분 모집합니다.',402,3026,'0','https://open.kakao.com/o/gROTPK9d','2022-04-10'),
(526,'4e99e0f3-934b-4c0d-833a-ff52b504ce5e','2022-04-03 19:48:10','cff6a528-784a-4b5a-b4fb-1cea4eb927fd','2022-04-05 19:56:52','전체','<p>???????????????</p>','',3,3,'RECRUITING','삭제삭제',16595,1476,'0','','2022-04-07'),
(527,'d35c3921-e994-4b77-b362-9c63d4876590','2022-04-04 19:49:58','27216c1d-ad3a-48a0-84e7-b5787b64d157','2022-04-05 21:20:20','전체','<p>그렇다고 합니다</p>','',3,3,'RECRUITING','삭제를 위한 글',868,1476,'0','','2022-04-15'),
(528,'ae739b55-f076-4b03-9dbe-a69830b361a3','2022-04-04 19:50:01','9e03d845-a6f8-4e0a-9b68-39d02cfaf03a','2022-04-07 16:27:29','경기','<ul><li>수원에 있는 유기동물 보호센터 갈 예정입니다</li><li>인원모집 완료 후 장소 정해봅시다!! </li></ul>','\0',3,3,'RECRUITING','수원 지역 봉사가실분!!',199,677,'0','https://open.kakao.com/o/gROTPK9d','2022-04-22'),
(529,'b9bc0054-6a60-4ee0-9e89-7b525725e6e3','2022-04-04 19:57:44','ea5d0703-924e-4ecc-b4f9-a362122d67e8','2022-04-05 19:57:50','전체','<p>왓츠롱윗유?????????</p>','',3,3,'RECRUITING','??문제를 모르겠당',94,1476,'0','','2022-04-07'),
(530,'1087a33a-ae22-40d4-abe1-7e98a20154da','2022-04-05 19:58:41','426f4c3a-b9c8-4b78-9604-095aa7af2b9d','2022-04-07 16:03:21','부산','<p>부산 동물 보호 센터에서 봉사활동 인원을 모집합니다!!</p><p>장소, 시간: 오전 10시 ~ 오후 3시 (보호센터에 오셔서 봉사 하러 왔다고 말씀해주세요~~)</p><p>준비물 : 편한 작업복, 신분증</p><p>기타 안내사항: 인원 모집이 완료되면 모집이 마감됩니다.</p><p><img src=\"http://j6a302.p.ssafy.io:8080/static/files/images/board/a918b72a-c63e-4424-bc16-f73a3d375025.jpg\"></p>','\0',8,3,'RECRUITING','부산 동물보호센터 봉사활동 가실 분 모집합니다!! 적극 참여 바람!!',1060,3027,'4','051-393-2523','2022-04-16'),
(531,'8ce3d968-c073-48c9-a0de-06e776760a6f','2022-04-05 20:36:43','d4e2be27-9c27-4302-a0c1-f36a12933158','2022-04-06 05:09:31','전체','<p>이ㄱㅔ뭐냐.....?</p>','',3,3,'RECRUITING','아니아니이게무슨에러지요',13270,2956,'0','','2022-04-07'),
(532,'51b8f38a-001d-4234-ab17-aa7e43fbcb5b','2022-04-05 20:42:42','edc9980c-c4ca-4dc5-b4e2-1e5b87d11943','2022-04-07 05:01:27','부산','<p>안녕하쇼잉 부산 동물 보호센터에서 봉사활동하실 분을 모집합니다잉</p><p><br></p><p>매주 일요일 오전 10시 ~ 오후 3시까지 에요잉</p><p>점심은 제공됩니다잉.</p><p><br></p><p>관심 있으신 분은 연락주쇼.</p><p><br></p><p>- 장소 : 부산 동물보호센터</p><p>- 기간 : 2022.04.17 ~ 2022.05.29</p>','\0',3,3,'RECRUITING','부산 동물 보호센터 봉사활동 모집합니다잉',425,3032,'24','010-0001-0004','2022-04-08'),
(533,'01a1948c-8a1f-4b88-ba6e-9575683bd7c3','2022-04-05 21:20:45','61f41a49-cd35-442f-b061-12aba3c21b81','2022-04-05 21:21:10','전체','<p>약간의 문제가 있습니다</p>','',3,3,'RECRUITING','에러테스트인데요',2,1476,'0','','2022-04-14'),
(534,'216edec3-9548-4b21-8fb8-3376c217119c','2022-04-05 21:21:37','fcfe3896-aff2-44c9-a8ac-eb2e3fe47c3b','2022-04-05 21:21:40','전체','<p>이제 다 잘되나?</p>','',3,3,'RECRUITING','삭제 될때도 있고 안될때도 있나',2,1476,'0','','2022-04-07'),
(535,'f14e3929-a52f-4c8c-a434-c61afea875b6','2022-04-05 21:21:59','7e950194-91a6-4dc7-9a05-8cef507ca84f','2022-04-05 21:22:03','전체','<p>아마도? 마지막 테스트.</p>','',3,3,'RECRUITING','삭제 이제 잘돼요',2,1476,'0','','2022-04-16'),
(658,'b65f765e-c74e-4480-81c1-1962914c504c','2022-04-06 16:16:06','c51946dd-49d3-4d07-80a5-2450a7cec985','2022-04-07 08:03:49','경기','<p>구합니다~~~</p><p>안녕하세요</p><p>토요일마다 저와 함께 보호소 봉사하러 가실 분 구합니다</p><p>제가 3년째 봉사해오고 있는 보호소이구요,</p><p>제 번호로 연락 주시면 됩니다~~</p><p>함께해용</p>','\0',3,3,'ONGOING','토요일마다 함께 봉사하실 분 ',1264,1476,'0','010-1122-2233','2022-04-09'),
(719,'f2d2141b-7701-4834-b6f2-eac9428dc3e8','2022-04-07 06:06:50','7b773050-a25c-4788-a80a-49f2f90922b3','2022-04-07 16:27:59','서울','<h2> 아직 정확한 장소는 안정했구요!!</h2><h2>4월 마지막주 주말 생각하고 있습니다.</h2><p>관심있으신 분 연락주세요~!!!</p>','\0',3,3,'RECRUITING','4월 말 보호센터 봉사하실 분 구합니다.',91,554,'3','https://open.kakao.com/o/gROTPK9d','2022-04-22'),
(744,'fec79231-2575-472c-b907-1918c3c8c4b1','2022-04-07 07:52:10','e460d1b2-4b3a-4a61-9076-cfe06721191d','2022-04-07 08:13:05','서울','<h4>활동 장소 : 경기도 양주시 유기견 보호 센터</h4><h4><br></h4><h4>활동 시간 : 4월 15일 금요일 10시 ~ 2시 </h4><h4><br></h4><h4>많이 많이 참여 해주세요~~ </h4><h4><br></h4><h4><img src=\"http://j6a302.p.ssafy.io:8080/static/files/images/board/b330a258-62e6-418d-8fa8-b212b6921a88.jpg\" width=\"427\" style=\"\"></h4><p><br></p>','',5,3,'RECRUITING','서울에서 유기견 보호센터 봉사 같이 가실 분 모집합니다!!!',56,3028,'4','https://open.kakao.com/o/gROTPK9d','2022-04-10');
/*!40000 ALTER TABLE `tb_volunteer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_volunteer_auth`
--

DROP TABLE IF EXISTS `tb_volunteer_auth`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_volunteer_auth` (
  `volunteer_seq` bigint(20) unsigned NOT NULL,
  `created_by` varchar(255) NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `last_modified_by` varchar(255) NOT NULL,
  `last_modified_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `content` text NOT NULL,
  `status` varchar(255) NOT NULL,
  PRIMARY KEY (`volunteer_seq`),
  CONSTRAINT `FK9igvymnqswowribll216bfoso` FOREIGN KEY (`volunteer_seq`) REFERENCES `tb_volunteer` (`volunteer_seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_volunteer_auth`
--

LOCK TABLES `tb_volunteer_auth` WRITE;
/*!40000 ALTER TABLE `tb_volunteer_auth` DISABLE KEYS */;
INSERT INTO `tb_volunteer_auth` VALUES
(514,'075f2843-5f72-4a30-8278-6aeb01889751','2022-04-06 06:45:19','9829d705-b2b5-440b-911c-4968d5b5b2ca','2022-04-06 07:04:40','<p>경기도 도우미견 나눔센터에서 봉사 수행하였습니다.</p><p><img src=\"http://j6a302.p.ssafy.io:8080/static/files/images/board/99415b20-4d49-4365-8172-78456a9da6a7.jpg\"></p>','REJECT'),
(528,'b35c1a93-b12b-4aae-a43c-5033f4ca490e','2022-04-07 16:25:54','b35c1a93-b12b-4aae-a43c-5033f4ca490e','2022-04-07 16:25:54','<p>수원 지역 봉사 인증요청합니다~!</p>','REQUEST'),
(658,'a6a2c00d-a563-4776-81fa-e23bc977f37f','2022-04-07 05:18:47','a6a2c00d-a563-4776-81fa-e23bc977f37f','2022-04-07 05:18:47','<p>봉사인증 신청합니다</p><p>매주 가니까</p><p>매주 신청할게요</p><p>ㅎㅎㅎ</p>','REQUEST'),
(719,'9d28a29a-f5cf-4670-8b11-aa226ea137e0','2022-04-07 16:21:02','9d28a29a-f5cf-4670-8b11-aa226ea137e0','2022-04-07 16:21:02','<p>귀여운 강아지 만나고 왔습니다.<img src=\"http://j6a302.p.ssafy.io:8080/static/files/images/board/7e1cbdef-bed5-402d-93fc-54b15f99136d.jpg\" width=\"223\" style=\"cursor: nwse-resize;\"></p>','REQUEST');
/*!40000 ALTER TABLE `tb_volunteer_auth` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_volunteer_comment`
--

DROP TABLE IF EXISTS `tb_volunteer_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_volunteer_comment` (
  `comment_seq` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `last_modified_by` varchar(255) NOT NULL,
  `last_modified_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `content` text NOT NULL,
  `is_deleted` tinyint(4) NOT NULL,
  `member_seq` bigint(20) unsigned NOT NULL,
  `parent_seq` bigint(20) unsigned DEFAULT NULL,
  `volunteer_seq` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`comment_seq`),
  KEY `FKt4hugqpoik5akdyfashcn3y66` (`member_seq`),
  KEY `FK7j3v7a3grop4jfker7yxh9ad4` (`parent_seq`),
  KEY `FK2b34523x2e5tdcsmdr6lxblh5` (`volunteer_seq`),
  CONSTRAINT `FK2b34523x2e5tdcsmdr6lxblh5` FOREIGN KEY (`volunteer_seq`) REFERENCES `tb_volunteer` (`volunteer_seq`),
  CONSTRAINT `FK7j3v7a3grop4jfker7yxh9ad4` FOREIGN KEY (`parent_seq`) REFERENCES `tb_volunteer_comment` (`comment_seq`),
  CONSTRAINT `FKt4hugqpoik5akdyfashcn3y66` FOREIGN KEY (`member_seq`) REFERENCES `tb_member` (`member_seq`)
) ENGINE=InnoDB AUTO_INCREMENT=131 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_volunteer_comment`
--

LOCK TABLES `tb_volunteer_comment` WRITE;
/*!40000 ALTER TABLE `tb_volunteer_comment` DISABLE KEYS */;
INSERT INTO `tb_volunteer_comment` VALUES
(121,'c1ea6bd3-abdd-4490-b81c-b4d195fc2fad','2022-04-05 19:31:13','c1ea6bd3-abdd-4490-b81c-b4d195fc2fad','2022-04-05 19:31:13','그런가요?\n',0,3021,NULL,524),
(122,'bb517b1e-eace-468a-9ae2-875244b8ef20','2022-04-05 19:51:26','bb517b1e-eace-468a-9ae2-875244b8ef20','2022-04-05 19:51:26','봉사 좋아요오오\n',0,3026,NULL,528),
(123,'c9209230-541d-4d5b-af4b-2016ca795a1f','2022-04-05 20:35:59','c9209230-541d-4d5b-af4b-2016ca795a1f','2022-04-05 20:35:59','저도 가고싶어요\n',0,2956,NULL,525),
(124,'0930990e-c2b8-494d-a74e-22a049f8e913','2022-04-05 20:43:12','0930990e-c2b8-494d-a74e-22a049f8e913','2022-04-05 20:43:12','사투리 너무 귀엽네요 ㅎㅎ\n',0,3031,NULL,532),
(125,'815f8ed6-f84a-479d-8a86-b1ac05042f1b','2022-04-05 21:18:52','3abbf32e-68f0-4284-8617-a09395aa2127','2022-04-05 21:18:57','댓굴\n',1,2956,NULL,531),
(126,'e060384c-ed5a-4963-86f9-d7148446a59e','2022-04-05 21:19:27','c16555bf-bfe4-43f1-a1f0-6d63b54a25a9','2022-04-05 21:19:31','댓글\n',1,2956,NULL,531),
(127,'1704cd20-9217-4e13-bc0f-3a8189419636','2022-04-07 01:49:44','1704cd20-9217-4e13-bc0f-3a8189419636','2022-04-07 01:49:44','봉사하실 분 댓글 남겨주세요~~~\n',0,1476,NULL,658),
(128,'e66272c7-3cb5-487f-a2e2-1a199c10eb63','2022-04-07 02:49:39','e66272c7-3cb5-487f-a2e2-1a199c10eb63','2022-04-07 02:49:39','3년째 봉사 ㄷㄷㄷㄷ 멋지십니다 지호님\n',0,1196,NULL,658),
(129,'278480a2-b1bb-430d-bb8e-fe3400f68a9c','2022-04-07 07:59:47','c17ebbe0-361e-4a9a-8840-fedaa290931d','2022-04-07 08:03:48','신청했씁니다!! ㅎㅎ\n',1,3028,NULL,530),
(130,'d77d4fc8-5458-4b3a-9b4c-7cba6aad07e9','2022-04-07 16:03:21','d77d4fc8-5458-4b3a-9b4c-7cba6aad07e9','2022-04-07 16:03:21','저 신청했습니다!!\n',0,3026,NULL,530);
/*!40000 ALTER TABLE `tb_volunteer_comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_volunteer_participant`
--

DROP TABLE IF EXISTS `tb_volunteer_participant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_volunteer_participant` (
  `member_seq` bigint(20) unsigned NOT NULL,
  `volunteer_seq` bigint(20) unsigned NOT NULL,
  `approve` bit(1) DEFAULT NULL,
  PRIMARY KEY (`member_seq`,`volunteer_seq`),
  KEY `FKg8x1lrgj8co85d5sxqb1xhye` (`volunteer_seq`),
  CONSTRAINT `FK3mnfuwt6xoal6yuljrv5dwhki` FOREIGN KEY (`member_seq`) REFERENCES `tb_member` (`member_seq`),
  CONSTRAINT `FKg8x1lrgj8co85d5sxqb1xhye` FOREIGN KEY (`volunteer_seq`) REFERENCES `tb_volunteer` (`volunteer_seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_volunteer_participant`
--

LOCK TABLES `tb_volunteer_participant` WRITE;
/*!40000 ALTER TABLE `tb_volunteer_participant` DISABLE KEYS */;
INSERT INTO `tb_volunteer_participant` VALUES
(1,522,'\0'),
(1,658,'\0'),
(554,523,'\0'),
(554,531,'\0'),
(554,719,''),
(677,528,''),
(677,531,'\0'),
(677,719,''),
(936,719,'\0'),
(1196,514,''),
(1196,531,'\0'),
(1252,518,''),
(1476,512,''),
(1476,516,''),
(1476,517,''),
(1476,524,''),
(1476,526,''),
(1476,527,''),
(1476,529,''),
(1476,531,''),
(1476,532,'\0'),
(1476,533,''),
(1476,534,''),
(1476,535,''),
(1476,658,''),
(2110,530,'\0'),
(2111,658,''),
(2956,530,''),
(2956,531,''),
(2956,658,''),
(2957,525,''),
(3017,513,''),
(3017,514,''),
(3017,515,''),
(3017,519,'\0'),
(3017,525,''),
(3018,513,''),
(3018,515,''),
(3018,519,'\0'),
(3018,523,'\0'),
(3018,528,'\0'),
(3020,513,''),
(3020,515,''),
(3020,519,''),
(3020,520,''),
(3021,521,''),
(3022,514,''),
(3022,522,''),
(3023,513,''),
(3023,518,''),
(3023,519,''),
(3023,523,''),
(3026,513,''),
(3026,514,''),
(3026,515,''),
(3026,519,'\0'),
(3026,521,'\0'),
(3026,523,'\0'),
(3026,525,''),
(3026,530,'\0'),
(3027,519,'\0'),
(3027,521,'\0'),
(3027,523,'\0'),
(3027,528,'\0'),
(3027,530,''),
(3028,525,''),
(3028,528,'\0'),
(3028,744,''),
(3029,522,'\0'),
(3029,523,'\0'),
(3031,513,''),
(3031,523,'\0'),
(3031,530,''),
(3031,532,''),
(3032,528,'\0'),
(3032,530,''),
(3032,532,'');
/*!40000 ALTER TABLE `tb_volunteer_participant` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-04-08 10:31:15
