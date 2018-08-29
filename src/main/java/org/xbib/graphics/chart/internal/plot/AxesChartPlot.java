package org.xbib.graphics.chart.internal.plot;

import org.xbib.graphics.chart.internal.chart.Chart;
import org.xbib.graphics.chart.internal.series.Series;
import org.xbib.graphics.chart.internal.style.AxesChartStyler;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class AxesChartPlot<ST extends AxesChartStyler, S extends Series> extends Plot<ST, S> {

    public AxesChartPlot(Chart<ST, S> chart) {
        super(chart);
        this.surfacePlot = new SurfacePlotAxesChart<>(chart);
    }

    @Override
    public void paint(Graphics2D g) {
        Rectangle2D yAxisBounds = chart.getAxisPair().getLeftYAxisBounds();
        Rectangle2D xAxisBounds = chart.getXAxis().getBounds();
        double xOffset = xAxisBounds.getX();
        double yOffset = yAxisBounds.getY();
        double width = xAxisBounds.getWidth();
        double height = yAxisBounds.getHeight();
        this.bounds = new Rectangle2D.Double(xOffset, yOffset, width, height);
        super.paint(g);
    }
}
