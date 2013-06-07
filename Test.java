import java.util.Random;

final class CashAccountRow 
{
    public int code; // 0 - 1000000
    public boolean gender; // 0 - 1
    public byte age; // 0 - 100
    public int amount_of_money; // 0 - 1000000
    public short height;   // 0 – 300

    public static CashAccountRow[] generateTable (int n)
    {
        CashAccountRow[] cashAccountTable = new CashAccountRow[n];
        Random randomGenerator = new Random();
        for (int i = 0; i < cashAccountTable.length; i++)
        {
            cashAccountTable[i] = new CashAccountRow();
            cashAccountTable[i].code = randomGenerator.nextInt(1000000);
            cashAccountTable[i].gender = randomGenerator.nextBoolean();
            cashAccountTable[i].age = (byte)randomGenerator.nextInt(100);
            cashAccountTable[i].amount_of_money = randomGenerator.nextInt(1000000);
            cashAccountTable[i].height = (short)randomGenerator.nextInt(300);                        
        }
        return cashAccountTable;
    }
    
    public final boolean rowInCondition(RangeFilter filter)
    {
        return (
            (!filter.useFilter[0] || this.code <= filter.end.code && this.code >= filter.begin.code )  &&
            (!filter.useFilter[1] || this.gender == filter.end.gender && this.gender == filter.begin.gender ) &&
            (!filter.useFilter[2] || this.age <= filter.end.age && this.age >= filter.begin.age ) &&
            (!filter.useFilter[3] || this.amount_of_money <= filter.end.amount_of_money && this.amount_of_money >= filter.begin.amount_of_money ) &&
            (!filter.useFilter[4] || this.height <= filter.end.height && this.height >= filter.begin.height) 
        );
        
    }
    
    public static int rowsInCondition(RangeFilter filter, CashAccountRow[] table)
    {
        int result = 0;
        for (int i = 0; i < table.length; i++)
            if (
                (!filter.useFilter[0] || table[i].code <= filter.end.code && table[i].code >= filter.begin.code )  &&
                (!filter.useFilter[1] || table[i].gender == filter.end.gender && table[i].gender == filter.begin.gender ) &&
                (!filter.useFilter[2] || table[i].age <= filter.end.age && table[i].age >= filter.begin.age ) &&
                (!filter.useFilter[3] || table[i].amount_of_money <= filter.end.amount_of_money && table[i].amount_of_money >= filter.begin.amount_of_money ) &&
                (!filter.useFilter[4] || table[i].height <= filter.end.height && table[i].height >= filter.begin.height) 
                )
                result++;        
        return result;
    }
}

class RangeFilter
{
    final CashAccountRow begin;
    final CashAccountRow end;
    final boolean [] useFilter;
    public RangeFilter(CashAccountRow begin, CashAccountRow end, boolean [] useFilter)
    {
        this.begin = begin;
        this.end = end;
        this.useFilter = useFilter;
    }
    public static RangeFilter generateRangeFilter ()    
    {
        Random randomGenerator = new Random();
        boolean [] useFilter = new boolean[5];        
        for(int i = 0; i < useFilter.length; i++)
        {
            useFilter[i] = randomGenerator.nextBoolean();
        }       
        CashAccountRow begin = new CashAccountRow();   
        begin.code = randomGenerator.nextInt(500000);
        begin.gender = randomGenerator.nextBoolean();
        begin.age = (byte)randomGenerator.nextInt(50);
        begin.amount_of_money = randomGenerator.nextInt(500000);
        begin.height = (short)randomGenerator.nextInt(150);  
        
        CashAccountRow end = new CashAccountRow();
        end.code = begin.code + randomGenerator.nextInt(500000);
        end.gender = begin.gender;
        end.age = (byte)(begin.age + randomGenerator.nextInt(50));
        end.amount_of_money = begin.amount_of_money + randomGenerator.nextInt(500000);
        end.height = (short)(begin.height + randomGenerator.nextInt(150));  
        
        return new RangeFilter(begin, end, useFilter);    
    }
}


public class Test
{
   public static void test1(RangeFilter filter, CashAccountRow [] table)
   {
        long start = System.nanoTime();    
        int result = 0; 
        for (int i = 0; i < table.length; i++)
            if (table[i].rowInCondition(filter)) result++;
        long elapsedTime = System.nanoTime() - start;
        System.out.println("Result = " + result);
        System.out.println("Elapsed time = " + elapsedTime/1000000.0 + "ms");        
   }
   
   public static void test2(RangeFilter filter, CashAccountRow [] table)
   {
       
        long start = System.nanoTime();            
        int result = CashAccountRow.rowsInCondition(filter, table);
        long elapsedTime = System.nanoTime() - start;
        System.out.println("Result = " + result);
        System.out.println("Elapsed time = " + elapsedTime/1000000.0 + "ms");
   }
   
   public static void main (String[] agrs)
   {
        double usedMemory;
        
        System.out.println("Generating table");
        CashAccountRow [] table = CashAccountRow.generateTable (10000000);         
        
        //usedMemory = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())/1000000.0;
        //System.out.println("Memory used " + usedMemory + "MB");
        //Runtime.getRuntime().gc();
        //usedMemory = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())/1000000.0;
        //System.out.println("Memory used " + usedMemory + "MB");
        
        //System.out.println("Generating filter");
        RangeFilter filter = RangeFilter.generateRangeFilter();
        //usedMemory = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())/1000000.0;
        //System.out.println("Memory used " + usedMemory + "MB");
        //Runtime.getRuntime().gc();
        //usedMemory = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())/1000000.0;
        //System.out.println("Memory used " + usedMemory + "MB");
        
        System.out.println("test1");
        test1(filter, table);        
        //usedMemory = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())/1000000.0;
        //System.out.println("Memory used " + usedMemory + "MB");
        //Runtime.getRuntime().gc();
        //usedMemory = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())/1000000.0;
        //System.out.println("Memory used " + usedMemory + "MB");



        //System.out.println("test2");
        //test2(filter, table);        
        //usedMemory = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())/1000000.0;
        //System.out.println("Memory used " + usedMemory + "MB");
        //Runtime.getRuntime().gc();
        //usedMemory = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())/1000000.0;
        //System.out.println("Memory used " + usedMemory + "MB");
   }
}