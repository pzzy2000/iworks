package cn.oxo.iworks.networks.hclient;

public class NetworkServiceException extends Exception {

    private static final long serialVersionUID = -5750116128452390118L;

    public NetworkServiceException() {

    }

    public NetworkServiceException(String message) {
        super( message );

    }

    public NetworkServiceException(Throwable cause) {
        super( cause );

    }

    public NetworkServiceException(String message, Throwable cause) {
        super( message, cause );

    }

}
