package com.pr;

import java.awt.geom.CubicCurve2D;
import java.awt.image.SampleModel;
import java.beans.FeatureDescriptor;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.event.RowSorterEvent;
import javax.swing.text.StyleContext.SmallAttributeSet;
import javax.xml.bind.annotation.XmlList;

public class RewardAndPunishment {

	int ITERATION = 100;
	double LEARNING_RATE = .7;
	double THERSHOLD = 0;

	int FEATURES = 0;
	int CLASS_TYPES = 0;
	int SAMPLES = 0;
	ArrayList<ArrayList<Double>> allFeaturList = new ArrayList<ArrayList<Double>>();
	ArrayList<Integer> classList = new ArrayList<Integer>();
	double[] weights;
	double[] tempWeights;
	Random random=new Random(-23);
	

	public RewardAndPunishment() {
		readFile();
		genWeights();
		learnPerception();
		
		
	}
	public void testData()
	{
		
	}
	private void genWeights()
	{
		
		weights=new double[FEATURES+1];
		tempWeights=new double[FEATURES+1];
		
		for(int i=0;i<=FEATURES;i++)
			weights[i]=randomInLimit(1,0);
		
		for(int i=0;i<=FEATURES;i++)
			tempWeights[i]=weights[i];
		
		printWeghts();
		
	}
	private void copyTemWeights()
	{
		for(int i=0;i<=FEATURES;i++)
			weights[i]=tempWeights[i];
	}
	private void printWeghts()
	{
		System.out.println("printing weights");
		for(int i=0;i<=FEATURES;i++)
			System.out.println(""+weights[i]);
	}

	private void readFile() {
		int count = 0;
		BufferedReader br = null;
		try {

			String sCurrentLine;

			br = new BufferedReader(new FileReader(
					"D://Android_workspace/Perspectron/TrainPerceptron.txt"));

			while ((sCurrentLine = br.readLine()) != null) {
				if (!sCurrentLine.equals("")) {
					if (count == 0) {
						String[] splits = sCurrentLine.split("\\s+");
						FEATURES = Integer.parseInt(splits[0]);
						CLASS_TYPES = Integer.parseInt(splits[1]);
						SAMPLES = Integer.parseInt(splits[2]);
						createFeatureList();

					} else {
						String[] splits = sCurrentLine.split("\\s+");
						System.out.println("curline" + sCurrentLine + "   "
								+ splits.length);
						for (int i = 1; i <= FEATURES; i++) {
							Double f = Double.parseDouble(splits[i]);
							allFeaturList.get(i-1).add(f);
						}
						int c = Integer.parseInt(splits[FEATURES + 1]);
						classList.add(c);

					}
					count++;
				}

			}
			SAMPLES=count-1;
			//print
			/*for (int j = 0; j < count-1; j++) {
				for (int i = 0; i < FEATURES; i++) {
					System.out.println(""+allFeaturList.get(i).get(j));
				}
			System.out.println(""+ classList.get(j));
				System.out.println();
			}*/

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}
	private void learnPerception()
	{
		int iteration=0;
		
		//System.out.println("featires "+FEATURES);
		
		while(true)
		{
			//printWeghts();
			double gError=0;
			for(int i=0;i<SAMPLES;i++)
			{
				int c=getItemClass(i);
				
				int error=c-classList.get(i);
				 //System.out.println("Lerror"+ error+ "   "+"o class"+ classList.get(i)+" now_class"+ c);
				
				for(int j=0;j<FEATURES;j++)
					weights[j] +=LEARNING_RATE*error*allFeaturList.get(j).get(i);
				weights[FEATURES]+=LEARNING_RATE*error;
				
				gError+=(error*error);
			}
			//copyTemWeights();
		    System.out.println("error "+ gError);
		    iteration++;
		    
		    if( gError==0 || iteration>100)
		    	break;
		}
		//printWeghts();
		Test test=new Test("reward andpunishment", weights);
		
	}
	private int  getItemClass(int samplePos)
	{
		//printWeghts();
		double sum=0;
		for(int i=0;i<FEATURES;i++)
			sum+=weights[i]*allFeaturList.get(i).get(samplePos);
		
		sum+=weights[FEATURES];
		
		
		if(sum>0)
			return 1;
		else
			return 2;
			 
		
	}

	private void createFeatureList() {

		for (int i = 0; i < FEATURES; i++) {
			allFeaturList.add(new ArrayList<Double>());
		}
		System.out.println("size_featureList" + allFeaturList.size());
	}
	private double randomInLimit(int max, int min)
	{
		double rand=random.nextDouble()*(max-min);
		
		return rand;
	}

}
