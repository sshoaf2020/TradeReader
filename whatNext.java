/* package codechef; // don't place package name! */

import java.util.*;
import java.lang.*;
import java.io.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.*;


public class whatNext implements Reader
{

    Trade firstTrade;
    Trade secondTrade;
    public static Trade lastTrade;
    public static String file1;
    public static String file2;
    public static String lastFile;
    public static int firstFileTradeIndex =0;
    public static int secondFileTradeIndex =0;

    public Trade next(){


        Trade nextTrade = new Trade();
        boolean over = false;
        String lastFile;
        while (true) {

            try (Stream<String> lines = Files.lines(Paths.get(file1))) {
                String line1 = lines.skip(firstFileTradeIndex).findFirst().orElse(null);
                if(line1 == null)
                {
                    throw new EOFException();
                }
                Trade tempTrade = new Trade();
                firstTrade = setTrade(line1);
            } catch (EOFException e) {
                System.out.println("End Of First File");
                lastFile = "file1";
                break;
            } catch (IOException e) {
                System.out.println(e);
            }

            try (Stream<String> lines = Files.lines(Paths.get(file2))) {
                String line2 = lines.skip(secondFileTradeIndex).findFirst().orElse(null);
                // System.out.println(line2);
                Trade tempTrade2 = new Trade();
                if(line2 == null)
                {
                    throw new EOFException();
                }
                secondTrade = setTrade(line2);
            } catch (EOFException e) {
                System.out.println("End Of Second File");
                lastFile = "file2";
                break;
            } catch (IOException e) {
                System.out.println(e);
            }

            if (firstTrade.time < secondTrade.time) {
                nextTrade = firstTrade;
                firstFileTradeIndex += 1;
            } else {
                nextTrade = secondTrade;
                secondFileTradeIndex += 1;
            }

            printTrade(nextTrade);
        }
        printLastTrade(lastFile);
        return nextTrade;
    }

    public static void setFiles(String whichFile, String fileName)
    {
        if(whichFile == "f1")
        {
            file1 = fileName;
        }
        else if(whichFile == "f2")
        {
            file2 = fileName;
        }
        else{
            System.out.println("Error: Incorrect file choice");
        }
    }

    private static Trade setTrade(String tradeData)
    {
        Trade tempTrade = new Trade();
        String[] newString = tradeData.split(",");
        tempTrade.time = Double.valueOf(newString[0]);
        tempTrade.symbol = newString[1];
        tempTrade.price = Double.valueOf(newString[2]);

        return tempTrade;
    }
    private static void printLastTrade(String eofTrade)
    {
        int index = determineLastFileIndex(eofTrade);
        {
            try (Stream<String> lines = Files.lines(Paths.get(lastFile))) {
                String line1 = lines.skip(index).findFirst().orElse(null);
                if(line1 == null)
                {
                    throw new EOFException();
                }
                lastTrade = setTrade(line1);
                printTrade(lastTrade);
                System.out.println("End Of All Files");
            } catch (EOFException e) {
                System.out.println(e);
            } catch (IOException e) {
                System.out.println(e);
            }
        }

    }

    private static int determineLastFileIndex(String file)
    {
        int finalIndex =0;
        if(file == "file2")
        {
            finalIndex = firstFileTradeIndex;
            lastFile = file1;
        }
        else if(file == "file1")
        {
            finalIndex = secondFileTradeIndex;
            lastFile = file2;
        }
        else{
            System.out.println("Error: Incorrect file choice");
        }

        return finalIndex;
    }

    private static void printTrade(Trade trade)
    {
        System.out.println("Next Trade is:  " + trade.symbol + " at: " + trade.time + " for: " + trade.price);
    }

    public static void main(String[] args)
    {

        setFiles("f1", "file1.txt");
        setFiles("f2", "file2.txt");
        whatNext nextTrade = new whatNext();
        Trade lastTrade = nextTrade.next();
    }

}

//trade in multiple markets
//get records in order
//need to read from 2 files at a time
//40gb file. (Large)

//input two readers
//output class that implements reader
//order by time

//two readers as contructors

/*
...
11:29:35.123677,AAPL,169.65
11:29:36.227687,AMZN,1574.95
11:29:36.337681,MSFT,104.89
...

11:29:35.123677,AAPL,169.65
11:29:36.227687,AMZN,1574.95
11:29:36.337681,MSFT,104.89
*/








