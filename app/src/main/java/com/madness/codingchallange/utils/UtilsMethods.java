package com.madness.codingchallange.utils;

import com.madness.codingchallange.utils.data_object.GenrePojo;

import java.util.ArrayList;

/**
 * Utility class to avoid method duplication
 */
public class UtilsMethods {

    public static String setGenre(Integer[] ids, ArrayList<GenrePojo> pojo){
        StringBuilder genre = new StringBuilder();
        String finalMsg = "";
        for(int i = 0; i < ids.length; i++){
            for(GenrePojo data : pojo){
                if(data.getId().equals(ids[i])){
                    genre.append(data.getName());
                    genre.append(", ");
                }
            }
        }
        if(genre.toString().endsWith(", ")){
            finalMsg = genre.substring(0, genre.length() - 2);
        }
        return "Genre: " + finalMsg;
    }

    public static String setGenreNoConcat(Integer[] ids, ArrayList<GenrePojo> pojo){
        StringBuilder genre = new StringBuilder();
        String finalMsg = "";

        if(pojo.isEmpty()){
            return "Missing text";
        }

        for (Integer id : ids) {
            for (GenrePojo data : pojo) {
                if (data.getId().equals(id)) {
                    genre.append(data.getName());
                    genre.append(", ");
                }
            }
        }
        if(genre.toString().endsWith(", ")){
            finalMsg = genre.substring(0, genre.length() - 2);
        }
        return finalMsg;
    }

    public static String setReleaseDate(String releaseDate){
        if(releaseDate.isEmpty()) {
            return "Missing release date!";
        }else{
            return "Release date: " + releaseDate;
        }
    }

    public static String setReleaseDateNoConcat(String releaseDate){
        if(releaseDate.isEmpty()) {
            return "Missing release date!";
        }else{
            return releaseDate;
        }
    }
}
