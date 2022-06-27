package com.sample.words;

import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;

@Service
public class WordCounter {

  private final Predicate<String> isNotAlphabetic = (word) -> !word.matches("^[A-Za-z]+$");

  private final WordStore storage;
  private final Translator translator;

  public WordCounter(WordStore storage, Translator translator) {
    this.storage = storage;
    this.translator = translator;
  }

  public void addWords(String... words) {
    checkAlphabetic(words);
    Stream.of(words).map(this::normalizeToEnglish).forEach(word -> storage.save(word));
  }

  private void checkAlphabetic(String[] words) {
    String result = Stream.of(words).filter(isNotAlphabetic).collect(Collectors.joining(","));
    if (!result.isEmpty()) {
      throw new IllegalArgumentException(
          String.format("Cannot add words [%s] , non alphabetic character found", result));
    }
  }

  private String normalizeToEnglish(String word) {
    return translator.translate(word);
  }

  public Integer getCount(String word) {
    return storage.getCount(word);
  }
}
