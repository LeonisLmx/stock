package com.app.stock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.charset.Charset;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StockApplicationTests {

    @Test
    public void contextLoads() throws Exception{
        int[][] arr={ {1,2,3,10},{4,5,6,11},{7,8,9,12},{2,3,6,5} };
        for(int i=0;i<arr.length;i++){
            for(int j=0;j<arr[i].length;j++){
                if(j == 1){
                    int temp = arr[i][j];
                    arr[i][j] = arr[i][arr[i].length-1];
                    arr[i][arr[i].length-1] = arr[i][j+1];
                    arr[i][j+1] = temp;
                }
            }
        }
    }

    @Test
    public void testArr(){
        System.out.println("1".compareTo("0"));
        System.out.println("-0.1".compareTo("0"));
//        int[][] arr={ {1,2,3,10},{4,5,6,11},{7,8,9,12},{2,3,6,5} };
//        arr = arrays(arr,1,3);
//        System.out.println(arr);
    }

    public int[][] arrays(int arr[][],int f1,int f2){
        for(int i=0;i<arr.length;i++){
            for(int j=0;j<arr.length;j++){
                if(f1 == j){
                    if(f1 < f2) {
                        int temp = arr[i][j];
                        arr[i][j] = arr[i][f2];
                        arr[i][f2] = temp;
                        test(j+1,arr,i,f2);
                    }else{
                        int temp = arr[i][f2];
                        arr[i][f2] = arr[i][f2+1];
                        arr[i][f2+1] = temp;
                        test(f2+1,arr,i,f1);
                    }
                }
            }
        }
        return arr;
    }

    public void test(int n,int arr[][],int i,int f2){
        if (n < f2) {
            int temp = arr[i][n];
            arr[i][n] = arr[i][f2];
            arr[i][f2] = temp;
            n++;
            test(n,arr,i,f2);
        }
    }

    @Test
    public void testJM(){
        String inStr = "7a90f1a4da8c487325b54f83e29429dd";
        char [] a = inStr.toCharArray();
        for  ( int  i =  0 ; i < a.length; i++) {
            a[i] = (char ) (a[i] ^  't' );
        }
        String k = new  String(a);
        System.out.println(k);
    }
}

