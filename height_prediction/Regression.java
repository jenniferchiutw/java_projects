

//用於計算簡單迴歸計算所需數值
public class Regression {
	double avg;
	double sum;
	double power;
	double var;
	double sd;
	
	public void compute_sum(double [] a, int n) { //計算陣列總和
		sum=0;
		for(int i=0;i<n;i++)
			sum+=a[i];
	}
	
	public void compute_sum(int [] a, int n) { //計算陣列總和
		sum=0;
		for(int i=0;i<n;i++)
			sum+=a[i];
	}
	
	public void compute_avg(double [] a, int n) { //計算陣列平均值
		avg=sum/n;
	}
	
	public void compute_avg(int [] a, int n) { //計算陣列平均值
		avg=sum/n;
	}
	
	public void compute_power(double [] a, double [] b, int n) { //計算陣列x*x,y*y,x*y
		power=0;
		for(int i=0;i<n;i++)
			power+=(a[i]*b[i]);
	}
	
	public void compute_power(int [] a, int [] b, int n) { //計算陣列x*x,y*y,x*y
		power=0;
		for(int i=0;i<n;i++)
			power+=(a[i]*b[i]);
	}
	
	public void compute_power(int [] a, double [] b, int n) { //計算陣列x*x,y*y,x*y
		power=0;
		for(int i=0;i<n;i++)
			power+=(a[i]*b[i]);
	}
	
	public void compute_var(double [] a, int n) { //計算陣列變異數
		for(int i=0;i<n;i++)
			var+=((a[i]-avg)*(a[i]-avg));
		var=var/n;
	}
	
	public void compute_sd(double [] a, int n) { //計算陣列標準差
		sd=Math.sqrt(var);
	}

}
