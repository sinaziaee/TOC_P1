import java.util.ArrayList;

public class DFA {

    String[] states;
    String[] letters;
    ArrayList<String> finals;
    ArrayList<String[]> list;

    int size;

    public DFA(String[] states, String[] letters, ArrayList<String[]> list, ArrayList<String> finals) {
        this.states = states;
        this.letters = letters;
        this.list = list;
        this.finals = finals;
        this.size = list.size();
    }

    public boolean isAcceptedByDFA(String input){
        String[] inputs = input.split("");
        String last = states[0];
        for(String each:inputs){
            String next_state = path(last, each);
            if (next_state.equals("-1")){
                return false;
            }
            else{
                if (!next_state.contains(",")){
                    last = next_state;
                }
                else{
                    // more that two states with same letter so do more code
                }
            }
        }
        // now its time to check if the last state is final state or not
        boolean result = isFinalState(last);

        return result;
    }

    public void makeSimpleDFA(){

    }

    public void showDFA(){
        for (String[] each : list){
            System.out.println(each[0] + " " + each[1] + " " + each[2]);
        }
    }

    public String path(String state, String letter){
        String result = "";
        int count = 0;
        for(int i=0;i<size;i++){
            if(list.get(i)[0].equals(state)){
                if(list.get(i)[2].equals(letter)){
                    count++;
                    if (count == 1){
                        result += list.get(i)[1];
                    }
                    else{
                        // more than two states with same letter
                        result += list.get(i)[1] + ",";
                    }
                }
                else{
                    //next letter in this rule
                }
            }
            else{
                // next rule
            }
        }
        if (result.length() != 0){
            return result;
        }
        else{
            return "-1";
        }
    }

    public boolean isFinalState(String state){
        for (String each : finals){
            if (each.equals(state)){
                return true;
            }
        }
        return false;
    }

}
