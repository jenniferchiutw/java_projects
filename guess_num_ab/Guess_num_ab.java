
//猜4個數字，任意兩位數不可重複，輸出幾A幾B
import java.util.Scanner;
public class Guess_num_ab {

	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		int [] ans=new int[4];
		boolean ok;
		
		
		//電腦產生數字
		do {
			ok=true;
			int temp=(int)(Math.random()*9000+1000); //1000~9999
			System.out.println("ans="+temp);
			for(int i=0;i<4;i++) { //迴圈執行4次，將各位數分離
				ans[i]=temp%10; //temp除以10，找出餘數。e.g. 1234/10，餘數=4。
				temp/=10; //temp值縮小10倍。e.g. 1234/10，商數=123。
			}
			for(int i=0;i<3&&ok==true;i++) //將數字進行比對，找出是否有兩個數字相同。e.g. i=[0]
				for(int j=i+1;j<4&&ok==true;j++) //e.g. j=[1]
					if(ans[i]==ans[j])
						ok=false; //發生數字重複，不ok
		}while(ok==false); //只要還不ok，就要重複迴圈
		
		
		//使用者猜數字
		int ttl=0, a=0, b=0; //紀錄得到幾a幾b
		int temp_guess;
		int [] guess=new int[4]; //用來保存玩家猜的答案
		do {
			System.out.println("Please input four numbers：>");
			temp_guess=sc.nextInt();
			for(int i=0;i<4;i++) {
				guess[i]=temp_guess%10;
				temp_guess/=10;
			}
			
			//判斷幾a幾b
			//計算ans和guess有幾個數字相等
			for(int i=0;i<4;i++)
				for(int j=0;j<4;j++)
					if(guess[i]==ans[j]) 
						ttl+=1;
			//判斷幾a
			for(int i=0;i<4;i++)
				if(guess[i]==ans[i]) {
					a+=1;
				}
			//判斷幾b
			b=ttl-a;
			
			
			//輸出幾a幾b
			System.out.printf("%da%db\n",a,b);
			//當情況不為4a時，ttl和a和b都歸0，重跑程式計算a和b
			if(a!=4) {
				ttl=0;a=0;b=0;
			}	
			
		}while(a!=4); //不為4a時，繼續跑程式	
		
	}

}
