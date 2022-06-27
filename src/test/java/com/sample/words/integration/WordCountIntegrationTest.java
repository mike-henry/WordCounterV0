package com.sample.words.integration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import com.sample.words.WordCountController;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WordCountIntegrationTest {

  @Autowired private WordCountController controller;

  @Test
  @DisplayName("Inserting a  word twice and getting the count should result in '2'")
  void addWord() {
    String appleWord = "apple";
    String strawberryWord = "strawberry";
    String pearWord = "pear";

    List<String> words = new ArrayList<>();
    words.add(appleWord);
    words.add(strawberryWord);
    words.add(pearWord);
    controller.addWord(words);
    List<String> nextWords = new ArrayList<>();
    nextWords.add(appleWord);
    controller.addWord(nextWords);
    assertThat(controller.getCount(appleWord), equalTo(2));
  }
}
