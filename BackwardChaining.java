/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aihomework2;

import java.util.ArrayList;

/**
 * Adapted from http://bit.ly/2f8wRPT
 *
 */
public class BackwardChaining {
  
    public static String KB;
    public static String query;
    public static ArrayList<String> agenda;
    public static ArrayList<String> facts;
    public static ArrayList<String> clauses;
    public static ArrayList<String> inferred;
    public static ArrayList<String> visited;

    public BackwardChaining(String t, String q) {
   
        agenda = new ArrayList<String>();
        clauses = new ArrayList<String>();
        inferred = new ArrayList<String>();
        facts = new ArrayList<String>();
        visited = new ArrayList<String>();
        KB = t;
        query = q;
        initialize(KB);
    }

// Method which sets up initial values for the algorithm
    public static void initialize(String kB) {
        agenda.add(query);

        String[] s = kB.split("\n");
        for (int i = 0; i < s.length; i++) {
            if (!s[i].contains(">")) {
                facts.add(s[i]);
            }else{
                clauses.add(s[i]);
            }
        }
    }

// Method which shows the results of the algorithm
    public String run() {
        String output = "";
        if (bc()) {
            output = "BC True: ";
            for (int i = visited.size() - 1; i >= 0; i--) {
                if (i == 0) {
                    output += visited.get(i);
                } else {
                    output += visited.get(i) + ", ";
                }
            }
        }
        else {
            output = "BC False";
        }
        return output;
    }

// BC algorithm
    public boolean bc() {
        while (!agenda.isEmpty()) {
            String q = agenda.remove(agenda.size() - 1);
            inferred.add(q);
            // It the element is a fact, discard it
            if (!facts.contains(q)) {
                //if not add to the new array
                ArrayList<String> premises = new ArrayList<String>();
                for (int i = 0; i < clauses.size(); i++) {
                    Boolean b;
                    String conclusion = clauses.get(i).split(">")[1];
                    //check if the premise is found in conclusion
                    if (conclusion.equals(q)) {
                        b = true;
                    } else {
                        b = false;
                    }
                    //if yes, look at its premises
                    if (b) {
                        visited.add(clauses.get(i));
                        String premise = clauses.get(i).split(">")[0];
                        String[] conj = premise.split("\\.");
                        ArrayList<String> temp = new ArrayList<String>();
                        
                        for (int l = 0; l < conj.length; l++) {
                            if (!agenda.contains(conj[l])) {
                                temp.add(conj[l]);
                            }
                        }                        
                        for (int j = 0; j < temp.size(); j++) {
                            premises.add(temp.get(j));
                        }
                    }
                }                
                if (premises.isEmpty()) {
                    return false;
                } else {
                    //check for previously processed and add to the agenda
                    for (int i = 0; i < premises.size(); i++) {
                        if (!inferred.contains(premises.get(i))) {
                            agenda.add(premises.get(i));
                        }
                    }
                }
            }
        }
        
        return true;
    }
}
