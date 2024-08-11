package SplitDetector;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class SplitDetector {

    static HashMap<String, Double> parm;
    static List<String> printBill;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        parm = new HashMap<>();
        System.out.println("Enter number of people: ");
        int n = sc.nextInt();
        for (int i = 0; i < n; i++) {
            System.out.println("Enter name: ");
            String name = sc.next();
            System.out.println("Enter amount: ");
            double amt = sc.nextDouble();
            parm.put(name, amt);
        }

        // Passing values to findPath Method.
        SplitDetector(parm);

        // System.out.println(printBill.toString());
        sc.close();
    }

    public static void SplitDetector(HashMap<String, Double> details) {
        printBill = new ArrayList<>();
        Double Max_Value = Collections.max(details.values());
        Double Min_Value = Collections.min(details.values());

        // Ensure we stop recursion when all values are zero or when Max_Value and Min_Value are equal
        if (Max_Value <= 0 || Min_Value >= 0) {
            return;
        }

        String Max_Key = getKeyFromValue(details, Max_Value).toString();
        String Min_Key = getKeyFromValue(details, Min_Value).toString();
        Double result = Max_Value + Min_Value;
        result = round(result, 1);
        if (result >= 0.0) {
            System.out.println(Min_Key + " needs to pay " + Max_Key + ": " + round(Math.abs(Min_Value), 2));
            details.put(Max_Key, result);
            details.put(Min_Key, 0.0);
        } else {
            System.out.println(Min_Key + " needs to pay " + Max_Key + ": " + round(Math.abs(Max_Value), 2));
            details.put(Max_Key, 0.0);
            details.put(Min_Key, result);
        }

        // Recursive call with updated details
        SplitDetector(details);
    }

    public static Object getKeyFromValue(HashMap<String, Double> hm, Double value) {
        for (String o : hm.keySet()) {
            if (hm.get(o).equals(value)) {
                return o;
            }
        }
        return null;
    }

    public static double round(double value, int places) {
        if (places < 0)
            throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
