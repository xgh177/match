package match.data.parse;


public abstract class Parse {
	/***
	 * 数据url
	 */
	private String dataUrl;
	
    public Parse(){
		
		
	}
	
	public Parse(String dataUrl){
		
		this.dataUrl = dataUrl;
	}
	
	public abstract Object parse();


	public String getDataUrl() {
		return dataUrl;
	}


	/***
	 * 设置数据源的url
	 * @param dataUrl
	 */
	public void setDataUrl(String dataUrl) {
		this.dataUrl = dataUrl;
	}
	
	


}
