# 자산 관리 시스템 (Asset Management)

Spring Boot + Thymeleaf 기반의 자산 관리 웹 애플리케이션입니다.

---

## 기술 스택

- Java 17
- Spring Boot 3.5.11
- Gradle
- Spring Security
- Spring Data JPA + QueryDSL
- Thymeleaf
- H2 Database (In-Memory)
- MapStruct, Lombok

---

## 실행 방법

### 1. 프로젝트 클론
```bash
git clone https://github.com/jeonmoo/asset-management.git
cd assetManagement
```

### 2. 빌드
```bash
./gradlew clean build
```

### 3. 실행
```bash
./gradlew bootRun
```

또는 빌드된 JAR 파일 실행:
```bash
java -jar build/libs/assetManagement-0.0.1-SNAPSHOT.jar
```

### 4. 접속

- 애플리케이션: [http://localhost:8080](http://localhost:8080)
- H2 콘솔: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
    - JDBC URL: `jdbc:h2:mem:assetdb`
    - Username: `sa`
    - Password: _(없음)_

---

## 기본 계정

| 구분 | 값 |
|------|----|
| 아이디 | `admin@company.com` |
| 비밀번호 | `admin1234` |

---

## 초기 시드 데이터 쿼리 위치
```bash
src/main/resources/data.sql
```

---

## 제약 사항

- **Java 17 이상** 필수
- **H2 인메모리 DB** 사용으로 애플리케이션 종료 시 모든 데이터가 초기화됩니다.
- `ddl-auto: create-drop` 설정으로 실행 시마다 스키마가 재생성됩니다.
- 초기 더미 데이터는 `data.sql`을 통해 자동 삽입됩니다. (`sql.init.mode: always`)
- 외부 DB 연동 없이 로컬 환경에서만 동작하도록 구성되어 있습니다.

---

## 📸 화면 구성

### 대시보드
![dashboard](docs/images/dashboard.png)

### 자산 목록
![assetList](docs/images/assetList.png)

### 자산 상세
![assetDetail](docs/images/assetDetail.png)

### 자산 등록
![assetRegist](docs/images/assetRegist.png)

### 자산 수정
![assetModify](docs/images/assetModify.png)

### 자산 할당
![assetAssign](docs/images/assetAssign.png)