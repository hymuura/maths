import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Gauss {
    private static int range;
    private static float[][] matrix;
    private static Float[] answer;

    public static void main(String[] args) throws IOException {
        File file = getFileFromArgs(args);
        loadMatrixDataFromFile(file);
        gauss(matrix);
        printMatrix(matrix);
        printAnswer();
    }

    private static void printAnswer() {
        for(int x=0; x<answer.length; x++){
            System.out.println(answer[x]);
        }
    }

    private static void gauss(float[][] matrix) {
        for(int x=0; x<range-1; x++){
            setPivot(matrix,x);
            float pivot = matrix[x][x];
            for(int y=x+1; y<range; y++){
                float subPivot = matrix[y][x];
                if(subPivot!=0){
                    for(int z=x; z<range+1; z++){
                        matrix[y][z] = pivot*matrix[y][z] - subPivot*matrix[x][z];
                    }
                }
            }
        }

        for(int x=range-1; x>=0; x--){
            float resultRow = matrix[x][range];
            float auxSum = 0;
            for(int y=range-1; y>=x; y--){
                if(answer[y]==null){
                    answer[y] = (resultRow-auxSum)/matrix[x][y];
                }else {
                    auxSum+=answer[y]*matrix[x][y];
                }
            }
        }
    }

    private static File getFileFromArgs(String[] args){
        return new File("files/input/"+args[0]);
    }

    private static void loadMatrixDataFromFile(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        int mRange = Integer.parseInt(br.readLine());
        range = mRange;
        answer = new Float[mRange];
        matrix = new float[mRange][mRange+1];

        String line;
        while ((line = br.readLine()) != null){
            String[] row = line.split(" ");
            for(int x=0; x<row.length; x++){
                matrix[range-mRange][x] = Float.parseFloat(row[x]);
            }
            mRange--;
        }
    }

    private static void setPivot(float[][] matrix, int col) {
        int i = col;
        while(matrix[col][i]==0 && i<range){
            i++;
        }
        if(i==range){
            throw new RuntimeException("matrix not SCD;");
        }
        if(i!=col){
            for(int x=0; x<range+1; x++){
                float aux = matrix[col][x];
                matrix[col][x] = matrix[i][x];
                matrix[i][x] = aux;
            }
        }
    }

    private static void printMatrix(float[][] matrix){
        for(int x=0; x<range; x++){
            for(int y=0; y<range+1; y++){
                System.out.print(matrix[x][y]);
            }
            System.out.println();
        }
    }
}
