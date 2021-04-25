package test_util;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DB_Utility {

    private static Connection con;
    private static ResultSet rs;
    private static Statement stm;
    private static ResultSetMetaData rsmd;


//1
    public static void createConnection(){

        String url = ConfigurationReader.getProperty("hr.database.url");
        String username = ConfigurationReader.getProperty("hr.database.username");
        String password = ConfigurationReader.getProperty("hr.database.password");

        try {
            con = DriverManager.getConnection(url, username, password);
            System.out.println("Connection successfull");
        }catch (SQLException e){
            System.out.println("Connection has failed" + e.getMessage());
        }

    }
//2
    public static void createConnection(String url, String username, String password){

        try {
            con = DriverManager.getConnection(url, username, password);
            System.out.println("Connection Successful");
        }catch (SQLException e){
            System.out.println("Connection has failed" + e.getMessage());

        }
    }
//3
    public static ResultSet runQuery(String sql){

        try {
            stm = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stm.executeQuery(sql);//setting the value of ResultSet object
            rsmd = rs.getMetaData(); //setting the value of ResultMetaData for reuse
        }catch (SQLException e){
            System.out.println("Error occurred while running query " + e.getMessage());
        }
        return rs;
    }

//4
    public static void destroy(){
        try{
            if(rs != null) rs.close();
            if(stm != null)stm.close();
            if(con != null)con.close();
        }catch (SQLException e){
            System.out.println("Error occurred while closing resources "+ e.getMessage());
        }
    }
//5 Row Count
    public static int rowCount() {
        int rowCount = 0;
        try {
            rs.last();
            rowCount = rs.getRow();
        } catch (SQLException e) {
            System.out.println("Error occurred while getting row count " + e.getMessage());
        }
        finally {
            {
                resetCursor();
            }
        }
        return rowCount;
    }

//6 Column Count
    public static int getColumnCount(){
        int columnCount = 0;
        try {
            columnCount = rsmd.getColumnCount();
        }catch (SQLException e){
            System.out.println("Error occurred while getting column count " + e.getMessage());
        }
        return columnCount;
    }

    //7 Get all the column names into a list object

    public static List<String> getAllColumnNamesAsLIst(){

        List<String> columnNameLst = new ArrayList<>();
        try {
            for (int colIndex = 1; colIndex <= getColumnCount(); colIndex++) {
                String columnName = rsmd.getColumnName(colIndex);
                columnNameLst.add(columnName);
            }
        }catch (SQLException e){
            System.out.println("Error occurred while getAllColumnNameList "+ e.getMessage());
        }
        return columnNameLst;

    }

    //8 Get entire row of data according to row number

    public static List<String> getRowDataAsList(int rowNum){

        List<String> rowDataAsLst = new ArrayList<>();
        int colCount = getColumnCount();
        try {
            rs.absolute(rowNum);

            for (int colIndex = 1; colIndex <= colCount; colIndex++) {

                String cellValue = rs.getString(colIndex);
                rowDataAsLst.add(cellValue);
            }

        } catch (SQLException e) {
            System.out.println("Error occurred while getRowDataAsList " + e.getMessage());
        }finally {
            resetCursor();
        }

        return rowDataAsLst;
    }

    //9 Get cell value according to row num and column index

    public static String getCellValue(int rowNum, int colIndex){

        String cellValue = "";

        try {
            rs.absolute(rowNum);
            cellValue = rs.getString(colIndex);
        } catch (SQLException e) {
            System.out.println("Error occurred while get CellValue " + e.getMessage());
        } finally {
            resetCursor();
        }
        return cellValue;
    }

    //9 Get cell value according to row num and column name
    public static String getCellValue(int rowNum, String columnName){

        String cellValue = "";

        try {
            rs.absolute(rowNum);
            cellValue = rs.getString(columnName);
        } catch (SQLException e) {
            System.out.println("Error occurred while get CellValue " + e.getMessage());
        }finally {
            resetCursor();
        }
        return cellValue;
    }

    //10 GETTING ENTIRE COLUMN DATA AS LIST ACCORDING TO COLUMN NUMBER

    public static List<String> getColumnDataAsLIst(int columnNum){

        List<String> columnDataLst = new ArrayList<>();
        try {
            rs.beforeFirst(); // make sure the cursor is at before first location
            while (rs.next()) {
            String cellValue = rs.getString(columnNum);
            columnDataLst.add(cellValue);
            }
        }catch(SQLException e){
            System.out.println("ERROR OCCURRED WHILE getColumnDataAsList " + e.getMessage());
            }finally {
            resetCursor();
        }
        return columnDataLst;
        }

//11 GETTING ENTIRE COLUMN DATA AS LIST ACCORDING TO COLUMN NUMBER

    public static List<String> getColumnDataAsLIst(String columnName){

        List<String> columnDataLst = new ArrayList<>();
        try {

            while (rs.next()) {
                String cellValue = rs.getString(columnName);
                columnDataLst.add(cellValue);
            }
            rs.beforeFirst();
        }catch(SQLException e){
            System.out.println("ERROR OCCURRED WHILE getColumnDataAsList " + e.getMessage());
        }finally {
            resetCursor();
        }
        return columnDataLst;
    }

    //12 DISPLAY ALL DATA FROM THE RESULTSET OBJECT

    public static void displayAllData(){
        int columnCount = getColumnCount();
        resetCursor();
        try{
            while (rs.next()){
                for (int colIndex = 1; colIndex <= columnCount; colIndex++) {
                    System.out.print(rs.getString(colIndex)+ "\t\t\t\t");
                }
                System.out.println();
            }
        }catch(SQLException e){
            System.out.println("Error occurred while displayAllData" + e.getMessage());
        }
    }

    //13 reset cursor
    public static void resetCursor() {
        try {
            rs.beforeFirst();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

        //14 Entire row data as Map<String,String>
        public static Map<String,String> getRowMap(int rowNum){

        Map<String,String> rowMap = new LinkedHashMap<>();
        int columnCount = getColumnCount();
        try{
            rs.absolute(rowNum);

            for (int colIndex = 1; colIndex <=columnCount; colIndex++) {
                String columnName = rsmd.getColumnName(colIndex);
                String cellValue = rs.getString(colIndex);
                rowMap.put(columnName,cellValue);

            }

        }catch(SQLException e){
            System.out.println("Error occurred while displaying All data "+ e.getMessage());
            }finally {
            {
                resetCursor();
            }
        }

        return rowMap;
        }

        //15 Store All rows as List of Map object

    public static List<Map<String,String>> getAllRowAsListOfMap(){

        List<Map<String,String>> allRowListOfMap = new ArrayList<>();
        int rowCount = rowCount();
        //move from first row till last row
        //get each rao as map object and add it to the list
        for (int rowindex = 1; rowindex <= rowCount ; rowindex++) {
            Map<String,String>rowMap = getRowMap(rowindex);
            allRowListOfMap.add(rowMap);

        }
        resetCursor();

        return allRowListOfMap;
    }

    //16 Get first cell value at first row first column

    public static String getFirstRowFirstColumn(){

        return getCellValue(1,1);
    }


}
