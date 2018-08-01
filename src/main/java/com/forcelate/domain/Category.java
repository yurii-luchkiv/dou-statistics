package com.forcelate.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Category {
    JAVA("Java"),
    NET(".NET"),
    NODEJS("Node.js"),
    PYTHON("Python"),
    UNITY("Unity");

    private String value;
}
