package waage;

/**
 * Coding Challenge
 * Teile ein Gewicht (40kg) in 4 Teile so dass auf einer Balkenwaage jede ganzzahlige Last zwischen 1kg und 40kg mit diesen 4 Teilen abgewogen werden kann.
 * 
 * @author Christian Binder
 * @version 1.0
 *
 */

/*
 * Anmerkung:
 * Um den Code möglich kurz zu halten verwende ich hier for-Schleifen mit Label und beaks. 
 * Unter normalen Umständen würde ich dies wohl eher mit Hife von while-Schleifen realisieren.
 * Da ich hierfür zusätzliche Abbruch-Variablen deklarieren müsste halte ich meine Vorgehensweise zugunsten der Übersichtlichkeit in diesem Fall für vertretbar.
 */
public class GewichteErmitteln {
	final static int GESAMTGEWICHT = 40;
	private static int[] gewicht = new int[4];

	public static void main(String[] args) {
		/*
		 * Vorüberlegung:
		 * Es kann immer nur ein einziges Gewicht einen Wert größer oder gleich 20 haben (die Hälfte des Gesamgewichtes), weil alle anderen Gewichte ja mindestens 1 kg betragen.
		 * 40 -1 -1 = 38
		 * 38 / 2 = 19  -> kleiner als 20
		 * 19 + 19 + 1 + 1 = 40
		 * Deshalb brauchen die drei Schleifendurchgänge auch nur bis zur Hälfte des Gesamtgewichtes durchlaufen werden.
		 * Die Gewichts-Werte zwischen 20kg und 37kg werden automatisch der Variable gewicht[3] zugewiesen, da dieses Gewicht abhängig von den anderen Gewichten ist.
		 * Aufgrund des "kommutativen" Charakters der Gewichte ist die Reihenfolge der Gewichte irrelevant und das Durchlaufen einer Variablen ausreichend.
		 */
		berechnung: for(int i=1; i<GESAMTGEWICHT/2; i++)  	// gewicht[0] 
			for(int j=1; j<GESAMTGEWICHT/2; j++)  			// gewicht[1]
				for(int k=1; k<GESAMTGEWICHT/2; k++) { 		// gewicht[2]
					gewicht[0] = i;
					gewicht[1] = j;
					gewicht[2] = k;
					gewicht[3] = GESAMTGEWICHT - (i+j+k); // Die Summe aller Gewichte muss das Gesamtgewicht ergeben
					
					// Prüft ob jede Last gemessen werden kann:
					for(int last=1; last<=GESAMTGEWICHT; last++) {
						if(!pruefeLastRekursiv(last, 0, 0)) break; // sobald eine Last nicht erzielt werden kann wird die Lasten-Schleife verlassen
						// Alle Lasten konnten gemessen werden?:
						if(last == GESAMTGEWICHT) {
							System.out.println("Folgende Gewichte konnten ermittelt werden:");
							gewichteAnzeigen();
							// bricht die Berechnung ab, sobald eine Lösung gefunden wurde:
							break berechnung; // diese Zeile auskommentieren um sämtliche Permutationen angezeigt zu bekommen
						}
					}
				}
		System.out.println("\n<***Ende***>");
	}
	

	
	// Gibt die aktuell gespeicherten Gewichte in der Konsole aus:
	public static void gewichteAnzeigen() {
		for(int index=0; index<gewicht.length; index++) System.out.print("[" + gewicht[index] + " kg] ");
		System.out.println();
	}


	// Legt gewicht[index] auf die linke Seite der Waage
	public static int legeGewichtLinks(int waage, int index) {
		return waage + gewicht[index];
	}

	// Legt gewicht[index] auf die rechte Seite der Waage
	public static int legeGewichtRechts(int waage, int index) {
		return waage - gewicht[index];
	}
	

	/*
	 * Die folgende Methode überprüft, ob eine bestimmte Gewichtslast mit den aktuell gespeicherten Gewichten auf der Waage gewogen werden kann.
	 * Sollte dies der Fall sein wird true zurückgegeben, ansonsten false.
	 * Die Methode geht dabei rekursiv sämtliche Möglichkeiten durch und bricht ab sobald die zu prüfende Last gemessen werden kann oder der Index+1 größer als die Anzahl der Gewichte (4) ist.
	 */
	public static boolean pruefeLastRekursiv(int checkLast, int waage, int index) {
		if(index < gewicht.length) { // alle Gewichte durchiterieren
			if( (legeGewichtLinks(waage, index) == checkLast) || (legeGewichtRechts(waage, index) == checkLast) ) return true; // Last kann gemessen werden :)
			if(pruefeLastRekursiv(checkLast, waage, index+1)) return true; // kein Gewicht auf die Waage legen und mit dem nächsten Gewicht fortfahren
			if(pruefeLastRekursiv(checkLast, legeGewichtLinks(waage, index), index+1)) return true;  // Gewicht auf die linke Seite der Waage legen und mit nächsten Gewicht fortfahren
			if(pruefeLastRekursiv(checkLast, legeGewichtRechts(waage, index), index+1)) return true; // Gewicht auf die rechte Seite der Waage legen und mit nächsten Gewicht fortfahren
		}
		return false; // zu prüfende Last konnte nicht gemessen werden :(
	}
}
