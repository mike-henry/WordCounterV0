package com.sample.words;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/words")
public class WordCountController {

  private final WordCounter wordCounter;

  public WordCountController(WordCounter wordCounter) {
    this.wordCounter = wordCounter;
  }

  @PutMapping("/add-word")
  @ResponseStatus(HttpStatus.OK)
  public void addWord(@RequestBody List<String> words) {
    this.wordCounter.addWords(words.toArray(new String[] {}));
  }

  @GetMapping("/get-count/{word}")
  public Integer getCount(@PathVariable("word") String word) {
    return wordCounter.getCount(word);
  }
}
