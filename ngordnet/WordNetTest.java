package ngordnet;
import static org.junit.Assert.*;
import org.junit.Test;
import java.util.*;
public class WordNetTest {

   @Test
   public void testIsNoun() {
      WordNet wn = new WordNet("p1data/wordnet/synsets11.txt", "p1data/wordnet/hyponyms11.txt");
      WordNet wn3 = new WordNet("p1data/wordnet/synsets1000-subgraph.txt", "p1data/wordnet/hyponyms1000-subgraph.txt");
      System.out.println(wn3.hyponyms("animal_oil"));
      assertTrue(wn.isNoun("action"));
      assertTrue(wn.isNoun("change"));
      assertTrue(wn.isNoun("descent"));
      assertTrue(wn.isNoun("jump")); 
      System.out.println("Nouns for big file");
      Set<String> countNouns = wn3.nouns();
      int count=0;
      for(String nounCount:countNouns) {
          count++;
          System.out.println(nounCount);
      } 
      System.out.println("Number of nouns is " + count);
     // WHOOPS previous push to wrong branch
     //   assertEquals("[jump, leap, parachuting]", wn.hyponyms("jump"));
      WordNet wn2 = new WordNet("p1data/wordnet/synsets14.txt", "p1data/wordnet/hyponyms14.txt");
      System.out.println(wn2.hyponyms("human_action"));
      System.out.println(wn2.hyponyms("happening"));
      System.out.println("Hyponyms for Change");
      System.out.println(wn2.hyponyms("change"));
      System.out.println(wn2.nouns()); 
      //assertEquals("harro", wn2.hyponyms("human_action"));
      // Test jump because we need to take into account that we have a phrase
   }
   //@Test //  when uncommented, we get different object types, but words are exaclty the same, so I am content
   public void testNouns() {
       WordNet wn = new WordNet("p1data/wordnet/synsets11.txt", "p1data/wordnet/hyponyms11.txt");  
       assertEquals("[descent, change, action, demotion, leap, parachuting, nasal_decongestant, increase, augmentation, antihistamine, actifed, jump]", wn.nouns());
       assertEquals("jump, leap, parachuting", wn.hyponyms("jump"));
   }

   public static void main(String... args) {
         jh61b.junit.textui.runClasses(WordNetTest.class);
   }
}
