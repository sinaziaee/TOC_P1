import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class NFA {

    ArrayList<ArrayList<String[]>> multipleGraph = new ArrayList<>();

    String[] states;
    String[] letters;
    ArrayList<String> finals;
    ArrayList<String[]> list;
    ArrayList<String[]> nfa_transition_table;
    ArrayList<String[]> dfa_transition_table;
    boolean[] checkList;
    HashMap<String, Boolean> isVisitedMap = new HashMap<>();
    HashMap<String, ArrayList<String[]>> nfa_map;
    HashMap<String, ArrayList<String[]>> dfa_map;

    ArrayList<String> has_already_been_in_queue = new ArrayList<>();

    Queue<String> queue = new LinkedList<>();

    public NFA(String[] states, String[] letters, ArrayList<String[]> list, ArrayList<String> finals) {
        this.states = states;
        this.letters = letters;
        this.list = list;
        this.finals = finals;
        this.checkList = new boolean[states.length];
        eliminateLambda();
    }

    public boolean isAcceptedByNFA(String input) {
        String[] inputs;
        if (input.length() > 1){
            inputs = input.split("");
        }
        else{
            String[] tmp = {input};
            inputs = tmp;
        }
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
                    String[]
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
        makeNfaMap();
        // now we have to convert the nfa transition table to dfa transition table
        convertNfaToDfa();
    }

    private void makeNfaMap() {
        nfa_map = new HashMap<>();

        for (int i=0;i<states.length;i++){
            ArrayList<String[]> tempList = new ArrayList<>();
            for (String[] each : nfa_transition_table){
                if (each[0].equals(states[i])){
                    String[] temp = {each[1],each[2]};
                    tempList.add(temp);
                }
            }
            nfa_map.put(states[i], tempList);
        }

        System.out.println("*********************" + " map " + "********************");
        for (String key : nfa_map.keySet()){
            System.out.println(key + " :");
            for (String[] each : nfa_map.get(key)){
                for (String x : each){
                    System.out.print(x + " ");
                }
                System.out.print("| ");
            }
            System.out.println();
        }

    }

    public void convertNfaToDfa() {
        dfa_map = new HashMap<>();
        // adding T to states map **//
        ArrayList<String[]> tmp = new ArrayList<>();
        for (String letter : letters){
            String[] temp = {letter, "T"};
            tmp.add(temp);
        }
        nfa_map.put("T", tmp);
        //**************************//

        String state = states[0];
        isVisited(state);
        // adding initial state transitions to dfa map
        for (String key : nfa_map.keySet()){
            if (key.equals(state)){
                ArrayList<String[]> temp = nfa_map.get(key);
                dfa_map.put(key, temp);
                for (String[] each : temp){
                    if (!each[1].equals(state)){
                        queue.add(each[1]);
                        has_already_been_in_queue.add(each[1]);
                    }
                }
            }
        }
        while (queue.size() != 0){
            state = queue.peek();
            // if state is visited there is no need to add it to the dfa table
            if (isVisited(state)){
                // pass
            }
            // else if it is not visited we add it to transition table
            else {
                // if it is a new state then we're gonna find the union but remember you have to check the queue not nfa transition in isNewState
                if (isNewState(state)){
                    ArrayList<String[]> help = new ArrayList<>();
                    for (String each : letters){
                        String res = findUnion(state, each);

                        // check in queue or not
                        boolean flag = false;
                        for (String x : has_already_been_in_queue){
                            if (x.contains(res)){
                                flag = true;
                                break;
                            }
                        }
                        if (flag){

                        }
                        else{
                            queue.add(res);
                            has_already_been_in_queue.add(res);
                        }
                        String[] temp = {each, res};
                        help.add(temp);
                    }
                    dfa_map.put(state, help);
                    queue.remove();
                }
                // else if it is not a new state the we just add it to the table
                else{//                    dfa_map.put(state, nfa_map.get(state));

                    ArrayList<String[]> help = nfa_map.get(state);
                    ArrayList<String[]> arr = new ArrayList<>();
                    for (String[] each : help){
                        String res = checkDfaForSuperSub(each[1]);
                        String[] temp = {each[0], res};
                        arr.add(temp);
                    }
                    dfa_map.put(state, arr);
                    // please don't forget the ones that are not visited and are not in a new state
                    ArrayList<String[]> temp = nfa_map.get(state);
                    for (String[] each : temp){
                        boolean flag = false;
                        for (String x : has_already_been_in_queue) {
                            if (x.contains(each[1])) {
                                flag = true;
                                break;
                            }
                        }
                        if (flag) {
                            // nothing
                        } else {
                            queue.add(each[1]);
                            has_already_been_in_queue.add(each[1]);
                        }
                    }
                    queue.remove();
                }
            }
        }

        System.out.println("*********************" + " dfa map " + "********************");
        for (String key : dfa_map.keySet()){
            System.out.println(key + " :");
            for (String[] each : dfa_map.get(key)){
                for (String x : each){
                    System.out.print(x + " ");
                }
                System.out.print("| ");
            }
            System.out.println();
        }

    }

    private String checkDfaForSuperSub(String state) {
        for (String key : dfa_map.keySet()){
            if (key.contains(state)){
                return key;
            }
        }
        return state;
    }

    public String findFromNfaTransitionTable(String state, String letter){
        String result = "";
        int count = 0;
        for (int i=0 ; i<nfa_transition_table.size();i++){
            if (nfa_transition_table.get(i)[0].equals(state)) {
                if (nfa_transition_table.get(i)[1].equals(letter)) {
                    count++;
                    if (count == 1) {
                        result += nfa_transition_table.get(i)[2];
                    }
                    else {
                        // more than two states with same letter
                        result += "," + nfa_transition_table.get(i)[2];
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

    public String findUnion(String states, String letter){

        String result = "";
        ArrayList<String> all = new ArrayList<>();
        String[] help = states.split(",");
        for (String state : help) {
            for (String key : nfa_map.keySet()) {
                if (key.equals(state)) {
                    ArrayList<String[]> temp = nfa_map.get(key);
                    for (String[] each : temp) {
                        if (each[0].equals(letter)) {
                            String next_state = each[1];
                            if (next_state.contains(",")) {
                                String[] next_states = next_state.split(",");
                                for (String x : next_states) {
                                    if (!all.contains(x)) {
                                        all.add(x);
                                    }
                                }
                            } else {
                                if (!all.contains(next_state)) {
                                    all.add(next_state);
                                }
                            }
                        }
                    }
                }
            }
        }

        for (String each : all){
            if (!each.equals("T") && all.size() != 1) {
                result += each + ",";
            }
        }
        result = result.substring(0, result.length()-1);

        // check if is in queue or not
        if (!queue.contains(result)) {
            boolean flag = false;
            System.out.println("list checker : --> ");
            for (String x : has_already_been_in_queue) {
                System.out.print(x + " - ");
                if (x.contains(result)) {
                    flag = true;
                    break;
                }
            }
            System.out.println();
            if (flag) {
                // nothing
            } else {
                queue.add(result);
                has_already_been_in_queue.add(result);
            }
        }

        return result;
    }

    public boolean isVisited(String state){
        if (!isVisitedMap.containsKey(state)){
            isVisitedMap.put(state, true);
            return false;
        }
        else {
            return true;
        }
    }

    public boolean isNewState(String state){
        for (String[] each : nfa_transition_table){
            if (state.equals(each[0])){
                return false;
            }
        }
        return true;
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

        for (String each : letters){
            String[] tmp = {"T", each, "T"};
            nfa_transition_table.add(tmp);
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
