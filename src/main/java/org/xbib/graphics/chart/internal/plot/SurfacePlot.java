package org.xbib.graphics.chart.internal.plot;

import org.xbib.graphics.chart.internal.chart.Chart;
import org.xbib.graphics.chart.internal.chart.ChartComponent;
import org.xbib.graphics.chart.internal.series.Series;
import org.xbib.graphics.chart.internal.style.Styler;

import java.awt.geom.Rectangle2D;

public abstract class SurfacePlot<ST extends Styler, S extends Series> implements ChartComponent {

    protected final Chart<ST, S> chart;

    protected SurfacePlot(Chart<ST, S> chart) {
        this.chart = chart;
    }

    @Override
    public Rectangle2D getBounds() {
        return chart.getPlot().getBounds();
    }
}
