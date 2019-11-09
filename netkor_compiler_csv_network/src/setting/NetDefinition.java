package setting;

public class NetDefinition {
	String targetFormat;
	String directedNetwork;
	String bipartiteProjection;
	String networkLayout;
	
	public NetDefinition() {
	}
	
	public void setTargetFormat(String format) {
		this.targetFormat = format;
	}
	
	public void setNetworkLayout(String format) {
		this.networkLayout = format;
	}
	
	public void setDirectedNetwork(String bool) {
		this.directedNetwork= bool;
	}
	
	public void setBipartiteProjection(String bool) {
		this.bipartiteProjection = bool;
	}
	
	public NetDefinition(String targetFormat, String directedNetwork ,String bipartiteProjection, String networkLayout) {
		this.targetFormat = targetFormat;
		this.directedNetwork = directedNetwork;
		this.bipartiteProjection = bipartiteProjection;
		this.networkLayout = networkLayout;
	}
}
