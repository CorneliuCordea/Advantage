public class Test {
	public static void main(String[] args) {
		int[][] values = new int[4][6];
		for(int i=1;i<6;i++){
			values[0][i-1]=1;
		}
		for(int i=1;i<6;i++){
			values[1][i-1]=2;
		}
		for(int i=1;i<6;i++){
			values[2][i-1]=3;
		}
		for(int i=1;i<6;i++){
			values[3][i-1]=4;
		}
		
		System.out.println(values);
//		for(int i=0;i<6;i++)
//			for(int j=0;j<6;j++){
//				
//			}
	}
}
