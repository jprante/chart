package org.xbib.graphics.chart.internal.chart;

import org.xbib.graphics.chart.axis.Direction;
import org.xbib.graphics.chart.internal.axis.Axis;
import org.xbib.graphics.chart.internal.axis.AxisPair;
import org.xbib.graphics.chart.internal.legend.Legend;
import org.xbib.graphics.chart.internal.plot.Plot;
import org.xbib.graphics.chart.internal.series.Series;
import org.xbib.graphics.chart.internal.style.Styler;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.text.Format;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * A Chart.
 */
public abstract class Chart<ST extends Styler, S extends Series> {

    protected final ST styler;
    protected final ChartTitle<ST, S> chartTitle;
    protected final Map<String, S> seriesMap = new LinkedHashMap<>();
    protected AxisPair axisPair;
    protected Plot<ST, S> plot;
    protected Legend<ST, S> legend;
    private int width;
    private int height;
    private String title = "";
    private String xAxisTitle = "";
    private String yAxisTitle = "";
    private Map<Integer, String> yAxisGroupTitleMap = new HashMap<Integer, String>();

    public Chart(int width, int height, ST styler) {
        this.styler = styler;
        this.width = width;
        this.height = height;
        this.chartTitle = new ChartTitle<>(this);
    }

    public abstract void paint(Graphics2D g, int width, int height);

    protected void paintBackground(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, styler.getAntiAlias()
                        ? RenderingHints.VALUE_ANTIALIAS_ON
                        : RenderingHints.VALUE_ANTIALIAS_OFF);
        g.setColor(styler.getChartBackgroundColor());
        Shape rect = new Rectangle2D.Double(0, 0, getWidth(), getHeight());
        g.fill(rect);
    }

    public List<Double> listFromDoubleArray(double[] data) {
        if (data == null) {
            return null;
        }
        List<Double> dataNumber;
        dataNumber = new ArrayList<>();
        for (double d : data) {
            dataNumber.add(d);
        }
        return dataNumber;
    }

    public List<Double> listFromFloatArray(float[] data) {
        if (data == null) {
            return null;
        }
        List<Double> dataNumber;
        dataNumber = new ArrayList<>();
        for (float f : data) {
            dataNumber.add((double) f);
        }
        return dataNumber;
    }

    public List<Double> listFromIntArray(int[] data) {
        if (data == null) {
            return null;
        }
        List<Double> dataNumber;
        dataNumber = new ArrayList<>();
        for (double d : data) {
            dataNumber.add(d);
        }
        return dataNumber;
    }

    public List<Double> getGeneratedData(int length) {
        List<Double> generatedData = new ArrayList<>();
        for (int i = 1; i < length + 1; i++) {
            generatedData.add((double) i);
        }
        return generatedData;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getXAxisTitle() {
        return xAxisTitle;
    }

    public void setXAxisTitle(String xAxisTitle) {
        this.xAxisTitle = xAxisTitle;
    }

    public String getyYAxisTitle() {
        return yAxisTitle;
    }

    public void setYAxisTitle(String yAxisTitle) {
        this.yAxisTitle = yAxisTitle;
    }


    public String getYAxisGroupTitle(int yAxisGroup) {
        String title = yAxisGroupTitleMap.get(yAxisGroup);
        if (title == null) {
            return yAxisTitle;
        }
        return title;
    }

    public void setYAxisGroupTitle(int yAxisGroup, String yAxisTitle) {
        yAxisGroupTitleMap.put(yAxisGroup, yAxisTitle);
    }

    public void setXAxisLabelOverrideMap(Map<Double, Object> overrideMap) {
        axisPair.getAxisLabelOverrideMap().put("X0", overrideMap);
    }

    public void setYAxisLabelOverrideMap(Map<Double, Object> overrideMap) {
        axisPair.getAxisLabelOverrideMap().put("Y0", overrideMap);
    }

    public void setYAxisLabelOverrideMap(Map<Double, Object> overrideMap, int yAxisGroup) {
        axisPair.getAxisLabelOverrideMap().put(("Y" + yAxisGroup), overrideMap);
    }

    public Map<Double, Object> getYAxisLabelOverrideMap(Direction direction, int yIndex) {
        Map<String, Map<Double, Object>> axisLabelOverrideMap = axisPair.getAxisLabelOverrideMap();
        return axisLabelOverrideMap.get((direction.name() + yIndex));
    }

    public ChartTitle<ST, S> getChartTitle() {
        return chartTitle;
    }

    public Legend<ST, S> getLegend() {
        return legend;
    }

    public Plot<ST, S> getPlot() {
        return plot;
    }

    public Axis getXAxis() {
        return axisPair.getXAxis();
    }

    public Axis getYAxis() {
        return axisPair.getYAxis();
    }

    public Axis getYAxis(int yIndex) {
        return axisPair.getYAxis(yIndex);
    }

    public AxisPair getAxisPair() {
        return axisPair;
    }

    public Map<String, S> getSeriesMap() {
        return seriesMap;
    }

    public S removeSeries(String seriesName) {
        return seriesMap.remove(seriesName);
    }

    public ST getStyler() {
        return styler;
    }

    public Format getXAxisFormat() {
        return axisPair.getXAxis().getAxisTickCalculator().getAxisFormat();
    }

    public Format getYAxisFormat() {
        return axisPair.getYAxis().getAxisTickCalculator().getAxisFormat();
    }
}
