package com.forcelate.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Category {
    JAVA("Java"),
    NODEJS("Node.js"),
    UNITY("Unity");

    private String value;
}