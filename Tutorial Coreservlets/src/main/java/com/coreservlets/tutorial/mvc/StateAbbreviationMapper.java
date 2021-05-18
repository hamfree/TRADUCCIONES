package com.coreservlets.tutorial.mvc;

import java.util.*;

/**
 * Gives a concrete implementation of the interface that maps US state names (e.g., Maryland) to post office abbreviations (e.g.,
 * MD)
 *
 * From <a href="http://courses.coreservlets.com/Course-Materials/">the coreservlets.com tutorials on servlets, JSP, JSF 2.x,
 * RESTful Web Services, Ajax, jQuery, GWT, Spring, Hibernate/JPA, Hadoop, and Java programming</a>.
 */
public class StateAbbreviationMapper implements StateAbbreviationService {

  private final Map<String, String> stateMap;

  private final String[][] stateArray
          = {{"Alabama", "AL"},
          {"Alaska", "AK"},
          {"Arizona", "AZ"},
          {"Arkansas", "AR"},
          {"California", "CA"},
          {"Colorado", "CO"},
          {"Connecticut", "CT"},
          {"Delaware", "DE"},
          {"District of Columbia", "DC"},
          {"Florida", "FL"},
          {"Georgia", "GA"},
          {"Hawaii", "HI"},
          {"Idaha", "ID"},
          {"Illinois", "IL"},
          {"Indiana", "IN"},
          {"Iowa", "IA"},
          {"Kansas", "KS"},
          {"Kentucky", "KY"},
          {"Louisiana", "LA"},
          {"Maine", "ME"},
          {"Maryland", "MD"},
          {"Massachusetts", "MA"},
          {"Michigan", "MI"},
          {"Minnesota", "MN"},
          {"Mississippi", "MS"},
          {"Missouri", "MO"},
          {"Montana", "MT"},
          {"Nebraska", "NE"},
          {"Nevada", "NV"},
          {"New Hampshire", "NH"},
          {"New Jersey", "NJ"},
          {"New Mexico", "NM"},
          {"New York", "NY"},
          {"North Carolina", "NC"},
          {"North Dakota", "ND"},
          {"Ohio", "OH"},
          {"Oklahoma", "OK"},
          {"Oregon", "OR"},
          {"Pennsylvania", "PA"},
          {"Rhode Island", "RI"},
          {"South Carolina", "SC"},
          {"South Dakota", "SD"},
          {"Tennessee", "TN"},
          {"Texas", "TX"},
          {"Utah", "UT"},
          {"Vermont", "VT"},
          {"Virginia", "VA"},
          {"Washington", "WA"},
          {"West Virginia", "WV"},
          {"Wisconsin", "WI"},
          {"Wyoming", "WY"}
          };

  public StateAbbreviationMapper() {
    stateMap = new HashMap<>();
    // Assumes state abbreviations are already in upper case
    for (String[] state : stateArray) {
      stateMap.put(state[0].toUpperCase(), state[1]);
    }
  }

  @Override
  public String findAbbreviation(String stateName) {
    return (stateMap.get(stateName.toUpperCase()));
  }
}
