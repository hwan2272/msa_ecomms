# msa_ecomms
마이크로서비스 방식의 ecommerce형 backend

패키지명 공통 : com.hwan2272.msaecomms.xxx



### 구성방향

Microservice + Spring Cloud + SpringBoot + JWT + Spring Security + Spring Data jpa + H2 Database + Kafka +

(AOP + Spring Rest docs + Docker + Kubernetes + AWS EC2)



### 서버구동

1. discovery-service 구동 : 포트 8761 - eureka 서버 - 127.0.0.1:8761 접속

2. gateway-service 구동 : 포트 8000 - 각 api로 연결되는 gateway 서비스

3. user-service 및 기타 service 구동 : 포트 랜덤


![spring cloud eureka 구동](https://user-images.githubusercontent.com/65170244/166868526-197915d1-18d2-49bd-8510-7b93dc885527.jpg)



### 참고 강의 / 소스

### 인프런

- 이도원님 강의 및 소스(https://github.com/joneconsulting/msa_with_spring_cloud) 기반 환경 구성

- 김영한님 강의 기반 Data JPA 구성 (및 일부 중복로직 등에 대한 적절한 디자인패턴 적용구상중)

- 기타 독학 부분
