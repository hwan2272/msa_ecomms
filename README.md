# msa_ecomms
마이크로서비스 방식의 ecommerce형 backend

패키지명 공통 : com.hwan2272.msaecomms.xxx



### 구성 라이브러리

Microservice(구성개념)

Spring Cloud + SpringBoot + Spring Security + JWT + Spring Data jpa + H2 Database + RabbitMQ + Kafka +

(AOP + Spring Rest docs + Docker + Kubernetes + AWS EC2)



### 서버 구동

1. discovery-service 구동 : 포트 8761 - eureka 서버 - 127.0.0.1:8761 접속
2. gateway-service 구동 : 포트 8000 - 각 api로 연결되는 gateway 서비스
3. config-service 구동 : 포트 8888 / RabbitMQ 구동 : (자동구동), 포트 5672
4. user-service 및 기타 service 구동 : 포트 랜덤

config-service 구동이 user-service등보다 늦었을 경우, busrefresh사용하여 config 재적용


![spring cloud eureka 구동](https://user-images.githubusercontent.com/65170244/166868526-197915d1-18d2-49bd-8510-7b93dc885527.jpg)



### Endpoint 목록

1. POST localhost:8000/users/users - 사용자 가입 (users insert)
2. POST localhost:8000/users/login - 로그인 (JWT발급) 
3. POST localhost:8000/users/actuator/busrefresh - 전모듈 config 정보 refresh (재가동 없음)
4. GET localhost:8000/users/users/{userId} - 사용자 정보 조회
5. POST localhost:8000/orders/{userId} - 주문하기 (orders insert)
6. GET localhost:8000/orders/{userId}/orders - 사용자의 주문 정보 조회
7. GET localhost:8000/product/{productId} - 상품 정보 조회

4~7 에는 Authorization header 필요 (Bearer JWT)



### 참고 강의 / 소스

### 인프런

- 이도원님 강의 및 소스(https://github.com/joneconsulting/msa_with_spring_cloud) 기반 환경 구성
- 김영한님 강의 기반 Data JPA 구성 (및 일부 중복로직 등에 대한 적절한 디자인패턴 적용구상중)
- 기타 독학 부분
