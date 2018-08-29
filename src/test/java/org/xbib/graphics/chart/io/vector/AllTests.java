package org.xbib.graphics.chart.io.vector;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.xbib.graphics.chart.io.vector.eps.EPSTests;
import org.xbib.graphics.chart.io.vector.intermediate.IRTests;
import org.xbib.graphics.chart.io.vector.pdf.PDFTests;
import org.xbib.graphics.chart.io.vector.svg.SVGTests;
import org.xbib.graphics.chart.io.vector.util.UtilTests;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        TestUtilsTest.class,
        UtilTests.class,
        IRTests.class,
        VectorGraphics2DTest.class,
        EPSTests.class,
        PDFTests.class,
        SVGTests.class
})
public class AllTests {
}
