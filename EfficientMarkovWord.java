import java.util.*;
/**
 * Write a description of EfficientMarkovWord here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class EfficientMarkovWord implements IMarkovModel{
    private Random myRandom;
    private int myorder;
    private String[] myText;
    private HashMap<WordGram,ArrayList<String>> hashMap;
    public EfficientMarkovWord(int order)
    {
        myRandom=new Random();
        myorder=order;
        hashMap=new HashMap<WordGram,ArrayList<String>>();
    }
    public void setRandom(int seed)
    {
        myRandom=new Random(seed);
    }
    public void setTraining(String text)
    {
        myText=text.split("\\s+");
        buildMap();
        printHashMapInfo();
    }
    public void buildMap()
    {
        for(int k=0;k<myText.length-myorder;k++)
        {
            WordGram key=new WordGram(myText,k,myorder);
            String next="";
            //System.out.println("key"+" "+key);
            if(k<myText.length-myorder)
            {
                next=myText[k+myorder];
            }
            if(!hashMap.containsKey(key))
            {
                ArrayList<String> nextarr=new ArrayList<String>();
                nextarr.add(next);
                hashMap.put(key,nextarr);
            }
            else
            {
                ArrayList<String> nextarr=hashMap.get(key);
                nextarr.add(next);
            }
        }
    }
    public ArrayList<String> getfollows(WordGram key)
    {
         return (hashMap.get(key));
    }
    public int indexOf(String[] word,WordGram target, int start)
    {
        for(int k=start;k<word.length-myorder;k++)
        {
            WordGram wg=new WordGram(word,k,myorder);
            if((wg.equals(target)))
            {
                return k;    
            }
        }
        return -1;
    }
    public String getRandomText(int numwords)
    {
        int max=0;
        StringBuilder sb=new StringBuilder();
        int index=myRandom.nextInt(myText.length-myorder);
        WordGram key=new WordGram(myText,index,myorder);
        sb.append(key.toString());
        sb.append(" ");
        for(int k=0;k<numwords-myorder;k++)
        {
            ArrayList<String> follows = new ArrayList<String> ();
            follows=getfollows(key);
            if(follows.size()>max)
            {
                max=follows.size();
            }
            if(follows==null|| follows.isEmpty())
            {
                 break;
            }
            index=myRandom.nextInt(follows.size());
            String next=follows.get(index);
            sb.append(next);
            sb.append(" ");
            key=key.shiftAdd(next);
        }
        System.out.println("size of largest follow arraylist "+max);
        return sb.toString().trim();
    }
    public void printHashMapInfo()
    {
        int maxSize=-1;
        ArrayList<WordGram> maxSizeKeys=new ArrayList();
        for(WordGram key:hashMap.keySet())
        {
            ArrayList<String> values=hashMap.get(key);
        
        if(values.size()>maxSize)
        {
          maxSize=values.size();  
        }
        }
        for (WordGram key : hashMap.keySet()) {
            int valueSize = hashMap.get(key).size();
            
            if (valueSize == maxSize) {
                maxSizeKeys.add(key);
            }
        }
        System.out.println("Number of keys in the HashMap: " + hashMap.size());
        System.out.println("Size of the largest value in the HashMap: " + maxSize);
        //System.out.println("Keys that have the maximum size value: " + maxSizeKeys);
    }
}
