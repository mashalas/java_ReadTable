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
 * @author mashalas_aa
 */
public class ReadTable {
    public final static int ALL_ROWS = -1;
    //public final static int ALL_COLUMNS = -2;
    protected ArrayList<String> header = new ArrayList<>();
    protected ArrayList<HashMap<String, String>> data = new ArrayList<>();
    
    /**
     * Очистить список столбцов и данные
     */
    public void reset() {
        this.header.clear();
        this.data.clear();
    }
    
    /** Скорректировать номер столбца. Если отрицательный - значит указана нумерация с конца
     * -1 - последний столбец; -2 - предподследний столбец.
     * Фактическое наличие номера столбца не проверяется.
     * @param columnNumber номер столбца (может быть отрицательным, если нумерация с конца)
     * @return скорректированный номер столбца
     */
    public int fixColumnNumber(int columnNumber) {
        //если отрицательный номер столбца - номер с конца (-1 = последний столбец, -2 = препоследний)
        if (columnNumber < 0)
            columnNumber = this.header.size() + columnNumber;
        return columnNumber;
    }
    
    /**
     * Получить значение по номеру строки и имени столбца
     * @param rowNumber номер строки
     * @param columnName имя столбца
     * @return значение ячейки данных
     */
    public String get(int rowNumber, String columnName) {
        if (this.exists(rowNumber, columnName))
            return this.data.get(rowNumber).get(columnName);
        return null;
    }
    
    /**
     * Получить значение по номеру строки и номеру столбца. Если отрицательный - значит указана нумерация с конца
     * -1 - последний столбец; -2 - предподследний столбец.
     * @param rowNumber номер строки
     * @param columnName имя столбца
     * @return значение ячейки данных
     */
    public String get(int rowNumber, int columnNumber) {
        columnNumber = this.fixColumnNumber(columnNumber);
        if (this.exists(rowNumber, columnNumber))
            return this.data.get(rowNumber).get(this.header.get(columnNumber));
        return null;
    }
    
    /**
     * Проверить существование ячейки данных по номеру строки и имени столбца
     * @param rowNumber номер строки
     * @param columnName имя столбца
     * @return 
     */
    public Boolean exists(int rowNumber, String columnName) {
        if (rowNumber < 0)
            return false;
        if (rowNumber >= this.data.size())
            return false;
        if (!this.header.contains(columnName))
            return false;
        return true;
    }
    
    /**
     * Проверить существование ячейки данных по номеру строки и номеру столбца. Если отрицательный - значит указана нумерация с конца
     * -1 - последний столбец; -2 - предподследний столбец.
     * @param rowNumber номер строки
     * @param columnName имя столбца
     * @return значение ячейки данных
     */
    public Boolean exists(int rowNumber, int columnNumber) {
        columnNumber = this.fixColumnNumber(columnNumber);
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
    
    /**
     * Сколько строк содержится в прочитанной таблице данных
     * @return целое число с количество строк
     */
    public int getRowsCount() {
        return this.data.size();
    }
    
    /**
     * Получить строковый массив с названиями столбцов
     * @return строковый массив с названиями столбцов
     */
    public String[] getHeader() {
        return this.header.toArray(new String[0]);
        //return this.header.toArray(String[]::new);
    }
    
    /**
     * Получить в виде массив строк значения указанной строки 
     * @param rowNumber номер строки
     * @return массив строк со значениями указанной строки
     */
    public String[] getRow(int rowNumber) {
        ArrayList<String> result = new ArrayList<>();
        for (int j=0; j<this.header.size(); j++) {
            result.add( this.data.get(rowNumber).get(this.header.get(j)) );
        }
        return result.toArray(new String[0]);
        //return result.toArray(String[]::new); //не работает в java-1.8
    }
    
    /**
     * Получить в виде массив строк значения указанного по имени столбца
     * @param columnName имя сторлбцв
     * @return массив строк со значениями указанной строки
     */
    public String[] getColumn(String columnName) {
        ArrayList<String> result = new ArrayList<>();
        for (int i=0; i<this.data.size(); i++) {
            result.add(this.data.get(i).get(columnName));
        }
        return result.toArray(new String[0]);
        //return result.toArray(String[]::new); //не работает в java-1.8
    }
    
    /**
     * Получить в виде массив строк значения указанного по имени столбца
     * @param columnNumber номер сторлбца. Если отрицательный - значит указана нумерация с конца
     * -1 - последний столбец; -2 - предподследний столбец.
     * @return массив строк со значениями указанного стролбца
     */
    public String[] getColumn(int columnNumber) {
        columnNumber = this.fixColumnNumber(columnNumber);
        return getColumn(this.header.get(columnNumber));
    }
    
    /**
     * Объединить значения строки таблицы (row) в одну текстовую строку (String) используя sep в качестве разделителя 
     * @param rowNumber номер строки таблицы
     * @param sep разделитель между значениями
     * @return текстовая строка со значениями строки 
     */
    public String joinRow(int rowNumber, String sep) {
        return joinRow(rowNumber, sep, 0, this.header.size(), null);
    }
    
    /**
     * Объединить значения строки таблицы (row) в одну текстовую строку (String) используя sep в качестве разделителя 
     * В объединённую строку не включается столбец с указанным индексом
     * @param rowNumber номер строки таблицы
     * @param sep разделитель между значениями
     * @param excludeColumnNumber номер столбца, который не включать в итоговую текстовую строку
     * @return текстовая строка со значениями строки 
     */
    public String joinRow(int rowNumber, String sep, int excludeColumnNumber) {
        int[] excludeColumnNumberArray = {excludeColumnNumber};
        return joinRow(rowNumber, sep, 0, this.header.size(), excludeColumnNumberArray);
    }
    
    /**
     * Содержится ли число в массиве
     * @param checking_value число, нахождение которого надо проверить
     * @param items массив чисел, среди которых может находиться проверяемое число
     * @return true если массив содержит проверяемое число; false - если не содержит
     */
    public static Boolean inArray(int checking_value, int[] items) {
        for (int x : items) {
            if (x == checking_value)
                return true;
        }
        return false;
    }
    
    /**
     * Объединить значения строки таблицы (row) в одну текстовую строку (String) используя sep в качестве разделителя 
     * В объединённую строку не включается столбец с указанным индексом
     * @param rowNumber номер строки таблицы
     * @param sep разделитель между значениями
     * @param sinceColumnNumber объединять столбцы начиная с этого номера столбца
     * @param untilColumnNumber объединять столбцы до этого номера столбца (не включая его)
     * @param excludeColumnsNumbers массив со индексами столбцов, которые не включать в итоговую текстую строку
     * @return 
     */
    public String joinRow(int rowNumber, String sep, int sinceColumnNumber, int untilColumnNumber, int[] excludeColumnsNumbers) {
        //untilColumnNumber = this.fixColumnNumber(untilColumnNumber) + 1;
        StringBuilder sb = new StringBuilder();
        for (int j=sinceColumnNumber; j<untilColumnNumber; j++) {
            if (excludeColumnsNumbers != null)
                if (inArray(j, excludeColumnsNumbers))
                    continue; //столбец с этим номером находится в списке игнорируемых
            if (sb.length() > 0)
                sb.append(sep);
            sb.append(this.data.get(rowNumber).get(this.header.get(j)));
        }
        return sb.toString();
    }

    
    /**
     * Удалить однострочный комментарий
     * @param s строка, в которой может содержаться комментарий
     * @param commentSequence символы обозначающие начало комментария
     * @return строка без комментариев, если они в ней были или исходная строка как есть
     */
    public static String removeComment(String s, String commentSequence) {
        int p = s.indexOf(commentSequence);
        if (p >= 0)
            //ab#cde
            //012345
            return s.substring(0, p);
        return s;
    }
    
    /**
     * Прочитать "таблицу" из текстового файла
     * @param filename имя файла
     * @param commentSequence последовательность обозначающая начало комментария
     * @param splitBy символ-разделитель между столбцами
     * @param firstLineIsHeader первая непустая строка в файле содержит названия столбцов, иначе названия столбцов - их номера начиная с 0
     * @return успешноли прочитан файл
     */
    public Boolean read(
            String filename,
            String commentSequence,
            String splitBy,
            Boolean firstLineIsHeader
    ) {
        header.clear();
        int i;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
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
                if (parts.length < header.size()) {
                    continue; //количество столбцов в строке меньше размера заголовка
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
    
    /**
     * Вывести в стандартный вывод заголовок
     * @param divider разделитель между столбцами
     */
    public void printHeader(String divider) {
        this.print("", divider, 0, 0);
    }
    
    /**
     * Вывести в стандартный вывод таблицу
     * @param caption подпись к выводимой таблице
     * @param divider разделитель между столбцами
     */
    public void print(String caption, String divider) {
        this.print(caption, divider, 0, ReadTable.ALL_ROWS);
    }
    
    /**
     * Вывести в стандартный вывод таблицу
     * @param caption подпись к выводимой таблице
     * @param divider разделитель между столбцами
     * @param sinceRow выводить строки таблицы начиная с этой
     * @param untilRow выводить строки таблицы до этой (не включая её)
     */
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
