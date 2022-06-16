package org.example.utils;

public class Generator {
/*
    public DoubleArrayGeneratorService(@Qualifier("kafkaDoubleArrayTemplate") KafkaTemplate<Long, double[]> template){
        this.template = template;
    }

    @Override public synchronized void startTask() {
        Supplier supplier = () -> {
            while (started) {
                try {
   
                    double[] array = getRArr();
                    template.send("double_array", 1L, array);
                    Thread.sleep(delay);
                } catch (InterruptedException ex) {
                    return "Thread was interrupted";
                }
            }
            return "Done";
        };
        startTask(supplier);
    }*/

    //Get random number in range [min;max)
    public static double getRNInRange(int min, int max){
        if (max == 0){
            return Math.random() - 1 - min;
        }
        return Math.random()*max - min;
    }

    //Get random array with size len and numbers in range [min;max)
    public static double[] getRArr(int len, int min, int max){
        double[] result = new double[len];
        for (int i = 0; i<len;i++){
            result[i] = getRNInRange(min, max);
        }
        return result;
    }

    //Get random array with size len and numbers in range [MinInt; MaxInt)
    public static double[] getRArr(int len){
        return getRArr(len, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    //Get random array with size in range [1;100_000) and numbers in range [MinInt; MaxInt)
    public static double[] getRArr(){
        return getRArr((int)getRNInRange(1,100_000));
    }
    public static char getRChar(){
        return (char) Generator.getRNInRange(32, 127);
    }

    public static String getRString(int len){
        StringBuilder res = new StringBuilder();
        for (int i = 0; i<len; i++){
            res.append(getRChar());
        }
        return res.toString();
    }

    public static String getRString(){
        return getRString((int) Generator.getRNInRange(5,1_000));
    }
}
