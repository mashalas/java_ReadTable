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
        ReadTable rt = new ReadTable();
        rt.read(
                "/mnt/lindata/sources/java/ReadTable/_data/sample1.txt",
                "#",
                ":",
                !true
        );
        rt.print("TABLE", "\t", 0, ReadTable.ALL_ROWS);
        System.out.println(rt.get(2, "name"));
        System.out.println(rt.get(3, 2));
        System.out.println(rt.exists(2, "namE"));
        rt.printHeader(";");
    }
}
