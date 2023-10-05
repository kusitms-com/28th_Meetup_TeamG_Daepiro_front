### 최강 안드로이드

## 📌 Commit Conventions
**Commit Message**
```
type : subject      (제목)

body(Optional)      (내용)

footer(Optional)    (꼬리말)
```

**Commit Type**
|태그|설명|
|:---:|---|
|Feat|신규 기능 구현 작업|
|Fix|버그 수정|
|Docs|문서 수정|
|Style|코드 스타일 변경|
|Design|UI 디자인 변경|
|Refactor|코드 리팩토링|
|Rename|변수, 클래스, 메소드, 패키지명 변경|
|Build|dependencies 추가 및 삭제|
|Chore|기타 변경사항(빌드 관련, 패키지 매니징, assets 등)|

**Sample**
```
[Feat #Issue Number] 회원가입 기능 구현

이메일 중복 검사 기능 추가
회원가입 API 연결

Resolves: #123
Ref: #456
Related to: #48, #45
```
## 📘 Git Flow 전략
|브랜치명|설명|
|:---:|---|
|main|출시 또는 배포 가능한 코드의 브랜치|
|dev|다음 버전을 개발하는 브랜치|
|feat/[이름]|기능을 개발하는 브랜치|
|fix/[이름]|버그를 수정하는 브랜치|

## 📙 Issue Template
이슈 이름은 **[이슈 태그]: 이슈 내용** 으로 작성  
ex) `[Feat] 로그인 관련 기능 구현`

|이슈태그|설명|
|:---:|---|
|Feat|신규 기능 구현 이슈|
|Fix|버그 수정 이슈|


**Issue**
- 이슈 내용: `ex) 로그인 관련 기능`

**Branch Name**
- 생성 브랜치 이름 : `ex) feat/login`

**To-Do List**
- [ ] `ex) 로그인 구현`
- [ ] `ex) 회원가입 구현`
