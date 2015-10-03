package com.pr;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Test {

	double[] weights;
	int FEATURES = 3;
	int CLASS_TYPES = 0;
	int SAMPLES = 0;
	double THERSHOLD = 0;
	ArrayList<ArrayList<Double>> allFeaturList = new ArrayList<ArrayList<Double>>();
	ArrayList<Integer> classList = new ArrayList<Integer>();

	public Test(String name, double[] w) {
		System.out.println("algo  " + name);
		this.weights = w;
		createFeatureList();
		readFile();
		testDatas();
	}
	private void createFeatureList() {

		for (int i = 0; i < FEATURES; i++) {
			allFeaturList.add(new ArrayList<Double>());
		}
		System.out.println("size_featureList:  " + allFeaturList.size());
	}
	private void testDatas()
	{
		int right=0;
		for(int i=0;i<SAMPLES;i++)
		{
			int type=checkClass(i);
			if(type==classList.get(i))
				right++;
				
		}
		System.out.println("right "+right+" total "+ SAMPLES+" Percentage: "+((double)right/SAMPLES)*100);
	}

	private void readFile() {
		int count = 0;
		BufferedReader br = null;
		try {

			String sCurrentLine;

			br = new BufferedReader(new FileReader(
					"D://Android_workspace/Perspectron/TestPerceptron.txt"));

			while ((sCurrentLine = br.readLine()) != null) {
				if (!sCurrentLine.equals("")) {

					String[] splits = sCurrentLine.split("\\s+");
					//System.out.println("curline" + sCurrentLine + "   "
					//		+ splits.length);
					for (int i = 1; i <= FEATURES; i++) {
						Double f = Double.parseDouble(splits[i]);
						allFeaturList.get(i - 1).add(f);
					}
					int c = Integer.parseInt(splits[FEATURES + 1]);
					classList.add(c);

				}
				count++;
			}

			SAMPLES = count;
			// print
			/*for (int j = 0; j < count - 1; j++) {
				for (int i = 0; i < FEATURES; i++) {
					System.out.println("" + allFeaturList.get(i).get(j));
				}
				System.out.println("" + classList.get(j));
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
	private int checkClass(int samplePos)
	{
		double sum=0;
		for(int i=0;i<FEATURES;i++)
			sum+=weights[i]*allFeaturList.get(i).get(samplePos);
		//System.out.println("length"+ weights.length);
		sum+=weights[FEATURES];
		
		
		if(sum>THERSHOLD)
			return 1;
		else
			return 2;
	}

	
}
