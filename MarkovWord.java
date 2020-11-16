import java.util.*;
/**
 * Write a description of MarkovWord here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MarkovWord implements IMarkovModel{
    private Random myrandom;
    private String[] mytext;
    private int order;
    public MarkovWord(int o)
    {
        myrandom = new Random();
        order=o;
    }
    public void setTraining(String s)
    {
        mytext=s.split("\\s+");
    }
    public void setRandom(int seed)
    {
        myrandom = new Random(seed);
    }    
    public String getRandomText(int numwords)
    
    {  StringBuilder sb = new StringBuilder();
        int index = myrandom.nextInt(mytext.length-order);  // random word to start with
        WordGram key = new WordGram(mytext,index,order);
        sb.append(key.toString());
        sb.append(" ");
        for(int k=0; k < numwords-order; k++){
            ArrayList<String> follows = getFollows(key);
            if (follows.size() == 0) {
                break;
            }
            index = myrandom.nextInt(follows.size());
            String next = follows.get(index);
            sb.append(next);
            sb.append(" ");
            key =key.shiftAdd(next);
        
         }
        return sb.toString().trim();
    }
    public int indexOf(String[] word , WordGram target, int start)
    {
        for(int k=start;k<word.length-order;k++)
        {
            WordGram wg=new WordGram(word,k,order);
            if(wg.equals(target))
            {
                return k;
               }
           }
        return -1;
    }
     private ArrayList<String> getFollows(WordGram key) {
        ArrayList<String> follows = new ArrayList<String>();
          int pos =0;
        while(pos < mytext.length) {
            
            int start =indexOf(mytext,key,pos);
            
            if(start == -1) {
                break;
               }
            
               
            if(start >= mytext.length-1) {
                break;   
            }
            
            
            String next = mytext[start+order];
            follows.add(next);
            pos=start+1;
        }
                return follows;
    
  }
}
