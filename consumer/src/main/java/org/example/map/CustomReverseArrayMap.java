package org.example.map;

import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.dropwizard.metrics.DropwizardMeterWrapper;
import org.apache.flink.metrics.Meter;
import org.example.utils.SomeAlgs;

import java.util.Arrays;
import java.util.Optional;

public class CustomReverseArrayMap extends RichMapFunction<String, String> {
    private Meter meter;

    @Override
    public String map(String s) {
        meter.markEvent();
        Optional<double[]> res = SomeAlgs.strToDArr(s);
        if (!res.isPresent()) return null;
        return Arrays.stream(res.get()).mapToObj(d->d+";").reduce((s1, s2)-> s1+s2).get();
    }

    @Override
    public void open(Configuration config) {
        com.codahale.metrics.Meter dropwizardMeter = new com.codahale.metrics.Meter();

        this.meter = getRuntimeContext()
                .getMetricGroup()
                .meter("myMeter", new DropwizardMeterWrapper(dropwizardMeter));
    }
}