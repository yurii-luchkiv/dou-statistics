package com.forcelate.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Progress {
    private ProgressState stage;
    private List<String> messages = new ArrayList<>();
}
