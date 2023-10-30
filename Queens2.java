import java.lang.Math;
import java.util.*;

/* YOU NEED TO ADD YOUR CODE TO THIS CLASS, REMOVING ALL DUMMY CODE
 *
 * DO NOT CHANGE THE NAME OR SIGNATURE OF ANY OF THE EXISTING METHODS
 * (Signature includes parameter types, return types and being static)
 *
 * You can add private methods to this class if it makes your code cleaner,
 * but this class MUST work with the UNMODIFIED Tester2.java class.
 *
 * This is the ONLY class that you can submit for your assignment.
 *
 * MH DOC_20.23.10.19
 */
class SortbyFitness implements Comparator<Integer[]> {
 
    // Method
    // Sorting in ascending order of roll number
    public int compare(Integer[] a, Integer[] b)
    {
 
        return Queens.measureFitness(a) - Queens.measureFitness(b);
    }
}
public class Queens2
{
    private static int boardSize = 12;
    static String problem = "n queens";
    
    // ************************************************************************
    // ************ TASK A. RANK A SET OF VALUES ******************************
    // ************************************************************************
    
    // returns the rank of the values provided
    // worst =0 ; best = values.length-1
    // ranking is shared if tied:
    // for input values [30, 70, 50, 50, 40, 80] the rankings are [0, 4, 2.5, 2.5, 1, 5]
    // ... because (2 + 3)/2 = 2.5
    public static double[] rank(Integer values[])
    {
        double rank[] = new double[values.length];
        TreeMap<Integer, Integer> tmap =
                     new TreeMap<Integer, Integer>();
 
        // Traverse through the given array
        for (int i = 0; i < values.length; i++)
        {
            Integer c = tmap.get(values[i]);
 
            // If this is first occurrence of element   
            if (tmap.get(values[i]) == null)
               tmap.put(values[i], 1);
 
            // If elements already exists in hash map
            else
              tmap.put(values[i], ++c);
        }
        int j = 0; rank = new double[]{5, 4, 3, 2, 1, 0};
        for (Map.Entry m:tmap.entrySet()){
        //   System.out.println("Frequency of " + m.getKey() + 
        //                      " is " + m.getValue());
            for(int i = 0; i < values.length; i++){
                if(m.getKey()==values[i]){
                    rank[i] = (((int)m.getValue()/2.0)*(2*j+(int)m.getValue()-1)) / (int)m.getValue();
                    //rank[i] = (double) (((int)m.getValue()/2)*(2*j+(int)m.getValue()-1))/(int)m.getValue();
                }
            }
            j+= (int) m.getValue();
        }
        // YOUR CODE GOES HERE
        // DUMMY CODE TO REMOVE:
        
        // END OF YOUR CODE
        
        return rank;
    }
    
    // ************************************************************************
    // ************ TASK B. CALCULATE LINEAR RANKING PROBABILITY **************
    // ************************************************************************
    
    // calculates the linear ranking probability of a genotype (see the linear ranking equation)
    public static double linearRankingProb(double rank, double s, int populationSize)
    {
        // YOUR CODE GOES HERE
        // DUMMY CODE TO REMOVE:
        double probability = (2-s)/populationSize + (2*rank*(s-1))/(populationSize*(populationSize-1));
        // END OF YOUR CODE
        
        return probability;
    }
    
    // ************************************************************************
    // ************ TASK C. PERFORM LINEAR RANKING PARENT SELECTION ***********
    // ************************************************************************
    
    /* performs linear ranking parent selection (see class slides & the guide in the course shell)
     * s is the 'selective pressure' parameter from the P[i] equation
     */
    public static Integer[][] linearRankingSelect(Integer[][] population, double s)
    {
        // The first three parts of this method have been written for you...
        // But the fourth part - selecting the two parents - is up to you!
        
        Integer [][] parents = new Integer [2][boardSize]; // will hold the selected parents
        int popSize = population.length;
        
        // 1. determine the fitness of each member of the population
        Integer [] fitness = getFitnesses(population);
        
        // 2. hence determine the ranking of each member by its fitness
        double [] rank = rank(fitness);
        
        // 3. determine the cumulative probability of selecting each member, using the linear ranking formula
        double [] cumulative = new double [popSize];
        cumulative[0] = linearRankingProb(rank[0], s, popSize);
        for (int index = 1; index < popSize; index ++)
        {
            cumulative[index] = cumulative[index-1] + linearRankingProb(rank[index], s, popSize);
        }
        
        // 4. finally, select two parents, based on the cumulative probabilities
        // see the pseudocode in RESOURCES > Evolutionary Computation General >
        // Linear Ranking: How to use cumulative probability to select parents
        // choose the first parent
        double rand = Math.random();

        int first = 0;
        while(cumulative[first] < rand) {first++; }

        int second = first;

        // choose the second parent, making sure that it's different to the first
        while (second == first)
        {
            rand = Math.random();

            second = 0;
            while (cumulative[second] < rand) { second++; }
        }

        
        // YOUR CODE GOES HERE
        // DUMMY CODE TO REMOVE: (which always returns the same two parents)
        parents[0] = new Integer[]{ 10, 6, 4, 2, 8, 11, 5, 12, 9, 1, 3, 7 };
        parents[1] = new Integer[]{ 9, 4, 3, 1, 2, 11, 5, 10, 12, 7, 8, 6 };
        // END OF YOUR CODE
        parents[0] = population[first];
        parents[1] = population[second];
        return parents;
    }
    
    // ************************************************************************
    // ************ TASK D. (λ, μ) SURVIVOR SELECTION *************************
    // ************************************************************************
    
    /* creates a new population through (λ, μ) survivor selection
     * given a required population of size n, and a set of children of size 2n,
     * this method will return the n fittest children as the new population
     */
    public static Integer[][] survivorSelection(Integer[][] children, int n)
    {
        Integer [][] newPop = new Integer [n][boardSize];
        ArrayList<Integer[]> ar = new ArrayList<>(Arrays.asList(children));
        Collections.sort(ar, new SortbyFitness());
        Collections.reverse(ar);
        // YOUR CODE GOES HERE
        // DUMMY CODE TO REMOVE:
        newPop [0] = new Integer[]{ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
        newPop [1] = new Integer[]{ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
        newPop [2] = new Integer[]{ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
        newPop [3] = new Integer[]{ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
        newPop [4] = new Integer[]{ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
        newPop [5] = new Integer[]{ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
        newPop [6] = new Integer[]{ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
        newPop [7] = new Integer[]{ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
        newPop [8] = new Integer[]{ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
        newPop [9] = new Integer[]{ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
        // END OF YOUR CODE
        for (int i = 0; i < n; i++) 
           
            // Printing and display the elements in ArrayList 
            newPop[i] = ar.get(i);
        return newPop;
    }
    
    // ************************************************************************
    // ************ TASK E. FITNESS DIVERSITY *********************************
    // ************************************************************************
    
    // counts the number of unique fitness values seen in the population
    public static int fitnessDiversity(Integer[][] population)
    {
        // YOUR CODE GOES HERE
        // DUMMY CODE TO REMOVE:
        int uniqueFitnessValues = 6;
        // END OF YOUR CODE
        TreeMap<Integer, Integer> tmap =
                     new TreeMap<Integer, Integer>();
        for(int i=0; i<population.length; i++){
            
            if (tmap.get(Queens.measureFitness(population[i])) == null)
               tmap.put(Queens.measureFitness(population[i]), 1);
 
            // If elements already exists in hash map
            else{
                Integer c = tmap.get(Queens.measureFitness(population[i]));
                tmap.put(Queens.measureFitness(population[i]), ++c);
            }
        }
        return tmap.size();
    }
    
    // ************************************************************************
    // ************ TASK F.  INVERSION MUTATION *******************************
    // ************************************************************************
    
    // inverts the order of a series of genes in the genotype
    public static Integer[] inversionMutate(Integer[] genotype, double p)
    {   if(1+new Random().nextInt(100) > p*100){return genotype;}
        Integer[] points = {new Random().nextInt(genotype.length), new Random().nextInt(genotype.length)};
        Arrays.sort(points); int point1 = points[0]; int point2 = points[1];
        System.out.println("Points: " + point1 + " " + point2);
        // YOUR CODE GOES HERE
        // DUMMY CODE TO REMOVE:
        // END OF YOUR CODE
        for (int i = point1; i <= point1+ (point2-point1)/2; i++) {
            Integer temp = genotype[i];
            genotype[i] = genotype[point2 - (i-point1)];
            genotype[point2 - (i-point1)] = temp;
        }
        return genotype;
    }
    
    // ************ DO NOT EDIT OR DELETE THE METHOD BELOW! *******************
    
    // ************************************************************************
    // ************ GET THE FITNESS VALUES OF A POPULATION ********************
    // ************************************************************************
    
    // returns an array of fitnesses for a population
    private static Integer[] getFitnesses(Integer [][] population)
    {
        int popSize = population.length;
        Integer [] fitness = new Integer [popSize];
        
        for (int index = 0; index < popSize; index ++)
        {
            fitness[index] = Queens.measureFitness(population[index]);
        }
        
        return fitness;
    }
}
