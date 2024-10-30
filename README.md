# Board Project

## 기술 스택

### 백엔드
- **Spring Boot**
- **Spring Security**
- **JPA**
- **AWS RDS (MySQL)**
- **Docker**
- **Redis**
- **Elasticsearch**

### 프론트엔드
- **HTML / CSS / JavaScript**

## 주요 기능
- **회원**
  - 회원가입 및 로그인 

- **게시글**
  - 게시글 작성, 조회, 수정, 삭제
  - 조회수 관리
  - 제목, 내용으로 게시글 검색

- **댓글**
  - 댓글 작성 및 조회

## 주요 패키지 설명
- `comment`: 댓글 관련 
- `config`: 설정 관련
- `member`: 회원 가입 및 로그인 관련
- `post`: 게시글 관련

## REST API 명세서

### Post API
| 메소드 | 엔드포인트 | 설명         |
| --- | --- |------------|
| POST | `/api/post` | 새 게시글 생성   |
| GET | `/api/post/{id}` | ID로 게시글 조회 |
| PUT | `/api/post/{id}` | ID로 게시글 수정 |
| DELETE | `/api/post/{id}` | ID로 게시글 삭제 |

### Search API
| 메소드 | 엔드포인트        | 설명             |
| --- |--------------|----------------|
| GET | `/api/posts` | 제목,내용으로 게시글 검색 |

### Member API
| 메소드 | 엔드포인트 | 설명 |
| --- | --- | --- |
| POST | `/member` | 새 회원 추가 |

### Comment API
| 메소드 | 엔드포인트 | 설명            |
| --- | --- |---------------|
| POST | `/api/comment` | 새 댓글 생성       |
| GET | `/api/comment/{postId}` | 특정 게시글의 댓글 조회 |

## MySQL 데이터베이스 구조

![](src/main/resources/static/images/DB.jpg)


