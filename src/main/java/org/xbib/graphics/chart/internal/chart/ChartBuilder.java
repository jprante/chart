package org.xbib.graphics.chart.internal.chart;

import org.xbib.graphics.chart.Theme;

public abstract class ChartBuilder<T extends ChartBuilder<?, ?>, C extends Chart> {

    private int width;
    private int height;
    private String title;
    private Theme theme;

    public ChartBuilder() {
        this.width = 800;
        this.height = 600;
        this.title = "";
        this.theme = Theme.DEFAULT;
    }

    public T width(int width) {
        this.width = width;
        return (T) this;
    }

    public int getWidth() {
        return width;
    }

    public T height(int height) {
        this.height = height;
        return (T) this;
    }

    public int getHeight() {
        return height;
    }

    public T title(String title) {
        this.title = title;
        return (T) this;
    }

    public String getTitle() {
        return title;
    }

    public T theme(Theme theme) {
        if (theme != null) {
            this.theme = theme;
        }
        return (T) this;
    }

    public Theme getTheme() {
        return theme;
    }

    public abstract C build();

}
