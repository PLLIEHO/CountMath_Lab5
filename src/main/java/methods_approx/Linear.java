package methods_approx;

public class Linear implements Countable {

    public MethodAnswer prepare(double[] arr_x, double[] arr_y) {
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

        double midX = sx/n;
        double midY = sy/n;
        double pirsonUp = 0;
        double pirsonDown1 = 0;
        double pirsonDown2 = 0;

       for(int j = 0; j<n; j++){
           pirsonUp += (arr_x[j]-midX)*(arr_y[j]-midY);
           pirsonDown1 += Math.pow(arr_x[j]-midX, 2);
           pirsonDown2 += Math.pow(arr_y[j]-midY, 2);
       }

       double pirson = pirsonUp/(Math.sqrt(pirsonDown1*pirsonDown2));

       double b = (sxy-(sxx*(sy/sx)))/(sx-((n/sx)*sxx));
       double a = (sy/sx) - (n/sx)*b;
       MethodAnswer answer =  count(arr_x, arr_y, a, b);
       answer.setPirson(pirson);
       answer.setDescriptor(0);
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
            P_x[i] = arr_x[i]*a + b;
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
