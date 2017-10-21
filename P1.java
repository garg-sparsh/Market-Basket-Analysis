import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

public class P1 {
    static private int count;
    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException {
        P1 p1_5 = new P1();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(System.in));
        String comment;
        try{
            comment = in.readLine();
            if(!comment.startsWith("#")){
                if(comment.contains("#")){
                    count = Integer.parseInt(comment.substring(0,comment.indexOf("#")));
                }
                else{
                    count = Integer.parseInt(comment);}
            }
            else{
                count = Integer.parseInt(in.readLine().substring(0,1));}
            String s;
            HashMap<String, Integer> input = new HashMap<>();
            List<Integer> First = new ArrayList<>();
            List<List> readFileOnLines = new ArrayList<>();
            int value = 1;
            while ((s = in.readLine()) != null) {
                if(s.contains("#")){
                    s = s.substring(0,s.indexOf("#"));
                }
                String[] country = s.split(",");
                List<Object> a5 = new ArrayList<>();
                for (String a : country) {
                    a = a.trim();
                    if (!input.containsKey(a)) {
                        input.put(a, value);
                        value++;
                    }
                }
                for(Object str : Arrays.asList(country)){
                    if(!a5.contains(str))
                        a5.add(str);
                }
                for (Object a : a5) {
                    String b = a.toString().trim();
                    if (!input.containsKey(b)) {
                        input.put(b, value);
                        value++;
                    }
                }
                List<Integer> a4 = new ArrayList<>();
                for (Object str:a5) {
                    First.add(input.get(str.toString().trim()));
                    a4.add(input.get(str.toString().trim()));
                }
                readFileOnLines.add(a4);
            }
            Map<Integer, Integer> hashMap = p1_5.occuranceMap(First); //all the singletons with counts
            hashMap = p1_5.frequentSubset(hashMap); //frequent singletons
            List<List> possibleFrequentMultiples = p1_5.possiblePairs(new ArrayList<Object>(hashMap.keySet())); //possible pairs after the first pass
            p1_5.display(new ArrayList<>(hashMap.keySet()),input); //displaying frequent singletons
            hashMap.clear();
            Map<List, Integer> occurrenceMap2 = p1_5.occuranceMapForCombination(readFileOnLines,possibleFrequentMultiples); //all the possible pairs with counts
            possibleFrequentMultiples.clear();
            occurrenceMap2 = p1_5.frequentSubsetForMultiples(occurrenceMap2); //frequent pairs
            p1_5.displaySets(new ArrayList(occurrenceMap2.keySet()),input); // displaying frequent pairs
            for(int i=3;i<=6;i++){
                possibleFrequentMultiples.clear();
                List<List<Integer>> possibleSets=p1_5.possibleTriples(new ArrayList<>(occurrenceMap2.keySet()),new ArrayList<>(occurrenceMap2.keySet()),i);
                //all the possible triples after 2nd pass
                occurrenceMap2.clear();
                for(List str: possibleSets){ // unique possible triples
                    if(!possibleFrequentMultiples.contains(str)){
                        possibleFrequentMultiples.add(str);
                    }
                }
                possibleSets.clear();
                occurrenceMap2=p1_5.occuranceMapForCombination(readFileOnLines,possibleFrequentMultiples);//counts of all the possible triples
                occurrenceMap2=p1_5.frequentSubsetForMultiples(occurrenceMap2); //frequent triples
                p1_5.displaySets(new ArrayList(occurrenceMap2.keySet()),input);
            }}
        catch (NumberFormatException e){
            System.out.println("Invalid File: First Line should be the threshold");
        }
    }

    /*
    take input as a list of frequent items and give output as list of pairs
    input: [1,2,3]
    output: [[1,2],[1,3],[2,3]
     */
    private List<List> possiblePairs(List file){
        List<List> listOLists = new ArrayList<>();
        for(int i=0;i<file.size();i++){
            for(int j=i+1;j<file.size();j++){
                List<Object> singleList = new ArrayList<>();
                singleList.add(file.get(i));
                singleList.add(file.get(j));
                listOLists.add(singleList);
            }
        }
        return listOLists;
    }
    /*
    take input as a list of frequent pairs and give output as list of triples
    input: [[1,2],[1,3],[2,3]
    output: [[1,2,3],[1,2,3],[1,2,3] duplicates will get removed later
    set is always >=3
     */
    @SuppressWarnings("unchecked")
    private List<List<Integer>> possibleTriples(List lol,List a5,int set){
        List<Integer> l = new ArrayList<>();
        List<String> a6 = new ArrayList<>();
        List<String> a7 = new ArrayList<>();
        List<List<String>> a8 = new ArrayList<>();
        List<List<Integer>> a9 = new ArrayList<>();
        for (Object item : lol) {
            List k = new ArrayList<>();
            for (int i = 0; i < a5.size(); i++) {
                k.add(item.toString());
                k.add(a5.get(i));
                l.addAll(k);
                k.clear();
            }
        }
        for (int i = 0; i <= l.size() - 2; i=i+2) {
            a6.add((l.subList(i, i + 2)).toString());
        }
        l=null;
        for(Object str: a6){
            a7.add((str.toString().replaceAll("[^a-zA-Z0-9,]","")));
        }
        for(int i=0;i<a7.size();i++){
            String[] arr = a7.get(i).split(",");
            Arrays.sort(arr);
            List<String> o = new ArrayList<>();
            for(String str: arr){
                if(!o.contains(str))
                    o.add(str);
            }
            if(o.size()==set)
                a8.add(o);
            o=null;
        }
        for(int i=0;i<a8.size();i++){
            List<Integer> e = new ArrayList<>();
            for(int j=0;j<a8.get(i).size();j++){
                e.add(Integer.parseInt(a8.get(i).get(j)));
            }
            a9.add(e);
        }
        a6=null;
        a7=null;
        a8=null;
        return a9;
    }

    /*
    creating a hashmap for counting the occurence of singletons
     */
    private Map<Integer,Integer> occuranceMap(List<Integer> outputOfFile){
        Map<Integer, Integer> occurrenceMap = new HashMap<>();
        int value=1;
        for(int str: outputOfFile){
            if(!occurrenceMap.containsKey(str)){
                occurrenceMap.put(str, value);
            }
            else{
                occurrenceMap.put(str, occurrenceMap.get(str)+1);
            }
        }
        return occurrenceMap;
    }
    /*
    creating a hashmap for counting the occurence of pairs,triples and so on
     */
    @SuppressWarnings("unchecked")
    private Map<List,Integer> occuranceMapForCombination(List<List> inputFile, List<List> possibleCombination){
        Map<List, Integer> occurrenceMap2 = new HashMap<>();
        for(int i=0;i<inputFile.size();i++){
            for(int j=0;j<possibleCombination.size();j++){
                if(inputFile.get(i).containsAll(possibleCombination.get(j))) {
                    if(!occurrenceMap2.containsKey(possibleCombination.get(j))){
                        occurrenceMap2.put(possibleCombination.get(j), 1);}
                    else{
                        occurrenceMap2.put(possibleCombination.get(j), occurrenceMap2.get(possibleCombination.get(j)) + 1);
                    }
                }
            }
        }
        return occurrenceMap2;
    }
    /*
    a set will be frequent if its occurence is greater than the support/threshold
    this func is to check the frequency of singletons
    */
    private Map<Integer,Integer> frequentSubset(Map<Integer,Integer> values){
        Iterator it = values.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            if(Integer.parseInt(pair.getValue().toString())<count)
                it.remove(); // avoids a ConcurrentModificationException
        }
        return values;
    }
    /*
    a set will be frequent if its occurence is greater than the support/threshold
    this func is to check the frequency of pairs/triples
    */
    private Map<List,Integer> frequentSubsetForMultiples(Map<List,Integer> values){
        Iterator it = values.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            if(Integer.parseInt(pair.getValue().toString())<count)
                it.remove(); // avoids a ConcurrentModificationException
        }
        return values;
    }
    /*
    to display the frequent singletons
     */
    private void display(List<Integer> frequentPair,Map<String, Integer> hm){
        for(int str : frequentPair){
            System.out.println(getKeyFromValue(hm,str));
        }
    }

    private String getKeyFromValue(Map<String, Integer> hm, Object value) {
        for (String o : hm.keySet()) {
            if (hm.get(o).equals(value)) {
                return o;
            }
        }
        return null;
    }
    /*
    to display the frequent pairs/multiples
     */
    private void displaySets(List<List<Integer>> frequentSets, Map<String, Integer> hm){
        for(int i=0;i<frequentSets.size();i++){
            int j=1;
            for(int l2: frequentSets.get(i)){
                if(j++ != frequentSets.get(i).size())
                    System.out.print(getKeyFromValue(hm,l2)+",");
                else
                    System.out.print(getKeyFromValue(hm,l2));
            }
            System.out.println();
        }
    }
}
