package org.xbib.graphics.chart.ohlc;

import org.xbib.graphics.chart.axis.DataType;
import org.xbib.graphics.chart.internal.series.AxesChartSeries;
import org.xbib.graphics.chart.legend.LegendRenderType;

import java.awt.Color;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

public class OHLCSeries extends AxesChartSeries {

    private List<?> xData;
    private List<? extends Number> openData;
    private List<? extends Number> highData;
    private List<? extends Number> lowData;
    private List<? extends Number> closeData;
    private OHLCSeriesRenderStyle ohlcSeriesRenderStyle;
    private Color upColor;
    private Color downColor;

    public OHLCSeries(String name,
                      List<?> xData,
                      List<? extends Number> openData,
                      List<? extends Number> highData,
                      List<? extends Number> lowData,
                      List<? extends Number> closeData,
                      DataType xDataType) {
        super(name, xDataType);
        this.xData = xData;
        this.openData = openData;
        this.highData = highData;
        this.lowData = lowData;
        this.closeData = closeData;
        calculateMinMax();
    }

    public OHLCSeriesRenderStyle getOhlcSeriesRenderStyle() {
        return ohlcSeriesRenderStyle;
    }

    public void setOhlcSeriesRenderStyle(OHLCSeriesRenderStyle ohlcSeriesRenderStyle) {
        this.ohlcSeriesRenderStyle = ohlcSeriesRenderStyle;
    }

    public Color getUpColor() {
        return upColor;
    }

    public void setUpColor(Color color) {
        this.upColor = color;
    }

    public Color getDownColor() {
        return downColor;
    }

    public void setDownColor(Color color) {
        this.downColor = color;
    }

    @Override
    public LegendRenderType getLegendRenderType() {
        return ohlcSeriesRenderStyle.getLegendRenderType();
    }

    void replaceData(List<?> newXData,
                     List<? extends Number> newOpenData,
                     List<? extends Number> newHighData,
                     List<? extends Number> newLowData,
                     List<? extends Number> newCloseData) {

        // Sanity check should already by done
        this.xData = newXData;
        this.openData = newOpenData;
        this.highData = newHighData;
        this.lowData = newLowData;
        this.closeData = newCloseData;
        calculateMinMax();
    }

    private List<Double> findMinMax(List<?> lows, List<?> highs) {
        Double min = Double.MAX_VALUE;
        Double max = -Double.MAX_VALUE;
        for (int i = 0; i < highs.size(); i++) {
            Object h = highs.get(i);
            Object l = lows.get(i);
            if (h instanceof Double) {
                Double d = (Double) h;
                if (d != Double.NaN && d > max) {
                    max = d;
                }
            } else if (h instanceof Instant) {
                Instant t = (Instant) h;
                if (t.toEpochMilli() > max) {
                    max = (double) t.toEpochMilli();
                }
            }
            if (l instanceof Double) {
                Double d = (Double) l;
                if (d != Double.NaN && d < min) {
                    min = d;
                }
            } else if (l instanceof Instant) {
                Instant t = (Instant) l;
                if (t.toEpochMilli() < min) {
                    min = (double) t.toEpochMilli();
                }
            }
        }
        return Arrays.asList(min, max);
    }

    @Override
    protected void calculateMinMax() {
        List<Double> xMinMax = findMinMax(xData, xData);
        setXMin(xMinMax.get(0));
        setXMax(xMinMax.get(1));
        List<Double> yMinMax = findMinMax(lowData, highData);
        setYMin(yMinMax.get(0));
        setYMax(yMinMax.get(1));
    }

    public List<?> getXData() {
        return xData;
    }

    public List<? extends Number> getOpenData() {
        return openData;
    }

    public List<? extends Number> getHighData() {
        return highData;
    }

    public List<? extends Number> getLowData() {
        return lowData;
    }

    public List<? extends Number> getCloseData() {
        return closeData;
    }

}
