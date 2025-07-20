# 📖 세모책(세상의 모든 책)
위 프로젝트는 책 검색부터 결제까지 할 수 있는 웹 서비스입니다.
## 🖥️ 개발 환경</h2>
- 백엔드 사용 언어 : Java, 개발 툴 : 이클립스(Eclipse)</li>
- 프론트엔드 사용 언어 : html5, css3, javaScript, 개발 툴 : VSCode</li>
- DB 엔진 : AWS RDS MySQL</li>
- 버전 관리 : Git & GitHub</li>
## 🗂️ 주요 기능
1. [👤 회원가입 및 로그인](https://github.com/dydtmd6801/java-semochaek-project/edit/main/README.md#%EB%A1%9C%EA%B7%B8%EC%9D%B8-%EB%B0%8F-%ED%9A%8C%EC%9B%90%EA%B0%80%EC%9E%85)
2. [🪪 Spring Security + JWT 기반 인증](https://github.com/dydtmd6801/java-semochaek-project/edit/main/README.md#spring-security-jwt-%EA%B8%B0%EB%B0%98-%EC%9D%B8%EC%A6%9D)
3. 찜하기 및 장바구니
4. 결제 시스템
### 👤 회원가입 및 로그인
#### 회원가입
1. 사용자에게 이메일, 비밀번호, 이름, 전화번호, 주소를 받아 DB에 저장
   - 이메일 인증을 통해 이메일의 유효성을 확인
   - 비밀번호는 `BCryptPasswordEncoder`를 통해 해싱
#### 로그인
1. 사용자에게 이메일과 비밀번호를 받아 서버로 로그인 요청 전송
3. 서버는 `AuthenticationManager`를 통해 인증을 수행
4. 인증에 성공하면 사용자 정보를 기반으로 JWT 토큰을 생성하여 클라이언트에 반환
5. 클라이언트는 JWT 토큰을 `localStorage`에 저장하여 인증 상태 유지

---
### 🪪Spring Security +JWT 기반 인증
1. 로그인 요청이 들어오면, `UsernamePasswordAuthenticationToken`을 생성
   - 아직 인증되지 않은 토큰 -> `AuthenticationManager`에게 인증 받기 위한 용도
2. `AuthenticationManger`를 통해 사용자 인증 수행
   - 내부적으로 `UserDetailService.loadUserByUsername()`을 호출하여 DB에서 사용자 정보 조회하고, `BCryptPasswordEncoder.maches()`를 통해 비밀번호 검증
   - 인증에 성공하면, `Authentication` 객체를 반환
3. 반환된 `Authentication`에서 `getPrincipal()`로 사용자 정보 추출
4. 추출한 사용자 정보에서 사용자 이메일과 권한(role)을 추출하여 JWT 생성
