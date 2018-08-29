package org.xbib.graphics.chart.ohlc;

import org.xbib.graphics.chart.internal.style.AxesChartStyler;
import org.xbib.graphics.chart.Theme;

public class OHLCStyler extends AxesChartStyler {

    private OHLCSeriesRenderStyle ohlcSeriesRenderStyle;

    public OHLCStyler() {
        this.setAllStyles();
        super.setAllStyles();
    }

    @Override
    protected void setAllStyles() {
        ohlcSeriesRenderStyle = OHLCSeriesRenderStyle.Candle;
    }

    public OHLCSeriesRenderStyle getDefaultSeriesRenderStyle() {

        return ohlcSeriesRenderStyle;
    }

    public OHLCStyler setDefaultSeriesRenderStyle(OHLCSeriesRenderStyle ohlcSeriesRenderStyle) {
        this.ohlcSeriesRenderStyle = ohlcSeriesRenderStyle;
        return this;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
        super.setAllStyles();
    }
}
