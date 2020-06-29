package socket.util;

import java.util.ArrayList;
import java.util.List;

public class CalculatorUtil {

    public static int cal(String s) {
        char[] strChar = s.toCharArray();
        List<String> stack = new ArrayList<>();
        List<String> calSig = new ArrayList<>();

        for(int i = 0 ; i < strChar.length ; i++) {
            if(strChar[i] == ' ')
                continue;

            StringBuilder sb = new StringBuilder();
            while((strChar[i] >= '0' && strChar[i] <= '9') || strChar[i] == ' '){
                if(strChar[i] != ' '){
                    sb.append(strChar[i]);
                }
                i++;
                if(i >= strChar.length)
                    break;
            }

            if(sb.length() != 0) {
                stack.add(sb.toString());
            }

            if(i >= strChar.length)
                break;

            if(strChar[i] == '+' || strChar[i] == '-' || strChar[i] == '*' || strChar[i] == '/') {
                stack.add(String.valueOf(strChar[i]));
            }
        }

        int a,b;
        a=b=0;
        String cal = "";
        int len = stack.size();
        int[] numNew = new int[(len+1)/2];
        int k = -1;
        boolean flag = false;

        for(int i = 0 ; i < len ; i++) {
            if(i % 2 == 0) {
                a = numNew[++k] = Integer.parseInt(stack.get(i));
                //flag = true;
            }
            else {
                cal = stack.get(i);
                if(cal.equals("+") || cal.equals("-"))
                    calSig.add(cal);
                else{

                    b = Integer.parseInt(stack.get(++i));
                    if(cal.equals("*")){
                        a *= b;
                    }else{
                        a /= b;
                    }

                    numNew[k] = a;
//                    calSig.add("+");

                    //flag = false;
                }

            }
        }

        if(numNew.length != 0) {
            a = numNew[0];

            for(int i = 0 ; i < calSig.size(); i++) {
                if(calSig.get(i).equals("+")){
                    a += numNew[i+1];
                }else{
                    a -= numNew[i+1];
                }
            }
        }

        return a;
    }
}
