# 블로그 검색 프로젝트

### 프로젝트 주요 아키텍처
- 헥사고날 아키텍처를 모방하여 in / out going 구성
  - 도메인은 도메인의 비즈니스 로직에만 집중
  - 페이징 처리 등을 도메인에서 하고있는데, 개발적 편의를 위한 합의
  - ![blogsearch.drawio.png](readmeimage%2Fblogsearch.drawio.png)
- 트래픽과 동시성 이슈를 고려한 아키텍처
  - 애플리케이션에서 조회시 remote cache (redis)
  - core domain 에서 키워드 검색이 발생될 시에
  - 검색하는 함수를 pointcut 있던 aspect 에서 proxy weaving
  - 검색 키워드를 event 발행
  - 비동기처리를 위한 event listener 에서 keyword insert (only insert)
    - blog_search_keyword (순수 데이터 저장용)
  - batch 에서 scheduler 에 의하여 count, update
    - property_keyword (인기 검색어 조회용)
  - batch 에서 job 이 끝나면 remote cache evict


### API 명세
- swagger : http://localhost:8080/swagger-ui/index.html
- | api               | method/URL                | parameter                    | response                                                          |
  |-------------------|---------------------------|------------------------------|-------------------------------------------------------------------|
  | 임의 batch 실행(admin)| POST /batch               |                              |                                                                   |
  | 블로그 검색            | GET /blog                 | keyword, sorting, page, size | Page.contents blogname, title, contents, url, datetime, thumbnail |
  | 인기 검색어 조회        | GET /blog/popular-keyword |                              | List keyword, count                                               |
  | 저장된 검색어 조회(admin)| GET /admin/blog           | page, size                   | Page.contents id, keyword, createdDtm, processedDtm               |
 

### 멀티 모듈 및 디펜던시 리스트 (오픈 소스)
- core
  - org.springframework.boot:spring-boot-starter-data-jpa => jpa
  - com.fasterxml.jackson.module:jackson-module-kotlin => jackson kotlin module
  - org.jetbrains.kotlin:kotlin-reflect => for kotlin reflection
  - org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0 => swagger, api document
  - org.springframework.cloud:spring-cloud-starter-openfeign => open feign
  - io.mockk:mockk:1.13.8 => test (mock)
  - org.springframework.boot:spring-boot-starter-test => test
  - org.jetbrains.kotlin:kotlin-test-junit:1.9.0 => test (kotlin test)
  - org.springframework.cloud:spring-cloud-dependencies => open feign

- infra
  - core
  - com.h2database:h2 => embedded DB
  - it.ozimov:embedded-redis:0.7.13 => embedded redis
  - org.springframework.boot:spring-boot-starter-data-redis => spring redis
  - org.springframework.boot:spring-boot-starter-cache => spring cache

- api
  - core
  - infra
  - batch
  - org.springframework.boot:spring-boot-starter-web => web
  - org.springframework.boot:spring-boot-starter-validation => controller parameter validation

- batch
  - core
  - infra
  - org.springframework.boot:spring-boot-starter-batch => spring batch

### 개선이 필요한 점
- batch 가 완성되지 않아 기능 구현의 아쉬움
- 로직상 분기가 있을 부분만 커버하는 테스트 코드
- 외부 api 사용에 대한 circuit breaker 미구현