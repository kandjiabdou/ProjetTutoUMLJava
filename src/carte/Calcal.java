package carte;

import java.util.ArrayList;
public class Calcal {

	public static void main(String[] args) {
		ArrayList<Double> tab= new ArrayList<Double>();
		for(int i=1;i<10000;i++) {
			tab.add((double)i);
			if(i<tab.size()) System.out.println(tab.get(i));
		}
		
		tab.add(Math.sqrt(Math.sqrt(2)*3));
		
		
		for(int k=0; k<tab.size()-1;k++) {
			double b = Math.sqrt(2)*k;
			//sSystem.out.println(b);
			if(b==tab.get(k)) {
				System.out.println("Le coté "+k+" et "+tab.get(k));
			}
		}
		System.out.println(" Fin ");

	}

}
