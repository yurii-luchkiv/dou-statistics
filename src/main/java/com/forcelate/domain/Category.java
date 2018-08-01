package com.forcelate.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Category {
    BLOCKCHAIN("Blockchain"),
    DESIGN("Дизайн"),
    DEVOPS("DevOps"),
    FRONT_END("Front End"),
    JAVA("Java"),
    HR("HR"),
    MARKETING("Маркетинг"),
    NET(".NET"),
    NODEJS("Node.js"),
    PHP("PHP"),
    QA("QA"),
    PRODUCT_MANAGER("Product Manager"),
    PROJECT_MANAGER("Project Manager"),
    PYTHON("Python"),
    RUBY("Ruby"),
    UNITY("Unity");

    private String value;
}
