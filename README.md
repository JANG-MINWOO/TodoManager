# 일정관리 애플리케이션

## 개요
이 프로젝트는 사용자들이 회원가입을 통해 일정 관리를 할 수 있는 애플리케이션입니다. 사용자들은 일정을 생성, 수정, 삭제할 수 있으며, 댓글을 남기고 관리할 수 있습니다. 또한, 도전과제 기능을 통해 사용자에게 추가적인 기능을 제공합니다.

## 주요 기능
### 1. 회원 관리 기능
- **회원가입**: 사용자 이름, 이메일, 비밀번호로 회원가입 가능
- **로그인**: 가입한 사용자만 로그인 가능
- **회원정보 수정**: 사용자는 자신의 정보를 수정할 수 있음
- **회원 탈퇴**: 사용자는 자신의 계정을 삭제할 수 있음

### 2. 일정 관리 기능
- **일정 생성**: 사용자는 일정을 생성할 수 있음
- **일정 수정**: 생성한 일정을 수정 가능
- **일정 삭제**: 생성한 일정을 삭제 가능
- **일정 검색**: 일정 제목이나 작성자로 검색 가능

### 3. 댓글 관리 기능
- **댓글 작성**: 일정에 댓글을 남길 수 있음
- **댓글 수정 및 삭제**: 작성한 댓글만 수정 및 삭제 가능

### 4. 도전과제 기능
- **어드민 회원가입**: 특정 관리자는 관리자 계정으로 회원가입 가능
- **특정 일정에 대한 관리자 권한 부여**: 어드민 권한으로 추가적인 작업 가능

## 기술 스택
- **Backend**: Spring Boot, Spring Data JPA
- **Database**: MySQL
- **Version Control**: Git

## 설치 및 실행 방법

### 1. 프로젝트 클론
```bash
git clone https://github.com/JANG-MINWOO/TodoManager.git
cd project-name
```

### 2. 데이터베이스 설정

```yaml
spring.application.name=Todo
spring.datasource.url=jdbc:mysql://localhost:3306/todo
spring.datasource.username=yourroot
spring.datasource.password=yourpassword
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

### 3. 애플리케이션 접속
Postman 에서 http://localhost:8080/api 를 기본으로 각 기능을 사용합니다.

## API 명세서

| 메소드    | 엔드포인트                                                       | 설명                      | 요청 바디                                                                                                                                   | 응답 바디                                                                                                                                                      |
|-----------|------------------------------------------------------------------|---------------------------|--------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------|
| POST      | `/api/members/signup`                                            | 회원가입 도전              | `{"username": "user", "email": "email@example.com", "password": "pass", "admin": true, "adminToken": "ADMIN_TOKEN"}`                       | `회원가입이 완료되었습니다.`                                                                                                                                     |
| POST      | `/api/members/register`                                          | 회원가입                   | `{"username": "user", "email": "email@example.com", "password": "pass"}`                                                                    | `회원가입이 완료되었습니다.`                                                                                                                                     |
| POST      | `/api/members/login`                                             | 로그인                      | `{"email": "user@email.com", "password": "pass"}`                                                                                           | JWT 토큰                                                                                                                                                       |
| PUT       | `/api/members/{memberId}`                                        | 회원 정보 수정              | `{"username": "user", "email": "email@example.com", "password": "pass"}`                                                                    | `{"id": 1, "username": "장유저", "email": "user@example.com", "createdAt": "2024-10-16T10:10:00.000000", "updatedAt": "2024-10-16T10:10:00.000000"}`           |
| DELETE    | `/api/members/{id}`                                              | 회원 탈퇴                   | N/A                                                                                                                                        | N/A                                                                                                                                                            |
| POST      | `/api/todo?memberId={memberId}`                                  | 일정 생성                   | `{"memberId": "memberId", "title": "Meeting", "password": "pass", "description": "Project meeting", "updatedAt": "2024-10-15T14:30:00"}`   | 생성된 일정 데이터                                                                                                                                             |
| PUT       | `/api/todo/{id}`                                                 | 선택 일정 수정              | `{"memberId": "memberId", "title": "Updated Meeting", "description": "Updated project meeting", "updatedAt": "2024-10-15T14:30:00"}`       | 수정된 일정 데이터                                                                                                                                             |
| DELETE    | `/api/todo/{id}`                                                 | 선택 일정 삭제              | `{"memberId": "memberId"}`                                                                                                                  | 페이지당 10개의 일정 데이터                                                                                                                                     |
| GET       | `/api/todo?page=0&size=10`                                       | 전체 일정 조회              | N/A                                                                                                                                        | 일정 데이터 (페이지당 10개)                                                                                                                                   |
| POST      | `/api/todo/comments`                                             | 댓글 작성                   | `{"content": "댓글 작성", "todoId": "1", "memberId": "1", "createdAt": "2024-10-15T14:30:00", "updatedAt": "2024-10-15T14:30:00"}`          | 작성된 댓글 데이터                                                                                                                                             |
| GET       | `/api/todo/comments/{todoId}`                                    | 댓글 불러오기               | N/A                                                                                                                                        | 특정 일정에 작성된 댓글 데이터                                                                                                                                 |
| PUT       | `/api/todo/comments/update/{commentId}`                          | 댓글 수정                   | `{"content": "1번째 일정에 댓글 수정", "memberId": "1", "createdAt": "2024-10-15T14:30:00", "updatedAt": "2024-10-15T14:30:00"}`            | 수정된 댓글 데이터                                                                                                                                             |
| DELETE    | `/api/todo/comments/delete/{commentId}?memberId={memberId}`       | 댓글 삭제                   | Query Params Key: memberId, Value: {memberId}                                                                                               | `댓글이 삭제되었습니다.`                                                                                                                                         |
<img src="https://github.com/user-attachments/assets/0871a08b-7d11-41d6-b968-b78ec3e4c6a4">
https://github.com/user-attachments/assets/0871a08b-7d11-41d6-b968-b78ec3e4c6a4
