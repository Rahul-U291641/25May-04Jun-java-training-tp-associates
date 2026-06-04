package com.ust.bankingKafka.rerty;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ManualRetryHandler {
    private final Map<String, Integer> retryCountMap = new ConcurrentHashMap<>();

    public int incrementRetryCount(String txnId) {
        int count = retryCountMap.getOrDefault(txnId, 0);
        count++;
        retryCountMap.put(txnId, count);
        return count;
    }

    public void clearRetry(String txnId) {
        retryCountMap.remove(txnId);
    }
}
