package WordNet;

import edu.princeton.cs.algs4.MaxPQ;
import edu.princeton.cs.algs4.ST;

public class Outcast
{
    private WordNet wordNet;

    public Outcast(WordNet wordnet)
    {
        this.wordNet = wordnet;
    }

    public String outcast(String[] nouns)
    {
        //Find maximum distance noun
        MaxPQ<Integer> maxPQ = new MaxPQ();

        //Symbol table for the distance
        ST<Integer, String> st = new ST<>();


        //iterate through nouns in array
        for (int i = 0; i < nouns.length; i++)
        {
            //add sum variable
            int sumDistance = 0;

            System.out.println("\n" + i + " " + nouns[i]);

            //sum distance with pointer noun to all other nouns
            for (int j = 0; j < nouns.length; j++)
            {
                System.out.print(nouns[j] + " " + wordNet.distance(nouns[i], nouns[j]) + " ");
                if (nouns[i] != nouns[j])
                {
                    sumDistance += wordNet.distance(nouns[i], nouns[j]);
                }
            }

            System.out.println("Total:  " + sumDistance);
            //add distance to the priority queue
            maxPQ.insert(sumDistance);

            //add noun and distance to the symbol table
            st.put(sumDistance, nouns[i]);


        }
        return st.get(maxPQ.max());

    }

    public static void main(String[] args)
    {
        String[] nounArray = {"water", "soda", "bed", "orange_juice", "milk", "apple_juice", "tea", "coffee"};
        WordNet wordnet = new WordNet("WordNetFiles/wordnet/synsets.txt", "WordNetFiles/wordnet/hypernyms.txt");
        Outcast oc = new Outcast(wordnet);

        System.out.println("\nOutcast: " + oc.outcast(nounArray));

    }
}

