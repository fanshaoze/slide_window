package CreateTimeRangeTestCase;

import java.util.ArrayList;

/**
 * Created by eason on 2017/3/3.
 */
public class Test {

    public static void main(String[] args) {
        ArrayList<String> lista = new ArrayList<>();
        ArrayList<String> listb = new ArrayList<>();
        lista.add("a");lista.add("b");lista.add("c");
        listb.add("a");listb.add("d");listb.add("e");

        lista.retainAll(listb);

        System.out.println(lista);


    }
}
