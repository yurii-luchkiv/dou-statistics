package com.forcelate.configuration;

public enum Language {
    EN, RU;

    public boolean isEn() {
        return EN.equals(this);
    }

    public boolean isRu() {
        return RU.equals(this);
    }
}
