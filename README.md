<div align="center">

# 🌸 퍼퓨온미 (Perfume On Me)

**향수를 더 쉽고 즐겁게, 경험하다**  
향기와 경험을 담아내는 새로운 방식의 **향수 추천·경험 플랫폼**

<img src="images/cover.png" alt="퍼퓨온미 커버 이미지" width="600" />

</div>

---

## 📌 프로젝트 소개

퍼퓨온미는 사용자가 자신에게 어울리는 향수를 쉽고 재미있게 찾을 수 있도록 돕는 향수 추천·경험 플랫폼입니다.
GPT 기반 분석, 키워드 검색, 설문 등 다양한 방법을 통해 사용자의 취향을 파악하고,
성격·기분·스타일에 맞춘 개인 맞춤형 향수 추천을 제공합니다.
이를 통해 단순한 제품 구매를 넘어, 향수를 통해 추억과 감정을 담아내는 새로운 경험을 제안합니다.

---

## 🌱 프로젝트 배경

수천 가지 향수가 존재하지만, 대부분의 사람들은 어떤 향이 자신에게 어울릴지 몰라 선택에 어려움을 겪습니다.
또한 향에 대한 취향은 언어로 설명하기 어려워 기존의 검색·추천 방식에는 한계가 있습니다.
퍼퓨온미는 이러한 문제를 해결하고자, 다양한 접근 방식과 개인화 추천을 결합한 플랫폼을 만들었습니다.
향수를 비싸고 어려운 액세서리가 아닌, 누구나 즐길 수 있는 일상의 취미로 바꾸는 것이 우리의 목표입니다.

---

## 🔗 배포 주소

> [🌐 퍼퓨온미 바로가기](https://perfuonme.example.com)

---

## ✨ 주요 기능

- 💡 **취향 맞춤 추천** : 취향 기반 개인 맞춤 향수 추천
- 📚 **향수 아카이브** :  성별, 상황, 계절, 가격, 노트별 등 검색 및 필터
- 🧾 **시향 기록** : 향에 대한 개인 다이어리 기록
- 📱 **추천 컨텐츠** : 이미지 기반, 온라인 공방, PBTI 등 다양한 경로의 추천

---

## 🎥 데모 & 미리보기

| 메인 화면                                                                                                                               | 향수 상세                                                                                                                               | 추천 화면                                                                                                                              |
|-------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------|
| <img width="393" height="1254" alt="Image" src="https://github.com/user-attachments/assets/6f1d611f-8237-47f0-b0b4-8b8a7d1f961c" /> | <img width="393" height="1729" alt="Image" src="https://github.com/user-attachments/assets/df9b5539-e860-46c2-9a54-f7ad1633e48b" /> | <img width="393" height="824" alt="Image" src="https://github.com/user-attachments/assets/8dae23fe-a7df-4326-be2a-c179803ca08d" /> |

![기능 시연](images/demo.gif)

---

---

## 🌿 브랜치 전략

본 프로젝트는 **Git Flow** 브랜치 전략을 기반으로 운영됩니다.

- `main` : 실제 배포 버전이 반영되는 브랜치
- `develop` : 개발이 진행되는 메인 브랜치
- `feature` : 기능 단위 개발 브랜치
- `fix` : 기능 단위 수정 브랜치
- `refactor` : 코드 리팩터링 브랜치
- `ci-cd` : CI/CD 브랜치
- `style` : 기능에 영향을 주지 않는 수정 브랜치
- `hotfix` : 배포 중 긴급 수정 브랜치

> 모든 PR은 `develop` 브랜치로 머지되며, 코드 리뷰 후 승인 절차를 거칩니다.

---

## 🛠 기술 스택

**Backend**

- `Java`: 21
- `JDK`: 21.0.2
- `Build`: Gradle 8.14.2
- `IDE`: IntelliJ IDEA 2024.1
- `Framework`: Spring Boot 3.5.3, FastAPI
- `Database`: MySQL (AWS RDS), Redis, AWS S3
- `ORM`: Spring Data JPA
- `CI/CD`: Github Actions (CI/CD) + Docker

**협업 도구**

- `Git/GitHub`
- `Notion`
- `Figma`
- `Slack`
- `Discord`

---

## 🧭 서버 아키텍처 다이어그램

![Image](https://github.com/user-attachments/assets/4247179d-01f8-4c24-953e-1df7d008787d)

---

## 📂 프로젝트 구조

```
└── 📁src
    └── 📁main
        └── 📁java
            └── 📁PerfumeOnMe
                └── 📁spring
                    └── 📁apiPayload
                        └── 📁code
                            └── 📁status
                        └── 📁exception
                    └── 📁chatbot
                        └── 📁converter
                        └── 📁domain
                        └── 📁repository
                        └── 📁service
                        └── 📁web
                            └── 📁controller
                            └── 📁dto
                    └── 📁common
                        └── 📁base
                        └── 📁config
                            └── 📁properties
                        └── 📁controller
                        └── 📁enums
                        └── 📁fragranceInit
                        └── 📁util
                        └── 📁validation
                            └── 📁annotation
                            └── 📁validator
                    └── 📁diary
                        └── 📁converter
                        └── 📁domain
                        └── 📁repository
                        └── 📁service
                        └── 📁web
                            └── 📁controller
                            └── 📁dto
                    └── 📁external
                        └── 📁fastapi
                            └── 📁dto
                        └── 📁openai
                    └── 📁fragrance
                        └── 📁converter
                        └── 📁domain
                            └── 📁mapping
                        └── 📁repository
                            └── 📁fragranceBaseNote
                            └── 📁fragranceLocation
                            └── 📁fragranceMiddleNote
                            └── 📁fragrancePrice
                            └── 📁fragranceSeason
                            └── 📁fragranceTopNote
                            └── 📁location
                            └── 📁note
                            └── 📁price
                            └── 📁season
                        └── 📁service
                        └── 📁validation
                            └── 📁annotation
                            └── 📁validator
                        └── 📁web
                            └── 📁controller
                            └── 📁dto
                    └── 📁imagekeyword
                        └── 📁converter
                        └── 📁domain
                        └── 📁redis
                        └── 📁repository
                            └── 📁imagekeyworddescription
                        └── 📁service
                        └── 📁util
                        └── 📁validation
                            └── 📁annotation
                            └── 📁validator
                        └── 📁web
                            └── 📁controller
                            └── 📁docs
                            └── 📁dto
                    └── 📁pbti
                        └── 📁converter
                        └── 📁domain
                        └── 📁repository
                        └── 📁service
                        └── 📁web
                            └── 📁controller
                            └── 📁dto
                    └── 📁s3file
                        └── 📁aws
                        └── 📁converter
                        └── 📁web
                            └── 📁controller
                            └── 📁dto
                    └── 📁security
                        └── 📁auth
                            └── 📁controller
                            └── 📁converter
                            └── 📁dto
                            └── 📁filter
                            └── 📁handler
                            └── 📁manager
                            └── 📁provider
                            └── 📁service
                            └── 📁token
                            └── 📁userDetails
                        └── 📁oauth
                            └── 📁controller
                            └── 📁converter
                            └── 📁dto
                            └── 📁service
                            └── 📁util
                    └── 📁user
                        └── 📁converter
                        └── 📁domain
                            └── 📁mapping
                        └── 📁repository
                            └── 📁userFragrance
                            └── 📁userNote
                        └── 📁service
                        └── 📁validation
                            └── 📁annotation
                            └── 📁validator
                        └── 📁web
                            └── 📁controller
                            └── 📁dto
                    └── 📁uuid
                        └── 📁domain
                        └── 📁repository
                    └── 📁workshop
                        └── 📁converter
                        └── 📁domain
                        └── 📁redis
                        └── 📁repository
                        └── 📁service
                        └── 📁validation
                            └── 📁annotation
                            └── 📁validator
                        └── 📁web
                            └── 📁controller
                            └── 📁docs
                            └── 📁dto
        └── 📁resources
            └── 📁data
            └── 📁prompts
    └── 📁test
        └── 📁java
            └── 📁PerfumeOnMe
                └── 📁spring
```

## 📅 Roadmap

- [ ] 향수 데이터 추가
- [ ] 퍼퓸다이어리 공유 기능
- [ ] 마이페이지 일부 기능 추가
- [ ] 향수 추천 알고리즘 고도화
- [ ] 모바일 앱 버전 출시

--- 

## 👥 팀원 정보

| 이름  | 역할      | GitHub                                               |
|-----|---------|------------------------------------------------------|
| 김은지 | Backend | [@hcg0127](https://github.com/hcg0127)               |
| 김찬우 | Backend | [@chanudevelop](https://github.com/chanudevelop)     |
| 이병웅 | Backend | [@bulee5328](https://github.com/bulee5328)           |
| 이원희 | Backend | [@leewonhee-3054](https://github.com/leewonhee-3054) |

--- 

## 📬 연락처

인스타그램: perfu_on_me

--- 

