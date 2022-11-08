
import java.io.*;
import java.util.*;
import java.util.ArrayList;
import org.apache.commons.math3.distribution.FDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;

public class Q1 {
	public static void main(String[] args) throws IOException {
		//電腦讀取資料
	    FileReader fr = new FileReader("/Users/Jennifer/eclipse-workspace/FJU_AdvProgramming/Final/src/height_avg.csv"); 
	    BufferedReader br = new BufferedReader(fr);
	    String line, tempstring;
	    String [] tempArray = new String[5];
	    ArrayList myList = new ArrayList();
	    int i=0;
	    while((line = br.readLine())!=null){
	        tempstring = line; 
	        tempArray = tempstring.split("\\t"); 
	        for(i=0;i<tempArray.length;i++){
	        	myList.add(tempArray[i]);
	        }
	    }
	    //將string轉換為int以及double
	    int k = myList.size()/5; //計算資料總列數
	    int count = 0; //讓資料可以從每列的index[0]loop到index[4]
	    double [][] measure = new double[k][5]; //存放總計、男、女身高資料
	    int [] time = new int[k]; //存放學年度
	    int [] old = new int[k]; //存放年齡
	    for(int x=0;x<k;x++){
	    	time[x]=Integer.parseInt((String) myList.get(count++)); //將學年度string轉為int
	    	old[x]=Integer.parseInt((String) myList.get(count++)); //將年齡string轉為int
	      for(int y=2;y<5;y++){  //因為從index[2]開始才是身高資料，且一列有3筆身高資料
	    	  measure[x][y]=Double.parseDouble((String) myList.get(count++)); //將身高string轉換成double
	      }
	    }
	    
	    //使用者輸入資料
	    Scanner sc=new Scanner(System.in);
	    System.out.print("Please input the age(6~15):> ");
	    int user_old=sc.nextInt();
	    System.out.print("Please input the gender(Male:1/Female:2):> ");
	    int user_gender=sc.nextInt();
	    double [] height = new double[13]; //存放符合使用者輸入之年齡、性別的身高，為用於後續簡單迴歸預測的資料
	    int [] n = new int [13]; //存放符合年齡條件的身高index
	    int [] m = new int [13]; //存放符合年齡條件的學年度
	    int z = 0; //用於紀錄for迴圈中陣列的index
	    
	    //找出符合使用者輸入年齡的身高index、學年度資料，存入陣列
	    for(int x=0;x<k;x++) { //將符合年齡的身高index存入n陣列中
	    	if(old[x]==user_old) {
	    		n[z]=x;
	    		z+=1;
	    	}
	    }
	    z=0; //因為下面的判斷式會使用到z，故將z歸0
	    for(int x=0;x<k;x++) { //將符合年齡的學年度存入m陣列中
	    	if(old[x]==user_old) {
	    		m[z]=time[x];
	    		z+=1;
	    	}
	    }
	    z=0; //因為下面的判斷式會使用到z，故將z歸0
//	    for(int x=97;x<=109;x++) { //m資料儲存方法二
//	    	m[z]=x;
//	    	z+=1;
//	    }
	    
	    //根據使用者輸入的性別，搭配n陣列所存入的身高index，找出身高，並存入height陣列
	    System.out.printf("In the year of 2008~2020, %d-year-old ", user_old);
	    if(user_gender==1) { //性別為男生
	    	System.out.print("male's height:> ");
	    	for(int x=0;x<k;x++) {
	    		for(int y=2;y<5;y++){
	    			if(z<13) {
	    				height[z]=measure[n[z]][3];
		    			System.out.print(height[z]+" ");
		    			z+=1;
	    			}
	    		}
		    }
	    }
	    else { //性別為女生
	    	System.out.print("female's height:> ");
	    	for(int x=0;x<k;x++) {
	    		for(int y=2;y<5;y++){
	    			if(z<13) {
	    				height[z]=measure[n[z]][4];
		    			System.out.print(height[z]+" ");
		    			z+=1;
	    			}
	    		}
		    }
	    }
	    System.out.println("");
	    
	    //以height陣列的資料做簡單迴歸(預測身高(height)=β*學年度(m)+α)
	    Regression rg=new Regression(); 
	    //計算avg
	    rg.compute_sum(height, 13); 
	    double sum_height=rg.sum;
	    rg.compute_avg(height, 13); //身高(height陣列)的avg
	    double avg_height=rg.avg; 
	    rg.compute_sum(m, 13);
	    double sum_m=rg.sum;
	    rg.compute_avg(m, 13); //年份(m陣列)的avg
	    double avg_m=rg.avg; 
	    //計算Σxx、Σxy、Σyy
	    rg.compute_power(m, m, 13); //Σxx，年份(m陣列)和年份(m陣列)的相乘和
	    double power_mm=rg.power;
	    rg.compute_power(m, height, 13); //Σxy，年份(m陣列)和身高(height陣列)的相乘和
	    double power_mheight=rg.power;
	    rg.compute_power(height, height, 13); //Σyy，身高(height陣列)和身高(height陣列)的相乘和
	    double power_heightheight=rg.power;
	    //計算SSxx、SSxy、SSyy，以學年度為x、身高為ｙ
	    double ssxx=power_mm-13*avg_m*avg_m; //SSxx
	    double ssxy=power_mheight-13*avg_m*avg_height; //SSxy
	    double ssyy=power_heightheight-13*avg_height*avg_height; //SSyy
	    //計算簡單迴歸β和α
	    double beta=ssxy/ssxx; //β，簡單迴歸中的係數
	    double alpha=avg_height-beta*avg_m; //α，簡單迴歸中的截距項
	    double predict_height=alpha+beta*110; //110學年度預測身高
	    double r2=(ssxy*ssxy)/(ssxx*ssyy); //R平方
	    //輸出簡單迴歸結果
	    System.out.println("=========Height Prediction=========");
	    System.out.printf("Regression Model:> Height=%f*year+%f\n", beta, alpha);
	    if(user_gender==1)
	    	System.out.printf("Prediction of the %d-year-old male's height in 2021:> %f\n", user_old, predict_height);
	    else
	    	System.out.printf("Prediction of the %d-year-old male's height in 2021:> %f\n", user_old, predict_height);
	    System.out.printf("R Squared:> %f\n", r2);
	    
	    //H0:β等於0;H1:β不等於0。雙尾，α=0.05
	    //計算F-test
	    System.out.println("=========F-test Calculation(α=0.05)=========");
	    double sse=ssyy-beta*ssxy; //SSE
	    double sst=ssyy; //SST
	    double ssr=sst-sse; //SSR
	    double f=(ssr/1)/(sse/(13-2)); //F值
	    System.out.println("F-Test:> "+f);
	    FDistribution fd1 = new FDistribution(11,1);
	    double f_left = fd1.inverseCumulativeProbability(0.975); //左尾拒絕域
	    System.out.println("F-Distribution(Left Tail)(α=0.05):> "+1/f_left);
	    FDistribution fd2 = new FDistribution(1,11);
	    double f_right = fd2.inverseCumulativeProbability(0.975); //右尾拒絕域
	    System.out.println("F-Distribution(Right Tail)(α=0.05):> "+f_right);
	    //輸出F-test判斷結果
	    if(f>(1/f_left) && f<f_right) { //F值介於左尾和右尾，不拒絕虛無假說
	    	System.out.println("This model cannot predict accurately.");
	    }	
	    else { //F值小於左尾或大於右尾，拒絕虛無假說
	    	System.out.println("This model can predict accurately.");
	    }
	    
	    //計算p-value
	    System.out.println("=========p-value Calculation(α=0.05)=========");
	    double sigma2=sse/(13-2); //σ^2
	    double z_denom=Math.sqrt(sigma2/ssxx); //z-value的分母
	    double z_value=(beta-0)/z_denom;
	    System.out.println("z-value:> "+z_value);
	    NormalDistribution nd=new NormalDistribution();
	    double n_left=nd.cumulativeProbability(z_value);
	    double p_value=n_left*2;
	    System.out.println("p-value:> "+p_value);
	    //輸出p-value判斷結果
	    if(0.05<=p_value) { //α>p-value，拒絕虛無假說
	    	System.out.println("This model cannot predict accurately."); 
	    }	
	    else { //α<=p-value，不拒絕虛無假說
	    	System.out.println("This model can predict accurately.");
	    }

	    
	    
	    //輸出全部資料集
//	    System.out.println("");
//	    System.out.println("資料總筆數:> "+k);
//	    System.out.println("學年度 年齡  總計    男     女");
//	    System.out.println("------------------------------");
//	    for(int x=0;x<k;x++) {
//	    	System.out.printf("%3d  ",time[x]);
//	    	System.out.printf(" %2d  ",old[x]);
//	    	for(int y=2;y<5;y++) {
//	    		System.out.printf("%4.1f  ",measure[x][y]);
//	    	}
//	    	System.out.println("");
//	    }
	    
	}

}
