package com.practice.chatbot.crud.util;

public class SearchIdAndIndex {
    public static int[] searchIdAndIndex(String content, int start) {
        int id = 0;
        if(start == 0) {
            while (start < content.length() && Character.isDigit(content.charAt(start))) {
                id = id * 10 + (content.charAt(start++) - '0');
            }
        } else {
            while (start >= 0 && Character.isDigit(content.charAt(start))) {
                id = id * 10 + (content.charAt(start--) - '0');
            }
        }
        return new int[] {id, start};
    }
}
