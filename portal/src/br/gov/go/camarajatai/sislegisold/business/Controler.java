package br.gov.go.camarajatai.sislegisold.business;

import br.gov.go.camarajatai.sislegisold.dao.PostgreConnection;

public class Controler {
	
	private static Controler control = null;
	
    private Controler() { }
    
    private synchronized static void sync() {
    	
    	if (control == null)
    		control = new Controler();
    }
    
    public static Controler getInstance() {
    	
    	if (control == null)
    		sync();
    	
    	return control;    	
    }
    
    
    

}
