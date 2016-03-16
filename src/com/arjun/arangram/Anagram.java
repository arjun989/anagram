package com.arjun.arangram;
import java.util.ArrayList;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class Anagram {

	public Key getLexicalKey() {
		return lexicalKey;
	}

	public void setLexicalKey(Key lexicalKey) {
		this.lexicalKey = lexicalKey;
	}

	public ArrayList<String> getAnagrams() {
		return anagrams;
	}

	public void setAnagrams(ArrayList<String> anagrams) {
		this.anagrams = anagrams;
	}
	
	public boolean addAnagram(String ang){
		if (anagrams==null)
			anagrams=new ArrayList<String>();
		if (anagrams.contains(ang))
			return false;
		anagrams.add(ang);
		return true;
	}
	
	public String getResults(String q){
		String temp="";
		for (int i=0;i<anagrams.size();i++){
			if (!anagrams.get(i).equals(q)){
				temp+=anagrams.get(i);
				if (!(i+1==anagrams.size()))
					temp+=" , ";
			}
		}
		if (temp.equals(""))
				return "Nothing Found !";
		else if (temp.endsWith(" , ")){
			temp=trimStringByString(temp," , ");
		}
		return temp;
	}

	// // we need to use a key object here
	@PrimaryKey
	@Persistent
	private Key lexicalKey;
	
	@Persistent
	private ArrayList<String> anagrams;

	public static String trimStringByString(String text, String trimBy) {
	    int beginIndex = 0;
	    int endIndex = text.length();

	    while (text.substring(beginIndex, endIndex).startsWith(trimBy)) {
	        beginIndex += trimBy.length();
	    } 

	    while (text.substring(beginIndex, endIndex).endsWith(trimBy)) {
	        endIndex -= trimBy.length();
	    }

	    return text.substring(beginIndex, endIndex);
	}
}
