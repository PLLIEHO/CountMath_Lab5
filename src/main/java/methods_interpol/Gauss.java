package methods_interpol;

import com.github.sh0nk.matplotlib4j.NumpyUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.LongStream;


public class Gauss {
    public void prepare(double[] arr_x1, double[] arr_y1, double h, double X, String funct, double[] inter) {
        int n = arr_x1.length;
        int mid;
        double[] arr_x;
        double[] arr_y;
        if(n%2!=0){
            mid = (n-1)/2;
            arr_x = arr_x1;
            arr_y = arr_y1;
        } else {
            List<Double> temp = DoubleStream.of(arr_x1).boxed().collect(Collectors.toList());
            temp.remove(0);
            arr_x = new double[temp.size()];
            for(int i = 0; i<temp.size(); i++){
                arr_x[i] = temp.get(i);
            }
            List<Double> temp1 = DoubleStream.of(arr_y1).boxed().collect(Collectors.toList());
            temp1.remove(0);
            arr_y = new double[temp1.size()];
            for(int i = 0; i<temp1.size(); i++){
                arr_y[i] = temp1.get(i);
            }
            mid = (arr_x.length-1)/2;
            n = arr_x.length;
        }

        List<List<Double>> table = new ArrayList<>();
        List<Double> row_1 = new ArrayList<>();

        System.out.println("Таблица конечных разностей: ");
        for(int l = 0; l<n-1; l++){
            row_1.add(arr_y[l+1]-arr_y[l]);
        }
        table.add(row_1);
        int counter = n-2;
        for(int j = 0; j<n-1; j++){
            List<Double> row = new ArrayList<>();
            for(int k = 0; k<counter; k++){
                row.add(table.get(j).get(k+1)-table.get(j).get(k));
            }
            counter--;
            if(row.size()!=0) {
                table.add(row);
            }
        }

        List<List<Double>> newTable = new ArrayList<>();
        int yy = n-1;
        for(int m = 0; m<n-1; m++){
            List<Double> empty = new ArrayList<>();
            newTable.add(empty);
            for(int t = 0; t<yy; t++){
                newTable.get(m).add(table.get(t).get(m));
            }
            yy--;
        }
        table = newTable;
        tablePrint(arr_x, arr_y, table, n, mid);
        double t = (X-arr_x[mid])/h;
        System.out.println(" ");
        if(X>arr_x[mid]) {
            System.out.println(countForward(arr_y, table, n, t, mid));
        }
        else{
            System.out.println(countBackward(arr_y, table, n, t, mid));
        }

        List<Double> x = NumpyUtils.linspace(arr_x[0], arr_x[arr_x.length-1], 1000);
        //List<Double> x = DoubleStream.of(arr_x).boxed().collect(Collectors.toList());
        List<Double> y = new ArrayList<>();
        for (Double aDouble : x) {
            if(aDouble>arr_x[mid]) {
                t = (aDouble-arr_x[mid])/h;
                y.add(countForward(arr_y, table, n, t, mid));
            }
            else{
                t = (aDouble-arr_x[mid])/h;
                y.add(countBackward(arr_y, table, n, t, mid));
            }
        }
        PlotMaker maker = new PlotMaker();
        maker.make(x, y, arr_x, arr_y, funct, inter);
    }

    private double countForward(double[] arr_y, List<List<Double>> table, int n, double t, int mid){
        int counter = 2;
        double y = 0;
        y += arr_y[mid];
        y += t*table.get(mid).get(0);
        for(int i = mid-1; i>=0; i--){
            double up = 1;
            for(int j = -1; j>=i-mid; j--){
                up = up * (t+j) * (t-j);
            }
            double second = (up*t);
            double first = second/(t-(i-mid));
            first = first/(LongStream.rangeClosed(1, counter).reduce(1, (long a, long b) -> a * b));
            first = first*table.get(i).get(counter-1);
            counter++;
            if(counter+1>=n){
                y+=first;
                break;
            } else {
                second = second / (LongStream.rangeClosed(1, counter).reduce(1, (long a, long b) -> a * b));
                second = second * table.get(i).get(counter - 1);
                y += first + second;
                counter++;
            }
        }
        return y;
    }

    private double countBackward(double[] arr_y, List<List<Double>> table, int n, double t, int mid){
        int counter = 3;
        double y = 0;
        y += arr_y[mid];
        y += t*table.get(mid-1).get(0);
        y += ((t*(t+1))/2)*table.get(mid-1).get(1);
//        System.out.println(table.get(mid-1).get(0));
//        System.out.println(table.get(mid-1).get(1));
        for(int i = mid-2; i>=0; i--){
            double up = 1;
            for(int j = -1; j>=i-mid+1; j--){
                up = up * (t+j) * (t-j);
                System.out.println(t+j);
            }
            double first = (up*t);
            double second = first*(t-(i-mid));
//            System.out.println(t-(i-mid));
            first = first/(LongStream.rangeClosed(1, counter).reduce(1, (long a, long b) -> a * b));
            System.out.println(LongStream.rangeClosed(1, counter).reduce(1, (long a, long b) -> a * b));
            first = first*table.get(i).get(counter-1);
//            System.out.println(table.get(i).get(counter-1));
            counter++;
            second = second/(LongStream.rangeClosed(1, counter).reduce(1, (long a, long b) -> a * b));
            second = second*table.get(i).get(counter-1);
//            System.out.println(table.get(i).get(counter-1));
            y+=first+second;
            counter++;
        }
        return y;
    }

    private void tablePrint(double[] arr_x, double[] arr_y, List<List<Double>> table, int n, int mid){
        for(int i = 0; i<n; i++){
            System.out.print("X" + (i-mid) + " : " + arr_x[i] + "; Y" + (i-mid) + " : " + arr_y[i] + "; ");
            if(i!=n-1) {
                for (int j = 0; j < table.get(i).size(); j++) {
                    List<Double> row = table.get(i);
                    System.out.print("delta^" + (j+1) + "(" + (i - mid) + ")" + " : " + row.get(j) + "; ");
                }
                System.out.println(" ");
            }
        }
    }
}
