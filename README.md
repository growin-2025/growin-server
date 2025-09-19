### 커밋 컨벤션
[Conventional Commits](https://www.conventionalcommits.org/) 규칙을 기반으로 합니다.

- `feat`: 새로운 기능 추가
- `fix`: 버그 수정
- `docs`: 문서 수정 (README, 주석 등)
- `style`: 코드 포맷팅, 세미콜론 누락 등 (코드 변경 없음)
- `refactor`: 코드 리팩토링 (동작 변화 없음)
- `test`: 테스트 코드 추가/수정
- `chore`: 빌드 업무, 패키지 매니저 수정 등
- `perf`: 성능 개선

**예시**

feat: 로그인 API 구현<br>
fix: 회원가입 시 이메일 중복 체크 로직 수정<br>
docs: README 브랜치 전략 추가<br>
refactor: UserService 의존성 주입 방식 변경<br>


---

###  브랜치 전략
`main` / `develop` 2개의 메인 브랜치를 유지하며, 기능 단위 브랜치를 활용합니다.

- `main`  
  실제 배포용 브랜치 (항상 안정적인 상태 유지)

- `develop`  
  다음 배포를 준비하는 브랜치 (기능 병합 대상)

- `feature/{이슈번호-기능명}`  
  새로운 기능 개발 브랜치  
  ex) `feature/12-login-api`

- `fix/{이슈번호-버그내용}`  
  버그 수정 브랜치  
  ex) `fix/34-email-validation`

- `hotfix/{버그내용}`  
  운영 중 긴급 버그 수정 브랜치  
  ex) `hotfix/token-expire-bug`

---

### Git Flow
1. 기능 개발 시 `develop`에서 `feature/*` 브랜치 생성
2. 기능 개발 완료 후 `develop`으로 PR → 코드 리뷰 → 머지
3. 배포 준비 시 `develop` → `main` 머지
4. 운영 중 긴급 수정은 `main`에서 `hotfix/*` 생성 후
    - 수정 완료 → `main` & `develop` 모두에 반영

---

### 커밋 & 브랜치 네이밍 규칙
- 커밋 메시지: `type: 설명` 형식 (한글/영문 가능, 소문자 권장)
- 브랜치 네이밍: `{type}/{이슈번호-설명}` (영문 kebab-case)

---

👉 위 전략을 지켜 협업 시 **일관성**과 **가독성**을 보장합니다.