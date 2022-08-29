import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class ass1_comp3010 {

    // make value array shared??
    public static void main(String[] args) throws Exception {

        HashMap<Integer, ArrayList<Integer>> map = new HashMap<>();
        Scanner input = new Scanner(System.in);
 
        System.out.println("Enter the number of groups from which you must find representatives: ");
        int groupNum = input.nextInt();
        int[] repList = new int[groupNum];
        System.out.println("Enter the list of members of each group (one group per line, each terminated by 0) :");
        
        int n = 0;
        while(n < groupNum){   
            int id = input.nextInt(); 
            if(id == 0){
                n++;
                continue;
            }   
            if(!map.containsKey(id)){
                map.put(id, new ArrayList<Integer>());
                map.get(id).add(n);
                if(repList[n] == 0){
                    repList[n] = id;
                    
                }
            }
            else{
                ArrayList<Integer> LISTTemp = map.get(id);
                LISTTemp.add(n);
                for(int k = 0; k < LISTTemp.size(); k++){
                    ArrayList<Integer> compareList = map.get(repList[n]);
                    
                    // if(compareList == null){                       
                    //     repList[n]= id;
                    //     compareList = map.get(repList[n]);
                    // }
                    if(compareList == null || compareList.size() <= LISTTemp.size()){
                        repList[LISTTemp.get(k)] = id;
                    }
                }
            }
        }
        // closing scanner
        input.close();
        System.out.println(Arrays.toString(repList));
        
        //map.clear();
        HashMap<Integer, ArrayList<Integer>> temp = new HashMap<>();
        for(int i = 0; i < repList.length; i++){
           // System.out.println(repList[i]);
            temp.put(repList[i], map.get(repList[i]));
        }
        temp.forEach((key, value) -> System.out.println( key + " : " + value));
        System.out.println("The number of members selected and their ids are :");
        System.out.println(temp.size());
        String output = "";
        //map.forEach((key, value) -> System.out.println(key + " : " + value));
        for(Integer value : temp.keySet()){
            output += value + " " ;
        }
        System.out.println(output);
    }
    
}
