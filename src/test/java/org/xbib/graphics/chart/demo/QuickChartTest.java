package org.xbib.graphics.chart.demo;

import org.junit.Test;
import org.xbib.graphics.chart.QuickChart;
import org.xbib.graphics.chart.io.VectorGraphicsEncoder;
import org.xbib.graphics.chart.xy.XYChart;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class QuickChartTest {

    @Test
    public void testQuick1() throws IOException {
        double[] xData = new double[] { 0.0, 1.0, 2.0 };
        double[] yData = new double[] { 2.0, 1.0, 0.0 };
        XYChart chart = QuickChart.getChart("Sample Chart",
                "X", "Y", "y(x)", xData, yData);
        VectorGraphicsEncoder.write(chart, Files.newOutputStream(Paths.get("build/quickchart1.svg")),
                VectorGraphicsEncoder.VectorGraphicsFormat.SVG);
    }
}
