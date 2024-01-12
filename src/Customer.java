public class Customer {
    private int pin;
    private String name;
    public Customer(String name, int pin) {
        this.name = name;
        this.pin = pin;
    }
    public void setPin(int newPin) {
        pin = newPin;
    }
    public String getName() {
        return name;
    }

    public int getPin() {
        return pin;
    }
}
