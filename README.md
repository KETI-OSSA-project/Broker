# Broker_java

## 파일 구조도 (예상)
```
app/
├── manifests/
│   └── AndroidManifest.xml       
├── java/
│   └── com/
│       └── example/
│           └── broker/                   
│               ├── MainActivity.java
│               ├── service/
│               │   ├── MQTTBroker.java     # java로 리팩토링 하고 있는 부분
│               │   └── ServerInstance.java
│               ├── util/
│               │   └── Utils.java
│               └── app/                    # 필요시 App 클래스 등
│
│       └── securityandsafetythings/
│           └── messagebroker/              # java로 구현된 표준 api (messagebroker)
│               ├── accesscontrol/
│               ├── applicationstitching/
│               ├── ports/
│               ├── MessagingManager.java
│               ├── Message.java
│               └── ...
│
│       └── com.example.broker (androidTest)
│       └── com.example.broker (test)

res/
├── drawable/
├── layout/
├── mipmap/
├── values/
├── xml/

Gradle Scripts/
```
