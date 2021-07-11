package cn.oxo.iworks.web.controller.bind;

public interface IVerificationfilter {
	
	    public <V> boolean verification(String filterKey,V bean)throws VerificationException;

	    
}
