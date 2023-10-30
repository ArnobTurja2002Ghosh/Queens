import java.util.*;
import java.lang.Math;

/* YOU NEED TO ADD YOUR CODE TO THIS CLASS, REMOVING ALL DUMMY CODE
 *
 * DO NOT CHANGE THE NAME OR SIGNATURE OF ANY OF THE EXISTING METHODS
 * (Signature includes parameter types, return types and being static)
 *
 * You can add private methods to this class if it makes your code cleaner,
 * but this class MUST work with the UNMODIFIED Tester.java class.
 *
 * This is the ONLY class that you can submit for your assignment.
 *
 * MH September 2023
 */
public class Queens
{
    private static int boardSize = 12;
    
    // creates a valid genotype with random values
    public static Integer [] randomGenotype()
    {
        Integer [] genotype = new Integer [boardSize];
        
        // YOUR CODE GOES HERE
        // DUMMY CODE TO REMOVE:
        genotype = new Integer[boardSize];
        for(int i=0; i<boardSize; i++){
            int r = 1+new Random().nextInt(12);
            while(Arrays.asList(genotype).contains(r)){
                r = 1+new Random().nextInt(12);
            }
            genotype[i] = r;
        }
        // END OF YOUR CODE
        
        return genotype;
    }
    
    // move a gene in the genotype
    // the move happens with probability p, so if p = 0.8
    // then 8 out of 10 times this method is called, a move happens
    public static Integer[] insertMutate(Integer[] genotype, double p) // nsadawi
    {
        // YOUR CODE GOES HERE
        // DUMMY CODE TO REMOVE:
        //int[] genotype1 = new Integer[]{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        if(new Random().nextInt(100)+1 > p*100){
            return genotype;
        }
        System.out.println("pick two different genes at random");
        int r1 = new Random().nextInt(12); int r2 = new Random().nextInt(12); 
        while(r2==r1){
            r2 = new Random().nextInt(12);
        }
        System.out.println(" First: " + genotype[r1] + " Second chosen: " + genotype[r2] );
        if(r2>r1){
            for(int i = r2-1; i > r1 ; i--){       
                int temp2 = genotype[i+1];
                genotype[i+1] = genotype[i];
                genotype[i] = temp2; 
            }
        }
        else if(r2 < r1){
            for(int i = r2+1; i<=r1; i++){
                int temp2 = genotype[i-1];
                genotype[i-1] = genotype[i];
                genotype[i] =temp2;
            }
        }
        // END OF YOUR CODE
        
        return genotype;
    }
    
    // creates 2 child genotypes using the 'cut-and-crossfill' method
    public static Integer[][] recombine(Integer[] parent0, Integer[] parent1)
    {
        Integer [][] children = new Integer [2][boardSize];
        
        // YOUR CODE GOES HERE
        // DUMMY CODE TO REMOVE:
        children[0] = new Integer[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        children[1] = new Integer[]{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

        for(int i=0; i<6; i++){
            children[0][i] = parent0[i];
            children[1][i] = parent1[i];
        }
        int j = 6; int k=6;
        for(int i=6; i<24; i++){
            if(!Arrays.asList(children[0]).contains(parent1[i%12])){
                children[0][j] = parent1[i%12];
                j++;
            }
            if(!Arrays.asList(children[1]).contains(parent0[i%12])){
                children[1][k] = parent0[i%12];
                k++;
            }
        }
        // END OF YOUR CODE
        
        return children;
    }
    
    // calculates the fitness of an individual
    public static int measureFitness(Integer [] genotype)
    {
        /* The initial fitness is the maximum pairs of queens
         * that can be in check (all possible pairs in check).
         * So we are using it as the maximum fitness value.
         * We deduct 1 from this value for every pair of queens
         * found to be in check.
         * So, the lower the score, the lower the fitness.
         * For a 12x12 board the maximum fitness is 66 (no checks),
         * and the minimum fitness is 0 (all queens in a line).
         */
        int fitness = (int) (0.5 * boardSize * (boardSize - 1));
        
        int clashes = 0;
	    int row_col_clashes = Math.abs(genotype.length - (int) Arrays.stream(genotype).distinct().count());
	    clashes += row_col_clashes;
        // YOUR CODE GOES HERE

        for(int i=0; i<genotype.length; i++){
            for(int j=i+1; j<genotype.length; j++){
                if ( i != j){
                    int dx = Math.abs(i-j);
                    int dy = Math.abs(genotype[i] - genotype[j]);
                    if(dx == dy){
                        clashes += 1;
                    }
                }
            }
        }
        return fitness-clashes;
    }
}
