import com.github.sh0nk.matplotlib4j.NumpyUtils;
import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;
import methods_approx.*;
import methods_interpol.Gauss;
import methods_interpol.LaGrange;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
//        double[] arr_x = {0.1, 0.2, 0.3, 0.4, 0.5};
//        double[] arr_y = {1.25, 2.38, 3.79, 5.44, 15.4};
//        double X = 0.28;
////        LaGrange grange = new LaGrange();
////        grange.prepare(arr_x, arr_y, X);
//        Gauss gauss = new Gauss();
//        gauss.prepare(arr_x, arr_y, 0.1, X);

        IOService service = new IOService();
        System.out.println("Введите 0, если хотите вводить с консоли. Введите 1, если хотите прочитать из файла. Введите 2, чтобы интерполировать готовую функцию.");
        Scanner sc = new Scanner(System.in);
        List<Double[]> arrs = new ArrayList<>();
        String line = sc.nextLine();
        int number = 0;
        try {
            number = Integer.parseInt(line);
            //arrs = consoleReading();
        } catch (NumberFormatException e){
            //arrs = fileReading(line);
            System.out.println("Ввод не распознан.");
            main(args);
        }

        double[] inter = new double[2];
        int n = 0;
        String i = null;
        switch (number){
            case 0:
                arrs = consoleReading();
                break;
            case 1:
                System.out.println("Введите полную ссылку на файл");
                arrs = fileReading(sc.nextLine());
                break;
            case 2:
                System.out.println("""
                        1. y = sin(x)
                        2. y = ln(x)
                        """);
                i = sc.nextLine();
                System.out.println("Введите интересующий интервал (сначала одно число, затем второе");
                try{
                    inter[0] =Double.parseDouble(sc.nextLine());
                    inter[1] = Double.parseDouble(sc.nextLine());
                    System.out.println("Введите количество точек (не менее 3-х)");
                    n = Integer.parseInt(sc.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Ввод не распознан");
                    main(args);
                }

                arrs = preparedFunctions(i, inter, n);

        }


        Double[] arr_xD = arrs.get(0);
        Double[] arr_yD = arrs.get(1);
        if(arr_yD.length!=arr_xD.length){
            System.out.println("Количество точек для x и y неодинаково.");
            main(args);
        }
        if(arr_yD.length<3){
            System.out.println("Точек слишком мало для интерполяции.");
            main(args);
        }
        double h = arr_xD[1] - arr_xD[0];
//        System.out.println(Arrays.toString(arr_xD));
//        System.out.println(h);
        if(number!=2) {
            for (int l = 0; l < arr_xD.length-1; l++) {
//                System.out.println(arr_xD[l+1]-arr_xD[l]);
                if (arr_xD[l+1]-arr_xD[l]-h>h/1000){
                    System.out.println("Шаг для значений x должен быть равномерным");
                    main(args);
                }
            }
        }
        double[] arr_x = new double[arr_xD.length];
        double[] arr_y = new double[arr_yD.length];
        for(int j = 0; j<arr_xD.length; j++){
            arr_x[j] = arr_xD[j];
            arr_y[j] = arr_yD[j];
        }
        System.out.println("Введите значение x, y которого будем вычислять:");
        Double X = 0.0;
        try {
            X = Double.parseDouble(sc.nextLine());
        } catch (NumberFormatException e){
            System.out.println("Ввод не распознан.");
            main(args);
        }
        if(number==2&&(X<inter[0]||X>inter[1])){
            System.out.println("У вас X в заданный интервал не влазит.");
            main(args);
        }
        System.out.println("Каким методом интерполировать будем?");
        System.out.println("""
                1. Лагранжа
                2. Гаусса
                """);
        switch (sc.nextLine()){
            case "1":
                LaGrange grange = new LaGrange();
                grange.prepare(arr_x, arr_y, X, i, inter);
                break;
            case "2":
                Gauss gauss = new Gauss();
                gauss.prepare(arr_x, arr_y, h, X, i, inter);
        }
    }

    private static List<Double[]> consoleReading(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите таблицу построчно, разделяя элементы пробелом.");


        List<Double[]> answer = new ArrayList<>();
        int counter = 0;
        while(counter<2) {
            List<Double> matrixLine = new ArrayList<>();
            String number = sc.nextLine();
            String delimeter = " ";
            String[] subStr = number.split(delimeter);
            try {
                for (int i = 0; i < subStr.length; i++) {
                    matrixLine.add(Double.parseDouble(subStr[i]));
                }
            } catch (NumberFormatException e){
                System.out.println("Формат ввода не распознан. Повторите.");
                consoleReading();
            }
            Double[] tempArray = matrixLine.toArray(new Double[0]);
            answer.add(tempArray);
            counter++;
        }
        return  answer;
    }

    private static List<Double[]> fileReading(String line) throws FileNotFoundException {
        File text = new File(line);
        Scanner sc = new Scanner(text);

        List<Double[]> answer = new ArrayList<>();
        int counter = 0;
        while(counter<2) {
            List<Double> matrixLine = new ArrayList<>();
            String number = sc.nextLine();
            String delimeter = " ";
            String[] subStr = number.split(delimeter);
            try {
                for (int i = 0; i < subStr.length; i++) {
                    matrixLine.add(Double.parseDouble(subStr[i]));
                }
            } catch (NumberFormatException e){
                System.out.println("Формат ввода не распознан. Повторите.");
                System.exit(1);
            }
            Double[] tempArray = matrixLine.toArray(new Double[0]);
            answer.add(tempArray);
            counter++;
        }
        return  answer;
    }

    private static List<Double[]> preparedFunctions(String i,double[] inter , int n){
        List<Double> x = null;
        List<Double> y = new ArrayList<>();
        switch(i){
            case "1":
                x = NumpyUtils.linspace(inter[0], inter[1], n);
                for(Double j : x){
                    y.add(Math.sin(j));
                }
                break;
            case "2":
                if(inter[1]<=0){
                    System.out.println("На введённом интервале функции не существует");
                    System.exit(0);
                }
                if(inter[0]>0.15){
                    x = NumpyUtils.linspace(inter[0], inter[1], n);
                    for(Double j : x){
                        y.add(Math.log(j));
                    }
                } else {
                    x = NumpyUtils.linspace(0.15, inter[1], n);
                    for (Double j : x) {
                        y.add(Math.log(j));
                    }
                }
                break;
            case "3":
                x = NumpyUtils.linspace(inter[0], inter[1], n);
                for(Double j : x){
                    y.add(Math.exp(j));
                }
                break;
            default:
                System.out.println("Ввод не распознан.");
                System.exit(0);
                break;
        }
        Double[] arr_xD = x.toArray(new Double[0]);
        Double[] arr_yD = y.toArray(new Double[0]);
        List<Double[]> answer = new ArrayList<>();
        answer.add(arr_xD);
        answer.add(arr_yD);
        System.out.println(Arrays.toString(arr_xD));
        System.out.println(Arrays.toString(arr_yD));
        return answer;
    }
}