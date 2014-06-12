import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Pattern;


public class StepOneAndAlsoMore {
	static HashMap<String,ArrayList<Integer>> docIndex;
	
	public static void main(String [ ] args)
	{
		StepOneAndAlsoMore hi = new StepOneAndAlsoMore();
		Scanner in = new Scanner(System.in);
		try {
			hi.indexFile("fedpaps");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
				//all of the printing happens here
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
		
		
	private static ArrayList<Integer> search(String querry) {
		String[] searchWords = querry.split(" ");
		ArrayList<Integer> validSpots = null;
		for (int i = 0; i < searchWords.length; i++) {
			ArrayList<Integer> posOfWord = docIndex.get(searchWords[i].toLowerCase());
			validSpots = doMagic(validSpots, posOfWord);
			//System.out.print("after " + searchWords[i] + " : ");
			//for (int j = 0; j < validSpots.size(); j++) {
			//	System.out.print(validSpots.get(j) + " ");
			//}
			//System.out.println();
		}
		return validSpots;
	}
	
	//private int

	private static ArrayList<Integer> doMagic(ArrayList<Integer> validSpots, ArrayList<Integer> posOfWord) {
		ArrayList<Integer> intersect = new ArrayList<Integer>();
		if (posOfWord == null) {
			return intersect;
		}
		if (validSpots == null) {
			return posOfWord; //for the first word all of the positions are all of the valid
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


	private void indexFile (String fileName) throws FileNotFoundException {
		File file = new File(fileName);
		//create new index
		docIndex = new HashMap<String,ArrayList<Integer>>();
		Scanner sc = new Scanner(file);
		Pattern pattern = Pattern.compile("\\w+");
		boolean done = false;
		int i = 0;
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