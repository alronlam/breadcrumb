package stepdetection;

import java.util.LinkedList;


public class SummarizedEntry {

    /*
      f.append ( "Number,"
                 + "Date,"
                 + "Orient_X Mean,"
                 + "Orient_X Std Dev,"
                 + "Orient_Y Mean,"
                 + "Orient_Y Std Dev,"
                 + "Orient_Z Mean,"
                 + "Orient_Z Std Dev,"
                 + "Gyro_X Mean,"
                 + "Gyro_X Std Dev,"
                 + "Gyro_Y Mean,"
                 + "Gyro_Y Std Dev,"
                 + "Gyro_Z Mean,"
                 + "Gyro_Z Std Dev,"
                 + "Mag_X Mean,"
                 + "Mag_X Std Dev,"
                 + "Mag_Y Mean,"
                 + "Mag_Y Std Dev,"
                 + "Mag_Z Mean,"
                 + "Mag_Z Std Dev,"
                 + "Acc_X Mean,"
                 + "Acc_X Std Dev,"
                 + "Acc_Y Mean,"
                 + "Acc_Y Std Dev,"
                 + "Acc_Z Mean,"
                 + "Acc_Z Std Dev,"
                 + "\n"
                 );
     */
    
    private int second;
    private String date;
    private double orientx_mean;
    private double orientx_stddev;
    private double orienty_mean;
    private double orienty_stddev;
    private double orientz_mean;
    private double orientz_stddev;
    private double gyrox_mean;
    private double gyrox_stddev;
    private double gyrox_energy;
    private double gyroy_mean;
    private double gyroy_stddev;
    private double gyroy_energy;
    private double gyroz_mean;
    private double gyroz_stddev;
    private double gyroz_energy;
    private double magx_mean;
    private double magx_stddev;
    private double magy_mean;
    private double magy_stddev;
    private double magz_mean;
    private double magz_stddev;
    private double accx_mean;
    private double accx_stddev;
    private double accx_energy;
    private double accy_mean;
    private double accy_stddev;
    private double accy_energy;
    private double accz_mean;
    private double accz_stddev;
    private double accz_energy;
    private double accx_fdom;
    private double accy_fdom;
    private double accz_fdom;
    private double accmag_fdom;
    private double gyrox_fdom;
    private double gyroy_fdom;
    private double gyroz_fdom;
    private double gyromag_fdom;
    private LinkedList<Double> sensors;

    public LinkedList<Double> getSensors() {
        return sensors;
    }

    public void setSensors(LinkedList<Double> sensors) {
        this.sensors = sensors;
    }
    
    public double getAccx_fdom() {
        return accx_fdom;
    }

    public void setAccx_fdom(double accx_fdom) {
        this.accx_fdom = accx_fdom;
    }

    public double getAccy_fdom() {
        return accy_fdom;
    }

    public void setAccy_fdom(double accy_fdom) {
        this.accy_fdom = accy_fdom;
    }

    public double getAccz_fdom() {
        return accz_fdom;
    }

    public void setAccz_fdom(double accz_fdom) {
        this.accz_fdom = accz_fdom;
    }

    public double getAccmag_fdom() {
        return accmag_fdom;
    }

    public void setAccmag_fdom(double accmag_fdom) {
        this.accmag_fdom = accmag_fdom;
    }

    public double getGyromag_fdom() {
        return gyromag_fdom;
    }

    public void setGyromag_fdom(double accmag_fdom) {
        this.gyromag_fdom = accmag_fdom;
    }
    
    public double getGyrox_fdom() {
        return gyrox_fdom;
    }

    public void setGyrox_fdom(double gyrox_fdom) {
        this.gyrox_fdom = gyrox_fdom;
    }

    public double getGyroy_fdom() {
        return gyroy_fdom;
    }

    public void setGyroy_fdom(double gyroy_fdom) {
        this.gyroy_fdom = gyroy_fdom;
    }

    public double getGyroz_fdom() {
        return gyroz_fdom;
    }

    public void setGyroz_fdom(double gyroz_fdom) {
        this.gyroz_fdom = gyroz_fdom;
    }
    
    public double getAccx_mean() {
        return accx_mean;
    }

    public void setAccx_mean(double accx_mean) {
        this.accx_mean = accx_mean;
    }

    public double getAccx_stddev() {
        return accx_stddev;
    }

    public void setAccx_stddev(double accx_stddev) {
        this.accx_stddev = accx_stddev;
    }

    public double getAccy_mean() {
        return accy_mean;
    }

    public void setAccy_mean(double accy_mean) {
        this.accy_mean = accy_mean;
    }

    public double getAccy_stddev() {
        return accy_stddev;
    }

    public void setAccy_stddev(double accy_stddev) {
        this.accy_stddev = accy_stddev;
    }

    public double getAccz_mean() {
        return accz_mean;
    }

    public void setAccz_mean(double accz_mean) {
        this.accz_mean = accz_mean;
    }

    public double getAccz_stddev() {
        return accz_stddev;
    }

    public void setAccz_stddev(double accz_stddev) {
        this.accz_stddev = accz_stddev;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getGyrox_mean() {
        return gyrox_mean;
    }

    public void setGyrox_mean(double gyrox_mean) {
        this.gyrox_mean = gyrox_mean;
    }

    public double getGyrox_stddev() {
        return gyrox_stddev;
    }

    public void setGyrox_stddev(double gyrox_stddev) {
        this.gyrox_stddev = gyrox_stddev;
    }

    public double getGyroy_mean() {
        return gyroy_mean;
    }

    public void setGyroy_mean(double gyroy_mean) {
        this.gyroy_mean = gyroy_mean;
    }

    public double getGyroy_stddev() {
        return gyroy_stddev;
    }

    public void setGyroy_stddev(double gyroy_stddev) {
        this.gyroy_stddev = gyroy_stddev;
    }

    public double getGyroz_mean() {
        return gyroz_mean;
    }

    public void setGyroz_mean(double gyroz_mean) {
        this.gyroz_mean = gyroz_mean;
    }

    public double getGyroz_stddev() {
        return gyroz_stddev;
    }

    public void setGyroz_stddev(double gyroz_stddev) {
        this.gyroz_stddev = gyroz_stddev;
    }

    public double getGyrox_energy() {
        return gyrox_energy;
    }

    public void setGyrox_energy(double gyrox_energy) {
        this.gyrox_energy = gyrox_energy;
    }

    public double getGyroy_energy() {
        return gyroy_energy;
    }

    public void setGyroy_energy(double gyroy_energy) {
        this.gyroy_energy = gyroy_energy;
    }

    public double getGyroz_energy() {
        return gyroz_energy;
    }

    public void setGyroz_energy(double gyroz_energy) {
        this.gyroz_energy = gyroz_energy;
    }

    public double getAccx_energy() {
        return accx_energy;
    }

    public void setAccx_energy(double accx_energy) {
        this.accx_energy = accx_energy;
    }

    public double getAccy_energy() {
        return accy_energy;
    }

    public void setAccy_energy(double accy_energy) {
        this.accy_energy = accy_energy;
    }

    public double getAccz_energy() {
        return accz_energy;
    }

    public void setAccz_energy(double accz_energy) {
        this.accz_energy = accz_energy;
    }
    
    public double getMagx_mean() {
        return magx_mean;
    }

    public void setMagx_mean(double magx_mean) {
        this.magx_mean = magx_mean;
    }

    public double getMagx_stddev() {
        return magx_stddev;
    }

    public void setMagx_stddev(double magx_stddev) {
        this.magx_stddev = magx_stddev;
    }

    public double getMagy_mean() {
        return magy_mean;
    }

    public void setMagy_mean(double magy_mean) {
        this.magy_mean = magy_mean;
    }

    public double getMagy_stddev() {
        return magy_stddev;
    }

    public void setMagy_stddev(double magy_stddev) {
        this.magy_stddev = magy_stddev;
    }

    public double getMagz_mean() {
        return magz_mean;
    }

    public void setMagz_mean(double magz_mean) {
        this.magz_mean = magz_mean;
    }

    public double getMagz_stddev() {
        return magz_stddev;
    }

    public void setMagz_stddev(double magz_stddev) {
        this.magz_stddev = magz_stddev;
    }

    public double getOrientx_mean() {
        return orientx_mean;
    }

    public void setOrientx_mean(double orientx_mean) {
        this.orientx_mean = orientx_mean;
    }

    public double getOrientx_stddev() {
        return orientx_stddev;
    }

    public void setOrientx_stddev(double orientx_stddev) {
        this.orientx_stddev = orientx_stddev;
    }

    public double getOrienty_mean() {
        return orienty_mean;
    }

    public void setOrienty_mean(double orienty_mean) {
        this.orienty_mean = orienty_mean;
    }

    public double getOrienty_stddev() {
        return orienty_stddev;
    }

    public void setOrienty_stddev(double orienty_stddev) {
        this.orienty_stddev = orienty_stddev;
    }

    public double getOrientz_mean() {
        return orientz_mean;
    }

    public void setOrientz_mean(double orientz_mean) {
        this.orientz_mean = orientz_mean;
    }

    public double getOrientz_stddev() {
        return orientz_stddev;
    }

    public void setOrientz_stddev(double orientz_stddev) {
        this.orientz_stddev = orientz_stddev;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }
}
