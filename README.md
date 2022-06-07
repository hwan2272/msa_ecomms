# msa_ecomms
마이크로서비스 방식의 ecommerce형 backend

패키지명 공통 : com.hwan2272.msaecomms.xxx



### 구성 라이브러리

Microservice(구성개념)

Spring Cloud + SpringBoot + Spring Security + JWT + Spring Data jpa + 

H2 Database||MariaDB + RabbitMQ + Kafka(Zookeeper, Kafka Server/Producer/Consumer, Kafka Connect, Kafka Sink) +

CircuitBreaker(Resilience4J) + Sleuth + Zipkin +

Prometheus + Grafana +

Docker + AWS EC2 +

(Spring Rest docs + Kubernetes)



### 서버 구동

1. discovery-service 구동 : 포트 8761 - eureka 서버 - 127.0.0.1:8761 접속
2. gateway-service 구동 : 포트 8000 - 각 api로 연결되는 gateway 서비스
3. config-service 구동 : 포트 8888 / RabbitMQ 구동 : (자동구동), 포트 5672
4. user-service 및 기타 service 구동 : 포트 랜덤

config-service 구동이 user-service등보다 늦었을 경우, busrefresh사용하여 config 재적용


![spring cloud eureka 구동](https://user-images.githubusercontent.com/65170244/166868526-197915d1-18d2-49bd-8510-7b93dc885527.jpg)


### Kafka 구동

Window 환경 - Window PowerShell 실행
1. zookeeper 구동 - .\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
2. Kafka server 구동 - .\bin\windows\kafka-server-start.bat .\config\server.properties

--테스트시--

3. Kafka producer 구동 및 토픽설정 - .\bin\windows\kafka-console-producer.bat --broker-list localhost:9092 --topic quickstart-events (토픽명 임의 지정)
4. Kafka consumer 구동 및 토픽설정 - .\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic quickstart-events (토픽명 임의 지정)
5. producer에서 message 입력 -> consumer에서 표시되는지 확인


![kafka구동](https://user-images.githubusercontent.com/65170244/168455543-bf4bbd25-8a2a-4e72-b6e7-09afed52eae9.png)

### Kafka Connect 구동

Window 환경 - Window PowerShell 실행
1. Kafka connect(distributed) 구동 - .\bin\windows\connect-distributed.bat .\etc\kafka\connect-distributed.properties
2. sink connect 생성 - POST localhost:8083/connectors (body내용-orders테이블용)
{
    "name": "my-sink-topic-orders-connect",
    "config": {
        "connector.class": "io.confluent.connect.jdbc.JdbcSinkConnector",
        "connection.url": "jdbc:mysql://localhost:3306/mydb",
        "connection.user": "root",
        "connection.password": "test1357",
        "key.converter": "org.apache.kafka.connect.json.JsonConverter",
        "value.converter": "org.apache.kafka.connect.json.JsonConverter",
        "key.converter.schemas.enable": true,
        "value.converter.schemas.enable": true,
        "auto.create": "true",
        "auto.evolve": "true",
        "delete.enabled": "false",
        "tasks.max": "1",
        "topics": "orders"
    }
}
3. connectors status 확인 - http://localhost:8083/connectors?expand=info&expand=status

적용시 2개 orderService에서 Kafka connect를 바라보며 POST orders호출시 connect에 담겨진 메세지가 MariaDB로 이관 insert됨


### Zipkin 서버 구동

1. docker pull openzipkin/zipkin
2. docker run -d -p 9411:9411 openzipkin/zipkin

- localhost:9411/zipkin

![zipkin구동_20220519](https://user-images.githubusercontent.com/65170244/169271404-120e8026-d7aa-4758-a3fa-ede75816b81b.jpg)




### Endpoint 목록

1. POST localhost:8000/users/users - 사용자 가입 (users insert)
2. POST localhost:8000/users/login - 로그인 (JWT발급) 
3. POST localhost:8000/users/actuator/busrefresh - 전모듈 config 정보 refresh (재가동 없음)
4. GET localhost:8000/users/users/{userId} - 사용자 정보 조회
5. POST localhost:8000/orders/{userId} - 주문하기 (orders insert)
6. GET localhost:8000/orders/{userId}/orders - 사용자의 주문 정보 조회
7. GET localhost:8000/product/{productId} - 상품 정보 조회

4~7 에는 Authorization header 필요 (Bearer JWT)


![KakaoTalk_20220513_095306553_3](https://user-images.githubusercontent.com/65170244/168190789-94f904f5-b16d-4ed0-904f-8841859de68a.jpg)


추가 EndPoint (kafka connect)

8. GET localhost:8083/connectors/{connectorName}
9. GET localhost:8083/connectors?expand=info&expand=status
10. POST localhost:8083/connectors
11. DELETE localhost:8083/connectors/{connectorName}

... 등등


### Docker (user-service)
1. docker build --tag hwan2272/user-service:1.0 .
2. docker push hwan2272/user-service:1.0 => https://hub.docker.com/repository/docker/hwan2272/user-service
3. docker pull hwan2272/user-service:1.0


AWS EC2 배포, Docker 구동 (예시)

- https://hub.docker.com/repository/docker/hwan2272/discovery-service
- https://hub.docker.com/repository/docker/hwan2272/gateway-service
- https://hub.docker.com/repository/docker/hwan2272/config-service

![AWS_EC2_Docker구동_20220517](https://user-images.githubusercontent.com/65170244/168740312-060800b9-3ca9-4a9e-98ee-fcac7b6fb5bd.jpg)


### 최종 배포 형상 (AWS EC2) - 2022-05-20

- http://ec2-3-34-214-154.ap-northeast-2.compute.amazonaws.com:8761/

![AWS_EC2_Docker구동_전체배포완료_20220520](https://user-images.githubusercontent.com/65170244/169505167-483591c3-2e03-4d5e-9312-d7609c533257.jpg)

![AWS_EC2_Spring Cloud Eureka_20220520](https://user-images.githubusercontent.com/65170244/169506462-4ee252b0-1f2e-4b2e-b17e-c25997cff3fa.jpg)



### 참고 강의 / 소스

### 인프런

- 이도원님 강의 (https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81-%ED%81%B4%EB%9D%BC%EC%9A%B0%EB%93%9C-%EB%A7%88%EC%9D%B4%ED%81%AC%EB%A1%9C%EC%84%9C%EB%B9%84%EC%8A%A4) 및 소스(https://github.com/joneconsulting/msa_with_spring_cloud) 기반 환경 구성
- 김영한님 강의 (https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81-%EB%8D%B0%EC%9D%B4%ED%84%B0-JPA-%EC%8B%A4%EC%A0%84) 기반 Data JPA 구성
- 기타 독학 부분


## 자체 시도/개선/커스터마이즈

- 강의에서는 
1. Data JPA를 사용하지 않았지만 본 소스에서는 Data JPA를 적용하고, 이에 대한 보충작업등을 추가
2. @Test를 사용하지 않았으나 본 소스에서는 사용, entity 정도는 test해보는 로직 추가
3. gatewayService의 AuthorizationHeaderFilter에서 JWT를 검증부분이 미흡하여 본 소스에서는 userFeignClient도입하였으나 실패, 다른방법 구상중
4. local PC에 Docker별 배포로 구축하고 끝났지만 본 소스에서는 AWS EC2를 사용하여 배포해봄
5. java docs 등도 없지만 본 소스에서는 Spring Rest Docs 적용 (진행중 - productService)

![ProductService Spring Rest Docs 진행중_20220607](https://user-images.githubusercontent.com/65170244/172313379-83f1cba1-deb8-48f8-bf37-c21b3946a6f7.jpg)

![ProductService Spring Rest Docs 진행중_20220607_adoc생성](https://user-images.githubusercontent.com/65170244/172314527-484c3b86-06fc-4913-b4d5-a7e8c9ef6fa5.jpg)

- 소스 리팩토링 - orderService KafkaConnectOrderDto V1- 예제상 4개 클래스 통합화, V2- schema, payload 세팅 소스간략화 시도
=> 성공, 커뮤니티 공유 (https://www.inflearn.com/chats/541160)

- MSA방식이라 모듈별로 나누어져있는 Entity들의 올바른 관리방법 구상중.. 
- (본 소스에서는 Spring Cloud의 FeignClient개념으로 MSA간 통신으로 참조문제가 해결되었지만 
- 타 MSA 등의 Spring Cloud가 아닐(참조할수 없는) 경우의 표준or올바른 관리 방법은?)


### TODO

- AWS S3, CloudFront 를 이용한 Frontend단(resources포함) 처리로 인프라 확장
- AWS RDS 를 이용한 DB 서버 관리 확장
- 

