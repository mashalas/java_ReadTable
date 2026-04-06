/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.example.readtable;

/**
 *
 * @author alexey
 */
public class ReadTableUsage {

    public static void main(String[] args) {
        String currentDirectory = System.getProperty("user.dir");
        System.out.println("currentDirectory: " + currentDirectory);
        
        ReadTable rt = new ReadTable();
        rt.read(
                currentDirectory + java.io.File.separator + "_data" + java.io.File.separator + "sample1.txt",
                "#",
                ":",
                true
        );
        rt.print("TABLE", "\t", 0, ReadTable.ALL_ROWS);
        System.out.println(rt.get(2, "name"));
        System.out.println(rt.get(3, 2));
        System.out.println(rt.exists(2, "namE"));
        rt.printHeader(";");
        
        String[] row1 = rt.getRow(1);
        for (int j=0; j<row1.length; j++) {
            System.out.print(row1[j] + " ; ");
        }
        System.out.println("");
        
        String[] columnLogins = rt.getColumn(1);
        for (int i=0; i<columnLogins.length; i++) {
            System.out.print(columnLogins[i] + " ; ");
        }
        System.out.println("");
    }
}
