package cn.oxo.iworks.networks.hclient;

public class ByteHttpRequestResult extends HttpRequestResult {

    private byte[] responseBtye;

    public byte[] getResponseBtye() {
        return responseBtye;
    }

    protected void setResponseBtye(byte[] responseBtye) {
        this.responseBtye = responseBtye;
    }

}
