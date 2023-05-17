package methods_approx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GaussSolving {
    public List<List<Double>> matrix;
    public List<Double> answers = new ArrayList<>();
    public int size;
    private boolean flag = false;
    public List<Double> gaussTriangle(int n) {
        List<Double> temp = new ArrayList<>(n + 1);
        for (int z = 0; z < n-1; z++) {
            for (int i = z+1; i < n; i++) {
                if (matrix.get(z).get(z) == 0)  {
                    for(int m = z+1; m<n; m++) {
                        if (matrix.get(m).get(z) != 0) {
                            Collections.swap(matrix, z, m);
                            flag = true;
                        }
                    }
                    if(!flag){
                        z++;
                        System.out.println(z);
                        continue;
                    }
                }
                Double coefficient = -(matrix.get(i).get(z) / matrix.get(z).get(z));
                System.out.println(coefficient);
                for (int j = 0; j <= n; j++) {
                    temp.add(matrix.get(z).get(j) * coefficient);
                    Double el = matrix.get(i).get(j);
                    matrix.get(i).set(j, el + temp.get(j));
                }
                temp.clear();
            }
            System.out.println(Arrays.toString(matrix.toArray()));
        }
        return gaussResolving(size);
    }

    public List<Double> gaussResolving(int size){
        int n = size - 1;
        for(int k = 0; k<=n; k++){
            answers.add((double) 0);
        }
        if(matrix.get(n).get(size-1)!=0) {
            answers.set(n, matrix.get(n).get(size) /

                    matrix.get(n).get(size - 1));

        } else {
            answers.set(n, (double) 0);
        }
        for(int i = n-1; i>-1; i--){
            double x = matrix.get(i).get(size);
            for(int j = n; j>i; j--){
                x -= (matrix.get(i).get(j)) * answers.get(j);
            }
            x = x/matrix.get(i).get(i);
            answers.set(i, x);
        }
        return answers;
    }
}
