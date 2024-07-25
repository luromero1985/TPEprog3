package tpe;


public class Main {

	public static void main(String args[]) {
		Servicios servicios = new Servicios("./src/tpe/datasets/Procesadores.csv", "./src/tpe/datasets/Tareas.csv");
		 // Solución usando backtracking
        servicios.backtracking(10);
        
        System.out.println("Solución usando Backtracking:");
        System.out.println(servicios);
		
		 // Solución usando el algoritmo greedy
        servicios.greedy(10);
        
        System.out.println("Solución usando Greedy:");
        System.out.println(servicios);
       
	}
       
	
}
