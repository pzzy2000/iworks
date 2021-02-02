package cn.oxo.iworks.networks.hclient;

import org.apache.http.Header;

import cn.oxo.iworks.networks.hclient.IHttpProtocolService.CharEncoded;

public abstract class PostSingleMessageRequest extends IRequest {

    // private String sendMessage;
    /**
     * 是否对发送的内容进行编码
     */
    private boolean contentencoding = true;

    private GetPostHttpRequestResult requestResult;

    public PostSingleMessageRequest(CharEncoded charEncoded, boolean contentencoding) {
        super(charEncoded);
        this.contentencoding = contentencoding;

    }

    public PostSingleMessageRequest() {
        super(CharEncoded.UTF8);
        this.setContentencoding(false);

    }

    public abstract String getSendMessage();

    public GetPostHttpRequestResult getRequestResult() {
        return requestResult;
    }

    public void setRequestResult(GetPostHttpRequestResult requestResult) {
        this.requestResult = requestResult;
    }

    public boolean isContentencoding() {
        return contentencoding;
    }

    public void setContentencoding(boolean contentencoding) {
        this.contentencoding = contentencoding;
    }
    
    @Override
	public String getResponse() {
		
		return this.getRequestResult().getResponse();
	}

	@Override
	public byte[] getResponseByte() {
		
		return this.getRequestResult().getResponseByte();
	}

	@Override
	public Header[] getResponseHeaders() {
		// TODO Auto-generated method stub
		return requestResult.getHeaders();
	}

	@Override
	public int getHttpCode() {
		// TODO Auto-generated method stub
		return requestResult.getHttpCode();
	}

}
