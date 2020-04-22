import java.util.Scanner;

public class Input_Entry {

    public static void main(String[] args) {
        System.out.println("Copy the initials");

        Scanner scanner = new Scanner(System.in);
        String input_states = scanner.nextLine();
        String input_letters = scanner.nextLine();
        int num_of_rules = Integer.parseInt(scanner.nextLine());
        String[][] rules = new String[num_of_rules][3];
        String[] temp = new String[num_of_rules];

        for (int i = 0; i < num_of_rules; i++) {
            temp[i] = scanner.nextLine();
        }

        String input_final_states = scanner.nextLine();

        for (int i = 0; i < num_of_rules; i++) {
            String[] tmp = temp[i].split(",");
            if (tmp.length == 2){
                // if the letter is landa than i put l in it
                String[] test = {tmp[0], tmp[1], "l"};
                rules[i] = test;
            }
            else{
                rules[i] = tmp;
            }
        }

        String[] states = input_states.substring(0, input_states.length() - 1).split(",");
        String[] letters = input_letters.substring(0, input_letters.length() - 1).split(",");
        String[] final_states = input_final_states.substring(0, input_final_states.length() - 1).split(",");

        NFA nfa = new NFA(rules, states, letters, final_states);
        DFA dfa = new DFA(rules, states, letters, final_states);

        while (true) {
            System.out.println("What to do next ? ");
            switch (scanner.nextLine()) {
                case "1":
                case "isAcceptedByNFA":
                    System.out.println("Please enter a string to check");
                    String input_string_nfa = scanner.nextLine();
                    boolean isAcceptedByNFA = nfa.isAcceptedByNFA(input_string_nfa);
                    System.out.println("Is Accepted ? " + isAcceptedByNFA);
                    break;
    //*****************************************************************//
                case "2":
                case "findRegex":
                    nfa.findRegex();
                    break;
    //*****************************************************************//
                case "3":
                case "createEquivalentDFA":
                    nfa.createEquivalentDFA();
                    break;
    //*****************************************************************//
                case "4":
                case "isAcceptedByDFA":
                    System.out.println("Please enter a string to check");
                    String input_string_dfa = scanner.nextLine();
                    boolean isAcceptedByDFA = dfa.isAcceptedByDFA(input_string_dfa);
                    System.out.println("Is Accepted ? " + isAcceptedByDFA);
                    break;
    //*****************************************************************//
                case "5":
                case "makeSimpleDFA":
                    dfa.makeSimpleDFA();
                    break;
    //*****************************************************************//
                case "6":
                case "showNFA":
                    nfa.showNFA();
                    break;
    //*****************************************************************//
                case "7":
                case "showDFA":
                    dfa.showDFA();
                    break;
    //*****************************************************************//
                case "-1":
                    System.out.println("Finished");
                    System.exit(1);
                    break;
    //*****************************************************************//
                default:
                    System.out.println("Please enter a correct command");
                    break;
            }
        }

    }

}
