package com.forcelate.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Category {
    DESIGN("Дизайн"),
    DEVOPS("DevOps"),
    JAVA("Java"),
    MARKETING("Маркетинг"),
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
