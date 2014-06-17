/**
 * StepOneAndAlsoMore.java  
 * 6/16/14
 * @author - Jane Woods
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * The class StepOneAndAlsoMore scans a given document, 
 * the Federalist Papers,puts the entire thing into a 
 * hashmap, and then matches a user-entered querry to 
 * the occurrences of said querry in said document.
 */	
public class StepOneAndAlsoMore {
	static HashMap<String,ArrayList<Integer>> docIndex;
	
	public static void main(String [ ] args){
		
		StepOneAndAlsoMore hi = new StepOneAndAlsoMore();
		Scanner in = new Scanner(System.in);
		try {
			hi.indexFile("fedpaps");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
				//all of the printing happens from here to end of main method
				System.out.print("Enter your punctuationless querry for the Federalist Documents: ");
				
				//takes entered querry
				String querry = in.nextLine();
				ArrayList<Integer> vs = search(querry);
				
				//if statements to modify the output depending on number of occurrences
				String times = "time, at word " + vs.toString() + ".";
				if (vs.size() > 1) { times = "times, at words " + vs.toString() + "."; }
				if (vs.size() > 20) { times = "times, and that is too many times for me to print out all the places it occurs without AT LEAST ten dollars for my efforts."; }
				System.out.println();
				if (vs.size() != 0) { 
					System.out.println("\"" + querry + "\""+ " appears " + vs.size() + " " + times);
				} else {
					System.out.println("\"" + querry + "\"" + " does not appear in this most sacred of documents.");
				}
		
	}
		
	/**
	 * Returns an ArrayList<Integers> that contains all the valid 
	 * occurrences of the querry, which it gets by finding all 
	 * of the positions of all the words in the querry and sending
	 * them to <code>domagic</code>. It gets the positions from the
	 * arraylist that is attached to the key word in docIndex.   
	 *
	 * @param  String querry, the question entered in main
	 * @return  ArrayList<Integers> validSpots
	 * @see   domagic()
	 * 
	 */	
	private static ArrayList<Integer> search(String querry) {
		String[] searchWords = querry.split(" ");
		ArrayList<Integer> validSpots = null;
		for (int i = 0; i < searchWords.length; i++) {
			ArrayList<Integer> posOfWord = docIndex.get(searchWords[i].toLowerCase());
			validSpots = doMagic(validSpots, posOfWord);
		}
		return validSpots;
	}
	
	/**
	 * Returns an ArrayList<Integers> that contains all the intersections 
	 * between the valid spots (or words that occur) of the querry; these  
	 * are the ints that the program will output for the user in main.
	 * 
	 *
	 * @param  ArrayList<Integers> validspots 
	 * @param  ArrayList<Integers> posOfWord, positions of the word, created 
	 * in <code>search</code>.
	 * @return  ArrayList<Integers> intersect
	 * @see search()
	 */	
	private static ArrayList<Integer> doMagic(ArrayList<Integer> validSpots, ArrayList<Integer> posOfWord) {
		ArrayList<Integer> intersect = new ArrayList<Integer>();
		if (posOfWord == null) {
			return intersect;
		}
		//for the first word all of the positions are all of the valid
		if (validSpots == null) {
			return posOfWord; 
		} 
		//nested loop to go through each validSpots and then each posOfWords
		for (int i = 0; i < validSpots.size(); i++) {
			for (int j = 0; j < posOfWord.size(); j++) {
				if (validSpots.get(i) == posOfWord.get(j) - 1) {
					intersect.add(posOfWord.get(j));
				}
			}
		}
		return intersect;
	}
	
	/**
	 * Initializes hashmap docIndex and fills it with all
	 * of the words in the file passed into it. It makes the
	 * file into one long string and uses a basic pattern to
	 * cut it into words and enter each one via <code>add</code>.
	 *
	 * @param  String fileName
	 * @see add()
	 */	
	private void indexFile (String fileName) throws FileNotFoundException {
		File file = new File(fileName);
		docIndex = new HashMap<String,ArrayList<Integer>>();
		Scanner sc = new Scanner(file);
		Pattern pattern = Pattern.compile("\\w+");
		boolean done = false;
		int i = 0;
		//puts each word into hashmap via add method
		while (!done) {
			String result = sc.findWithinHorizon(pattern, 999);
			if (result == null) {
				done = true;
			} else {
				add(result, i);
				i++;
			}
		}
	}
	
	/**
	 * Just adds each instance of the word to
	 * ArrayList<Integer> wps and then adds wps
	 * (which contains all instances of
	 * the word) with the word to the hashmap 
	 * docIndex. 
	 *
	 * @param  String word
	 * @param  int number
	 */	
	private void add(String word, int number) {
		word = word.toLowerCase();
		ArrayList<Integer> wps;
		if(docIndex.containsKey(word)) {
			wps = docIndex.get(word);
		} else {
			wps = new ArrayList<Integer>();
			docIndex.put(word, wps);
		}
		wps.add(new Integer(number));
	}
	
	/**
	 * A method to print the hashmap docIndex, only used in testing. 
	 */	
	public void printIndex() {
		if (docIndex == null) {
			System.out.println("No document indexed, person");
		} else {
			System.out.println("There are " + docIndex.size() + " words in this document.");
			for (Iterator<String> it = docIndex.keySet().iterator();it.hasNext();) {
				String lakey = it.next();
				System.out.print(lakey + " : ");
				ArrayList<Integer> lating = docIndex.get(lakey);
				for (int i = 0; i < lating.size(); i++) {
					System.out.print(lating.get(i) + " ");
				}
				System.out.println();
				
			}
		}
	}

	
	

}