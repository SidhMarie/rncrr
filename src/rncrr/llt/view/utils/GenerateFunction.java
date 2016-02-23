package rncrr.llt.view.utils;

import javafx.scene.control.ChoiceBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Sidh on 22.02.2016.
 * This class to generate points for various functions
 */
public class GenerateFunction {

    public List<ChoiceBox> doListWindows(List<ChoiceBox> oldList, ChoiceBox window) {
        List<ChoiceBox> newList = new ArrayList<>();
        for(ChoiceBox old : oldList){
            if(!Objects.equals(old.getValue(), window.getValue())){
                newList.add(old);
            }
        }
        newList.add(window);
        return newList;
    }

    public List<String> doListWindows(List<String> oldList, String window) {
        List<String> newList = new ArrayList<>();
        for(String old : oldList){
            if(!Objects.equals(old, window)){
                newList.add(old);
            }
        }
        newList.add(window);
        return newList;
    }

    public static void main(String[] args) {
        List<String> oldList = new ArrayList<>();
//        oldList.add("RECTANGULAR");
        oldList.add("GAUSS");
//        oldList.add("HAMMING");
//        oldList.add("HANN");
        oldList.add("BLACKMAN_HARRIS");

        GenerateFunction gf = new GenerateFunction();
        List<String> newList = gf.doListWindows(oldList, "HAMMING");
        for(String s : newList){
            System.out.println(s);
        }

    }
}
