/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package stepdetection;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import weka.classifiers.Classifier;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ConverterUtils;
import android.content.res.AssetManager;
import android.util.Log;
import data.Complex;
import data.EntryInstance;
import data.SensorEntry;

/**
 *
 * @author Chalsy
 */
public class PredictionModule {
    
    private Classifier classifier;
    private int windowSize;
    private int CLASS_WALKING_INDEX = 1;
    private LinkedList<SensorEntry> entries;
    private Instances referenceData;
    private Boolean prediction;
    private StringBuffer sb;
    private int entryFrequency = 100;
    
    private AssetManager assetManager;
    
    public PredictionModule(AssetManager assetManager){
    	this.assetManager = assetManager;
    	sb = new StringBuffer("");
    	this.windowSize= 100;
    	this.referenceData = openFile("references.arff", 27);
    	loadClassifier("");
    }
    
    public Boolean getPrediction(){
        return prediction;
    }
    
    public Instances openFile(String fileName, int classIndex) {
        try {
            ConverterUtils.DataSource source = new ConverterUtils.DataSource(assetManager.open(fileName));
            Instances data = source.getDataSet();
            data.setClassIndex(classIndex-1);
            return data;
            
        } catch (Exception ex) {
        	Log.e("PredictionModule", ex.toString());
            //Logger.getLogger(PredictionModule.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void loadClassifier(String filename) {
    	classifier = new J48Classifier();
//        try {
//            System.out.println("loading classifier..." + filename);
//            Log.d("Prediction", "trying to load "+filename);
//            classifier = (Classifier) SerializationHelper.read(assetManager.open(filename));
//            Log.d("Prediction", "finished loading "+filename);
//        } catch (Exception ex) {
//            Logger.getLogger(PredictionModule.class.getName()).log(Level.SEVERE, null, ex);
//            System.err.println("loadclass " + filename);
//            Log.d("Prediction", "failed to load "+filename);
//        }
    }
    
    public Boolean predict(SummarizedEntry e) {
        Instances data = referenceData;
        int attrSize = referenceData.classIndex();
        EntryInstance inst = new EntryInstance(27); //hard coded the attribute size for now
        inst.setDataset(referenceData);
        if (refDataContains("Accx_mean"))
            inst.setValue(data.attribute("Accx_mean"), e.getAccx_mean());
        if(refDataContains("Accy_mean"))
            inst.setValue(data.attribute("Accy_mean"), e.getAccy_mean());
        if(refDataContains("Accz_mean"))
            inst.setValue(data.attribute("Accz_mean"), e.getAccz_mean());
        if(refDataContains("Gyrox_mean"))
            inst.setValue(data.attribute("Gyrox_mean"), e.getGyrox_mean());
        if(refDataContains("Gyroy_mean"))
            inst.setValue(data.attribute("Gyroy_mean"), e.getGyroy_mean());
        if(refDataContains("Gyroz_mean"))
            inst.setValue(data.attribute("Gyroz_mean"), e.getGyroz_mean());
        if(refDataContains("Accx_stddev"))
            inst.setValue(data.attribute("Accx_stddev"), e.getAccx_stddev());
        if(refDataContains("Accy_stddev"))
            inst.setValue(data.attribute("Accy_stddev"), e.getAccy_stddev());
        if(refDataContains("Accz_stddev"))
            inst.setValue(data.attribute("Accz_stddev"), e.getAccz_stddev());
        if(refDataContains("Gyrox_stddev"))
            inst.setValue(data.attribute("Gyrox_stddev"), e.getGyrox_stddev());
        if(refDataContains("Gyroy_stddev"))
            inst.setValue(data.attribute("Gyroy_stddev"), e.getGyroy_stddev());
        if(refDataContains("Gyroz_stddev"))
            inst.setValue(data.attribute("Gyroz_stddev"), e.getGyroz_stddev());
        if(refDataContains("Accx_energy"))
            inst.setValue(data.attribute("Accx_energy"), e.getAccx_energy());
        if(refDataContains("Accy_energy"))
            inst.setValue(data.attribute("Accy_energy"), e.getAccy_energy());
        if(refDataContains("Accz_energy"))
            inst.setValue(data.attribute("Accz_energy"), e.getAccz_energy());
        if(refDataContains("Gyrox_energy"))
            inst.setValue(data.attribute("Gyrox_energy"), e.getGyrox_energy());
        if(refDataContains("Gyroy_energy"))
            inst.setValue(data.attribute("Gyroy_energy"), e.getGyroy_energy());
        if(refDataContains("Gyrox_energy"))
            inst.setValue(data.attribute("Gyroz_energy"), e.getGyroz_energy());
        if(refDataContains("Accx_fdom"))
            inst.setValue(data.attribute("Accx_fdom"), e.getAccx_fdom());
        if(refDataContains("Accy_fdom"))
            inst.setValue(data.attribute("Accy_fdom"), e.getAccy_fdom());
        if(refDataContains("Accz_fdom"))
            inst.setValue(data.attribute("Accz_fdom"), e.getAccz_fdom());
        if(refDataContains("Gyrox_fdom"))
            inst.setValue(data.attribute("Gyrox_fdom"), e.getGyrox_fdom());
        if(refDataContains("Gyroy_fdom"))
            inst.setValue(data.attribute("Gyroy_fdom"), e.getGyroy_fdom());
        if(refDataContains("Gyroz_fdom"))
            inst.setValue(data.attribute("Gyroz_fdom"), e.getGyroz_fdom());
        if(refDataContains("Accmag_fdom"))
            inst.setValue(data.attribute("Accmag_fdom"), e.getAccmag_fdom());
        if(refDataContains("Gyromag_fdom"))
            inst.setValue(data.attribute("Gyromag_fdom"), e.getGyromag_fdom());
        
        sb.append(e.getAccx_mean()+",");
        sb.append(e.getAccx_stddev()+",");
        sb.append(e.getAccx_energy()+",");
        sb.append(e.getAccx_fdom()+",");
        sb.append(e.getAccy_mean()+",");
        sb.append(e.getAccy_stddev()+",");
        sb.append(e.getAccy_energy()+",");
        sb.append(e.getAccy_fdom()+",");
        sb.append(e.getAccz_mean()+",");
        sb.append(e.getAccz_stddev()+",");
        sb.append(e.getAccz_energy()+",");
        sb.append(e.getAccz_fdom()+",");
        sb.append(e.getGyrox_mean()+",");
        sb.append(e.getGyrox_stddev()+",");
        sb.append(e.getGyrox_energy()+",");
        sb.append(e.getGyrox_fdom()+",");
        sb.append(e.getGyroy_mean()+",");
        sb.append(e.getGyroy_stddev()+",");
        sb.append(e.getGyroy_energy()+",");
        sb.append(e.getGyroy_fdom()+",");
        sb.append(e.getGyroz_mean()+",");
        sb.append(e.getGyroz_stddev()+",");
        sb.append(e.getGyroz_energy()+"\n");
        sb.append(e.getGyroz_fdom()+",");
        sb.append(e.getAccmag_fdom()+",");
        sb.append(e.getGyromag_fdom()+",");
        
        // System.out.println("//////// PREDICTION ENTRY \n" +sb.toString());
        int index = -1;
        try {
            // double[] result = classifier.distributionForInstance(inst);
            // System.out.println(data.classAttribute());
            // int index = getHighestIndex(result);
            index = (int) classifier.classifyInstance(inst);
            System.out.println("predicted : " + index + " is " + data.attribute("Class").value(index));
            // System.out.println(index + " " + data.attribute("POStag").value(index));
            //tagList.add(data.attribute("POStag").value(index));
        } catch (Exception ex) {
            Logger.getLogger("prediction = " + PredictionModule.class.getName()).log(Level.SEVERE, null, ex);
        }
        if ( index == CLASS_WALKING_INDEX ){
            return true;
        }else{
            return false;
        }
    }
    
    public Boolean refDataContains(String attr){
        int size = referenceData.classIndex();
        attr = "@attribute " + attr + " numeric";
        Enumeration temp = referenceData.enumerateAttributes();
        while(temp.hasMoreElements()){
            String t = temp.nextElement().toString();
            if(t.equals(attr)){
                // System.out.println("\t\t TRUE contains " + attr);
                return true;
            }
        }
        return false;
    }
    
    public SummarizedEntry summarizeEntries (LinkedList<SensorEntry> entries){
        SummarizedEntry se = new SummarizedEntry();
        Double accx_mean = calculateMean(entries, 0);
        Double accy_mean = calculateMean(entries, 1);
        Double accz_mean = calculateMean(entries, 2);
        Double gyrox_mean = calculateMean(entries, 3);
        Double gyroy_mean = calculateMean(entries, 4);
        Double gyroz_mean = calculateMean(entries, 5);
        se.setAccx_mean(accx_mean);
        se.setAccy_mean(accy_mean);
        se.setAccz_mean(accz_mean);
        se.setGyrox_mean(gyrox_mean);
        se.setGyroy_mean(gyroy_mean);
        se.setGyroz_mean(gyroz_mean);
        se.setAccx_stddev(calculateStdDev(entries, 0, accx_mean));
        se.setAccy_stddev(calculateStdDev(entries, 1, accy_mean));
        se.setAccz_stddev(calculateStdDev(entries, 2, accz_mean));
        se.setGyrox_stddev(calculateStdDev(entries, 3, gyrox_mean));
        se.setGyroy_stddev(calculateStdDev(entries, 4, gyroy_mean));
        se.setGyroz_stddev(calculateStdDev(entries, 5, gyroz_mean));
        se.setAccx_energy(calculateEnergy(entries, 0));
        se.setAccy_energy(calculateEnergy(entries, 1));
        se.setAccz_energy(calculateEnergy(entries, 2));
        se.setGyrox_energy(calculateEnergy(entries, 3));
        se.setGyroy_energy(calculateEnergy(entries, 4));
        se.setGyroz_energy(calculateEnergy(entries, 5));
        se.setAccx_fdom(calculateDomFreq(entries, 0));
        se.setAccy_fdom(calculateDomFreq(entries, 1));
        se.setAccz_fdom(calculateDomFreq(entries, 2));
        se.setGyrox_fdom(calculateDomFreq(entries, 3));
        se.setGyroy_fdom(calculateDomFreq(entries, 4));
        se.setGyroz_fdom(calculateDomFreq(entries, 5));
        se.setAccmag_fdom(calculateAccMagDomFreq(entries));
        se.setGyromag_fdom(calculateGyroMagDomFreq(entries));
        //System.out.println(accx_mean+","+accy_mean+","+accz_mean+" ACC");
        //System.out.println(gyrox_mean+","+gyroy_mean+","+gyroz_mean+" GYRO");
        return se;
    }
    
    
    public Double fftbinFrequency(int bin_index){
        double freq = (bin_index * (entryFrequency/2))/(windowSize/2);
        return freq;
    }
    
    public Double calculateAccMagDomFreq(LinkedList<SensorEntry> tempWindow){
        FFT fft = new FFT();
        int size = tempWindow.size();
        Complex[] x = new Complex[size];
        
        for( int i = 0; i < size; i++ ){
            double norm = Math.sqrt(tempWindow.get(i).calculateAcc_norm());
            x[i] = new Complex(norm, 0);
        }
        x = ZeroPad.zeropad(x);
        Complex[] y = fft.fft(x);
        
        int length = y.length/2 + 1;
        double max = 0; 
        int index = -1;
        for ( int i = 1; i < length; i++ ){
            double mag = y[i].calculateMag();
            if ( mag > max ){
                max = mag;
                index = i;
            }
            // System.out.println("acc mag i is " + i + " max is " + max + " mag is " + mag + " index is " + index);
        }
        
        return fftbinFrequency(index);
    }
    
    public Double calculateGyroMagDomFreq(LinkedList<SensorEntry> tempWindow){
        FFT fft = new FFT();
        int size = tempWindow.size();
        Complex[] x = new Complex[size];
        
        for( int i = 0; i < size; i++ ){
            double norm = Math.sqrt(tempWindow.get(i).calculateGyro_norm());
            x[i] = new Complex(norm, 0);
        }
        x = ZeroPad.zeropad(x);
        Complex[] y = fft.fft(x);
        
        int length = y.length/2 + 1;
        double max = 0; 
        int index = -1;
        for ( int i = 1; i < length; i++ ){
            double mag = y[i].calculateMag();
            if ( mag > max ){
                max = mag;
                index = i;
            }
        }
        
        return fftbinFrequency(index);
    }
    
    public Double calculateDomFreq(LinkedList<SensorEntry> tempWindow, int sensorIndex){
        FFT fft = new FFT();
        int size = tempWindow.size();
        Complex[] x = new Complex[size];
        
        for( int i = 0; i < size; i++ ){
            x[i] = new Complex(tempWindow.get(i).getSensors().get(sensorIndex), 0);
        }
        x = ZeroPad.zeropad(x);
        Complex[] y = fft.fft(x);
        
        int length = y.length/2 +1;
        double max = 0; 
        int index = -1;
        for ( int i = 1; i < length; i++ ){
            double mag = y[i].calculateMag();
            if ( mag > max ){
                max = mag;
                index = i;
            }
            // System.out.println("i is " + i + " max is " + max + " mag is " + mag + " index is " + index);
        }
        
        return fftbinFrequency(index);
    }
    
    public Double calculateMean ( LinkedList<SensorEntry> tempWindow, int sensorIndex )
    {  
        Double average = 0.0;
        //System.out.println("SENSOR INDEX " + sensorIndex);
        for ( int i = 0; i < tempWindow.size(); i++ )
        {
            average += tempWindow.get(i).getSensors().get(sensorIndex);
            //System.out.print(tempWindow.get(i).getSensors().get(sensorIndex)+",");
        }
        //System.out.println("\n");
        return average / windowSize;
    }
    
    public Double calculateStdDev ( LinkedList<SensorEntry> tempWindow, int sensorIndex, Double mean )
    {
        Double stddev = 0.0;
        for ( int i = 0; i < tempWindow.size(); i++ )
        {
            stddev += Math.pow( ( tempWindow.get(i).getSensors().get(sensorIndex) - mean ), 2);
            // stddev += Math.pow( (tempSet.get(i).get(index) - mean), 2 );
        }
        stddev /= windowSize;
        
        return Math.sqrt(stddev);
    }
    
    public Double calculateEnergy ( LinkedList<SensorEntry> tempWindow, int sensorIndex ){
        Double energy = 0.0;
        for ( int i = 0; i < tempWindow.size(); i++ ){
            energy += Math.pow(tempWindow.get(i).getSensors().get(sensorIndex), 2);
        }
        return energy;
    }
    
    public LinkedList<SensorEntry> sublist(int windowSize, LinkedList<SensorEntry> tempEntries, int index){
        LinkedList<SensorEntry> temp = new LinkedList<SensorEntry>();
        for ( int i = 0; i < windowSize; i++ ){
            temp.add(tempEntries.get((index*windowSize)+i));
        }
        return temp;
    }
    
}

