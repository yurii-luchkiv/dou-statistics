package com.forcelate.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Category {
    DEVOPS("DevOps"),
    JAVA("Java"),
    NET(".NET"),
    NODEJS("Node.js"),
    PHP("PHP"),
    PRODUCT_MANAGER("Product Manager"),
    PROJECT_MANAGER("Project Manager"),
    PYTHON("Python"),
    RUBY("Ruby"),
    UNITY("Unity");

    private String value;
}
