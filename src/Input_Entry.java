import java.util.Scanner;

public class Input_Entry {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input_states = scanner.nextLine();
        String input_letters = scanner.nextLine();
        int num_of_rules = scanner.nextInt();
        String[][] rules = new String[num_of_rules][3];
        for(int i=0;i<num_of_rules;i++){
            String line = scanner.nextLine();
            rules[i] = line.split(",");
        }
        String input_final_states = scanner.nextLine();

        String[] states = input_states.substring(0, input_states.length()-1).split(",");
        String[] letters = input_letters.substring(0, input_letters.length()-1).split(",");
        String[] final_states = input_final_states.substring(0, input_final_states.length()-1).split(",");


    }

}
