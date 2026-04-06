/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.readtable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author alexey
 */
public class ReadTable {
    public final static int ALL_ROWS = -1;
    protected ArrayList<String> header = new ArrayList<>();
    protected ArrayList<HashMap<String, String>> data = new ArrayList<>();
    
    
    public void reset() {
        this.header.clear();
        this.data.clear();
    }
    
    public String get(int rowNumber, String columnName) {
        if (this.exists(rowNumber, columnName))
            return this.data.get(rowNumber).get(columnName);
        return null;
    }
    
    public String get(int rowNumber, int columnNumber) {
        if (this.exists(rowNumber, columnNumber))
            return this.data.get(rowNumber).get(this.header.get(columnNumber));
        return null;
    }
    
    public Boolean exists(int rowNumber, String columnName) {
        if (rowNumber < 0)
            return false;
        if (rowNumber >= this.data.size())
            return false;
        if (!this.header.contains(columnName))
            return false;
        return true;
    }
    
    public Boolean exists(int rowNumber, int columnNumber) {
        if (rowNumber < 0)
            return false;
        if (rowNumber >= this.data.size())
            return false;
        if (columnNumber < 0)
            return false;
        if (columnNumber >= this.header.size())
            return false;
        return true;
    }
    
    public int getRowsCount() {
        return this.data.size();
    }
    
    public String[] getHeader() {
        //return this.header.toArray(new String[0]);
        return this.header.toArray(String[]::new);
    }
    
    public String[] getRow(int rowNumber) {
        ArrayList<String> result = new ArrayList<>();
        for (int j=0; j<this.header.size(); j++) {
            result.add( this.data.get(rowNumber).get(this.header.get(j)) );
        }
        //return result.toArray(new String[0]);
        return result.toArray(String[]::new);
    }
    
    public String[] getColumn(String columnName) {
        ArrayList<String> result = new ArrayList<>();
        for (int i=0; i<this.data.size(); i++) {
            result.add(this.data.get(i).get(columnName));
        }
        //return result.toArray(new String[0]);
        return result.toArray(String[]::new);
    }
    
    public String[] getColumn(int columnIndex) {
        return getColumn(this.header.get(columnIndex));
    }
    
    //Удалить однострочный комментарий
    public static String removeComment(String s, String commentSequence) {
        int p = s.indexOf(commentSequence);
        if (p >= 0)
            //ab#cde
            //012345
            return s.substring(0, p);
        return s;
    }
    
    public Boolean read(
            String filename,
            String commentSequence,
            String splitBy,
            Boolean firstLineIsHeader
            //int skipFirstN
    ) {
        header.clear();
        int i;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line); // Обработка каждой строки
                line = removeComment(line, commentSequence);
                if (line.length() == 0)
                    continue;
                String[] parts = line.split(splitBy);
                if (header.isEmpty()) {
                    //читается заголовок
                    if (firstLineIsHeader) {
                        //первая строка файла является заголовком
                        for (String s : parts) {
                            header.add(s);
                        }
                        continue;
                    }
                    else {
                        //файл без заголовка, обращаться к столбцам по номерам
                        for (i=0; i<parts.length; i++) {
                            header.add(String.valueOf(i));
                        }
                    }
                }
                //читается строка с данными
                if (parts.length != header.size()) {
                    continue; //количество столбцов в строке не совпадает с размером заголовка
                }
                HashMap<String, String> row = new HashMap<>();
                for (i=0; i<header.size(); i++) {
                    final String key = header.get(i);
                    final String value = parts[i];
                    row.put(key, value);
                }
                data.add(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }        
        return true;
    }
    
    public void printHeader(String divider) {
        this.print("", divider, 0, 0);
    }
    
    public void print(String caption, String divider) {
        this.print(caption, divider, 0, ReadTable.ALL_ROWS);
    }
    
    public void print(String caption, String divider, int sinceRow, int untilRow) {
        int i, j;
        if (untilRow < 0)
            untilRow = data.size();
        if (caption.length() > 0)
            System.out.println("--- " + caption + " ---");
        for (j=0; j<this.header.size(); j++) {
            System.out.print(this.header.get(j));
            if (j < this.header.size()-1)
                System.out.print(divider);
            else
                System.out.println("");
        }
        for (i=sinceRow; i<untilRow; i++) {
            for (j=0; j<this.header.size(); j++) {
                System.out.print(this.data.get(i).get(this.header.get(j)));
            if (j < this.header.size()-1)
                System.out.print(divider);
            else
                System.out.println("");
            }
        }
        if (caption.length() > 0)
            System.out.println("--- end of " + caption + " ---");
    }
    
}
