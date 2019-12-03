package com.maxminmajcdg.entities;

import java.util.Map;

public interface VoteEntity {
	public Map<PartyCategory, Integer> getVotes();
	public String toString();
}
