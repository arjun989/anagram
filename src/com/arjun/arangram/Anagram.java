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
	
	public void addAnagram(String ang){
		if (anagrams==null)
			anagrams=new ArrayList<String>();
		if (anagrams.contains(ang))
			return;
		anagrams.add(ang);
	}

	// // we need to use a key object here
	@PrimaryKey
	@Persistent
	private Key lexicalKey;
	
	@Persistent
	private ArrayList<String> anagrams;

}
