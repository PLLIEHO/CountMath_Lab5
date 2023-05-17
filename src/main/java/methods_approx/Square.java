package methods_approx;

import java.util.ArrayList;
import java.util.List;

public class Square implements Countable{
    public MethodAnswer prepare(double[] arr_x, double[] arr_y) {
        int n = arr_x.length;
        double sx = 0;
        double sxx = 0;
        double sxxx = 0;
        double sxxxx = 0;
        double sy = 0;
        double sxy = 0;
        double sxxy = 0;

        for(int i = 0; i<n; i++){
            sx += arr_x[i];
            sxx += Math.pow(arr_x[i], 2);
            sxxx += Math.pow(arr_x[i], 3);
            sxxxx += Math.pow(arr_x[i], 4);
            sy += arr_y[i];
            sxy += arr_x[i]*arr_y[i];
            sxxy += arr_x[i]*arr_x[i]*arr_y[i];
        }

        GaussSolving solving = new GaussSolving();
        solving.matrix = matrixFill(n, sx, sxx, sxxx, sxxxx, sy, sxy, sxxy);
        solving.size = 3;
        List<Double> coeff = solving.gaussTriangle(3);
        double a = coeff.get(2);
        double b = coeff.get(1);
        double c = coeff.get(0);
        MethodAnswer answer =  count(arr_x, arr_y, a, b, c);
        answer.setPirson(0);
        answer.setDescriptor(1);
        answer.setA(a);
        answer.setB(b);
        answer.setC(c);
        return answer;
    }
    private MethodAnswer count(double[] arr_x, double[] arr_y, double a, double b, double c){
        int n = arr_x.length;
        double[] P_x = new double[n];
        double[] e = new double[n];
        double tmp = 0;
        for(int i = 0; i<n; i++){
            P_x[i] = arr_x[i]*arr_x[i]*a + b*arr_x[i] + c;
            e[i] = P_x[i] - arr_y[i];
            tmp += Math.pow(e[i], 2);
        }
        double mdlsqr = Math.sqrt((tmp)/n);
        MethodAnswer answer = new MethodAnswer();
        answer.setE(e);
        answer.setP_x(P_x);
        answer.setMidsqr(mdlsqr);
        return answer;
    }

    private List<List<Double>> matrixFill(int n, double sx, double sxx, double sxxx, double sxxxx, double sy, double sxy, double sxxy){
        List<List<Double>> matrix = new ArrayList<>();
        List<Double> row = new ArrayList<>();
        List<Double> row1 = new ArrayList<>();
        List<Double> row2 = new ArrayList<>();
        row.add((double) n);
        row.add(sx);
        row.add(sxx);
        row.add(sy);
        matrix.add(row);

        row1.add(sx);
        row1.add(sxx);
        row1.add(sxxx);
        row1.add(sxy);
        matrix.add(row1);

        row2.add(sxx);
        row2.add(sxxx);
        row2.add(sxxxx);
        row2.add(sxxy);
        matrix.add(row2);
        return  matrix;
    }
}
