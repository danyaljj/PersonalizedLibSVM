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
		// changing options : 
		/*
		 case 's':
					param.svm_type = atoi(argv[i]);
					break;
				case 't':
					param.kernel_type = atoi(argv[i]);
					break;
				case 'd':
					param.degree = atoi(argv[i]);
					break;
				case 'g':
					param.gamma = atof(argv[i]);
					break;
				case 'r':
					param.coef0 = atof(argv[i]);
					break;
				case 'n':
					param.nu = atof(argv[i]);
					break;
				case 'm':
					param.cache_size = atof(argv[i]);
					break;
				case 'c':
					param.C = atof(argv[i]);
					break;
				case 'e':
					param.eps = atof(argv[i]);
					break;
				case 'p':
					param.p = atof(argv[i]);
					break;
				case 'h':
					param.shrinking = atoi(argv[i]);
					break;
				case 'b':
					param.probability = atoi(argv[i]);
					break;
				case 'w':
					++param.nr_weight;
					{
						int[] old = param.weight_label;
						param.weight_label = new int[param.nr_weight];
						System.arraycopy(old,0,param.weight_label,0,param.nr_weight-1);
					}

					{
						double[] old = param.weight;
						param.weight = new double[param.nr_weight];
						System.arraycopy(old,0,param.weight,0,param.nr_weight-1);
					}
		
					param.weight_label[param.nr_weight-1] = atoi(argv[i-1].substring(2));
					param.weight[param.nr_weight-1] = atof(argv[i]);
					break;
				default:
					System.err.print("unknown option\n");
		*/
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
