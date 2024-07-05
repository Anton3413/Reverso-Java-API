package reverso.data.response.entity;

import com.google.gson.Gson;

public class Stats {
    private int textLength;
    private int wordCount;
    private int sentenceCount;
    private int longestSentence;

    public Stats() {
    }

    public Stats(int textLength, int wordCount, int sentenceCount, int longestSentence) {
        this.textLength = textLength;
        this.wordCount = wordCount;
        this.sentenceCount = sentenceCount;
        this.longestSentence = longestSentence;
    }

    public int getTextLength() {
        return textLength;
    }

    public void setTextLength(int textLength) {
        this.textLength = textLength;
    }

    public int getWordCount() {
        return wordCount;
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }

    public int getSentenceCount() {
        return sentenceCount;
    }

    public void setSentenceCount(int sentenceCount) {
        this.sentenceCount = sentenceCount;
    }

    public int getLongestSentence() {
        return longestSentence;
    }

    public void setLongestSentence(int longestSentence) {
        this.longestSentence = longestSentence;
    }

    public String toJson(){
        return new Gson().toJson(this);
    }
}