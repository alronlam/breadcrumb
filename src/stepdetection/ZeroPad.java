/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stepdetection;

import data.Complex;

/**
 *
 * @author Chalsy
 */
public class ZeroPad {
    
    public ZeroPad(){
        
    }
    /*
    public static double[] zeropad(double[] real){
        int length = real.length;
        double ld = Math.log(length) / Math.log(2.0);
        if (((int) ld) - ld != 0) {
            int exponent = (int)ld + 1;
            int pad = (int) Math.pow(2, exponent) - length;

            System.out.println("length is " + length + " exponent is " + exponent + " pad is " + pad);

            double[] newReal = new double[(int)Math.pow(2, exponent)];
            int ctr = 0;
            for ( ctr = 0; ctr < length; ctr++ ){
                newReal[ctr] = real[ctr];
            }
            for ( int i = 0; i < pad; i++ ){
                newReal[ctr+i] = 0.0;
            }

            for ( int i = 0; i < newReal.length; i++ ){
                System.out.println(newReal[i]);
            }

            return newReal;
        }
        return real;
    }
    */
    public static Complex[] zeropad(Complex[] values){
        int length = values.length;
        double ld = Math.log(length) / Math.log(2.0);
        //System.out.println("length is " + length + " and ld is " + ld);
        if (((int) ld) - ld != 0) {
            int exponent = (int)ld + 1;
            int pad = (int) (Math.pow(2, exponent)*2) - length;

            //System.out.println("length is " + length + " exponent is " + exponent + " pad is " + pad);

            //double[] newReal = new double[(int)Math.pow(2, exponent)];
            Complex[] newReal = new Complex[(int)Math.pow(2, exponent)*2];
            int ctr = 0;
            for ( ctr = 0; ctr < length; ctr++ ){
                // newReal[ctr] = values[ctr].re();
                newReal[ctr] = new Complex(values[ctr].re(), values[ctr].im());
            }
            for ( int i = 0; i < pad; i++ ){
                // newReal[ctr+i] = 0.0;
                newReal[ctr+i] = new Complex(0.0, 0.0);
            }

//            for ( int i = 0; i < newReal.length; i++ ){
//                System.out.println(newReal[i].re());
//            }

            return newReal;
        }
        else{
            Complex[] newReal = new Complex[length*2];
            for ( int i = 0; i < length; i++ ){
                newReal[i] = values[i];
            } 
            for ( int i = length; i < length*2; i++ ){
                newReal[i] = new Complex(0.0,0.0);
            }
            return newReal;
        }
        // return values;
    }
    
    public static void main (String args[]){
        /*
        double[] sample = zeropad(new double[]{1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0});
        for( int i = 0; i < sample.length; i++ ){
            System.out.println(sample[i]);
        }
        */
//         FFTbase fftbase = new FFTbase();
//         fftbase.fft(new double[]{1.0, 2.0, 3.0, 1.0, 1.0}, new double[]{1.0, 2.0, 3.0, 1.0, 1.0}, true);
//         double[] real = fftbase.getRealValues();
//         double[] imag = fftbase.getImagValues();
//        int length = real.length;
//        for ( int i = 0; i < length; i++ ){
//            System.out.println(real[i]);
//        }
    }
}
