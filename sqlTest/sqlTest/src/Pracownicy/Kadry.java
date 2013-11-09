package Pracownicy;

import SQL.DB;

public class Kadry {
	DB db;
	public Kadry() {
		db = new DB("mysql.agh.edu.pl", "balon", "balon", "rrDmk13d");
	}
	public void addPracownik(Pracownik p) {
		char typ = p.getClass().getName().equals("Pracownicy.Student") ? 'S' : 'P';
		db.addPracownik(p.pesel, p.wynagrodzenieBrutto, typ);
	}
	public void delPracownik(Pracownik p) {
		db.delPracownik(p.pesel);
	}
	public Pracownik findPracownik(String pesel) {
		
	}
	//chgWynBr();
	//getWynBr();
	//getWynNt();
}
