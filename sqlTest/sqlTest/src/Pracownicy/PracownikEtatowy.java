package Pracownicy;

public class PracownikEtatowy extends Pracownik {
	public PracownikEtatowy(String pesel, double wynB) {
		this.pesel = pesel;
		this.wynagrodzenieBrutto = wynB;
	}
	public double obliczWynagrodzenieNetto() {
		return this.wynagrodzenieBrutto/1.23;
	}
}
