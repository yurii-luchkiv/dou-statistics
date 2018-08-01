package com.forcelate.services;

import com.forcelate.configuration.Configuration;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.style.PieStyler;
import org.knowm.xchart.style.Styler;

import java.io.IOException;
import java.util.LinkedHashMap;

import static com.forcelate.utils.PathUtils.chartFilePath;

public class ChartService {

    public static void savePieChart(Configuration configuration, LinkedHashMap<String, Long> sortedWordsByPopularity) throws IOException {
        String name = configuration.getCategory().getValue() + "-" + configuration.getLanguage();
        PieChart chart = new PieChartBuilder()
                .width(800)
                .height(600)
                .title(name)
                .theme(Styler.ChartTheme.GGPlot2)
                .build();

        chart.getStyler().setLegendVisible(false);
        chart.getStyler().setAnnotationType(PieStyler.AnnotationType.LabelAndPercentage);
        chart.getStyler().setAnnotationDistance(1.15);
        chart.getStyler().setPlotContentSize(.7);
        chart.getStyler().setStartAngleInDegrees(90);
        sortedWordsByPopularity.forEach(chart::addSeries);

        String fileName = chartFilePath(name);
        BitmapEncoder.saveBitmapWithDPI(chart, fileName, BitmapEncoder.BitmapFormat.PNG, 300);
    }
}
