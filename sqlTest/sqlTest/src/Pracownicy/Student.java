package Pracownicy;

public class Student extends Pracownik {
	public Student(String pesel, double wynB) {
		this.pesel = pesel;
		this.wynagrodzenieBrutto = wynB;
	}
	public double obliczWynagrodzenieNetto() {
		return this.wynagrodzenieBrutto/1.23;
	}
}
