package org.wikipedia.model;

import java.util.List;
import java.util.Objects;

public class RandomWord {
    private List<String> word;

    public RandomWord(List<String> word) {
        this.word = word;
    }

    public List<String> getWord() {
        return word;
    }

    public void setWord(List<String> word) {
        this.word = word;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RandomWord that = (RandomWord) o;
        return Objects.equals(word, that.word);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(word);
    }

    @Override
    public String toString() {
        return "RandomWord{" +
                "word=" + word +
                '}';
    }
}
