package com.forcelate.services;

import com.forcelate.utils.SkillsUtils;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class WordsService {

    public static LinkedHashMap<String, Long> sortAndLimit(Map<String, Long> wordsByWeight) {
        return wordsByWeight.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
//                .filter(entry -> entry.getValue() > 5)
                .limit(5)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }

    public static LinkedHashMap<String, Long> sortAndLimitAndFilterBySkills(Map<String, Long> wordsByWeight) {
        return wordsByWeight.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .filter(entry -> SkillsUtils.isSkill(entry.getKey()))
                .limit(6)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }
}
