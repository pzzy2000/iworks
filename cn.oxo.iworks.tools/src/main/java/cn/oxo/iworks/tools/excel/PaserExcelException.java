package cn.oxo.iworks.tools.excel;

public class PaserExcelException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -9044863324853297385L;

    private int code;

    private String msg;

    public PaserExcelException(int code, String message, Throwable cause) {
        super(message, cause);
        this.msg = message;
        this.code = code;

    }

    public PaserExcelException(int code, String message) {
        super(message);
        this.msg = message;
        this.code = code;

    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
