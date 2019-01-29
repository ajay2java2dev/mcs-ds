package ComputerExercise;

public class Keyboard extends ComputerPeripheral{

    public Keyboard(Computer computer) {
        super(computer);
    }

    @Override
    public String run() {
        return super.run();
    }

    public String getDescription() {
        return super.getDescription() + " with keyboard";
    }
}
