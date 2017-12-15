/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aihomework2;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Adapted from http://bit.ly/2fjEeXE
 *
 */
public class ForwardChaining {

    public static String KB = "";
    public static String query;

    public static ArrayList<String> agenda;
    public static ArrayList<String> clauses;
    public static ArrayList<Integer> count;
    public static ArrayList<String> inferred;

    public ForwardChaining(String t, String q) {

        agenda = new ArrayList<String>();
        clauses = new ArrayList<String>();
        inferred = new ArrayList<String>();
        count = new ArrayList<Integer>();
        KB = t;
        query = q;
        initialize(KB);
    }

// Method which sets up initial values for the algorithm
    public static void initialize(String kB) {
        //fill in the agenda queue with single statements
        String[] s = kB.split("\n");
        for (int i = 0; i < s.length; i++) {
            if (!s[i].contains(">")) {
                agenda.add(s[i]);
            }
        }
        //fill in clauses and count queues (count for the number of known
        //premises within the clauses)
        for (int i = 0; i < s.length; i++) {
            if (s[i].contains(">")) {
                clauses.add(s[i]);
                String[] temp = s[i].split(">");
                if (temp[0].contains(".")) {
                    String[] temp2 = temp[0].split("\\.");
                    count.add(temp2.length);
                } else {
                    count.add(1);
                }
            }
        }
    }

// Method which shows the results of the algorithm
    public String run() {
        String output = "";
        if (fc()) {
            // the method returned true so it entails
            output = "FC True: ";
            // for each inferred symbol
            for (int i = 0; i < inferred.size(); i++) {
                output += inferred.get(i) + ", ";
            }
            output += query;
        } else {
            output = "FC False";
        }
        return output;
    }

// FC algorithm
    public boolean fc() {

        while (!agenda.isEmpty()) {
            //pop the first object from agenda list
            String p = agenda.remove(0);
//            inferred.add(p);
            for (int i = 0; i < clauses.size(); i++) {
                Boolean b;
                String clause = clauses.get(i);
                String prem = clause.split(">")[0];
                String[] conj = prem.split("\\.");
                //check if the rule has premises or has the truth statements
                if (conj.length == 1) {
                    b = prem.equals(p);
                } else {
                    b = Arrays.asList(conj).contains(p);
                }
                //consider the rules with current premise in them
                if (b) {
                    //add the used rule to the inferred list if it is not there
                    
                    if(!inferred.contains(clauses.get(i))){
                        inferred.add(clauses.get(i));
                    }
                    int k = count.get(i);
                    count.set(i, --k);
                    //consider the premise, since all members are true
                    if (count.get(i) == 0) {
                        String head = clauses.get(i).split(">")[1];
                        if (head.equals(query)) {
                            return true;
                        }
                        agenda.add(head);
                    }
                }
            }
        }
        return false;
    }
}
