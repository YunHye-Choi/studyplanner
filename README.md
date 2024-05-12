#  studyplanner - 인프런 강의 수강 플래너
## 배포 URL
- http://studyplanner-env-1.eba-vdaqipwa.ap-northeast-2.elasticbeanstalk.com/
## How to use
- https://github.com/YunHye-Choi/studyplanner/wiki
## 업데이트 노트
- `2024.05.12.` AWS Elastic Beanstalk을 이용해 웹 상에 배포
- `2024.05.11.` 웹 화면으로 강의 URL만 입력받아서 웹 화면으로 출력
- `2024.05.09.` 웹 화면으로 강의 커리큘럼 복사한 텍스트 받아오고, 표준출력
- `2024.05.08.` 바닐라 자바 프로젝트 (https://github.com/YunHye-Choi/inflearnStudyPlanner) 에서 Spring 프로젝트로 이관

---
## Troubles
### bugs
- 섹션 0외의 섹션에 강의제목 - 강의시간 매칭 안되는 부분 있으면 에러 발생
  - 해당 부분이 강의인지 아닌지 판별하는 로직 필요
### ops
- AWS Elastic Beanstalk 환경 상태 [Warning]인 이유 원인 파악 필요
## 개선할 점
### feat
- 수강 계획 결과 클립보드에 복사 기능
- 수강 계획 결과 csv등의 파일로 받아보기 기능
- 하루 수강 분량 사용자로부터 입력 받기
### refactor
- 사용자에게서 받아온 URL validation
- exception 구체화
- 서비스 코드 리팩토링
### ops
- Github Action과 Docker를 이용한 CI/CD 도입
- dns 예쁘게 만지기
## 고민 
(추가할 지 말 지 확실하지 않은 아이디어들)
- 날짜 지정 기능 추가 (사용자로부터 startDate, 제외 날짜 등 입력받아 수강 계획과 매핑)
- 특정 기간 내 모두 수강하려면 하루 몇시간씩 공부해야하는 지 계산해주기
  - 비현실적이고 지키기 어려운 계획이 될 수 있음
  - 특정 기간 내 수강해야 하는 사람들이 이런게 필요할까?