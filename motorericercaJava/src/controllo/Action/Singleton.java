/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllo.Action;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author Daniele Palladino
 * @author Adriano Bellia
 */

public class Singleton {

	private double DELAY; // 24 ore

	private static Singleton instance;

	private Calendar lastUpdate;
	private IndexThread indexThread;

	private Singleton() {
            this.indexThread = new IndexThread();
	}

	/**
         * 
         * @return
         */
        public static Singleton getInstance() {
		if (instance == null)
			instance = new Singleton();
		return instance;
	}

        /**
         * 
         * @param delay
         */
        public void setDelay(double delay){
            this.DELAY = delay*60*60*1000;
        }

        /**
         * 
         * @return
         */
        public double getDelay(){
            return this.DELAY;
        }

        
	/**
         * aggiungere controllo di esistenza e ultima modifica su file di indice 
         */
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

	/**
         * 
         * @return
         */
        private boolean timeElapsed() {
		double d = new GregorianCalendar().getTimeInMillis() - this.lastUpdate.getTimeInMillis();
		return d > Singleton.getInstance().getDelay();
	}

	/**
         * 
         * @return
         */
        private boolean inExecution() {
		// controlla che l'aggiornamento sia in esecuzione
		return this.indexThread.getInExecution();
	}

	
}