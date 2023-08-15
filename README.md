# spring-landlog


## 과제 제출 방법

1. 자신의 레포로 fork
2. 과제 구현
3. 각자 자신의 branch로 PR
   - 권용현: yonghyun
   - 김동하: dongha
   - 김수환: suhwan
   - 박준영: junyoung
   - 양재승: jaeseung
   - 오형석: hyungseok
   - 이세원: sewon
   - 이승철: seungcheol
   - 임주민: jumin

## 1. 로그인 구현

### 요구사항
1. 회원가입 필드 추가
2. 홈화면 -> 로그인 버튼 추가
   - _html 파일에 버튼추가_
3. 로그인 페이지
   - Path: /memebers/login
   - 로그인
     - 성공 -> 블로그 리스트 페이지(/blogs?creatorId=1)
     - 실패 -> 홈(/) 
4. 블로그 페이지

### 구현
1. 회원가입 필드 추가
   - createMemberForm.html : email, password 추가
   - memberListForm.html : email 추가
   - Member 클래스 : email,password 추가
   - MemberForm 클래스 : email,password 추가 
   - MemberController 클래스 : 생성자로 의존 주입 email,password 추가
   - MemberService
     - 중복회원예외처리 : 이름에서 email로 변경
     - 이름, 비밀번호 입력 안한 경우에 대한 예외 처리 로직 추가
     - 이메일 형식을 지키지 않은 경우에 대한 예외 처리 로직 추가
2. 홈 화면
   - 로그인 버튼 추가
   - members/login 으로 이동
3. 로그인 페이지 (/members/login)
   - MemberController에 @GetMapping("/members/login") 함수 만들어서 /members/loginForm 과 매칭
   - MemberService에 login함수 추가
     - 이메일, 비밀번호 유효성 검사
       - findByEmail, getPassword.equals()
   - MemberController에 login함수 추가
     - 성공시 : /blogs (블로그화면)
     - 실패시 : / (홈화면)
   - loginForm.html 추가
4. 블로그 페이지
   - BlogController 추가 
     - MemberService 의존성 주입
     - model <- findById()
     - html 파일 (blogs/blogList) 와 매칭



[5. swaggerAPI] 

