package org.xbib.graphics.chart.xy;

import org.xbib.graphics.chart.axis.DataType;
import org.xbib.graphics.chart.internal.series.AxesChartSeriesNumericalNoErrorBars;
import org.xbib.graphics.chart.legend.LegendRenderType;

import java.util.List;

/**
 * A Series containing X and Y data to be plotted on a Chart
 */
public class XYSeries extends AxesChartSeriesNumericalNoErrorBars {

    private XYSeriesRenderStyle xySeriesRenderStyle = null;

    public XYSeries(String name,
                    List<?> xData,
                    List<? extends Double> yData,
                    List<? extends Double> errorBars,
                    DataType dataType) {
        super(name, xData, yData, errorBars, dataType);
    }

    public XYSeriesRenderStyle getXySeriesRenderStyle() {
        return xySeriesRenderStyle;
    }

    public void setXySeriesRenderStyle(XYSeriesRenderStyle xySeriesRenderStyle) {
        this.xySeriesRenderStyle = xySeriesRenderStyle;
    }

    @Override
    public LegendRenderType getLegendRenderType() {
        return xySeriesRenderStyle.getLegendRenderType();
    }


}