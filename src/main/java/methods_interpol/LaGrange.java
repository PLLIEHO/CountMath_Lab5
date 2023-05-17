package methods_interpol;

import com.github.sh0nk.matplotlib4j.NumpyUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class LaGrange {
    public void prepare(double[] arr_x, double[] arr_y, double X, String function, double[] inter){
        int n = arr_x.length;
        List<Double> y = new ArrayList<>();
        List<Double> x = NumpyUtils.linspace(arr_x[0], arr_x[arr_x.length-1], 1000);
        for (Double aDouble : x) {
            y.add(countArg(aDouble, n, arr_x, arr_y));
        }

        System.out.println(countArg(X, n, arr_x, arr_y));
        PlotMaker maker = new PlotMaker();
        maker.make(x, y, arr_x, arr_y, function, inter);
    }

    private double countArg(double arg, int n, double[] arr_x, double[] arr_y){
        double y = 0;
        for(int i = 0; i<n; i++){
            double tempUp = 1;
            double tempDown = 1;
            for(int j=0; j<n; j++){
                if(i!=j) {
                    tempUp = tempUp * (arg - arr_x[j]);
                    tempDown = tempDown * (arr_x[i] - arr_x[j]);
                }
            }
            y += (tempUp/tempDown)*arr_y[i];
        }
        return y;
    }
}
