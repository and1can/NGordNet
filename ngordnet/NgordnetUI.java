package ngordnet;
import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.In;

/** Provides a simple user interface for exploring WordNet and NGram data.
 *  @author [Andy Chu]
 */
public class NGordnetUI {
    public static void main(String[] args) {
        In in = new In("./ngordnet/ngordnetui.config");
        System.out.println("Reading ngordnetui.config...");
        String wordFile = in.readString();
        String countFile = in.readString();
        String synsetFile = in.readString();
        String hyponymFile = in.readString();
        System.out.println("\nBased on ngordnetui.config, using the following: "
        + wordFile + ", " + countFile + ", " + synsetFile +
        ", and " + hyponymFile + ".");
        int startDate;
        int endDate;
        NGramMap ngm = new NGramMap(wordFile, countFile);
        WordNet wn = new WordNet(synsetFile, hyponymFile);
        while (true) {
            try {
                System.out.println("> ");
                String line = StdIn.readLine();
                String[] rawTokens = line.split(" ");
                String command = rawTokens[0];
                switch (command) {
                case "quit":
                    System.out.println("quit now");
                    return;
                case "help":
                    System.out.println("quit:");
                    System.out.println("help");
                    System.out.println("range[start][end]");
                    System.out.println("count[word][year]");
                    System.out.println("hyponyms[word]");
                    System.out.println("history[words...]"); 
                    System.out.println("hypohist [words...]:"); 
                    break;
                case "range":
                    startDate = Integer.parseInt(rawTokens[1]);
                    endDate = Integer.parseInt(rawTokens[2]);
                    System.out.println("Start date: " + startDate);
                    System.out.println("End date: " + endDate);
                    break;
                case "count":
                    System.out.println(ngm.countInYear(rawTokens[1], Integer.parseInt(rawTokens[2])));
                    break;
                case "hyponyms":
                    System.out.println(wn.hyponyms(rawTokens[1]));
                    break;
                default:
                    System.out.println("Invalid command.");
                    break;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Invalid command");
            } catch (NumberFormatException e) {
                System.out.println("Wrong type");
            }  
        }
    }
}  
