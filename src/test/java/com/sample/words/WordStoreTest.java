package com.sample.words;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class WordStoreTest {

  private WordStore subject;

  @BeforeEach
  void initialize() {
    subject = new WordStore();
  }

  @Test
  @DisplayName("Adding the same word twice should result in a count of two")
  void addTheSameWordTwice() {
    String appleWord = "apple";
    String strawberryWord = "strawberry";
    String pearWord = "pear";

    subject.save(appleWord);
    subject.save(appleWord);
    subject.save(strawberryWord);
    subject.save(pearWord);
    assertThat(subject.getCount(appleWord), equalTo(2));
  }

  @Test
  @DisplayName("Adding a word once should result in a count of one")
  void addOneWord() {
    String appleWord = "apple";
    subject.save(appleWord);
    assertThat(subject.getCount(appleWord), equalTo(1));
  }

  @Test
  @DisplayName("If the word has not been added it should result in a count of 0 ")
  void wordNotAddedFound() {
    String appleWord = "apple";
    assertThat(subject.getCount(appleWord), equalTo(0));
  }
}
