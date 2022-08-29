import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class SetCoverAlg{

    ArrayList<SortedSet> tree;
    HashMap<Integer, HashMap<Integer, GroupNumber>> map;
    HashMap<GroupNumber, HashMap<Integer, Integer>> universeSet;

    int[] repList;
    int groupNum;
    int largest; 

    SetCoverAlg(){
        tree = new ArrayList<>();

        map = new HashMap<>();
        universeSet = new HashMap<>();
        groupNum = 0;
    }

    public void setGroupSize(int size){
        groupNum = size;
        repList = new int[groupNum];
    }


    public void init_(Scanner input){
        GroupNumber n = new GroupNumber(0);
        universeSet.put(n, new HashMap<Integer, Integer>());
        while(n.groupNum() < groupNum){   
            Integer id = input.nextInt(); 
            
            if(id == 0){
                n = new GroupNumber(n.groupNum() + 1);
                universeSet.put(n, new HashMap<Integer, Integer>());
                continue;
            }   

            if(!map.containsKey(id)){

                universeSet.get(n).put(id, null);
                map.put(id, new HashMap<Integer, GroupNumber>());
                map.get(id).put(n.groupNum(), n);
                  
                tree.add(new SortedSet(id, new HashMap<Integer, GroupNumber>()));
                tree.get(tree.size() - 1).arrayMap = map.get(id);
                
            }
            else{

                map.get(id).put(n.groupNum(), n);

                universeSet.get(n).put(id, null);                            
            }
        }
        input.close();

        //map.forEach((key, value) -> System.out.println("1 Map  " + key + " : " + value));


        mergeSort(tree);
        // tree.forEach((key) -> System.out.println("1 Tree  " + key.arrayMap.toString() + " : "));
        // universeSet.forEach((key, value) -> System.out.println("1 Uni  " + + key.groupNum() + " : " + value ));

    }

    public String runAlg(){
        HashMap<GroupNumber, HashMap<Integer, Integer>> testSet = new HashMap<GroupNumber, HashMap<Integer, Integer>>(universeSet);
        HashMap<Integer, HashMap<Integer, Integer>> compSet = new HashMap<>();
        String retString = "";
        HashMap<Integer, GroupNumber> tempMap = null;
        Integer keys = 0;
        int repNum = 0;
        while(universeSet.size() > 1){
            compSet.put(tree.get(0).groupID, null);
            retString += tree.get(0).groupID + " ";
            for(Map.Entry<Integer, GroupNumber> v : tree.get(0).arrayMap.entrySet()){
                for(Integer x : universeSet.get(v.getValue()).keySet()) {
                    tempMap = new HashMap<Integer, GroupNumber>(map.get(x));
                    tempMap.remove(v.getKey());
                    map.put(x, tempMap);
                    for (int i = 0; i < tree.size(); i++) {
                        if (tree.get(i).groupID == x) {
                            tree.get(i).arrayMap = map.get(x);
                        }
                    }
                }
                universeSet.remove(v.getValue());
                mergeSort(tree);
            }

            tree.remove(tree.get(tree.size() - 1));
            repNum++;
            mergeSort(tree);

        }
        compSet.forEach((key, value) -> System.out.println(key));
        System.out.println("\n Test Set");
        System.out.println(compSet.size());
        System.out.println("\n Test Set");
        testSet.forEach((key, value) -> System.out.println(key));
        return retString;
    }


    public static void merge(ArrayList<SortedSet> Larr, ArrayList<SortedSet> Rarr, ArrayList<SortedSet> arr, int left_size, int right_size){
        
        int i = 0;
        int left = 0;
        int right = 0;

        //The while loops check the conditions for merging
        while(left < left_size && right < right_size){
            
            if(Larr.get(left).groupSize() > Rarr.get(right).groupSize()){
                arr.set(i, Larr.get(left));
                i++;
                left++;
            }
            else{
                arr.set(i, Rarr.get(right));
                i++;
                right++;
            }
        }

        while(left < left_size){
            arr.set(i, Larr.get(left));
            i++;
            left++;
        }

        while(right < right_size){
            arr.set(i, Rarr.get(right));
            i++;
            right++;
        }
    }

    public static void mergeSort(ArrayList<SortedSet> arr){
        int len = arr.size();
        if (arr.size() > 1){
        
        int mid = len / 2;
        ArrayList<SortedSet> Larr = new ArrayList<>(arr.subList(0, arr.size() / 2));
        ArrayList<SortedSet> Rarr = new ArrayList<>(arr.subList(arr.size() / 2, arr.size()));
 
    // Recursively calling the function to divide the subarrays further
        mergeSort(Larr);
        mergeSort(Rarr);
    // Calling the merge method on each subdivision
        merge(Larr,Rarr,arr,mid,len-mid);
        }
    }



}

class SortedSet{

    int size; 
    int groupID;
    HashMap<Integer, GroupNumber> arrayMap;

    SortedSet(int groupID, HashMap<Integer, GroupNumber> arr){
        arrayMap = arr;
        this.groupID = groupID;
        size = arr.size();
    }

    public int groupSize(){
        size = arrayMap.size();
        return size;
    }

}

class GroupNumber{
    int groupNumber; 
    
    GroupNumber(int groupNumber){
        this.groupNumber = groupNumber;
    }

    public GroupNumber getGroup(){
        return this;
    }

    public int groupNum(){
        return groupNumber;
    }
}