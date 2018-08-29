package org.xbib.graphics.chart.bubble;

import org.xbib.graphics.chart.internal.axis.AxisPair;
import org.xbib.graphics.chart.internal.chart.Chart;
import org.xbib.graphics.chart.internal.plot.AxesChartPlot;
import org.xbib.graphics.chart.internal.plot.ContentPlot;
import org.xbib.graphics.chart.internal.style.SeriesColorMarkerLineStyle;
import org.xbib.graphics.chart.internal.style.SeriesColorMarkerLineStyleCycler;
import org.xbib.graphics.chart.Theme;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.List;
import java.util.Map;

public class BubbleChart extends Chart<BubbleStyler, BubbleSeries> {

    public BubbleChart(int width, int height) {
        super(width, height, new BubbleStyler());
        axisPair = new AxisPair<>(this);
        plot = new BubblePlot<>(this);
        legend = new BubbleLegend<>(this);
    }

    public BubbleChart(int width, int height, Theme theme) {
        this(width, height);
        styler.setTheme(theme);
    }

    public BubbleChart(BubbleChartBuilder chartBuilder) {
        this(chartBuilder.getWidth(), chartBuilder.getHeight(), chartBuilder.getTheme());
        setTitle(chartBuilder.getTitle());
        setXAxisTitle(chartBuilder.xAxisTitle);
        setYAxisTitle(chartBuilder.yAxisTitle);
    }

    /**
     * Add a series for a Bubble type chart using using double arrays
     *
     * @param seriesName
     * @param xData the X-Axis data
     * @param xData the Y-Axis data
     * @param bubbleData the bubble data
     * @return A Series object that you can set properties on
     */
    public BubbleSeries addSeries(String seriesName,
            List<?> xData,
            List<? extends Number> yData,
            List<? extends Number> bubbleData) {
        sanityCheck(seriesName, xData, yData, bubbleData);
        BubbleSeries series;
        if (xData != null) {
            if (xData.size() != yData.size()) {
                throw new IllegalArgumentException("X and Y-Axis sizes are not the same");
            }
            series = new BubbleSeries(seriesName, xData, yData, bubbleData);
        } else { // generate xData
            series = new BubbleSeries(seriesName, getGeneratedData(yData.size()), yData, bubbleData);
        }
        seriesMap.put(seriesName, series);
        return series;
    }

    private void sanityCheck(String seriesName, List<?> xData,
                             List<? extends Number> yData,
                             List<? extends Number> bubbleData) {
        if (seriesMap.keySet().contains(seriesName)) {
            throw new IllegalArgumentException("Series name >"
                            + seriesName
                            + "< has already been used. Use unique names for each series");
        }
        if (yData == null) {
            throw new IllegalArgumentException("Y-Axis data cannot be null >" + seriesName);
        }
        if (yData.size() == 0) {
            throw new IllegalArgumentException("Y-Axis data cannot be empty >" + seriesName);
        }
        if (bubbleData == null) {
            throw new IllegalArgumentException("Bubble data cannot be null >" + seriesName);
        }
        if (bubbleData.size() == 0) {
            throw new IllegalArgumentException("Bubble data cannot be empty >" + seriesName);
        }
        if (xData != null && xData.size() == 0) {
            throw new IllegalArgumentException("X-Axis data cannot be empty >" + seriesName);
        }
        if (bubbleData.size() != yData.size()) {
            throw new IllegalArgumentException("Bubble Data and Y-Axis sizes are not the same >" + seriesName);
        }
    }

    @Override
    public void paint(Graphics2D g, int width, int height) {
        setWidth(width);
        setHeight(height);
        // set the series types if they are not set. Legend and Plot need it.
        for (BubbleSeries bubbleSeries : getSeriesMap().values()) {
            BubbleSeriesRenderStyle seriesType =
                    bubbleSeries.getBubbleSeriesRenderStyle(); // would be directly set
            if (seriesType == null) { // wasn't overridden, use default from Style Manager
                bubbleSeries.setBubbleSeriesRenderStyle(getStyler().getDefaultSeriesRenderStyle());
            }
        }
        setSeriesStyles();

        paintBackground(g);

        axisPair.paint(g);
        plot.paint(g);
        chartTitle.paint(g);
        legend.paint(g);
    }

    /** set the series color based on theme */
    private void setSeriesStyles() {
        SeriesColorMarkerLineStyleCycler seriesColorMarkerLineStyleCycler =
                new SeriesColorMarkerLineStyleCycler(
                        getStyler().getSeriesColors(),
                        getStyler().getSeriesMarkers(),
                        getStyler().getSeriesLines());
        for (BubbleSeries series : getSeriesMap().values()) {
            SeriesColorMarkerLineStyle seriesColorMarkerLineStyle =
                    seriesColorMarkerLineStyleCycler.getNextSeriesColorMarkerLineStyle();
            if (series.getLineStyle() == null) { // wasn't set manually
                series.setLineStyle(seriesColorMarkerLineStyle.getStroke());
            }
            if (series.getLineColor() == null) { // wasn't set manually
                series.setLineColor(seriesColorMarkerLineStyle.getColor());
            }
            if (series.getFillColor() == null) { // wasn't set manually
                series.setFillColor(seriesColorMarkerLineStyle.getColor());
            }
        }
    }

    private class BubblePlot<ST extends BubbleStyler, S extends BubbleSeries> extends AxesChartPlot<ST, S> {

        private BubblePlot(Chart<ST, S> chart) {
            super(chart);
            this.contentPlot = new BubbleContentPlot<ST, S>(chart);
        }
    }

    class BubbleContentPlot<ST extends BubbleStyler, S extends BubbleSeries> extends ContentPlot<ST, S> {

        private final ST stylerBubble;

        private BubbleContentPlot(Chart<ST, S> chart) {
            super(chart);
            stylerBubble = chart.getStyler();
        }

        @Override
        public void doPaint(Graphics2D g) {
            double xTickSpace = stylerBubble.getPlotContentSize() * getBounds().getWidth();
            double xLeftMargin = ((int) getBounds().getWidth() - xTickSpace) / 2.0;
            double yTickSpace = stylerBubble.getPlotContentSize() * getBounds().getHeight();
            double yTopMargin = ((int) getBounds().getHeight() - yTickSpace) / 2.0;
            double xMin = chart.getXAxis().getMin();
            double xMax = chart.getXAxis().getMax();
            if (stylerBubble.isXAxisLogarithmic()) {
                xMin = Math.log10(xMin);
                xMax = Math.log10(xMax);
            }
            Map<String, S> map = chart.getSeriesMap();
            for (S series : map.values()) {
                if (!series.isEnabled()) {
                    continue;
                }
                double yMin = chart.getYAxis(series.getYAxisGroup()).getMin();
                double yMax = chart.getYAxis(series.getYAxisGroup()).getMax();
                if (stylerBubble.isYAxisLogarithmic()) {
                    yMin = Math.log10(yMin);
                    yMax = Math.log10(yMax);
                }
                for (int i = 0; i < series.getXData().size(); i++) {
                    Double x = (Double) series.getXData().get(i);
                    if (stylerBubble.isXAxisLogarithmic()) {
                        x = Math.log10(x);
                    }
                    if (Double.isNaN((Double)series.getYData().get(i))) {
                        continue;
                    }
                    Double yOrig = (Double) series.getYData().get(i);
                    double y;
                    if (stylerBubble.isYAxisLogarithmic()) {
                        y = Math.log10(yOrig);
                    } else {
                        y = yOrig;
                    }
                    double xTransform = xLeftMargin + ((x - xMin) / (xMax - xMin) * xTickSpace);
                    double yTransform =
                            getBounds().getHeight() - (yTopMargin + (y - yMin) / (yMax - yMin) * yTickSpace);
                    if (Math.abs(xMax - xMin) / 5 == 0.0) {
                        xTransform = getBounds().getWidth() / 2.0;
                    }
                    if (Math.abs(yMax - yMin) / 5 == 0.0) {
                        yTransform = getBounds().getHeight() / 2.0;
                    }
                    double xOffset = getBounds().getX() + xTransform;
                    double yOffset = getBounds().getY() + yTransform;
                    if (series.getExtraValues() != null) {
                        Double bubbleSize = (Double) series.getExtraValues().get(i);
                        Shape bubble;
                        bubble = new Ellipse2D.Double(xOffset - bubbleSize / 2, yOffset - bubbleSize / 2, bubbleSize, bubbleSize);
                        g.setColor(series.getFillColor());
                        g.fill(bubble);
                        g.setColor(series.getLineColor());
                        g.setStroke(series.getLineStyle());
                        g.draw(bubble);
                    }
                }
            }
        }
    }
}