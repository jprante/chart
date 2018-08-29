package org.xbib.graphics.chart.io;

import org.xbib.graphics.chart.internal.chart.Chart;
import org.xbib.graphics.chart.io.vector.EPSGraphics2D;
import org.xbib.graphics.chart.io.vector.PDFGraphics2D;
import org.xbib.graphics.chart.io.vector.ProcessingPipeline;
import org.xbib.graphics.chart.io.vector.SVGGraphics2D;

import java.io.IOException;
import java.io.OutputStream;

/**
 * A helper class with static methods for saving Charts as bitmaps
 */
public final class VectorGraphicsEncoder {

    public enum VectorGraphicsFormat {
        EPS, PDF, SVG
    }

    private VectorGraphicsEncoder() {
    }

    public static void write(Chart chart, OutputStream outputStream, VectorGraphicsFormat vectorGraphicsFormat)
            throws IOException {
        ProcessingPipeline g = null;
        switch (vectorGraphicsFormat) {
            case EPS:
                g = new EPSGraphics2D(0.0, 0.0, chart.getWidth(), chart.getHeight());
                break;
            case PDF:
                g = new PDFGraphics2D(0.0, 0.0, chart.getWidth(), chart.getHeight());
                break;
            case SVG:
                g = new SVGGraphics2D(0.0, 0.0, chart.getWidth(), chart.getHeight());
                break;

            default:
                break;
        }
        chart.paint(g, chart.getWidth(), chart.getHeight());
        if (outputStream != null) {
            outputStream.write(g.getBytes());
        }
    }
}
