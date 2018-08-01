package com.forcelate.domain;

import lombok.Getter;

@Getter
public enum ProgressState {
    FOLDERS_ARE_PREPARING,
    DRIVER_ARE_LOADING,
    STOP_WORDS_ARE_LOADING,
    SHIT_WORDS_ARE_LOADING,
    URL_SCRAPPING,
    URL_SAVING,
    DESCRIPTION_SCRAPPING,
    DESCRIPTION_SAVING,
    DESCRIPTION_READING,
    WORDS_PROCESSING,
    WORDS_POPULARITY,
    WORDS_SORTING_AND_LIMITING,
    WORDS_PRINTING,
    CHART_GENERATING
}
