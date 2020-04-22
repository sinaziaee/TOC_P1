import java.util.ArrayList;

public class NFA {

    String[] states;
    String[] letters;
    ArrayList<String> finals;
    ArrayList<String[]> list;
    ArrayList<String[]> nfa_transition_table;
    boolean[] checkList;

    public NFA(String[] states, String[] letters, ArrayList<String[]> list, ArrayList<String> finals) {
        this.states = states;
        this.letters = letters;
        this.list = list;
        this.finals = finals;
        this.checkList = new boolean[states.length];
        eliminateLambda();
    }

    public boolean isAcceptedByNFA(String input) {
        String[] inputs = input.split("");
        String last = states[0];
        for (String each : inputs) {
            String next_state = findingNextState(last, each);
            if (next_state.equals("-1")) {
                return false;
            } else {
                if (!next_state.contains(",")) {
                    last = next_state;
                } else {
                    // more that two states with same letter so do more code
                }
            }
        }
        // now its time to check if the last state is final state or not
        boolean result = isFinalState(last);

        return result;
    }

    public void findRegex() {

    }

    public void createEquivalentDFA() {
        // first we should find the complete nfa transition table
        completeNfaTransitionTable();
        // now we have to convert the nfa transition table to dfa transition table
        convertNfaToDfa();
    }

    public void convertNfaToDfa() {

    }

    public void completeNfaTransitionTable() {
        nfa_transition_table = new ArrayList<>();
        for (int i=0;i<list.size();i++){
            String cur_state = list.get(i)[0];
            //***********   filling checkList   ***************//
            for (int j=0;j<states.length;j++){
                if(cur_state.equals(states[j])){
                    if (!checkList[j]){
                        checkList[j] = true;
                    }
                    else{
                        // stay checked
                    }
                }
                else{
                    // pass
                }
            }
            //*************************************************//
            for (String each : letters){
                if (searchInList(cur_state, each)){
                    // this transition has already been saved
                }
                else{
                    // find the next state/states
                    String result = findingNextState(cur_state, each);
                    if (result.equals("-1")){
                        String[] tmp = {cur_state, each, "T"};
                        nfa_transition_table.add(tmp);
                    }
                    else{
                        String[] tmp = {cur_state, each, result};
                        nfa_transition_table.add(tmp);
                    }
                }
            }
        }

        // don't forget the states without transition
        for (int i=0;i<checkList.length;i++){
            if (!checkList[i]){
                for (String each : letters){
                    String[] tmp = {states[i],each,"T"};
                    nfa_transition_table.add(tmp);
                }
            }
            else{
                // it's already in the table
            }
        }

        for (String[] each : nfa_transition_table){
            System.out.println(each[0] + " " + each[1] + " " + each[2]);
        }
    }

    public boolean searchInList(String state, String letter){
        for (String[] each : nfa_transition_table){
            if(each[0].equals(state) && each[1].equals(letter)){
                return true;
            }
        }
        return false;
    }

    public void showNFA() {
        for (String[] each : list) {
            System.out.println(each[0] + " " + each[1] + " " + each[2]);
        }
    }

    public String findingNextState(String state, String letter) {
        String result = "";
        int count = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i)[0].equals(state)) {
                if (list.get(i)[2].equals(letter)) {
                    count++;
                    if (count == 1) {
                        result += list.get(i)[1];
                    }
                    else {
                        // more than two states with same letter
                        result += "," + list.get(i)[1];
                    }
                } else {
                    // next letter in this list
                }
            } else {
                // next rule
            }
        }
        if (result.length() != 0) {
            return result;
        } else {
            return "-1";
        }
    }

    public boolean isFinalState(String state) {
        for (String each : finals) {
            if (each.equals(state)) {
                return true;
            }
        }
        return false;
    }

    public void eliminateLambda() {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i)[2].equals("l")) {
                // find the next states transitions and add them to previous states transitions
                changingNextStatesOfLambdaTransition(list.get(i)[1], list.get(i)[0]);
                // removing the lambdaTransition
                list.remove(i);
            }
        }
    }

    public void changingNextStatesOfLambdaTransition(String nextState, String previousState) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i)[0].equals(nextState)) {
                if (!isFinalState(nextState)) {
                    // nothing to do now
                } else {
                    finals.add(previousState);
                }
                String[] temp = {previousState, list.get(i)[1], list.get(i)[2]};
                list.add(temp);
            }
        }
    }

}
