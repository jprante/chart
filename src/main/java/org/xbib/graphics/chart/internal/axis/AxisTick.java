package org.xbib.graphics.chart.internal.axis;

import org.xbib.graphics.chart.axis.Direction;
import org.xbib.graphics.chart.internal.chart.Chart;
import org.xbib.graphics.chart.internal.chart.ChartComponent;
import org.xbib.graphics.chart.internal.series.AxesChartSeries;
import org.xbib.graphics.chart.internal.style.AxesChartStyler;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/**
 * An axis tick.
 */
public class AxisTick<ST extends AxesChartStyler, S extends AxesChartSeries> implements ChartComponent {

    private final Chart<ST, S> chart;
    private final Direction direction;
    private Rectangle2D bounds;
    private AxisTickLabels<ST, S> axisTickLabels;
    private AxisTickMarks<ST, S> axisTickMarks;

    protected AxisTick(Chart<ST, S> chart, Direction direction, Axis yAxis) {
        this.chart = chart;
        this.direction = direction;
        axisTickLabels = new AxisTickLabels<>(chart, direction, yAxis);
        axisTickMarks = new AxisTickMarks<>(chart, direction, yAxis);
    }

    @Override
    public Rectangle2D getBounds() {
        return bounds;
    }

    @Override
    public void paint(Graphics2D g) {
        if (direction == Direction.Y && chart.getStyler().isYAxisTicksVisible()) {
            axisTickLabels.paint(g);
            axisTickMarks.paint(g);
            bounds = new Rectangle2D.Double(axisTickLabels.getBounds().getX(),
                    axisTickLabels.getBounds().getY(),
                    axisTickLabels.getBounds().getWidth() + chart.getStyler().getAxisTickPadding() + axisTickMarks.getBounds().getWidth(),
                    axisTickMarks.getBounds().getHeight()
            );
        } else if (direction == Direction.X && chart.getStyler().isXAxisTicksVisible()) {
            axisTickLabels.paint(g);
            axisTickMarks.paint(g);
            bounds = new Rectangle2D.Double(axisTickMarks.getBounds().getX(),
                    axisTickMarks.getBounds().getY(),
                    axisTickLabels.getBounds().getWidth(),
                    axisTickMarks.getBounds().getHeight() + chart.getStyler().getAxisTickPadding() + axisTickLabels.getBounds().getHeight()
            );
        } else {
            bounds = new Rectangle2D.Double();
        }
    }

    protected AxisTickLabels<ST, S> getAxisTickLabels() {
        return axisTickLabels;
    }

}
