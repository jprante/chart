package org.xbib.graphics.chart.internal.plot;

import org.xbib.graphics.chart.internal.chart.Chart;
import org.xbib.graphics.chart.internal.chart.ChartComponent;
import org.xbib.graphics.chart.internal.series.Series;
import org.xbib.graphics.chart.internal.style.Styler;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

public abstract class ContentPlot<ST extends Styler, S extends Series> implements ChartComponent {

    protected final Chart<ST, S> chart;

    protected final Stroke errorBarStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);

    protected ContentPlot(Chart<ST, S> chart) {
        this.chart = chart;
    }

    @Override
    public Rectangle2D getBounds() {
        return chart.getPlot().getBounds();
    }

    @Override
    public void paint(Graphics2D g) {
        Rectangle2D bounds = getBounds();
        if (bounds.getWidth() < 30) {
            return;
        }
        Shape saveClip = g.getClip();
        g.setClip(bounds.createIntersection(bounds));
        doPaint(g);
        g.setClip(saveClip);
    }

    /**
     * Closes a path for area charts if one is available.
     */
    protected void closePath(Graphics2D g, Path2D.Double path, double previousX, Rectangle2D bounds, double yTopMargin) {
        if (path != null) {
            double yBottomOfArea = getBounds().getY() + getBounds().getHeight() - yTopMargin;
            path.lineTo(previousX, yBottomOfArea);
            path.closePath();
            g.fill(path);
        }
    }

    protected abstract void doPaint(Graphics2D g);
}
