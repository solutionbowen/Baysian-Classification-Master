/**
*作者: Bo-Wen Duan (Paul)
*聯絡方式: bowenduan618@gmail.com
*修改日期: 2016 / 1 / 12
*/
package BaysianClassificationMain;
import java.io.*;
public class BaysianClassification {
	private final static String InputFileName = "iris.arff";      //測試資料檔名
	private final static String MVInputFileName = "iris_mean_variable.txt";
	private final static String OutputFileName = "BaysianClassificationResult.txt";  //Mining後結果輸出檔名
	private final static double sepallength[] = new double[150];
	private final static double sepalwidth[] = new double[150];
	private final static double petallength[] = new double[150];
	private final static double petalwidth[] = new double[150];
	private final static String class1[] = new String[150];
	private final static double mean_variable[][] = new double[8][3];
	private final static String Result[] = new String[150];
	private final static double two_pi_sqrt = Math.sqrt(2*(Math.PI));
	private final static double exp = Math.E;
	private  static int Data;
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(InputFileName));  //FileReader的裝飾類(BufferedReader)
		BufferedReader br1 = new BufferedReader(new FileReader(MVInputFileName));  //FileReader的裝飾類(BufferedReader)
        BufferedWriter bw = new BufferedWriter(new FileWriter(OutputFileName)); //FileWriter的裝飾類(BufferedWriter)
        String Line,TempString,Line1,TempString1;     //宣告字串形態變數(Line,TempString)
        String[] TempArray = new String[4];  //宣告字串陣列TempArray，陣列大小為資料筆的屬性數
        String[] TempArray1 = new String[3];
        int datacount = 0;
        int datacount1 = 0;
        while((Line = br.readLine())!=null){  //將資料一行一行讀進來，用Line接收每一行資料，直到讀到的是null則跳出迴圈
        	TempString = Line; //用TempString接收Line的每一行內容
        	TempArray = TempString.split(","); //將TempString(每一行資料)以","作為切割點進行切割，存到TempArray陣列中
             for(int i=0 ; i<TempArray.length ; i++){  //將TempArray的資料依序存到TestDataList中
            	if(i==0)
            	   sepallength[datacount] = Double.parseDouble(TempArray[i]);
            	if(i==1)
            	   sepalwidth[datacount] = Double.parseDouble(TempArray[i]);
            	if(i==2)
            	   petallength[datacount] = Double.parseDouble(TempArray[i]);
            	if(i==3)
            	   petalwidth[datacount] = Double.parseDouble(TempArray[i]);
            	class1[datacount] = TempArray[i];
             }
             datacount++;
        }
        Data = datacount;
        while((Line1 = br1.readLine())!=null){  //將資料一行一行讀進來，用Line接收每一行資料，直到讀到的是null則跳出迴圈
        	TempString1 = Line1; //用TempString接收Line的每一行內容
        	TempArray1 = TempString1.split(","); //將TempString(每一行資料)以","作為切割點進行切割，存到TempArray陣列中
            for(int y = 0 ; y < TempArray1.length ; y++){
              mean_variable[datacount1][y] = Double.parseDouble(TempArray1[y]); 
            }
            datacount1++;
        }
		bw.write("isis資料集屬性及類別表");  bw.newLine();
		bw.write("    sepallength\t    sepalwidth\t    petallength\t    petalwidth\t            Class"); 
		bw.newLine();
		for(int i=0;i<datacount;++i){
			bw.write((i+1)+"\t"+sepallength[i]+"\t\t"+sepalwidth[i]+"\t\t"+petallength[i]+
					"\t\t"+petalwidth[i]+"\t\t"+class1[i]);
			bw.newLine();
		}
		bw.newLine();
		bw.write("isis資料集屬性對應各類別的平均以及標準差"); bw.newLine();
		bw.write(" Iris-setosa\t            Iris-versicolor               Iris-virginica"); bw.newLine();
		for(int x = 0 ; x < mean_variable.length ; x++){  
			   for(int y = 0 ; y < mean_variable[x].length ; y++){
				   bw.write("   "+mean_variable[x][y]+"  \t\t"); 
	           }
			   bw.newLine();
			}
		compu_iris();
		for(int x = 0 ; x < Result.length ; x++){
			bw.write("第" + (x+1) + "筆資料類別為" + Result[x]);
			bw.newLine();
		}
		br.close();
		br1.close();
		bw.close();
		sop("Mining結束，請打開"+ OutputFileName +"查看Mining結果");
	}
	public static void sop(Object obj){  //列印的function
    	System.out.println(obj);
    }
	public static void compu_iris(){
		double prob_Iris_setosa = 0.0;
		double prob_Iris_versicolor = 0.0;
		double prob_Iris_virginica = 0.0;
		for(int i = 0 ; i< Data ; i++){
			prob_Iris_setosa = (1/(two_pi_sqrt*mean_variable[1][0]))*
					Math.pow(exp,((-0.5)*Math.pow(((sepallength[i]-mean_variable[0][0])/mean_variable[1][0]), 2)))*
					(1/(two_pi_sqrt*mean_variable[3][0]))*
					Math.pow(exp,((-0.5)*Math.pow(((sepalwidth[i]-mean_variable[2][0])/mean_variable[3][0]), 2)))*
					(1/(two_pi_sqrt*mean_variable[5][0]))*
					Math.pow(exp,((-0.5)*Math.pow(((petallength[i]-mean_variable[4][0])/mean_variable[5][0]), 2)))*
					(1/(two_pi_sqrt*mean_variable[7][0]))*
					Math.pow(exp,((-0.5)*Math.pow(((petalwidth[i]-mean_variable[6][0])/mean_variable[7][0]), 2)));
		  
			prob_Iris_versicolor = (1/(two_pi_sqrt*mean_variable[1][1]))*
					Math.pow(exp,((-0.5)*Math.pow(((sepallength[i]-mean_variable[0][1])/mean_variable[1][1]), 2)))*
					(1/(two_pi_sqrt*mean_variable[3][1]))*
					Math.pow(exp,((-0.5)*Math.pow(((sepalwidth[i]-mean_variable[2][1])/mean_variable[3][1]), 2)))*
					(1/(two_pi_sqrt*mean_variable[5][1]))*
					Math.pow(exp,((-0.5)*Math.pow(((petallength[i]-mean_variable[4][1])/mean_variable[5][1]), 2)))*
					(1/(two_pi_sqrt*mean_variable[7][1]))*
					Math.pow(exp,((-0.5)*Math.pow(((petalwidth[i]-mean_variable[6][1])/mean_variable[7][1]), 2)));
			
			prob_Iris_virginica = (1/(two_pi_sqrt*mean_variable[1][2]))*
					Math.pow(exp,((-0.5)*Math.pow(((sepallength[i]-mean_variable[0][2])/mean_variable[1][2]), 2)))*
					(1/(two_pi_sqrt*mean_variable[3][2]))*
					Math.pow(exp,((-0.5)*Math.pow(((sepalwidth[i]-mean_variable[2][2])/mean_variable[3][2]), 2)))*
					(1/(two_pi_sqrt*mean_variable[5][2]))*
					Math.pow(exp,((-0.5)*Math.pow(((petallength[i]-mean_variable[4][2])/mean_variable[5][2]), 2)))*
					(1/(two_pi_sqrt*mean_variable[7][2]))*
					Math.pow(exp,((-0.5)*Math.pow(((petalwidth[i]-mean_variable[6][2])/mean_variable[7][2]), 2)));
		  if(prob_Iris_setosa > prob_Iris_versicolor && prob_Iris_setosa > prob_Iris_virginica)
			  Result[i] = "Iris-setosa";	
		  else if(prob_Iris_versicolor > prob_Iris_setosa && prob_Iris_versicolor > prob_Iris_virginica)
			  Result[i] = "Iris-versicolor";
		  else
			  Result[i] = "Iris-virginica";
		}
	}
}
