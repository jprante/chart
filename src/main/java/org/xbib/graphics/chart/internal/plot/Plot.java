package org.xbib.graphics.chart.internal.plot;

import org.xbib.graphics.chart.internal.chart.Chart;
import org.xbib.graphics.chart.internal.chart.ChartComponent;
import org.xbib.graphics.chart.internal.series.Series;
import org.xbib.graphics.chart.internal.style.Styler;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Plot<ST extends Styler, S extends Series> implements ChartComponent {

    protected final Chart<ST, S> chart;
    protected Rectangle2D bounds;
    protected SurfacePlot surfacePlot;
    protected ContentPlot contentPlot;

    public Plot(Chart<ST, S> chart) {
        this.chart = chart;
    }

    @Override
    public void paint(Graphics2D g) {
        surfacePlot.paint(g);
        if (chart.getSeriesMap().isEmpty()) {
            return;
        }
        contentPlot.paint(g);
    }

    @Override
    public Rectangle2D getBounds() {
        return bounds;
    }
}
