# Broker_java
---

## Azena Messaging Broker

**개요**

Broker 모듈은 **Azena 메시징 생태계의 중심 허브**로, Publisher가 발행한 메시지를 토픽 기반으로 관리하고, 구독 중인 Subscriber에게 라우팅합니다.
또한 Gateway와 연동하여, 내부 메시지를 외부 MQTT Broker(Mosquitto)로 전달하거나 외부 메시지를 내부로 주입할 수 있습니다.

---

## 디렉토리 구조

```
broker_java/
└─ app/
   └─ java/com/example/broker/
      ├─ Filter.java           # 토픽 패턴 필터링 (허용/차단 규칙 적용)
      ├─ MainActivity.java     # 안드로이드 UI 진입점 (브로커 실행/상태 표시)
      ├─ Message.java          # 메시지 데이터 구조 (topic, payload 등)
      └─ MessagingService.java # 브로커 핵심 로직 (발행, 구독, 라우팅 처리)
```

---

## 주요 기능

* **발행(Publish) 관리**: Publisher의 메시지를 수신하여 저장
* **구독(Subscribe) 관리**: Subscriber 등록/해제, 토픽 기반 구독 관리
* **메시지 라우팅**: 구독자에게 토픽별 메시지 전달
* **토픽 패턴 필터링** (`Filter.java`)
* **브로커 서비스 실행/제어** (`MainActivity` + `MessagingService`)
* **Gateway 연동 지원**: 외부 MQTT Broker와 내부 메시지 교환

---

## 실행 환경

* **Android Studio Koala (2024.1.1 Patch 1)**
* **Android 12 이하 (SDK/API Level ≤ 32)**
* Publisher, Subscriber 실행 시 Broker가 반드시 선행 실행되어야 함
* 외부 연동 시 Gateway + Mosquitto 필요

---

## 빌드 & 실행

1. Android Studio에서 `broker_java` 열기
2. `MainActivity` 실행 → Broker UI 실행
3. 내부 Publisher/Subscriber 연결 상태 확인

---

## 사용 예시

### 내부 메시지 라우팅

1. Subscriber → `azena/test` 토픽 구독
2. Publisher → 동일 토픽(`azena/test`)으로 메시지 발행
3. Broker(`MessagingService`) → 메시지를 구독자에게 전달

### Gateway 연동

1. 외부 MQTT Publisher → `downlink/device1/data` 발행
2. Gateway → Broker에 메시지 전달
3. Broker → 내부 Subscriber에 메시지 라우팅

---

## 동작 규칙

* 구독 이전에 발행된 메시지는 수신되지 않음
* 구독 해제된 토픽은 더 이상 메시지를 받지 않음
* 동일 토픽을 여러 Subscriber가 구독하면 모두 수신
* 동일 토픽 중복 구독은 1회만 수신 처리
* Gateway Uplink/Downlink 규칙과 일치해야 외부 메시지가 교환됨

---

## 빠른 점검 (Troubleshooting)

* **메시지가 안 전달됨**:

  * Subscriber가 발행 전 구독했는지 확인
  * 토픽 문자열 정확성 확인
* **외부 메시지 미수신**: Gateway Downlink 규칙과 일치하는지 확인
* **앱 실행 안 됨**: Android 권한(네트워크) 및 API Level 확인
