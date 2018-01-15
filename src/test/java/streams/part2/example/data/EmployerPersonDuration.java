package streams.part2.example.data;

public class EmployerPersonDuration {
    private final EmployerPersonPair employerPersonPair;
    private final int duration;

    public EmployerPersonPair getEmployerPersonPair() {
        return employerPersonPair;
    }

    public int getDuration() {
        return duration;
    }

    public EmployerPersonDuration(EmployerPersonPair employerPersonPair, int duration) {

        this.employerPersonPair = employerPersonPair;
        this.duration = duration;
    }
}
