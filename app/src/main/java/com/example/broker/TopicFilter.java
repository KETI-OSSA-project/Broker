package com.example.broker;

/**
 * MQTT 5.0 4.7절 토픽 필터 규칙 (절차서 18번).
 * '+'는 한 단계, '#'은 그 단계와 하위 전체. '+', '#'으로 시작하는 필터는 '$' 토픽에 맞지 않는다.
 */
final class TopicFilter {

    private TopicFilter() {
    }

    /** '#'은 마지막 단계에 혼자만, '+'는 한 단계에 혼자만 올 수 있다 (예: a/#/b, a+ 는 오류) */
    static boolean isValidFilter(String filter) {
        if (filter == null || filter.isEmpty()) return false;
        String[] levels = filter.split("/", -1);
        for (int i = 0; i < levels.length; i++) {
            String level = levels[i];
            if (level.contains("#") && (!level.equals("#") || i != levels.length - 1)) return false;
            if (level.contains("+") && !level.equals("+")) return false;
        }
        return true;
    }

    /** 발행 토픽에는 와일드카드를 쓸 수 없다 */
    static boolean isValidTopic(String topic) {
        return topic != null && !topic.isEmpty() && topic.indexOf('+') < 0 && topic.indexOf('#') < 0;
    }

    static boolean matches(String filter, String topic) {
        if (topic.startsWith("$") && (filter.startsWith("+") || filter.startsWith("#"))) return false;
        String[] f = filter.split("/", -1);
        String[] t = topic.split("/", -1);
        for (int i = 0; i < f.length; i++) {
            if (f[i].equals("#")) return true;          // sport/# 는 sport 자신도 포함
            if (i >= t.length) return false;
            if (!f[i].equals("+") && !f[i].equals(t[i])) return false;
        }
        return f.length == t.length;
    }
}
