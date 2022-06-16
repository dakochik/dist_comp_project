package org.example.map;

import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.dropwizard.metrics.DropwizardMeterWrapper;
import org.apache.flink.metrics.Meter;
import org.apache.flink.shaded.zookeeper3.org.apache.zookeeper.Op;
import org.example.utils.RandomSort;
import org.example.utils.SomeAlgs;

import java.util.Arrays;
import java.util.Optional;

public class CustomBogoMap extends RichMapFunction<String, String> {
    private Meter meter;

    @Override
    public String map(String s) {
        meter.markEvent();
        Optional<double[]> res = SomeAlgs.strToDArr(s);
        if (!res.isPresent()) return "null";
        if (res.get().length<1) return "null";
        Optional<double[]> tmp = RandomSort.RSort(res.get());
        if (!tmp.isPresent()) return "null";
        return Arrays.stream(tmp.get())
                .mapToObj((double d) -> d +";")
                .reduce((String s1, String s2) -> s1+s2)
                .get();
        //return "MESSAGE SIZE: " + s.length();
    }

    @Override
    public void open(Configuration config) {
        com.codahale.metrics.Meter dropwizardMeter = new com.codahale.metrics.Meter();

        this.meter = getRuntimeContext()
                .getMetricGroup()
                .meter("myMeter", new DropwizardMeterWrapper(dropwizardMeter));
    }
}