# Broker
---

## Azena Messaging Broker
<p align="center">
  <img src="https://github.com/user-attachments/assets/d15b5f1f-2052-4b2e-b914-270107ee7431" alt="<프로젝트명> Banner" width="70%" />
</p>

<p align="center">
  <b>Validate and integrate MQTT messaging services and brokers with a unified tool.</b>
</p>

<div align="center">

  <!-- 윗줄: Android 관련 2개 -->
  <img src="https://img.shields.io/badge/Android_Studio-Koala-3DDC84?logo=androidstudio&logoColor=white" />
  <img src="https://img.shields.io/badge/Android-12_(API_32)-3DDC84?logo=android&logoColor=white" />
  <!-- 아랫줄: 나머지 3개 -->
  <img src="https://img.shields.io/badge/Java-11-007396?logo=openjdk&logoColor=white" />
  <img src="https://img.shields.io/badge/Mosquitto-2.0.22-3C5280?logo=eclipseide&logoColor=white" />

</div>

## 개요

Broker 모듈은 **Azena 메시징 생태계의 중심 허브**로, Publisher가 발행한 메시지를 토픽 기반으로 관리하고, 구독 중인 Subscriber에게 라우팅합니다.
또한 Gateway와 연동하여, 내부 메시지를 외부 MQTT Broker(Mosquitto)로 전달하거나 외부 메시지를 내부로 주입할 수 있습니다.

---

## Demo
### 내부 ↔ 내부 통신
<p align="center">
  <img src="https://github.com/user-attachments/assets/5fda5904-9d37-4324-8dae-87ddd36bc08a" width="40%" />
</p>

### 내부 ↔ 외부 통신
<table>
<tr>
    <td><img src="https://github.com/user-attachments/assets/3372b3b6-22df-4a4c-b832-d55950039a4c" alt="uplink-broker"></td>
    <td><img src="https://github.com/user-attachments/assets/e029e60a-968f-4e0d-9743-4cc5d612e071" alt="downlink-subscriber"></td>
</tr>
</table>
<p align="center">
  <a href="https://drive.google.com/file/d/1_TukQ0CwD708QUjqD8ZCfM2t8XZdoEBp/view?usp=drive_link">
    <b>📽️ MQTT Demo</b>
  </a>
</p>

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

## Acknowledgement
**개발 및 유지 관리 기관** : 한국전자기술연구원(KETI)

※ 본 연구는 2025년도 정부(과학기술정보통신부)의 재원으로 정보통신기획평가원의 지원을 받아 수행된 연구임(No. RS-2024-00394190, 온-디바이스 자율보호가 내재화된 개방형 영상보안플랫폼 기술 개발).
