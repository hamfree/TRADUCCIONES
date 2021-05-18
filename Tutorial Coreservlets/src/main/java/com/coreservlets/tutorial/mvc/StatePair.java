package com.coreservlets.tutorial.mvc;

import java.io.*;

// Serializable because we store the bean in the session in one of the exercises
public class StatePair implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = -5280921317435237317L;
  private final String stateName, stateAbbreviation;

  public StatePair(String stateName, String stateAbbreviation) {
    this.stateName = stateName;
    this.stateAbbreviation = stateAbbreviation;
  }

  public String getStateName() {
    return (stateName);
  }

  public String getStateAbbreviation() {
    return (stateAbbreviation);
  }
}
