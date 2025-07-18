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
│               │   ├── MqttService.java    # java로 리팩토링 하고 있는 부분
│               │   └── ServerInstance.java
│               ├── util/
│               │   ├── AppPreferences.java
│               │   ├── NetworkCallBack.java
│               │   └── Utils.java
│               └── app/                    # 필요시 App 클래스 등
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
