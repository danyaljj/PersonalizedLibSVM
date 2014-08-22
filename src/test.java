// Sample toy code written for my own usage 
// July 2014 
// Daniel Khashabi 

import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_node;
import libsvm.svm_parameter;
import libsvm.svm_problem;


public class test {

	public static void main(String[] argc)
	{
		RegressionExample(); 
	}
	public static void RegressionExample()
	{ 
		System.out.println("Salam! This is a toy code! ");
	
		svm_parameter param = new svm_parameter();

		// default values
		param.svm_type = svm_parameter.EPSILON_SVR;
		param.kernel_type = svm_parameter.RBF;
		param.degree = 3;
		param.gamma = 1;
		param.coef0 = 0;
		param.nu = 1;
		param.cache_size = 40;
		param.C = 1;
		param.eps = 1e-4;
		param.p = 0.01;
		param.shrinking = 1;
		param.probability = 0;
		param.nr_weight = 0;
		
		//param.weight_label = new int[0];
		//param.weight = new double[0];

		svm_problem prob = new svm_problem();
		prob.l = 30; 
		prob.y = new double[prob.l];
		prob.x = new svm_node[prob.l][1];
		for(int i = 0; i < prob.l; i++)
		{
			prob.x[i][0] = new svm_node();
			//prob.x[i][1] = new svm_node();
			prob.x[i][0].index = 1;
			//prob.x[i][1].index = 2;
			prob.x[i][0].value = i * 0.2;  
			//prob.x[i][1].value = (i/2%2==0)?-1:1; 
			prob.y[i] = Math.sin(2 * i); 
			// System.out.println("X = [ " + prob.x[i][0].value + ", " + prob.x[i][1].value + " ] \t ->  " + prob.y[i] );
		}
		svm_model model = svm.svm_train(prob, param);

		int test_length = 20;
		for(double[] a: model.sv_coef){
			for (double b: a)
				System.out.println(b+" ");
		}
		
		// run on train data 
		for( int i = 0; i < prob.l; i++)
		{
			svm_node[] x_test = prob.x[i]; 
			double d = svm.svm_predict(model, x_test);
			System.out.println("X[0] = " + x_test[0].value + "\t\t\t Y = "
					+ Math.sin(2* i) + "\t\t\t The predicton = " + d);
		}
		
		// run on test data 
		for( int i = 0; i < test_length; i++)
		{
			svm_node[] x_test = new svm_node[1];
			x_test[0] = new svm_node(); 
			//x_test[1] = new svm_node(); 
			x_test[0].index = 1; 
			x_test[0].value = i * 0.2;
			
			//x_test[1].index = 2;
			//x_test[1].value = (i/2%2==0)?-1:1; 
			double d = svm.svm_predict(model, x_test);
			System.out.println("X[0] = " + x_test[0].value + "\t\t\t Y = "
					+ Math.sin(2* i) + "\t\t\t The predicton = " + d);
		}
	}
	
	public static void classificationExample() { 
		svm_parameter param = new svm_parameter();

		// default values
		param.svm_type = svm_parameter.C_SVC;
		param.kernel_type = svm_parameter.RBF;
		param.degree = 3;
		param.gamma = 1;
		param.coef0 = 0;
		param.nu = 1;
		param.cache_size = 40;
		param.C = 1;
		param.eps = 1e-2;
		param.p = 0.1;
		param.shrinking = 1;
		param.probability = 0;
		param.nr_weight = 0;/*
		param.weight_label = new int[0];
		param.weight = new double[0]; */
		
		svm_problem prob = new svm_problem();
		prob.l = 4; 
		prob.y = new double[prob.l];
		prob.x = new svm_node[prob.l][2];
		for(int i = 0; i < prob.l; i++)
		{
			prob.x[i][0] = new svm_node();
			prob.x[i][1] = new svm_node();
			prob.x[i][0].index = 1;
			prob.x[i][1].index = 2;
			prob.x[i][0].value = (i%2!=0)?-1:1; 
			prob.x[i][1].value = (i/2%2==0)?-1:1; 
			prob.y[i] = (prob.x[i][0].value == 1 && prob.x[i][1].value == 1)?1:-1;
			System.out.println("X = [ " + prob.x[i][0].value + ", " + prob.x[i][1].value + " ] \t ->  " + prob.y[i] );
		}
		svm_model model = svm.svm_train(prob, param);

		for(double[] a: model.sv_coef)
			for (double b: a)
				System.out.println(b+" ");
		
		
		int test_length = 4; 
		for( int i = 0; i < test_length; i++)
		{
			svm_node[] x_test = new svm_node[2];
			x_test[0] = new svm_node(); 
			x_test[1] = new svm_node(); 
			x_test[0].index = 1;
			x_test[0].value = (i%2!=0)?-1:1; 
			x_test[1].index = 2;
			x_test[1].value = (i/2%2==0)?-1:1; 
			double d = svm.svm_predict(model, x_test);
			System.out.println("X[0] = " + x_test[0].value + "  X[1] = " + x_test[1].value + "\t\t\t Y = "
					+ ((x_test[0].value == 1 && x_test[1].value == 1)?1:-1) + "\t\t\t The predicton = " + d);
		}
	}
}
