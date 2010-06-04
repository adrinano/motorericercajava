/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package persistence;

/**
 *
 * @author palla
 */
public class StorageException extends Exception{

	public StorageException(String s){

		super(s);
		}

		public StorageException(){

		super();
		}

		public StorageException(Exception e){

			super(e);
			}
		public StorageException(String s, Exception e){

			super(s, e);
			}


}
