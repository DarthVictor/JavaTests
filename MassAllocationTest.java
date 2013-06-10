import java.util.Random;

final class IntAndDouble 
{
    public int A;
    public double B;
}

public class MassAllocationTest
{
    final static int ARRAY_SIZE = 20000000;
    final static int NUM_OF_ITER = 100;
    public static void test1()
    {
        long allocFullTime = 0;
        long freeFullTime = 0;
        long result = 0; 

        System.out.println("========== Test1 ==========");

        Random randomGenerator = new Random();
        
        for ( int z = 0; z < NUM_OF_ITER; z++ ) {

            long startAlloc = System.nanoTime();    
            IntAndDouble [] array = new IntAndDouble[ARRAY_SIZE];
            for (int i = 0; i < ARRAY_SIZE; i++)
            {
                array[i] = new IntAndDouble();
            }
            allocFullTime += System.nanoTime() - startAlloc;
            
            // исключаем удаление цикла
            array[randomGenerator.nextInt(ARRAY_SIZE - 1)].A = randomGenerator.nextInt();
            array[randomGenerator.nextInt(ARRAY_SIZE - 1)].B = randomGenerator.nextDouble();
            result = array[0].A + (int)array[0].B;
            
            long startFree = System.nanoTime();    
            Runtime.getRuntime().gc();
            freeFullTime += System.nanoTime() - startFree;       
            
            if (z % (NUM_OF_ITER/10) == 0 && z > 0) System.out.print("" + z + "%...."); // showing progress
        }

        System.out.println("Result = " + result);
        System.out.println("Average time for one allocation = " + allocFullTime/(1000.0*1000.0*NUM_OF_ITER) + "ms");        
        System.out.println("Average time for one free = " + freeFullTime/(1000.0*1000.0*NUM_OF_ITER) + "ms");        
    }
   
   
    public static void main (String[] agrs)
    {
        test1();        
    }
}
