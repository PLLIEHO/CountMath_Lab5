package methods_interpol;

import com.github.sh0nk.matplotlib4j.NumpyUtils;
import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class PlotMaker {
    public void make(List<Double> x, List<Double> y, double[] arr_x, double[] arr_y, String funct, double[] inter){
        Plot plt = Plot.create();
        Thread plot = new Thread(() -> {
            plt.ylim(-10, 10);
            plt.plot().add(x, y);

//            List<Double> new_y = new ArrayList<>();
//            for (double v : arr_y) {
//                new_y.add(v);
//            }
//
//            List<Double> finalX2 = new ArrayList<>();
//            for(int k = 0; k<arr_x.length; k++){
//                finalX2.add(arr_x[k]);
//            }

            //List<Double> finalX2 = NumpyUtils.linspace(arr_x[0], arr_x[arr_x.length-1], arr_x.length);

            for(int j = 0; j<arr_y.length; j++){
                List<Double> oneX = new ArrayList<>();
                oneX.add(arr_x[j]-0.1);
                oneX.add(arr_x[j]);
                oneX.add(arr_x[j]+0.1);
                List<Double> oneY = new ArrayList<>();
                oneY.add(arr_y[j]);
                oneY.add(arr_y[j]);
                oneY.add(arr_y[j]);
                plt.plot().add(oneX, oneY);
            }

            if(funct!=null){
                List<Double> functX = null;
                List<Double> functY = new ArrayList<>();
                    switch (funct){
                        case "1":
                            functX = NumpyUtils.linspace(-100, 100, 200);
                            for(Double f : functX) {
                                functY.add(Math.sin(f));
                            }
                            break;
                        case "2":
                            functX = NumpyUtils.linspace(0.001, 100, 100);
                            for(Double f : functX) {
                                functY.add(Math.log(f));
                            }
                            break;
                        case "3":
                            functX = NumpyUtils.linspace(-100, 100, 200);
                            for(Double f : functX) {
                                functY.add(Math.exp(f));
                            }
                            break;
                }
                plt.plot().add(functX, functY);
            }

            try {
                plt.show();
            } catch (IOException | PythonExecutionException e) {
                System.out.println("warning");
                throw new RuntimeException(e);
            }
        });
        plot.start();
    }
}
