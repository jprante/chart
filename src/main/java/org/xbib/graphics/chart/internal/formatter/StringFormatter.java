package org.xbib.graphics.chart.internal.formatter;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

public class StringFormatter extends Format {

    public StringFormatter() {
    }

    @Override
    public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
        String string = obj.toString();
        toAppendTo.append(string);
        return toAppendTo;
    }

    @Override
    public Object parseObject(String source, ParsePosition pos) {
        return null;
    }
}
