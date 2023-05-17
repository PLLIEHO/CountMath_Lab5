package methods_approx;

public class Logarifmic implements Countable{
    public MethodAnswer prepare(double[] arr_x0, double[] arr_y0) {
        double[] arr_x = new double[arr_x0.length];
        double[] arr_y = new double[arr_x0.length];
        for(int k = 0; k<arr_x0.length; k++){
            arr_x[k] = Math.log(arr_x0[k]);
            arr_y[k] = arr_y0[k];
        }
        int n = arr_x.length;
        double sx = 0;
        double sxx = 0;
        double sy = 0;
        double sxy = 0;

        for(int i = 0; i<n; i++){
            sx += arr_x[i];
            sxx += Math.pow(arr_x[i], 2);
            sy += arr_y[i];
            sxy += arr_x[i]*arr_y[i];
        }


        double b = (sxy-(sxx*(sy/sx)))/(sx-((n/sx)*sxx));
        double a = (sy/sx) - (n/sx)*b;
        //a = Math.exp(a);
        MethodAnswer answer =  count(arr_x0, arr_y0, a, b);
        answer.setPirson(0);
        answer.setDescriptor(4);
        answer.setA(a);
        answer.setB(b);
        return answer;
    }
    private MethodAnswer count(double[] arr_x, double[] arr_y, double a, double b){
        int n = arr_x.length;
        double[] P_x = new double[n];
        double[] e = new double[n];
        double tmp = 0;
        for(int i = 0; i<n; i++){
            P_x[i] = a*Math.log(arr_x[i])+b;
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
}
