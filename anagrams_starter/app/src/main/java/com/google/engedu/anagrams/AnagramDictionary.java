/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    private ArrayList<String> wordlist=new ArrayList<>();
    private HashMap<String,ArrayList<String>> hashMap=new HashMap<>();
    private HashSet<String> dictionary=new HashSet<>();

    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;


        while ((line = in.readLine()) != null) {
            String word = line.trim();
            dictionary.add(word);
            wordlist.add(word);
            char[] chars = word.toCharArray();
            Arrays.sort(chars);
            String sortedword = new String(chars);
            if (hashMap.containsKey(sortedword))
                hashMap.get(sortedword).add(word);
            else {
                ArrayList<String> arrayList = new ArrayList<>();
                arrayList.add(word);
                hashMap.put(sortedword, arrayList);
            }


        }
    }

    public boolean isGoodWord(String word, String base) {
        if (dictionary.contains(word) && !word.contains(base))
            return true;
        return false;


    }

    public List<String> getAnagrams(String targetWord) {
        return hashMap.get(getSortedString(targetWord));
    }

    public List<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        for (char i = 'a'; i <= 'z'; i++) {
            List list = getAnagrams(word + i);
            if (list != null)
                result.addAll(list);
        }
        return result;
    }

    public String pickGoodStarterWord() {
        while (true) {
            String word = wordlist.get(random.nextInt(wordlist.size()));
            if (hashMap.get(getSortedString(word)).size() > MIN_NUM_ANAGRAMS && word.length() <= MAX_WORD_LENGTH)
                return word;
        }

    }

    public static String getSortedString(String word) {
        char[] chars = word.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }
}
