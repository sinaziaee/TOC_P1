public class NFA {

    String[][] rules;
    String[] states;
    String[] letters;
    String[] final_states;

    int size;

    public NFA(String[][] rules, String[] states, String[] letters, String[] final_states) {
        this.rules = rules;
        this.states = states;
        this.letters = letters;
        this.final_states = final_states;
        this.size = rules.length;
    }

    public boolean isAcceptedByNFA(String input){
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

    public void findRegex(){

    }

    public void createEquivalentDFA(){

    }

    public void showNFA(){

    }

    public String path(String state, String letter){
        boolean hasLanda = false;
        String result = "";
        int count = 0;
        for(int i=0;i<size;i++){
            if(rules[i][0].equals(state)){
                if(rules[i][2].equals(letter)){
                    count++;
                    if (count == 1){
                        result += rules[i][1];
                    }
                    else{
                        // more than two states with same letter
                        result += rules[i][1] + ",";
                    }
                }
                else if(rules[i][2].equals("l")){
                    hasLanda = true;
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
            if (hasLanda){
                checkLandaAsWell();
            }
            return "-1";
        }
    }

    public boolean isFinalState(String state){
        for (String each : final_states){
            if (each.equals(state)){
                return true;
            }
        }
        return false;
    }

    public void checkLandaAsWell(){

    }

}
