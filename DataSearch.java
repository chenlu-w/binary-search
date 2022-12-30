/**
 * Data Search Assignment
 * 
 * @author Chenlu Wang
 * @version 6 Dec 2022
 */

import java.util.ArrayList;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class DataSearch {
    private static String[] seek;
    private static Book[] data_list;
    private static String[] found;

    //insertion sort
    public static void dataSorting(Book[] data_list, int n){
        for(int i = 1; i < n; i++){
            /* insert data_list[i] into a sorted sublist data_list[0..i-1]
                so that data_list[0..i] is sorted. */ 
            Book currentElement = data_list[i];
            int j = i - 1;

            /* move elements of data_lst[0..i-1], that are greater than currentElement
                to one position right of their current position */
            while(j >= 0 && data_list[j].getTitle().compareTo(currentElement.getTitle()) > 0){
                data_list[j + 1] = data_list[j];
                j--;
            }

            //insert the current element into data_list[j + 1]
            data_list[j + 1] = currentElement;
        }
    }
    

    //get the booktitle from the array
    public static void bookTitle(String[] seek){
        for(int i = 0; i < seek.length; i++){
            System.out.println(seek + "!");
            recuriveSearch(data_list, 0, data_list.length-1, seek[i]);
        }
    }


    //binary search for a target in an array
    public static boolean recuriveSearch(Book[] data_list, int low, int high, String target){
        if(low > high){
            return false;
        }
        else{
            int mid = (high + low)/2;
            if(data_list[mid].getTitle().equals(target)){
                return true;
            } 
            else if(data_list[mid].getTitle().toString().compareTo(target) > 0){
                return recuriveSearch(data_list, low, mid-1, target);
            }
            else{
                return recuriveSearch(data_list, mid+1, high, target);
            }
        }
        }
    


    public static void main(String[] args)throws Exception{

        File seek_file = new File(args[0]);
        Scanner sc = new Scanner(seek_file);
        ArrayList<String> seek_values = new ArrayList<String>();

        
        while(sc.hasNextLine()){
            seek_values.add(sc.nextLine());

            seek = new String[seek_values.size()];
            seek = seek_values.toArray(seek);
        }
        sc.close();
        

        File data_file = new File(args[1]);
        Scanner sc1 = new Scanner(data_file);
        ArrayList<Book> data_values = new ArrayList<Book>(); 


        while(sc1.hasNext()){
            String line = sc1.nextLine();
            String[] data = line.split(",");

            String title = data[0];
            String author = data[1];
            int pageCount = Integer.parseInt(data[2]);
            int yearPublished = Integer.parseInt(data[3]);
            String genres = data[4];
            
            Book newBook = new Book(title, author, pageCount, yearPublished, genres);
            data_values.add(newBook);

        }
            data_list = new Book[data_values.size()];
            data_list = data_values.toArray(data_list);

        sc1.close();

        
        PrintWriter outs = new PrintWriter(new FileOutputStream(args[2]));
        
        found = new String[seek.length];
   
        dataSorting(data_list, data_list.length);
        // for(int i = 0; i < data_list.length; i++){
        //     System.out.println(data_list[i]);
        // }

        for(int i = 0; i < seek.length; i++){
            if(recuriveSearch(data_list, 0 , data_list.length-1, seek[i]) == true){
                found[i] = "Found";
                outs.println(seek[i] + ": Yes");
            }
            else{
                found[i] = "Not found";
                outs.println(seek[i] + ": No");
            }
            //System.out.println(found[i]);
        }
    
    
    outs.close();
    }
}

    


