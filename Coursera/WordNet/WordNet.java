import edu.princeton.cs.algs4.BST;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.SeparateChainingHashST;
import edu.princeton.cs.algs4.Topological;

import java.util.ArrayList;
import java.util.LinkedList;

public class WordNet {

    private final SeparateChainingHashST<String, LinkedList<Integer>> hashIndex;
    private final BST<String, Boolean> noun;
    private final ArrayList<String> syn;
    private final Digraph graph;
    private int totalVertices;
    private final SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null)
            throw new IllegalArgumentException();
        hashIndex = new SeparateChainingHashST<>();
        noun = new BST<>();
        syn = new ArrayList<>();
        totalVertices = 0;
        readSynsets(synsets);
        graph = new Digraph(totalVertices);
        readHypernyms(hypernyms);

        Topological topological = new Topological(graph);
        if (!topological.hasOrder())
            throw new IllegalArgumentException();

        int cnt = 0;
        for (int i = 0; i < graph.V(); i++) {
            if (!graph.adj(i).iterator().hasNext())
                cnt++;
        }
        if (cnt > 1)
            throw new IllegalArgumentException();

        sap = new SAP(graph);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return noun.keys();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null)
            throw new IllegalArgumentException();
        return noun.contains(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB))
            throw new IllegalArgumentException();
        return sap.length(hashIndex.get(nounA), hashIndex.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB))
            throw new IllegalArgumentException();
        int index = sap.ancestor(hashIndex.get(nounA), hashIndex.get(nounB));

        return syn.get(index);
    }

    private void readSynsets(String synsets) {
        In in = new In(synsets);
        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] element = line.split(",");
            if (element.length >= 2) {
                int id = Integer.parseInt(element[0]);
                if (id != totalVertices)
                    throw new IllegalArgumentException();
                String[] words = element[1].split("\\s");
                for (String word : words) {
                    if (!hashIndex.contains(word))
                        hashIndex.put(word, new LinkedList<>());
                    hashIndex.get(word).add(id);
                    noun.put(word, true);
                }
                totalVertices++;
                syn.add(element[1]);
            } else throw new IllegalArgumentException();
        }
    }

    private void readHypernyms(String hypernyms) {
        In in = new In(hypernyms);
        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] element = line.split(",");
            if (element.length >= 2) {
                int v = Integer.parseInt(element[0]);
                if (v >= totalVertices || v < 0)
                    throw new IllegalArgumentException();
                for (int i = 1; i < element.length; i++) {
                    int w = Integer.parseInt(element[i]);
                    if (w >= totalVertices || w < 0)
                        throw new IllegalArgumentException();
                    graph.addEdge(v, w);
                }
            }
        }
    }

    public static void main(String[] args) {

    }
}