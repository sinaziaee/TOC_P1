public class NFA {

    String[][] rules;
    String[] states;
    String[] letters;
    String[] final_states;

    public NFA(String[][] rules, String[] states, String[] letters, String[] final_states) {
        this.rules = rules;
        this.states = states;
        this.letters = letters;
        this.final_states = final_states;
    }

    public boolean isAcceptedByNFA(String input){
        String[] inputs = input.split("");
        String last = states[0];
        for(String each:inputs){

        }
        return false;
    }

    public void findRegex(){

    }

    public void createEquivalentDFA(){

    }

    public void showNFA(){

    }

    public void path(){

    }
}
