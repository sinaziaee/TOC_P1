import java.util.ArrayList;

public class DFA
{
    ArrayList<String> states;
    ArrayList<States> My_States = new ArrayList<>();
    String[] letters;
    ArrayList<String> finals;
    ArrayList<String[]> list;
    String initialState;
    ArrayList<String[]> minimizedDFA = new ArrayList<>();

    int size;

    public DFA (ArrayList<String> states,
                String[] letters,
                ArrayList<String[]> list,
                ArrayList<String> finals,
                String initialState)
    {
        this.states = states;
        this.letters = letters;
        this.list = list;
        this.finals = finals;
        this.size = list.size();
        this.initialState = initialState;

        for(int i = 0 ; i < states.size() ; i++)
        {
            States s = new States(states.get(i));
            My_States.add(s);
        }

        for (int i = 0 ; i < states.size(); i++)
        {
            for(int k = 0 ; k < size ; k++)
            {
                if (My_States.get(i).name == list.get(k)[0])
                {
                    States result = null;

                    for(int index = 0 ; index < states.size() ; index++)
                        if (My_States.get(index).name == list.get(k)[1])
                            result = My_States.get(index);

                    Transitions t = new Transitions(result ,list.get(k)[2] );

                    My_States.get(i).Add_Transition(t);
                }
            }

        }//end this for


    }




    public boolean isAcceptedByDFA(String input)
    {
        String[] inputs = input.split("");
        String last = initialState;
        for(String each:inputs)
        {
            String next_state = path(last, each);
            if (next_state.equals("-1"))
            {
                return false;
            }
            else {
                last = next_state;
                System.out.println(last);
            }
        }
        // now its time to check if the last state is final state or not
        return isFinalState(last);
    }

    public void makeSimpleDFA()
    {

        System.out.println("********************starting to minimize DFA*********************");
        //remove trap states
        for(int i = 0 ; i < states.size() ; i++)
        {
            boolean flag = false; int j;

            for( j = 0 ; j < size ; j++)
            {
                if (i == j)
                    continue;

                if(My_States.get(i).name == list.get(j)[0])
                { flag = true; break;}
            }

            if (!flag)
            {
                My_States.remove(i);
                list.remove(j);
            }
        }

        int flag = 0 , j;
        for(int i = 0 ; i < My_States.size(); i++)
        {
            for( j = 0 ; j < My_States.size();j++)
            {
                if (i == j)
                    continue;

                for(int x = 0 ; x  < letters.length ; x++)
                {
                    if(My_States.get(i).My_transitions !=
                            My_States.get(j).My_transitions)
                        break;

                    else
                        flag++;
                }
            }

            if (flag == letters.length)
                My_States.remove(j);
        }


        for (States each : My_States){
            ArrayList<Transitions> transitions = each.My_transitions;
            for (Transitions tmp : transitions){
                    if (tmp.next == null){
                        System.out.println(each.name + " " + null + " " + tmp.letter);
                    }
                    else{
                        System.out.println(each.name + " " + tmp.next.name + " " + tmp.letter);
                    }
            }
        }

        System.out.println("*********************** Finished minimising *****************************");

        for (States each : My_States){
            ArrayList<Transitions> transitions = each.My_transitions;
            for (Transitions tmp : transitions){
                if (tmp.next != null){
                    String[] temp = {each.name, tmp.next.name, tmp.letter};
                    minimizedDFA.add(temp);
                    System.out.println(each.name + " " + tmp.next.name + " " + tmp.letter);
                }
            }
        }

    }

    public void showDFA()
    {
        for (String[] each : list)
            System.out.println(each[0] + " " + each[1] + " " + each[2]);
    }

    public String path(String state, String letter)
    {
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


    public boolean isFinalState(String state)
    {
        for (String each : finals)
        {
            if (each.equals(state))
            {
                return true;
            }
        }
        return false;
    }

}
