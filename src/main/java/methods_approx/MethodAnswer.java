package methods_approx;

public class MethodAnswer {
    private int descriptor;
    private double[] P_x;
    private double[] e;
    private double midsqr;
    private double pirson;
    private double a;
    private double b;
    private double c;
    private double d;
    public MethodAnswer(){}

    public int getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(int descriptor) {
        this.descriptor = descriptor;
    }

    public double getMidsqr() {
        return midsqr;
    }

    public double getPirson() {
        return pirson;
    }

    public double[] getE() {
        return e;
    }

    public double[] getP_x() {
        return P_x;
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double getC() {
        return c;
    }

    public double getD() {
        return d;
    }

    public void setA(double a) {
        this.a = a;
    }

    public void setB(double b) {
        this.b = b;
    }

    public void setC(double c) {
        this.c = c;
    }

    public void setD(double d) {
        this.d = d;
    }

    public void setE(double[] e) {
        this.e = e;
    }

    public void setMidsqr(double midsqr) {
        this.midsqr = midsqr;
    }

    public void setP_x(double[] p_x) {
        P_x = p_x;
    }

    public void setPirson(double pirson) {
        this.pirson = pirson;
    }
}
