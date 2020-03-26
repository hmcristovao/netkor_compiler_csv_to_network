package setting;

public class NetDefinition {
	String targetFormat;
	String sourceFormat;
	String directedNetwork;
	String bipartiteProjection;
	String networkLayout;
	String header;
	String separator;
	
	public NetDefinition() {
	}
	
	public NetDefinition(String targetFormat, String directedNetwork ,String bipartiteProjection, 
						String networkLayout, String header, String sourceFormat, String separator) {
		this.targetFormat = targetFormat;
		this.directedNetwork = directedNetwork;
		this.bipartiteProjection = bipartiteProjection;
		this.networkLayout = networkLayout;
		this.header = header;
		this.sourceFormat = sourceFormat;
		this.separator = separator;
	}
	
	public void setTargetFormat(String format) {
		this.targetFormat = format;
	}
	
	public void setNetworkLayout(String format) {
		this.networkLayout = format;
	}
	
	public void setDirectedNetwork(String bool) {
		this.directedNetwork = bool;
	}
	
	public void setBipartiteProjection(String bool) {
		this.bipartiteProjection = bool;
	}
	
	public void setSourceFormat(String format) {
		this.sourceFormat = format;
	}
	
	public void setHeader(String bool) {
		this.sourceFormat = bool;
	}
	
	public String getHeader() {
		return this.header;
	}
	
	public String getSourceFormat() {
		return this.sourceFormat;
	}
	
	public String getTargetFormat() {
		return this.targetFormat;
	}
	
	public String getNetworkLayout() {
		return this.networkLayout;
	}
	
	public String getDirectedNetwork() {
		return this.directedNetwork;
	}
	
	public String getBipartiteProjection() {
		return this.bipartiteProjection;
	}
	
	public String getSeparator() {
		return this.separator;
	}
}
