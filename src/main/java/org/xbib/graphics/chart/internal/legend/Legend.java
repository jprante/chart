package org.xbib.graphics.chart.internal.legend;

import org.xbib.graphics.chart.internal.chart.ChartComponent;
import org.xbib.graphics.chart.internal.series.Series;
import org.xbib.graphics.chart.internal.style.Styler;

public interface Legend<ST extends Styler, S extends Series> extends ChartComponent {

    double getSeriesLegendRenderGraphicHeight(S series);

}
