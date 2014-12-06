/* Sviluppato da Matteo Piccinini */

package dbmanager;

public class DbManager {

    public DbManager(){

    }
    
    public void start(){
		Master master = new Master();

        //Nel frattempo inizializzo il report
        Thread t = new Thread(){
			public void run(){
				org.jfree.report.Boot.start();
			}
		};
		t.start();    	
    }

    public static void main(String[]args){
		new DbManager().start();
    }
}
