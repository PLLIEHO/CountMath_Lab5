import com.github.sh0nk.matplotlib4j.NumpyUtils;
import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;
import methods_approx.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class IOService {

    public void readFromConsole(double[] arr_x, double[] arr_y) {
        Linear linear = new Linear();
        Square square = new Square();
        Power power = new Power();
        Exponent exp = new Exponent();
        Logarifmic log = new Logarifmic();
        Coub coub = new Coub();
        //double[] arr_x = {1.1, 2.3, 3.7, 4.5, 5.4, 6.8, 7.5};
        //double[] arr_y = {2.73, 5.12, 7.74, 8.91, 10.59, 12.75, 13.43};
        MethodAnswer ansLin = linear.prepare(arr_x, arr_y);
        MethodAnswer ansSq = square.prepare(arr_x, arr_y);
        MethodAnswer ansPow = power.prepare(arr_x, arr_y);
        MethodAnswer ansExp = exp.prepare(arr_x, arr_y);
        MethodAnswer ansLog = log.prepare(arr_x, arr_y);
        MethodAnswer ansCoub = coub.prepare(arr_x, arr_y);
        MethodAnswer[] ansList = {ansLin, ansSq, ansPow, ansExp, ansLog, ansCoub};
        double min = ansLin.getMidsqr();
        MethodAnswer best = ansLin;
        for(int i = 1; i<6; i++){
            if(ansList[i].getMidsqr()<min){
                min = ansList[i].getMidsqr();
                best = ansList[i];
            }
        }
        Function<Double, Double> function = null;
        MethodAnswer finalBest = best;
        System.out.println(" ");
        System.out.print("X: ");
        System.out.println(Arrays.toString(arr_x));
        System.out.print("Y: ");
        System.out.println(Arrays.toString(arr_y));
        System.out.print("P_x: ");
        System.out.println(Arrays.toString(finalBest.getP_x()));
        System.out.print("e: ");
        System.out.println(Arrays.toString(finalBest.getE()));
        System.out.print("Среднеквадратичное отклонение: ");
        System.out.println(finalBest.getMidsqr());
        System.out.print("Descriptor: ");
        System.out.println(finalBest.getDescriptor());
        List<Double> x = null;
        switch(best.getDescriptor()){
            case 0:
                function = arg -> finalBest.getA()*arg + finalBest.getB();
                System.out.print("Pirson: ");
                System.out.println(finalBest.getPirson());
                x = NumpyUtils.linspace(arr_x[0], arr_x[arr_x.length-1], 100);
                break;
            case 1:
                function = arg -> finalBest.getA()*arg*arg + finalBest.getB()*arg + finalBest.getC();
                x = NumpyUtils.linspace(arr_x[0], arr_x[arr_x.length-1], 100);
                break;
            case 2:
                function = arg -> finalBest.getA()*Math.pow(arg, finalBest.getB());
                x = NumpyUtils.linspace(arr_x[0], arr_x[arr_x.length-1], 100);
                break;
            case 3:
                function = arg -> finalBest.getA()*Math.exp(arg * finalBest.getB());
                x = NumpyUtils.linspace(arr_x[0], arr_x[arr_x.length-1], 100);
                break;
            case 4:
                function = arg -> finalBest.getA()*Math.log(arg) + finalBest.getB();
                x = NumpyUtils.linspace(1.1, arr_x[arr_x.length-1], 100);
                break;
            case 5:
                function = arg -> finalBest.getA()*arg*arg*arg + finalBest.getB()*arg*arg + finalBest.getC()*arg + finalBest.getD();
                x = NumpyUtils.linspace(arr_x[0], arr_x[arr_x.length-1], 100);
                break;
            default:
                break;
        }
        Plot plt = Plot.create();
        List<Double> finalX = x;
        Function<Double, Double> finalFunction = function;
        Thread plot = new Thread(() -> {
            List<Double> y = finalX.stream().map(finalFunction).toList();
            plt.ylim(-10, 10);
            plt.plot().add(finalX, y);
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
