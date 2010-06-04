/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllo.Action;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Singleton {

	private static double DELAY = 24*60*60*1000; // 24 ore

	private static Singleton instance;

	private Calendar lastUpdate;
	private IndexThread indexThread;

	private Singleton() {
		this.indexThread = new IndexThread();
	}

	public static Singleton getInstance() {
		if (instance == null)
			instance = new Singleton();
		return instance;
	}



        //aggiungere controllo di esistenza e ultima modifica su file di indice
	public void checkUpdate() {
		if (this.lastUpdate == null || (!this.inExecution() && this.timeElapsed()))
			this.startUpdate();
	}

	private void startUpdate() {
		this.lastUpdate = new GregorianCalendar();
		// lancia il thread che aggiorna
		Thread t = new Thread(indexThread);
		t.start();
	}

	private boolean timeElapsed() {
		double d = new GregorianCalendar().getTimeInMillis() - this.lastUpdate.getTimeInMillis();
		return d > Singleton.DELAY;
	}

	private boolean inExecution() {
		// controlla che l'aggiornamento sia in esecuzione
		return this.indexThread.getInExecution();
	}

	
}