package com.sample.words;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WordStore {

  private final Map<String, Integer> words = new ConcurrentHashMap<>();

  public synchronized void save(String word) {
    log.debug("saving word [{}]", word);
    words.computeIfPresent(word, (key, value) -> value + 1);
    words.putIfAbsent(word, 1);
  }

  public Integer getCount(String word) {
    return words.getOrDefault(word, 0);
  }
}
