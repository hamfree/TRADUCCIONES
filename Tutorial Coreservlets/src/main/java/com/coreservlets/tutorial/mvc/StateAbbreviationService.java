package com.coreservlets.tutorial.mvc;

/**
 * Main interface for classes that let you retrieve state abbreviations (e.g., "MD") from a full state name (e.g., "Maryland"). This
 * interface provides no way to modify the mappings, so you don't have to worry about race conditions with shared data.
 */
public interface StateAbbreviationService {

  public String findAbbreviation(String stateName);
}
