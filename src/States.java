import java.util.ArrayList;

public class States
{
    public  ArrayList<Transitions> My_transitions;
    public  String name;

    public States(String name)
    {
        this.name = name;
        My_transitions = new ArrayList<>();
//        My_transitions = t;
    }

    public void Add_Transition(Transitions t)
    {
        this.My_transitions.add(t);
    }


}
