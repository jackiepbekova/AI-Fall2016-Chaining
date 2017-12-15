/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aihomework2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Read {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
        BufferedReader br;

        String currLine="", KB="";
        br = new BufferedReader(new FileReader("input.txt"));
        
            while ((currLine = br.readLine()) != null) {
                KB += currLine;
                KB += "\n";
            }
            
            System.out.println(KB);
            ForwardChaining fc = new ForwardChaining(KB, "Q");
            System.out.println(fc.run());
            
            BackwardChaining bc = new BackwardChaining(KB, "Q");
            System.out.println(bc.run());
            
            br.close();
            
   }
    
}
