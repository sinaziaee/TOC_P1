public class Transitions
{
    States next;
    String letter;

    public Transitions(States next_state , String My_letter)
    {
        this.letter = My_letter;
        this.next = next_state;
    }
}
