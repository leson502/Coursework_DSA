public class Outcast {

    private final WordNet wordNet;

    public Outcast(WordNet wordnet) {
        this.wordNet = wordnet;
    }

    public String outcast(String[] nouns) {
        String result = null;
        int max = 0;
        for (String noun : nouns) {
            int distance = 0;
            for (String noun1 : nouns)
                if (noun != noun1)
                    distance += wordNet.distance(noun, noun1);
            if (distance > max) {
                max = distance;
                result = noun;
            }
        }
        return result;
    }
}