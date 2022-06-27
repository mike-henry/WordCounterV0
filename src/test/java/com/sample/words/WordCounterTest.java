package com.sample.words;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class WordCounterTest {

  private WordCounter subject;

  @Mock private WordStore storage;

  @Mock private Translator translator;

  @BeforeEach()
  void initialize() {
    subject = new WordCounter(storage, translator);
  }

  @Test
  @DisplayName("Add english words")
  void addEnglishWord() {
    String appleWord = "apple";
    String strawberryWord = "strawberry";
    String pearWord = "pear";

    when(translator.translate(eq(appleWord))).thenReturn(appleWord);
    when(translator.translate(eq(strawberryWord))).thenReturn(strawberryWord);
    when(translator.translate(eq(pearWord))).thenReturn(pearWord);

    subject.addWords(appleWord, strawberryWord, pearWord);

    verify(translator, times(3)).translate(any());
    verify(translator, times(1)).translate(eq(appleWord));
    verify(translator, times(1)).translate(eq(strawberryWord));
    verify(translator, times(1)).translate(eq(pearWord));

    verify(storage, times(3)).save(any());
    verify(storage, times(1)).save(eq(appleWord));
    verify(storage, times(1)).save(eq(strawberryWord));
    verify(storage, times(1)).save(eq(pearWord));
  }

  @Test
  @DisplayName("Add a foreign words translates word into english before saving")
  void addForeignWords() {
    String germanFlowerWord = "blume";
    String spanishFlowerWord = "flor";
    String flowerWord = "flower";
    when(translator.translate(eq(germanFlowerWord))).thenReturn(flowerWord);
    when(translator.translate(eq(spanishFlowerWord))).thenReturn(flowerWord);
    when(translator.translate(eq(flowerWord))).thenReturn(flowerWord);
    subject.addWords(germanFlowerWord, flowerWord, spanishFlowerWord);
    verify(translator, times(3)).translate(any());
    verify(translator, times(1)).translate(eq(germanFlowerWord));
    verify(translator, times(1)).translate(eq(flowerWord));
    verify(translator, times(1)).translate(eq(spanishFlowerWord));
    verify(storage, times(3)).save(any());
    verify(storage, times(3)).save(eq(flowerWord));
  }

  @Test
  @DisplayName("Should NOT allow addition of words with non-alphabetic characters")
  void addWordWithNumericShouldFail() {
    String flowerWord = "flower";
    String badWord = "bad1";
    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, () -> subject.addWords(badWord, flowerWord));
    assertThat(exception.getMessage(), containsString("non alphabetic character found"));
  }

  @Test
  @DisplayName("Storage is called to get the count")
  void getCount() {
    String flowerWord = "flower";
    when(storage.getCount(eq(flowerWord))).thenReturn(2);
    assertThat(subject.getCount(flowerWord), equalTo(2));
  }
}
