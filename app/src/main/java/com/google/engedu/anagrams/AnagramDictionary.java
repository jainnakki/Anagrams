package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    private ArrayList<String> wordList;
    private HashSet<String> wordSet;
    private HashMap<Integer, ArrayList<String>> sizeToWords;
    private HashMap<String, ArrayList<String>> lettersToWord;
    private int wordLength = DEFAULT_WORD_LENGTH;

    public AnagramDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        String line;

        wordList = new ArrayList<>();
        wordSet = new HashSet<>();
        lettersToWord = new HashMap<>();
        sizeToWords = new HashMap<>();

        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordList.add(word);
            wordSet.add(word);
            String k=sortLetters(word);
            if(lettersToWord.containsKey(k)) {
                lettersToWord.get(k).add(word);
            } else {
                ArrayList<String> t=new ArrayList<>();
                t.add(word);
                lettersToWord.put(k,t);
            }

            int l = k.length();

            if(sizeToWords.containsKey(l)) {
                sizeToWords.get(l).add(word);
            } else {
                ArrayList<String> t = new ArrayList<>();
                t.add(word);
                sizeToWords.put(l, t);
            }
        }
    }

    public boolean isGoodWord(String word, String base) {
        return wordSet.contains(word) && !word.contains(base);
    }

    public ArrayList<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<String>();
        String k=sortLetters(targetWord);
        for(String i:wordList)
        if(k.equals(sortLetters(i)))
            result.add(i);
        return result;
    }



    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        String k;
        for (char i = 'a'; i <= 'z'; i++) {
            k = sortLetters(word.concat("" + i));

            if(lettersToWord.containsKey(k)) {
                result.addAll(lettersToWord.get(k));
            }
        }
        return result;
    }

    public String pickGoodStarterWord() {

        ArrayList<String> t = sizeToWords.get(wordLength);

        if(wordLength < MAX_WORD_LENGTH) wordLength++;

        while (true) {
            String w = t.get(random.nextInt(t.size()));
            if(getAnagramsWithOneMoreLetter(w).size() >= MIN_NUM_ANAGRAMS) return w;
        }
    }

    public String sortLetters(String word)
    {
        char[] sortedWord=word.toCharArray();
        Arrays.sort(sortedWord);
        return new String(sortedWord);
    }
}
