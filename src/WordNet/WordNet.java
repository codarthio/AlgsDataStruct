package WordNet;

import edu.princeton.cs.algs4.*;

public class WordNet
{

    //Setup Symbol Tables, Graphs & SAP
    SeparateChainingHashST<String, Queue<Integer>> wordToInt;
    SeparateChainingHashST<Integer, String> intToWord;
    Digraph graph;
    SAP sap;


    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms)
    {
        String[] vLine, sLine; //delimit lines by comma
        String[] vWords, sWords; //delimit words by space


        if (synsets == null || hypernyms == null) throw new NullPointerException("Null Text Files");
        wordToInt = new SeparateChainingHashST<>();
        intToWord = new SeparateChainingHashST<>();
        int vertices = 0;

        //Read in Lines and Parse them
        In in = new In(synsets);
        while (in.hasNextLine())
        {
            vertices++;
            vLine = in.readLine().split(",");
            vWords = vLine[1].split(" ");
            Integer number = Integer.valueOf(vLine[0]);
            intToWord.put(Integer.valueOf(vLine[0]), vLine[1]);
            for (int i = 0; i < vWords.length; i++)
            {
                Queue<Integer> wordToIntQueue = wordToInt.get(vWords[i]);
                if (wordToIntQueue == null)
                {
                    wordToIntQueue = new Queue<>();
                    wordToIntQueue.enqueue(number);
                    wordToInt.put(vWords[i], wordToIntQueue);
                } else
                {
                    if (!contains(wordToIntQueue, number))
                    {
                        wordToIntQueue.enqueue(number);
                    }
                }
            }
        }
        graph = new Digraph(vertices);
        in = new In(hypernyms);
        while (in.hasNextLine())
        {
            sLine = in.readLine().split(",");
            for (int i = 1; i < sLine.length; i++)
                graph.addEdge(Integer.parseInt(sLine[0]), Integer.parseInt(sLine[i]));
        }
        sap = new SAP(graph);
        if (!sap.isRootedDAG()) throw new IllegalArgumentException("Hypernyms have to be rooted.");
    }

    private <Item> boolean contains(Iterable<Item> iterable, Item item)
    {
        for (Item query : iterable)
            if (query == item) return true;
        return false;
    }

    // returns all WordNet nouns
    public Iterable<String> nouns()
    {
        return wordToInt.keys();

    }

    // is the word a WordNet noun?
    public boolean isNoun(String word)
    {
        if (word == null) throw new NullPointerException("Arguments can't be null");
        return wordToInt.contains(word);

    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB)
    {
        if (nounA == null || nounB == null) throw new NullPointerException("Arguments can't be null");
        if (wordToInt.get(nounA) == null || wordToInt.get(nounB) == null)
            throw new IllegalArgumentException("Nouns must be contained in WordNet");
        Iterable<Integer> integerA = wordToInt.get(nounA);
        Iterable<Integer> integerB = wordToInt.get(nounB);
        return sap.length(integerA, integerB);

    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB)
    {
        if (nounA == null || nounB == null) throw new NullPointerException("Arguments can't be null");
        if (wordToInt.get(nounA) == null || wordToInt.get(nounB) == null)
            throw new IllegalArgumentException("Nouns must be contained in WordNet");
        Iterable<Integer> integerA = wordToInt.get(nounA);
        Iterable<Integer> integerB = wordToInt.get(nounB);
        return intToWord.get(sap.ancestor(integerA, integerB));

    }

    // do unit testing of this class
    public static void main(String[] args)
    {
        WordNet wordnet = new WordNet("WordNetFiles/wordnet/synsets.txt", "WordNetFiles/wordnet/hypernyms.txt");
        String word = "drum";
        int graphNumber = 34;
        int stNumber = 390;

        //Print out synset numbers for Alabama
        System.out.print(word + ":");
        for (Integer i : wordnet.wordToInt.get(word))
        {
            System.out.print(" " + i);
        }
        System.out.println();

        System.out.println(stNumber + ": " + wordnet.intToWord.get(stNumber));

        System.out.println("\nGraph Vertices: " + wordnet.graph.V() + ", Expected: 82192"); //count vertices in synsets.txt
        System.out.println("Graph Edges: " + wordnet.graph.E() + ", Expected: 84505"); //
        System.out.print(graphNumber + ":");
        for (int num : wordnet.graph.adj(graphNumber))
        {
            System.out.print(" " + num);
        }

        System.out.print("\n\nCommon Ancestor: ");
        System.out.println(wordnet.sap("Alabama", "California"));
        System.out.println("Distance: " + wordnet.distance("Alabama", "California"));
    }
}
