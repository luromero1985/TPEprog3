package tpe;

import java.util.Hashtable;


public class Estado {
	private Hashtable<Procesador, Procesador> solucion;
	private int tiempoEjecucionTotal;


	/* CONSTRUCTOR*/
	public Estado (Hashtable<Procesador, Procesador> asignacion) {
		this.tiempoEjecucionTotal=0;
		this.solucion= new Hashtable<>();
		for (Procesador p : asignacion.keySet()) {
			Procesador aux = p.getCopia();
			solucion.put(aux,aux);
		}

	}

	/*GETERS Y SETERS */ 

	public int getTiempoEjecucionTotal() {
		return this.tiempoEjecucionTotal;
	}

	private void setTiempoEjecucionTotal(int tiempoEjecucion) {
		this.tiempoEjecucionTotal=tiempoEjecucion;
	}
	
	
	/*MÉTODOS IMPLEMENTADOS*/

	public boolean getDiponibilidadCriticas(Procesador p) {
		return this.solucion.get(p).getDisponibilidadTareasCriticas()>0;
	}
	
	public int getTiempoEjecucionProcesador(Procesador p) {
		return this.solucion.get(p).getTiempoEjecucion();
	}
	
	
	
	public void addSolucion(Procesador p, Tarea t) {
		this.solucion.get(p).addTarea(t);
		int tiempoProcesador= this.solucion.get(p).getTiempoEjecucion();
		if(tiempoProcesador>this.getTiempoEjecucionTotal()) {
			this.setTiempoEjecucionTotal(tiempoProcesador);
		}
	}

	public void removeSolucion (Procesador p, Tarea t) {
		int tiempoProcesador= this.solucion.get(p).getTiempoEjecucion();
		this.solucion.get(p).removeTarea(t);	
		if(this.getTiempoEjecucionTotal()==tiempoProcesador) {
			int nuevoTiempo = this.calcularTiempoTotal();
			this.setTiempoEjecucionTotal(nuevoTiempo);
		}
		
	}


	private int calcularTiempoTotal() {
		int tiempoMax = 0;
		for (Procesador p : solucion.keySet()) {
			int totalTime = p.getTiempoEjecucion();
			if (totalTime > tiempoMax) {
				tiempoMax = totalTime;
			}
		}
		return tiempoMax;
			
	}

	
	
	public Hashtable<Procesador, Procesador> getCopia() {
		Hashtable<Procesador, Procesador> copia = new Hashtable<>();
		for (Procesador p : this.solucion.keySet()) {
			Procesador aux = p.getCopia();
			copia.put(aux,aux);
		}
		return copia;
	}
}
